package structures.services;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Unit;
/*This is TWO-EVENTS-TRIGGERED-ACTION*/
public class DoUnitAttack {
    public void attackUnit(ActorRef out, GameState gameState, Unit attacker, Unit targetUnit){
        attacker.displayRangeOfMovement(out,gameState,attacker.getCurrentTile(gameState),0);
        attacker.attack(out,gameState,targetUnit);

        DoCheckWinner doCheckWinner = new DoCheckWinner();
        doCheckWinner.checkWinner(out, gameState);
    }
}
