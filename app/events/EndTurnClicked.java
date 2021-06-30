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

	public static boolean isOver = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		BasicCommands.addPlayer1Notification(out,"It's ai",2);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		AIPlayer aiPlayer = gameState.AIPlayer;
		aiPlayer.chooseACard(out);
		aiPlayer.updateHandCardsView(out);
		Card card_ai = aiPlayer.cardsInPlayerHand.get(0);
		if(card_ai.getId() == 20){
			Tile[] tiles = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
			Random random = new Random();
			Tile tile = tiles[random.nextInt(tiles.length)];
			card_ai.spellOf03(out,gameState,card_ai,tile);
		}else if(card_ai.getId() == 21){
			Tile[] tiles = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
			Random random = new Random();
			Tile tile = tiles[random.nextInt(tiles.length)];
			card_ai.spellOf03(out,gameState,card_ai,tile);
		}else{
			aiPlayer.determineTilesCanSummon(out,gameState,aiPlayer.map_Unit_ai);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			Random random = new Random();
			Tile tile = aiPlayer.tiles_canSummon.get(random.nextInt(aiPlayer.tiles_canSummon.size()));
			aiPlayer.summonCardToUnit(out,gameState,card_ai,aiPlayer.ai_cardToUnit.get(card_ai),tile, aiPlayer, 1,1);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			aiPlayer.deleteTilesCanSummon(out,aiPlayer.tiles_canSummon);
		}

		for(Card card : aiPlayer.cardsShouldBeRemove){
			aiPlayer.cardsInPlayerHand.remove(card);
		}
		aiPlayer.cardsShouldBeRemove.clear();

		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

//		aiPlayer.deleteCardInView(out);

		isOver = true;
		gameState.turn++;
		if (gameState.humanPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "Game Over", 2);
		}
		if (gameState.AIPlayer.getHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "Game Over", 2);
		}

		else{
			CardClicked.isCardClicked = false;
			TileClicked.isMoveOrAttackUnit = false;
			TileClicked.isTileClicked = false;
			for(Card card : gameState.humanPlayer.cardsShouldBeRemove){
				gameState.humanPlayer.cardsInPlayerHand.remove(card);
			}
			gameState.humanPlayer.cardsShouldBeRemove.clear();
			BasicCommands.addPlayer1Notification(out, "It's turn " + gameState.turn, 2);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//
			//set mana
			int mana_now = 0;
			if(gameState.turn <= 9) mana_now = gameState.turn + 1;
			else mana_now = 10;
			gameState.humanPlayer.setMana(mana_now);
			gameState.AIPlayer.setMana(mana_now);
			BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);
			BasicCommands.setPlayer2Mana(out,gameState.AIPlayer);

			//drew card
				Card card = gameState.humanPlayer.chooseACard(out);
				gameState.humanPlayer.updateHandCardsView(out);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		}
	}

}
