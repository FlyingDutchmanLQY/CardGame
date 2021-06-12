package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import demo.CommandDemo;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Player;
import structures.basic.Tile;
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

		//CommandDemo.executeDemo(out); // this executes the command demo, comment out this when implementing your solution
		//画棋盘
		for(int i=0;i<9;i++){
			for(int j=0;j<5;j++){
				gameState.board[i][j] = BasicObjectBuilders.loadTile(i, j);
				BasicCommands.drawTile(out, gameState.board[i][j], 0);
			}
		}

		//属性初始化
		gameState.humanPlayer = new Player(20, 0);
		gameState.AIPlayer = new Player(20, 0);
		BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
		gameState.humanPlayer.setMana(1);
		BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);

		//画单位
		Unit unit = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 0, Unit.class);
		gameState.unitList.add(unit);
		unit.setPositionByTile(gameState.board[1][2]);
		BasicCommands.drawUnit(out, unit, gameState.board[1][2]);

		Card hailstone_golem = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, 0, Card.class);
		BasicCommands.drawCard(out, hailstone_golem, 1, 0);
	}
}


