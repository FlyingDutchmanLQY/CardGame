package events;


import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
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
	public static boolean isCardClicked = false;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		handPosition = message.get("position").asInt();
		isCardClicked = true;

		//remove card in hand
//		BasicCommands.deleteCard(out,handPosition);
		
		
	}

}
