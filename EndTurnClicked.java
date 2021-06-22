package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
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
		GameState.turn++;
		if(GameState.humanPlayer.getHealth() < 0 || GameState.AIPlayer.getHealth() < 0){
			BasicCommands.addPlayer1Notification(out,"game end",2);
		}

		BasicCommands.addPlayer1Notification(out,"It's turn " + GameState.turn,2);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
		//set mana
		GameState.humanPlayer.setMana(GameState.turn);
		BasicCommands.setPlayer1Mana(out,GameState.humanPlayer);

		//drew card

		if(GameState.humanPlayer.cardsInPlayerHand.size() < 5) {
			Random random = new Random();
			Card card = GameState.cards.get(random.nextInt(GameState.cards.size()));
			BasicCommands.drawCard(out, card, GameState.humanPlayer.index_cardInHand++, 0);
			GameState.humanPlayer.cardsInPlayerHand.add(card);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
