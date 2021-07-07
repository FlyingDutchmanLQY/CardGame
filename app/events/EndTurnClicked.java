package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.AIPlayer;
import structures.basic.Card;
import structures.basic.Tile;

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

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		DoEnemyTurn doEnemyTurn = new DoEnemyTurn();
		doEnemyTurn.doAITurn(out,gameState);

//		aiPlayer.deleteCardInView(out);

		gameState.turn++;
		if (gameState.humanPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "Game Over", 2);
		}
		if (gameState.AIPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "Game Over", 2);
		}

		else{
			gameState.resetEventSignals();
			gameState.isCardClicked = false;
			//gameState.isMoveOrAttackUnit = false;
			gameState.isTileClicked = false;
			for(Card card : gameState.humanPlayer.cardsShouldBeRemove){
				gameState.humanPlayer.cardsInPlayerHand.remove(card);
			}
			gameState.humanPlayer.cardsShouldBeRemove.clear();
			BasicCommands.addPlayer1Notification(out, "It's turn " + gameState.turn, 2);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//set mana
			int mana_now = 0;
			if(gameState.turn <= 9) mana_now = gameState.turn + 1;
			else mana_now = 10;
			gameState.humanPlayer.setMana(mana_now);
			gameState.AIPlayer.setMana(mana_now);
			BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);
			BasicCommands.setPlayer2Mana(out,gameState.AIPlayer);

			//drew card
			Card card = gameState.humanPlayer.drawACard(out);
			gameState.humanPlayer.updateHandCardsView(out);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
