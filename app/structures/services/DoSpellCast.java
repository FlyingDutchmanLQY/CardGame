package structures.services;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Player;
import structures.basic.Unit;

public class DoSpellCast {
    public void castSpell(ActorRef out, GameState gameState, Card card, Unit targetUnit, Player player){
        if(card.getId() == 1 || card.getId() == 11){
            card.spellOf01(out, gameState, targetUnit);
            BasicCommands.deleteCard(out, player.cardsInPlayerHand.indexOf(card) + 1);
            player.cardsInPlayerHand.remove(card);
        }else if(card.getId() == 2 || card.getId() == 12){
            card.spellOf02(out, gameState, targetUnit);
            BasicCommands.deleteCard(out, player.cardsInPlayerHand.indexOf(card) + 1);
            player.cardsInPlayerHand.remove(card);
        }else if(card.getId() == 22){
            if(player.tryDeductFromMana(out, gameState, card)){
                card.spellOf03(out, gameState);
                player.cardsInPlayerHand.remove(card);
                //player.updateHandCardsView(out);
            }
        }else if(card.getId() == 23){
            if(player.tryDeductFromMana(out, gameState, card)){
                card.spellOf04(out, gameState, card, targetUnit);
                player.cardsInPlayerHand.remove(card);
                //player.updateHandCardsView(out);
            }
        }
    }
}
