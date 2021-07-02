package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;

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
	//public static boolean isCardClicked = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		handPosition = message.get("position").asInt();
		gameState.isCardClicked = true;
		BasicCommands.addPlayer1Notification(out,"Choose A Tile", 3);
		BasicCommands.drawCard(out,gameState.humanPlayer.cardsInPlayerHand.get(handPosition - 1),handPosition,1);

		int ind = gameState.humanPlayer.cardsInPlayerHand.get(handPosition - 1).getId();
		if(ind == 0){
			BasicCommands.addPlayer1Notification(out,"Select Enemy Unit",2);
		}else if(ind == 10){
			BasicCommands.addPlayer1Notification(out,"Select Enemy Unit",2);
		}else if(ind == 1){
			BasicCommands.addPlayer1Notification(out,"Select Unit",2);
		}else if(ind == 11){
			BasicCommands.addPlayer1Notification(out,"Select Unit",2);
		}else if(ind == 9){
			BasicCommands.addPlayer1Notification(out,"Summon Anywhere",2);
		}
		else if(ind == 9){
			BasicCommands.addPlayer1Notification(out,"Summon Anywhere",2);
		}else {
			gameState.humanPlayer.determineTilesCanSummon(out,gameState,gameState.humanPlayer.map_Unit_human);
		}

	}


}
