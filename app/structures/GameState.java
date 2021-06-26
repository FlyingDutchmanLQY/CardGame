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

   public static int turn = 1;

   public static Tile[][] board = new Tile[9][5];

//   public static ArrayList<String> unitStaticConfFiles = new ArrayList<>();

   public static ArrayList<Card> cards = new ArrayList<>();

   public static ArrayList<Unit> unitList = new ArrayList<Unit>();

   public static int id_Unit = 0;

   public static HashMap<Tile,Unit> map_Unit = new HashMap<>();

   public static HumanPlayer humanPlayer = new HumanPlayer(20,0);
   public static AIPlayer AIPlayer = new AIPlayer(20,0) ;

}
