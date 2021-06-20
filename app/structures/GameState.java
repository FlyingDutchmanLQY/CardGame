package structures;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.basic.HumanPlayer;
import structures.basic.AIPlayer;
import structures.basic.Tile;
import structures.basic.Unit;
import utils.BasicObjectBuilders;

import java.util.ArrayList;

/**
 * This class can be used to hold information about the on-going game.
 * Its created with the GameActor.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class GameState {
    //画棋盘
    public Tile board[][] = new Tile[9][5];
    public ArrayList<Unit> unitList = new ArrayList<Unit>();
    public HumanPlayer humanPlayer;
    public AIPlayer AIPlayer;
    public int turn;
	
	
}
