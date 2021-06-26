package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;

import java.util.Iterator;
import java.util.Map;

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
	public static Unit unit_Click;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		tilex = message.get("tilex").asInt();
		tiley = message.get("tiley").asInt();
		isTileClicked = true;
		unit_Click = GameState.map_Unit.get(GameState.board[tilex][tiley]);

		if(CardClicked.isCardClicked) {
			Card card = GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition -1);
			Unit unit = GameState.humanPlayer.human_cardToUnit.get(card);
			unit.setPositionByTile(GameState.board[tilex][tiley]);
			cardToUnit(out, card, unit);

			CardClicked.tiles_canSummon.clear();
			Iterator iterator = GameState.map_Unit.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry entry = (Map.Entry) iterator.next();
				Tile tile = (Tile) entry.getKey();
				int x = tile.getTilex();
				int y = tile.getTiley();
				if(x < 8)BasicCommands.drawTile(out,GameState.board[x+1][y],0);
				if(0 < x)BasicCommands.drawTile(out,GameState.board[x-1][y],0);
				if(y < 4)BasicCommands.drawTile(out,GameState.board[x][y+1],0);
				if(0 < y)BasicCommands.drawTile(out,GameState.board[x][y-1],0);
			}
		}else {

//		if(CardClicked.isCardClicked && GameState.map_Unit.get(GameState.board[tilex][tiley]) == null){
//			//remove card in hand
//			Card card = GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition - 1);
//			GameState.humanPlayer.cardsInPlayerHand.remove(CardClicked.handPosition - 1);
//			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//			GameState.humanPlayer.updateHandCardsView(out);
////			Random random = new Random();
////			int index = random.nextInt(GameState.unitStaticConfFiles.size());
////			Unit unit = BasicObjectBuilders.loadUnit(GameState.unitStaticConfFiles.get(index),GameState.id_Unit++, Unit.class);
//			Unit unit = GameState.humanPlayer.human_cardToUnit.get(card);
//			unit.setPositionByTile(GameState.board[tilex][tiley]);
//			//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
//			BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);
//			GameState.map_Unit.put(gameState.board[tilex][tiley],unit);
//			CardClicked.isCardClicked = false;
//		}


			if (isMoveUnit) {
				//move unit
				if (unit_Click == null) {
					GameState.map_Unit.remove(start_move_tile);
					GameState.humanPlayer.map_Unit_human.remove(start_move_tile);
					BasicCommands.moveUnitToTile(out, unit_Move, GameState.board[tilex][tiley]);
					unit_Move.setPositionByTile(GameState.board[tilex][tiley]);
					GameState.map_Unit.put(GameState.board[tilex][tiley], unit_Move);
					GameState.humanPlayer.map_Unit_human.put(GameState.board[tilex][tiley], unit_Move);
					//标志位，1为第一次点击tile，0为第二次点击
//					index_move = 0;
//					isMoveUnit = false;
				}
				//attack
				else if(isAttack(out,start_move_tile,GameState.board[tilex][tiley]) == false){
					BasicCommands.addPlayer1Notification(out,"can't attack",2);
				}else if (unit_Click != null) {
					BasicCommands.playUnitAnimation(out, unit_Move, UnitAnimationType.attack);
					unit_Click.health -= unit_Move.attack;
					BasicCommands.setUnitHealth(out, unit_Click, unit_Click.health);
					int id = unit_Click.getId();
					if (id == 0) {
						GameState.humanPlayer.setHealth(unit_Click.health);
						BasicCommands.setPlayer1Health(out, GameState.humanPlayer);
					}
					if (id == 1) {
						GameState.AIPlayer.setHealth(unit_Click.health);
						BasicCommands.setPlayer2Health(out, GameState.AIPlayer);
					}
//					index_move = 0;
//					isMoveUnit = false;
					if (unit_Click.health <= 0) {
						BasicCommands.deleteUnit(out, unit_Click);
						GameState.map_Unit.remove(GameState.board[tilex][tiley]);

						BasicCommands.addPlayer1Notification(out, "unit died", 2);
					}
				}
				index_move = 0;
				isMoveUnit = false;
			}

			if (GameState.map_Unit.get(GameState.board[tilex][tiley]) != null && isMoveUnit == false && index_move == 1) {
				isMoveUnit = true;
				unit_Move = GameState.map_Unit.get(GameState.board[tilex][tiley]);
				start_move_tile = GameState.board[tilex][tiley];
				BasicCommands.addPlayer1Notification(out,"please choose a tile or unit",3);
//			GameState.map_Unit.remove(GameState.board[tilex][tiley]);
			}

			index_move = 1;
		}

//		//remove card in hand
//		BasicCommands.deleteCard(out,CardClicked.handPosition);
//		Unit unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion,1, Unit.class);
//		unit.setPositionByTile(GameState.board[tilex][tiley]);
//		//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
//		BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);
		
	}

	public void cardToUnit(ActorRef out, Card card, Unit unit){
		if(unit_Click == null){
			int mana = GameState.humanPlayer.getMana() - card.getManacost();
			if(mana < 0){
				BasicCommands.addPlayer1Notification(out,"not enough mana",2);
			}else if(!CardClicked.tiles_canSummon.contains(GameState.board[tilex][tiley])){
				BasicCommands.addPlayer1Notification(out,"Can't summon",2);
			}else{
//				Card card = GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition - 1);
				GameState.humanPlayer.setMana(mana);
				BasicCommands.setPlayer1Mana(out,GameState.humanPlayer);
				GameState.humanPlayer.cardsInPlayerHand.remove(CardClicked.handPosition - 1);
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//				GameState.humanPlayer.updateHandCardsView(out);
				BasicCommands.deleteCard(out,CardClicked.handPosition);
//				Unit unit = GameState.humanPlayer.human_cardToUnit.get(card);
//				unit.setPositionByTile(GameState.board[tilex][tiley]);
				//使用的手中卡牌GameState.humanPlayer.cardsInPlayerHand.get(CardClicked.handPosition)
				BasicCommands.drawUnit(out,unit,GameState.board[tilex][tiley]);
				BasicCommands.setUnitHealth(out,unit,unit.health);
				BasicCommands.setUnitAttack(out,unit,unit.attack);
				GameState.map_Unit.put(GameState.board[tilex][tiley],unit);
				GameState.humanPlayer.map_Unit_human.put(GameState.board[tilex][tiley],unit);
			}
			CardClicked.isCardClicked = false;
		}
	}

	public boolean isAttack(ActorRef out, Tile start, Tile end){
		int x = Math.abs(start.getTilex() - end.getTilex());
		int y = Math.abs(start.getTiley() - end.getTiley());

		if(( x == 1 && y == 0) || (x == 0 && y == 1)){
			return true;
		}else{
			return false;
		}
	}

}
