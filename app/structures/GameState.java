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

   public boolean gameOver = false;

   public int turn = 1;

   public Tile[][] board = new Tile[9][5];

   //public ArrayList<Card> cards = new ArrayList<>();

   public ArrayList<Unit> unitList = new ArrayList<Unit>();

   public HashMap<Tile,Unit> unitPositionMap = new HashMap<>();

   public HumanPlayer humanPlayer = new HumanPlayer(20,0);
   public AIPlayer AIPlayer = new AIPlayer(20,0) ;

   public ArrayList<String> eventRecorder = new ArrayList<String>();

   public boolean isCardClicked = false;
   public boolean isTileClicked = false;
   //public boolean isMoveOrAttackUnit = false;
   public boolean isUnitClicked = false;
   public boolean isSpellCard = false;

   public Unit tempUnit = null;
   public int tempHandPosition = -1;

   public void resetEventSignals(){
      isCardClicked = false;
      isTileClicked = false;
      //isMoveOrAttackUnit = false;
      isUnitClicked = false;
      isSpellCard = false;
      tempUnit = null;
      tempHandPosition = -1;
      eventRecorder.clear();
   }
}
