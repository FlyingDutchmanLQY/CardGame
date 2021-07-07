package structures.basic;


import akka.actor.ActorRef;
import commands.BasicCommands;
import events.CardClicked;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import static events.TileClicked.unit_Click;

/**
 * This is the base representation of a Card which is rendered in the player's hand.
 * A card has an id, a name (cardname) and a manacost. A card then has a large and mini
 * version. The mini version is what is rendered at the bottom of the screen. The big
 * version is what is rendered when the player clicks on a card in their hand.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Card {
	
	int id;
	
	String cardname;
	int manacost;

	MiniCard miniCard;
	BigCard bigCard;
	
	public Card() {};
	
	public Card(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard) {
		super();
		this.id = id;
		this.cardname = cardname;
		this.manacost = manacost;
		this.miniCard = miniCard;
		this.bigCard = bigCard;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public int getManacost() {
		return manacost;
	}
	public void setManacost(int manacost) {
		this.manacost = manacost;
	}
	public MiniCard getMiniCard() {
		return miniCard;
	}
	public void setMiniCard(MiniCard miniCard) {
		this.miniCard = miniCard;
	}
	public BigCard getBigCard() {
		return bigCard;
	}
	public void setBigCard(BigCard bigCard) {
		this.bigCard = bigCard;
	}

	public void spellOf01(ActorRef out, GameState gameState, Unit targetUnit, Player player, int handPosition){
		Tile tile_Click = targetUnit.getCurrentTile(gameState);
		if(unit_Click == null){
			BasicCommands.addPlayer1Notification(out,"Does not meet the rules",2);
		}else{
			BasicCommands.addPlayer1Notification(out,"Spell 0",2);
			player.summonCard(out, gameState, this, player,handPosition);
			BasicCommands.playEffectAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_inmolation),tile_Click);
			unit_Click.changeHealth(out,gameState,tile_Click,2);
		}
	}

	public void spellOf02(ActorRef out, GameState gameState, Card card, Tile tile_Click,Player player, int handPosition){
		if(unit_Click == null){
			BasicCommands.addPlayer1Notification(out,"Does not meet the rules",2);
		}else{
			gameState.humanPlayer.summonCard(out,gameState,card, player,handPosition);
			BasicCommands.addPlayer1Notification(out,"Spell 1",2);
			BasicCommands.playEffectAnimation(out,BasicObjectBuilders.loadEffect(StaticConfFiles.f1_buff),tile_Click);
			if(unit_Click.health + 5 <= unit_Click.rawHealth){
				unit_Click.changeHealth(out,gameState,tile_Click,-5);
			}else{
				unit_Click.health = unit_Click.rawHealth;
				BasicCommands.setUnitHealth(out,unit_Click,unit_Click.health);
				unit_Click.determineUnitStatue(out,gameState,tile_Click);
			}
		}
	}

	public void spellOf03(ActorRef out, GameState gameState, Card card, Tile tile_Click){
		BasicCommands.addPlayer1Notification(out,"Spell 3",2);
		gameState.AIPlayer.summonCard(out,gameState,card,gameState.AIPlayer,1);
	}

	public void spellOf04(ActorRef out, GameState gameState, Card card, Tile tile_Click){
		BasicCommands.addPlayer1Notification(out,"Spell 4",2);
		gameState.AIPlayer.summonCard(out,gameState,card,gameState.AIPlayer,1);
	}

	
}
