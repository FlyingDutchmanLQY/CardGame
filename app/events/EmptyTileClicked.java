package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import structures.GameState;
import structures.basic.Tile;
import structures.services.DoUnitMovement;
import structures.services.DoUnitSummon;

public class EmptyTileClicked {
    public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
        int tilex = message.get("tilex").asInt();
        int tiley = message.get("tiley").asInt();
        Tile targetTile = gameState.board[tilex][tiley];
        gameState.eventRecorder.add("EmptyTileClicked");
        gameState.isTileClicked = true;         //empty tiles been clicked

        //Two events will trigger this service
        if(                 //Move Unit
            gameState.eventRecorder.get(0).equals("UnitClicked") &&
            gameState.eventRecorder.get(1).equals("EmptyTileClicked")
        ){
            DoUnitMovement doUnitMovement = new DoUnitMovement();
            doUnitMovement.moveUnit(out, gameState, gameState.tempUnit, targetTile);
            gameState.resetEventSignals();
        }
        else if(                 //Summon Unit
            gameState.eventRecorder.get(0).equals("CardClicked") &&
            gameState.eventRecorder.get(1).equals("EmptyTileClicked")
        ){
            DoUnitSummon doUnitSummon = new DoUnitSummon();
            doUnitSummon.summonUnit(out, gameState, gameState.tempHandPosition, targetTile);
            gameState.resetEventSignals();
        }
        else {
            gameState.resetEventSignals();
        }
    }
}
