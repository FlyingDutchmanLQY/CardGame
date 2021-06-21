package events;


import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Unit;
import utils.BasicObjectBuilders;

import java.util.Random;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a tile.
 * The event returns the x (horizontal) and y (vertical) indices of the tile that was
 * clicked. Tile indices start at 1.
 * 
 * { 
 *   messageType = “tileClicked”
 *   tilex = <x index of the tile>
 *   tiley = <y index of the tile>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class TileClicked implements EventProcessor{

	public static int tilex;
	public static int tiley;
	public static boolean isTileClicked = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		tilex = message.get("tilex").asInt();
		tiley = message.get("tiley").asInt();
		isTileClicked = true;
		if(CardClicked.isCardClicked){
			//remove card in hand
			BasicCommands.deleteCard(out,CardClicked.handPosition);
			Random random = new Random();
			int index = random.nextInt(gameState.unitStaticConfFiles.size());
			Unit unit = BasicObjectBuilders.loadUnit(gameState.unitStaticConfFiles.get(index),1, Unit.class);
			unit.setPositionByTile(gameState.board[tilex][tiley]);
			//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
			BasicCommands.drawUnit(out,unit,gameState.board[tilex][tiley]);
			CardClicked.isCardClicked = false;
		}

//		//remove card in hand
//		BasicCommands.deleteCard(out,CardClicked.handPosition);
//		Unit unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion,1, Unit.class);
//		unit.setPositionByTile(GameState.board[tilex][tiley]);
//		//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
//		BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);

	}
}
