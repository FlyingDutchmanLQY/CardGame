package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import events.CardClicked;

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
	public int index_cardInHand = 1;
	public ArrayList<Card> cardsInPlayerHand = new ArrayList<>();
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

	public Card chooseACard(){
		if(cardsInPlayerHand.size() < 6){
			Card card = this.deck.get(0);
			this.cardsInPlayerHand.add(card);
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
			if(i == CardClicked.handPosition) continue;
			BasicCommands.deleteCard(out, i);
		}
		ArrayList<Card> mid = new ArrayList<>();
		for(Card card : cardsInPlayerHand){
			mid.add(card);
		}
		this.cardsInPlayerHand = new ArrayList<>(mid);
		for(int i=0;i<this.cardsInPlayerHand.size();i++){
			BasicCommands.drawCard(out, this.cardsInPlayerHand.get(i), i+1, 0);

		}
	}
	
	
	
}
