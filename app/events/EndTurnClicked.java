package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;

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
		if (gameState.humanPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "game end", 2);
		}
		if (gameState.AIPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "game end", 2);
		}

		else{
			CardClicked.isCardClicked = false;
			TileClicked.isMoveUnit = false;
			TileClicked.isTileClicked = false;
			for(Card card : TileClicked.cardsShouldBeRemove){
				gameState.humanPlayer.cardsInPlayerHand.remove(card);
			}
			TileClicked.cardsShouldBeRemove.clear();
			BasicCommands.addPlayer1Notification(out, "It's turn " + gameState.turn, 2);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//
			//set mana
			int mana_now = 0;
			if(gameState.turn <= 10) mana_now = gameState.turn;
			else mana_now = 10;
			gameState.humanPlayer.setMana(mana_now);
			BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);

			//drew card

			if (gameState.humanPlayer.cardsInPlayerHand.size() < 5) {
//				Random random = new Random();
//				Card card = GameState.cards.get(random.nextInt(GameState.cards.size()));
//				BasicCommands.drawCard(out, card, GameState.humanPlayer.index_cardInHand++, 0);
//				GameState.humanPlayer.cardsInPlayerHand.add(card);
				Card card = gameState.humanPlayer.chooseACard();
//				GameState.humanPlayer.cardsInPlayerHand.add(card);
				gameState.humanPlayer.updateHandCardsView(out);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
