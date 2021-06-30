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

	public static int tilex;
	public static int tiley;

	public static boolean isTileClicked = false;
	public static boolean isMoveOrAttackUnit = false;

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
		isTileClicked = true;
		tile_Click = gameState.board[tilex][tiley];
		unit_Click = gameState.map_Unit.get(tile_Click);

		if(CardClicked.isCardClicked) {
			Card card = gameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition -1);
			if(card.getId() == 0){
				card.spellOf01(out,gameState,card,tile_Click, gameState.humanPlayer);
			}else if(card.getId() == 10){
				card.spellOf01(out,gameState,card,tile_Click, gameState.humanPlayer);
			}else if(card.getId() == 1){
				card.spellOf02(out,gameState,card,tile_Click, gameState.humanPlayer);
			}else if(card.getId() == 11){
				card.spellOf02(out,gameState,card,tile_Click, gameState.humanPlayer);
			}
			else{
				Unit unit = gameState.humanPlayer.human_cardToUnit.get(card);
				unit.setPositionByTile(tile_Click);
				gameState.humanPlayer.summonCardToUnit(out, gameState,card, unit,tile_Click, gameState.humanPlayer, CardClicked.handPosition,0);
			}
			CardClicked.isCardClicked = false;
			gameState.humanPlayer.deleteTilesCanSummon(out,gameState.humanPlayer.tiles_canSummon);
		}
		else {
			if (isMoveOrAttackUnit) {
				//move unit
				unit_Move.rangeOfMove(out,gameState,start_move_tile,0);
				if (unit_Click == null) {
					unit_Move.moveUnit(out,gameState,unit_Move,start_move_tile,tile_Click);
					tiles_canMove.clear();
				}
				//attack
				else if (unit_Click != null) {
					unit_Move.attack(out,gameState,start_move_tile,tile_Click);
				}
				index_move = 0;
				isMoveOrAttackUnit = false;
			}

			//TODO 显示move的范围
			if (unit_Click != null && isMoveOrAttackUnit == false && index_move == 1) {
				isMoveOrAttackUnit = true;
				start_move_tile = tile_Click;
				unit_Move = gameState.map_Unit.get(start_move_tile);
				BasicCommands.addPlayer1Notification(out,"Choose Tile Or Unit",3);
				unit_Move.rangeOfMove(out,gameState,start_move_tile,2);
			}

			index_move = 1;
		}

	}

}
