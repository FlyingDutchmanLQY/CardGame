package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;

import java.util.ArrayList;

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

	public int tilex;
	public int tiley;

	//public static boolean isTileClicked = false;
	//public static boolean isMoveOrAttackUnit = false;

	//标志位，1为第一次点击tile，0为第二次点击
	public static int index_move = 1;

	//开始位置的tile和unit
	public static Tile start_move_tile;
	public static Unit unit_Move;
	//当前位置的tile和unit
	public static Unit unit_Click;
	public static Tile tile_Click;

//	public static ArrayList<Card> cardsShouldBeRemove = new ArrayList<>();
	public static ArrayList<Tile> tiles_canMove = new ArrayList<>();

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		tilex = message.get("tilex").asInt();
		tiley = message.get("tiley").asInt();
		gameState.isTileClicked = true;
		tile_Click = gameState.board[tilex][tiley];
		unit_Click = gameState.map_Unit.get(tile_Click);

		if(unit_Click == null) {
			EmptyTileClicked emptyTileClicked = new EmptyTileClicked();   	//Meaning an empty tile is clicked
			emptyTileClicked.processEvent(out, gameState, message);			//Manually trigger
		}
		else{   //unit_Click != null
			UnitClicked unitClicked = new UnitClicked();					// Meaning a unit is clicked
			unitClicked.processEvent(out, gameState, message);				//Manually trigger
		}
	}

}
