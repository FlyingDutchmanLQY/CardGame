package structures.services;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Unit;

public class DoSpellCast {
    public void castSpell(ActorRef out, GameState gameState, Card card, Unit targetUnit){
        if(card.getId() == 0 || card.getId() == 10){
            //card.spellOf01(out, gameState, card, targetUnit, gameState.humanPlayer, handPosition);
        }else if(card.getId() == 1 || card.getId() == 11){
            //card.spellOf02(out, gameState, card, targetTile, gameState.humanPlayer, handPosition);
        }
    }
}
