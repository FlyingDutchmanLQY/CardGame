package structures.services;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;
//double trigger action
public class DoUnitSummon {
    public void summonUnit(ActorRef out, GameState gameState, int handPosition, Tile targetTile){
        Card card = gameState.humanPlayer.cardsInPlayerHand.get(handPosition - 1);
        Unit unit = gameState.humanPlayer.human_cardToUnit.get(card);
        unit.setPositionByTile(targetTile);
        gameState.humanPlayer.summonCardToUnit(out, gameState, card, unit, targetTile);

        gameState.humanPlayer.deleteTilesCanSummon(out);

    }
}
