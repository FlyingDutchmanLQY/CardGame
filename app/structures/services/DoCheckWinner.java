package structures.services;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;

public class DoCheckWinner {
    public void checkWinner(ActorRef out, GameState gameState){
         if(gameState.humanPlayer.getHealth() <= 0) {//Human player die
            BasicCommands.addPlayer1Notification(out,"You Lost",4);
            gameState.gameOver = true;
        }
        if(gameState.AIPlayer.getHealth() <= 0){//AI player die
            BasicCommands.addPlayer1Notification(out,"Winner Winner Chicken Dinner",4);
            gameState.gameOver = true;
        }
    }
}
