package structures.basic;


import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

//import static events.TileClicked.unit_Click;

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

	//Name Truestrike
	//Mana Cost 1
	//Abilities • Deal 2 damage to an enemy unit
	public void spellOf01(ActorRef out, GameState gameState, Unit targetUnit){
		Tile tile = targetUnit.getCurrentTile(gameState);
		if(targetUnit.getId() <= 20){
			BasicCommands.addPlayer1Notification(out,"Cannot Choose Ally",2);
		}else{
			BasicCommands.addPlayer1Notification(out,"Truestrike",2);
			BasicCommands.playEffectAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_inmolation),tile);
			targetUnit.changeHealth(out, gameState, targetUnit,2);
		}
	}

	//Name Sundrop Elixir
	//Mana Cost 1
	//Abilities • Add +5 health to a Unit. This cannot take a unit over its starting health value.
	public void spellOf02(ActorRef out, GameState gameState, Unit targetUnit){
		Tile tile = targetUnit.getCurrentTile(gameState);
		BasicCommands.addPlayer1Notification(out, "Sundrop Elixir",2);
		BasicCommands.playEffectAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_buff), tile);
		if(targetUnit.health + 5 <= targetUnit.rawHealth){
			targetUnit.changeHealth(out, gameState, targetUnit, -5);
		}else{
			targetUnit.health = targetUnit.rawHealth;
			targetUnit.determineUnitStatus(out, gameState);
		}
		BasicCommands.setUnitHealth(out, targetUnit, targetUnit.health);

	}

	//Staff of Y’Kir
	//Mana Cost 2
	//Abilities • Add +2 attack to your avatar
	public void spellOf03(ActorRef out, GameState gameState){
		gameState.AIPlayer.avatar.attack += 2;
		BasicCommands.setUnitAttack(out, gameState.AIPlayer.avatar, gameState.AIPlayer.avatar.attack);
		BasicCommands.addPlayer1Notification(out,"Staff of Y’Kir",2);
	}

	//Entropic Decay
	//Mana Cost 5
	//Abilities • Reduce a non-avatar unit to 0 health
	public void spellOf04(ActorRef out, GameState gameState, Card card, Unit targetUnit){
		if(gameState.humanPlayer.avatar != targetUnit && gameState.AIPlayer.avatar != targetUnit){
			targetUnit.health = 0;
			targetUnit.determineUnitStatus(out, gameState);
			BasicCommands.addPlayer1Notification(out,"Entropic Decay",2);
		}
		//gameState.AIPlayer.summonCard(out,gameState,card,gameState.AIPlayer,1);
	}

	
}
