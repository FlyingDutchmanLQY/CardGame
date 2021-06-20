package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
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
		else{
			return null;
		}
	}

	void shuffleDeck(){
		int times = 50;
		Random ran = new Random();
		while(times-- > 0){
			Collections.swap(this.deck, ran.nextInt(10), ran.nextInt(10));
		}
	}

	public void updateHandCardsView(ActorRef out){
		for(int i=1;i<=6;i++){
			BasicCommands.deleteCard(out, i);
		}
		for(int i=0;i<this.cardsInHand.size();i++){
			BasicCommands.drawCard(out, this.cardsInHand.get(i), i+1, 0);
		}
	}
}
