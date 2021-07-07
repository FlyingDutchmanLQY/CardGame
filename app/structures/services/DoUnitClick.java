package structures.services;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Unit;
/*This is single triggered action*/
public class DoUnitClick {
    public void clickUnit(ActorRef out, GameState gameState, Unit unit){

        if(unit.isFromHuman()){                 //Player cannot use enemy unit
            BasicCommands.addPlayer1Notification(out,"Choose Tile Or Unit To Move Or Attack",3);
            unit.displayRangeOfMovement(out, gameState, unit.getCurrentTile(gameState),2);
        }
        else{
            BasicCommands.addPlayer1Notification(out,"It's Enemy Unit",3);
            gameState.resetEventSignals();
        }

    }
}
