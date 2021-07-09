package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * somewhere that is not on a card tile or the end-turn button.
 * 
 * { 
 *   messageType = “otherClicked”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class OtherClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		if(
				gameState.isCardClicked ||
				gameState.isUnitClicked ||
				gameState.isSpellCard ||
				gameState.isTileClicked){
			BasicCommands.addPlayer1Notification(out, "Canceled",2);
		}
		gameState.resetEventSignals();
		
	}

}


