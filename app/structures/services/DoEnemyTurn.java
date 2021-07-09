package structures.services;

import akka.actor.ActorRef;
import structures.GameState;

public class DoEnemyTurn {
    public void doAITurn(ActorRef out, GameState gameState){
        gameState.AIPlayer.AI(out,gameState);
    }
}
