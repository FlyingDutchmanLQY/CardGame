package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
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
	public static boolean isMoveUnit = false;
	public static Unit unit_Move;
	public static int index_move = 1;
	public static Tile start_move_tile;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		tilex = message.get("tilex").asInt();
		tiley = message.get("tiley").asInt();
		isTileClicked = true;

		if(CardClicked.isCardClicked){
			//remove card in hand
			BasicCommands.deleteCard(out,CardClicked.handPosition);
			Random random = new Random();
			int index = random.nextInt(GameState.unitStaticConfFiles.size());
			Unit unit = BasicObjectBuilders.loadUnit(GameState.unitStaticConfFiles.get(index),GameState.id_Unit++, Unit.class);
			unit.setPositionByTile(GameState.board[tilex][tiley]);
			//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
			BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);
			GameState.map_Unit.put(gameState.board[tilex][tiley],unit);
			CardClicked.isCardClicked = false;
		}

		//move unit
		if(isMoveUnit){
			if(GameState.map_Unit.get(GameState.board[tilex][tiley]) == null){
				GameState.map_Unit.remove(start_move_tile);
				BasicCommands.moveUnitToTile(out,unit_Move,GameState.board[tilex][tiley]);
				unit_Move.setPositionByTile(GameState.board[tilex][tiley]);
				GameState.map_Unit.put(GameState.board[tilex][tiley],unit_Move);
				//标志位，1为第一次点击tile，0为第二次点击
				index_move = 0;
				isMoveUnit = false;
			}else if(GameState.map_Unit.get(GameState.board[tilex][tiley]) != null){
				BasicCommands.playUnitAnimation(out,unit_Move, UnitAnimationType.attack);
				GameState.map_Unit.get(GameState.board[tilex][tiley]).health -= unit_Move.attack;
				BasicCommands.setUnitHealth(out,GameState.map_Unit.get(GameState.board[tilex][tiley]),GameState.map_Unit.get(GameState.board[tilex][tiley]).health);
				index_move = 0;
				isMoveUnit = false;
				if(GameState.map_Unit.get(GameState.board[tilex][tiley]).health <= 0){
					BasicCommands.deleteUnit(out,GameState.map_Unit.get(GameState.board[tilex][tiley]));
					GameState.map_Unit.remove(GameState.board[tilex][tiley]);
					BasicCommands.addPlayer1Notification(out,"unit died", 2);
				}
			}
		}

		if(GameState.map_Unit.get(GameState.board[tilex][tiley]) != null && isMoveUnit == false && index_move == 1) {
			isMoveUnit = true;
			unit_Move = GameState.map_Unit.get(GameState.board[tilex][tiley]);
			start_move_tile = GameState.board[tilex][tiley];
//			GameState.map_Unit.remove(GameState.board[tilex][tiley]);
		}

		index_move = 1;

//		//remove card in hand
//		BasicCommands.deleteCard(out,CardClicked.handPosition);
//		Unit unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion,1, Unit.class);
//		unit.setPositionByTile(GameState.board[tilex][tiley]);
//		//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
//		BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);
		
	}

}
