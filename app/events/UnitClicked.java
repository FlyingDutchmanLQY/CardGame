package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.services.DoSpellCast;
import structures.services.DoUnitAttack;
import structures.services.DoUnitClick;

public class UnitClicked {
    public void processEvent(ActorRef out, GameState gameState, JsonNode message){
        int tilex = message.get("tilex").asInt();
        int tiley = message.get("tiley").asInt();
        Tile targetTile = gameState.board[tilex][tiley];
        Unit targetUnit = gameState.map_Unit.get(targetTile);
        gameState.isUnitClicked = true;
        gameState.eventRecorder.add("UnitClicked");

        if(gameState.isSpellCard){
            DoSpellCast doSpellCast = new DoSpellCast();
            Card card = gameState.humanPlayer.cardsInPlayerHand.get(gameState.tempHandPosition - 1);
            doSpellCast.castSpell(out, gameState, card, targetUnit);
        }

        //This is single triggered action
        if(             //single click on the unit to display the attack and move range
            gameState.eventRecorder.get(0).equals("UnitClicked") &&
            gameState.eventRecorder.size() == 1
        ){
            gameState.tempUnit = targetUnit;
            DoUnitClick doUnitClick = new DoUnitClick();
            doUnitClick.clickUnit(out, gameState, targetUnit);
        }
        else if(            //attack                    //double triggered action
            gameState.eventRecorder.get(0).equals("UnitClicked") &&
            gameState.eventRecorder.get(1).equals("UnitClicked")
        ){
            DoUnitAttack doUnitAttack = new DoUnitAttack();
            doUnitAttack.attackUnit(out, gameState, gameState.tempUnit, targetUnit);
            gameState.resetEventSignals();
        }
        else{
            gameState.resetEventSignals();
        }
    }
}
