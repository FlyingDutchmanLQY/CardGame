package structures.services;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Tile;
import structures.basic.Unit;
//Double triggered action
public class DoUnitMovement {
    public void moveUnit(ActorRef out, GameState gameState, Unit unit, Tile targetTile){
        unit.displayRangeOfMovement(out, gameState, unit.getCurrentTile(gameState),0);
        unit.moveUnit(out, gameState, targetTile);
    }
}
