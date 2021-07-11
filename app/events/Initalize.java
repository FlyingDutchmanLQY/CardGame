<<<<<<< Updated upstream
package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Unit;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;


/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * 
 * { 
 *   messageType = “initalize”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Initalize implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		// this executes the command demo, comment out this when implementing your solution
//		CommandDemo.executeDemo(out);


		CardClicked.isCardClicked = false;
		TileClicked.isMoveOrAttackUnit = false;
		TileClicked.isTileClicked = false;
		TileClicked.tiles_canMove.clear();
		gameState.cards.clear();
		gameState.map_Unit.clear();
		gameState.unitList.clear();

		//画棋盘
		for(int i=0;i<9;i++){
			for(int j=0;j<5;j++){
				gameState.board[i][j] = BasicObjectBuilders.loadTile(i, j);
				BasicCommands.drawTile(out, gameState.board[i][j], 0);
			}
		}

		//属性初始化
		gameState.humanPlayer.initDeck();
		gameState.AIPlayer.initDeck();
		BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		BasicCommands.setPlayer2Health(out, gameState.AIPlayer);

		//画单位
		Unit unit_human = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, gameState.id_Unit++, Unit.class);
		gameState.unitList.add(unit_human);
		unit_human.setPositionByTile(gameState.board[1][2]);
		BasicCommands.drawUnit(out, unit_human, gameState.board[1][2]);
		gameState.map_Unit.put(gameState.board[1][2],unit_human);
		gameState.humanPlayer.map_Unit_human.put(gameState.board[1][2],unit_human);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out,unit_human,2);
		BasicCommands.setUnitHealth(out,unit_human,20);
		unit_human.health = 20;
		unit_human.rawHealth = 20;
		unit_human.attack = 2;


		Unit unit_ai = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, gameState.id_Unit++, Unit.class);
		gameState.unitList.add(unit_ai);
		unit_ai.setPositionByTile(gameState.board[7][2]);
		BasicCommands.drawUnit(out, unit_ai, gameState.board[7][2]);
		gameState.map_Unit.put(gameState.board[7][2],unit_ai);
		gameState.AIPlayer.map_Unit_ai.put(gameState.board[7][2],unit_ai);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out,unit_ai,2);
		BasicCommands.setUnitHealth(out,unit_ai,20);
		unit_ai.health = 20;
		unit_ai.rawHealth = 20;
		unit_ai.attack = 2;


		BasicCommands.addPlayer1Notification(out,"It's turn " + gameState.turn,2);
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
			//set mana
			gameState.humanPlayer.setMana(2);
			gameState.AIPlayer.setMana(2);
			BasicCommands.setPlayer1Mana(out,gameState.humanPlayer);
			BasicCommands.setPlayer2Mana(out,gameState.AIPlayer);

		//drew card
		Card card = gameState.humanPlayer.chooseACard(out);
		gameState.humanPlayer.updateHandCardsView(out);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

	}

}


=======
package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Unit;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;


/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * 
 * { 
 *   messageType = “initalize”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Initalize implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		// this executes the command demo, comment out this when implementing your solution
		//CommandDemo.executeDemo(out);

		//TileClicked.tiles_canMove.clear();

		//draw the board
		for(int i=0;i<9;i++){
			for(int j=0;j<5;j++){
				gameState.board[i][j] = BasicObjectBuilders.loadTile(i, j);
				BasicCommands.drawTile(out, gameState.board[i][j], 0);
			}
		}

		//initialize players
		gameState.humanPlayer.initDeck();
		gameState.AIPlayer.initDeck();
		BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		BasicCommands.setPlayer2Health(out, gameState.AIPlayer);

		//draw avatars
		Unit humanAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 0, Unit.class);
		gameState.unitList.add(humanAvatar);
		gameState.humanPlayer.avatar = humanAvatar;
		humanAvatar.setPositionByTile(gameState.board[1][2]);
		BasicCommands.drawUnit(out, humanAvatar, gameState.board[1][2]);
		gameState.unitPositionMap.put(gameState.board[1][2],humanAvatar);
		gameState.humanPlayer.map_Unit_human.put(gameState.board[1][2],humanAvatar);
		humanAvatar.health = 20;
		humanAvatar.rawHealth = 20;
		humanAvatar.attack = 2;
		BasicCommands.setUnitAttack(out,humanAvatar,2);
		BasicCommands.setUnitHealth(out,humanAvatar,20);

		Unit AIAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 21, Unit.class);
		gameState.unitList.add(AIAvatar);
		gameState.AIPlayer.avatar = AIAvatar;
		AIAvatar.setPositionByTile(gameState.board[7][2]);
		BasicCommands.drawUnit(out, AIAvatar, gameState.board[7][2]);
		gameState.unitPositionMap.put(gameState.board[7][2],AIAvatar);
		gameState.AIPlayer.map_Unit_ai.put(gameState.board[7][2],AIAvatar);
		AIAvatar.health = 20;
		AIAvatar.rawHealth = 20;
		AIAvatar.attack = 2;
		BasicCommands.setUnitAttack(out,AIAvatar,2);
		BasicCommands.setUnitHealth(out,AIAvatar,20);


		BasicCommands.addPlayer1Notification(out,"It's turn " + gameState.turn,2);		//demonstrate the num of turn

		//set mana
		gameState.humanPlayer.setMana(2);
		gameState.AIPlayer.setMana(2);
		BasicCommands.setPlayer1Mana(out,gameState.humanPlayer);
		BasicCommands.setPlayer2Mana(out,gameState.AIPlayer);

		//drew card
		Card card = gameState.humanPlayer.drawACard(out);
		gameState.humanPlayer.updateHandCardsView(out);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

		//set remaining attack and move times,also check for Azurite Lion twice attack
		for (Unit u : gameState.unitList ) {
			u.resetRemainingAtkMovTimes();
		}
	}

}


>>>>>>> Stashed changes
