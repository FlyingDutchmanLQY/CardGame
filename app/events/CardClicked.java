package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a card.
 * The event returns the position in the player's hand the card resides within.
 * 
 * { 
 *   messageType = “cardClicked”
 *   position = <hand index position [1-6]>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class CardClicked implements EventProcessor{

	public static int handPosition = 0;
	public static boolean isCardClicked = false;
	public static ArrayList<Tile> tiles_canSummon = new ArrayList<>();

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		handPosition = message.get("position").asInt();
		isCardClicked = true;
		BasicCommands.addPlayer1Notification(out,"please choose a tile", 3);

		//TODO 修改攻击的范围
		Iterator iterator = gameState.humanPlayer.map_Unit_human.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry entry = (Map.Entry) iterator.next();
			Tile tile = (Tile) entry.getKey();
			int x = tile.getTilex();
			int y = tile.getTiley();
			if(x < 8 && gameState.map_Unit.get(gameState.board[x+1][y]) == null){
				tiles_canSummon.add(gameState.board[x+1][y]);
				BasicCommands.drawTile(out,gameState.board[x+1][y],1);
			}
			if(0 < x && gameState.map_Unit.get(gameState.board[x-1][y]) == null){
				tiles_canSummon.add(gameState.board[x-1][y]);
				BasicCommands.drawTile(out,gameState.board[x-1][y],1);
			}
			if(y < 4 && gameState.map_Unit.get(gameState.board[x][y+1]) == null){
				tiles_canSummon.add(gameState.board[x][y+1]);
				BasicCommands.drawTile(out,gameState.board[x][y+1],1);
			}
			if(0 < y && gameState.map_Unit.get(gameState.board[x][y-1]) == null){
				tiles_canSummon.add(gameState.board[x][y-1]);
				BasicCommands.drawTile(out,gameState.board[x][y-1],1);
			}
		}

		//remove card in hand
//		BasicCommands.deleteCard(out,handPosition);
		
		
	}

}
