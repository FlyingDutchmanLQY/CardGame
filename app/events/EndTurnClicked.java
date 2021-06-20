package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;

import java.util.Random;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * the end-turn button.
 * 
 * { 
 *   messageType = “endTurnClicked”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class EndTurnClicked implements EventProcessor{

	public static boolean isOver = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		isOver = true;
		gameState.turn++;
		if(gameState.humanPlayer.getHealth() < 0 || gameState.AIPlayer.getHealth() < 0){
			BasicCommands.addPlayer1Notification(out,"game end",2);
		}

		BasicCommands.addPlayer1Notification(out,"It's turn " + gameState.turn,2);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
		//set mana
		gameState.humanPlayer.setMana(gameState.turn);
		BasicCommands.setPlayer1Mana(out,gameState.humanPlayer);

		//draw card

		if(gameState.humanPlayer.cardsInHand.size() <= 6) {
			gameState.humanPlayer.drawACard();
			Card card = gameState.humanPlayer.drawACard();
			BasicCommands.drawCard(out, card, 3, 0);
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
