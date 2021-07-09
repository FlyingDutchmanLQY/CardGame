package structures.services;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.AIPlayer;
import structures.basic.Card;
import structures.basic.HumanPlayer;

//Single triggered action
public class DoCardClicked {
    public void clickCard(ActorRef out, GameState gameState, int handPosition){
        Card card = gameState.humanPlayer.cardsInPlayerHand.get(handPosition - 1);
        int mana = gameState.humanPlayer.getMana() - card.getManacost();
        if(mana < 0){
            BasicCommands.addPlayer1Notification(out,"not enough mana",2);
            gameState.resetEventSignals();
        }
        else{                   //enough mana
            BasicCommands.addPlayer1Notification(out,"Choose A Tile", 3);
            BasicCommands.drawCard(out, card, handPosition,1);

            int id = card.getId();
            if(id == 1 || id == 11){
                BasicCommands.addPlayer1Notification(out,"Select Enemy Unit",2);
                gameState.isSpellCard = true;       //Spell Card detection
            }else if(id == 2 || id == 12){
                BasicCommands.addPlayer1Notification(out,"Select Unit",2);
                gameState.isSpellCard = true;       //Spell Card detection
            }else if(id == 10 || id == 20){
                //BasicCommands.addPlayer1Notification(out,"Summon Anywhere",2);
            }else {
                gameState.humanPlayer.determineTilesCanSummon(out,gameState);
            }
        }
    }
}
