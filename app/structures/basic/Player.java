package structures.basic;

import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A basic representation of of the Player. A player
 * has health and mana.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	int health;
	int mana;
	public ArrayList<Card> cardsInHand = new ArrayList<Card>();
	public ArrayList<Card> deck = new ArrayList<Card>();

	public Player() {
		super();
		this.health = 20;
		this.mana = 0;
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public Card drawACard(){
		if(cardsInHand.size() < 6){
			Card card = this.deck.get(0);
			this.cardsInHand.add(card);
			this.deck.remove(0);
			return card;
		}
		return null;
	}
	public void initHumanPlayerDeck(){
		Card cardTemp;
		int id = 0;
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_truestrike, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_sundrop_elixir, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_comodo_charger, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azure_herald, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azurite_lion, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_fire_spitter, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_ironcliff_guardian, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_pureblade_enforcer, id++, Card.class);
		this.deck.add(cardTemp);
		cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_silverguard_knight, id++, Card.class);
		this.deck.add(cardTemp);
		shuffleDeck();
	}
	private void shuffleDeck(){
		int times = 50;
		Random ran = new Random();
		while(times-- > 0){
			Collections.swap(this.deck, ran.nextInt(10), ran.nextInt(10));
		}
	}
}
