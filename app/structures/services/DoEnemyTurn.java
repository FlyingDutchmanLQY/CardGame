package structures.services;

public class DoEnemyTurn {
    public void doAITurn(ActorRef out, GameState gameState){
        gameState.AIPlayer.AI(out,gameState);
    }
}
