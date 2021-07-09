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
		BasicCommands.setUnitAttack(out,humanAvatar,2);
		BasicCommands.setUnitHealth(out,humanAvatar,20);
		humanAvatar.health = 20;
		humanAvatar.rawHealth = 20;
		humanAvatar.attack = 2;

		Unit AIAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 21, Unit.class);
		gameState.unitList.add(AIAvatar);
		gameState.AIPlayer.avatar = AIAvatar;
		AIAvatar.setPositionByTile(gameState.board[7][2]);
		BasicCommands.drawUnit(out, AIAvatar, gameState.board[7][2]);
		gameState.unitPositionMap.put(gameState.board[7][2],AIAvatar);
		gameState.AIPlayer.map_Unit_ai.put(gameState.board[7][2],AIAvatar);
		BasicCommands.setUnitAttack(out,AIAvatar,2);
		BasicCommands.setUnitHealth(out,AIAvatar,20);
		AIAvatar.health = 20;
		AIAvatar.rawHealth = 20;
		AIAvatar.attack = 2;

		BasicCommands.addPlayer1Notification(out,"It's turn " + gameState.turn,2);

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


