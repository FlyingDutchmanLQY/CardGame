package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.services.DoCardClicked;
import structures.services.DoUnitSummon;

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

	public int handPosition;
	//public static boolean isCardClicked = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		gameState.eventRecorder.clear();
		gameState.eventRecorder.add("CardClicked");
		handPosition = message.get("position").asInt();
		gameState.isCardClicked = true;
		gameState.tempHandPosition = handPosition;

		DoCardClicked doCardClicked = new DoCardClicked();   //single triggered action
		doCardClicked.clickCard(out, gameState, handPosition);
	}


}

