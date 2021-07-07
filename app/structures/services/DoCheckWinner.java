package structures.services;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;

public class DoCheckWinner {
    public void checkWinner(ActorRef out, GameState gameState){
        if(gameState.humanPlayer.getHealth() <= 0) {
            BasicCommands.addPlayer1Notification(out,"Winner Winner Chicken Dinner",2);

        }
        if(gameState.AIPlayer.getHealth() <= 0){
            BasicCommands.addPlayer1Notification(out,"You Lost",2);
        }
    }
}
