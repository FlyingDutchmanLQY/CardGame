package structures;

import structures.basic.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class can be used to hold information about the on-going game.
 * Its created with the GameActor.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class GameState {

   public int turn = 1;

   public Tile[][] board = new Tile[9][5];

//   public static ArrayList<String> unitStaticConfFiles = new ArrayList<>();

   public ArrayList<Card> cards = new ArrayList<>();

   public ArrayList<Unit> unitList = new ArrayList<Unit>();

   public int id_Unit = 0;

   public HashMap<Tile,Unit> map_Unit = new HashMap<>();

   public HumanPlayer humanPlayer = new HumanPlayer(20,0);
   public AIPlayer AIPlayer = new AIPlayer(20,0) ;

}
