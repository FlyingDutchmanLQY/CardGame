package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.*;

//import static events.TileClicked.unit_Click;

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
	//public int index_cardInHand = 1;
	public ArrayList<Card> cardsInPlayerHand = new ArrayList<>();
	public ArrayList<Card> deck = new ArrayList<Card>();
	public ArrayList<Tile> tiles_canSummon = new ArrayList<>();

	public ArrayList<Card> cardsShouldBeRemove = new ArrayList<>();
	public Unit avatar;
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

	public Card drawACard(ActorRef out){
		if(cardsInPlayerHand.size() < 6){
			if(deck.size() > 0){
				Card card = this.deck.get(0);
				this.cardsInPlayerHand.add(card);
				this.deck.remove(0);
				//System.out.println(this.deck.get(0).getCardname());
				//shuffleDeck();
				return card;
			}else{
				BasicCommands.addPlayer1Notification(out,"No Card In Deck",2);
				return null;
			}
		}
		else{
			BasicCommands.addPlayer1Notification(out,"Hands Full",2);
			return null;
		}
	}

	void shuffleDeck(){
		int times = 50;
		Random ran = new Random();
		int size = deck.size();
		while(times-- > 0){
			Collections.swap(this.deck, ran.nextInt(size), ran.nextInt(size));
		}
	}

	public void updateHandCardsView(ActorRef out){
		deleteCardInView(out);
		ArrayList<Card> mid = new ArrayList<>();
		for(Card card : cardsInPlayerHand){
			mid.add(card);
		}
		this.cardsInPlayerHand = new ArrayList<>(mid);
		for(int i=0;i<this.cardsInPlayerHand.size();i++){
			BasicCommands.drawCard(out, this.cardsInPlayerHand.get(i), i+1, 0);
		}
	}

	public void deleteCardInView(ActorRef out){
		for(int i=1;i<=6;i++){
//			if(i == CardClicked.handPosition) continue;
			BasicCommands.deleteCard(out, i);
		}
	}

	/**
	 * @param out
	 * @param card
	 * @param unit
	 * @param gameState
	 */
	/* Involving effects:
	 * 	id	name
	 *	8	Azurite Lion	attack twice
	 * */
	public void summonCardToUnit(ActorRef out, GameState gameState, Card card, Unit unit, Tile tile){
		System.out.println(tiles_canSummon);
		if(!tiles_canSummon.contains(tile)){
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.addPlayer1Notification(out,"Cannot Summon Here",2);
		} else {
			boolean canSummon = summonCard(out, gameState, card);
			summonUnit(out, gameState, card, unit, tile, canSummon);
			if(unit.getId() == 8 || unit.getId() == 18){
				unit.remainingAttackTimes = 2;
			}
		}
	}

	//try to deduct mana when using a card, if enough, do deduct mana
	public boolean tryDeductFromMana(ActorRef out, GameState gameState, Card card){
		int mana = this.getMana() - card.getManacost();
		if(mana < 0){
			BasicCommands.addPlayer1Notification(out,"Not Enough Mana",2);
			return false;
		} else{
			this.setMana(mana);
			if(this instanceof HumanPlayer) BasicCommands.setPlayer1Mana(out,gameState.humanPlayer);
			if(this instanceof AIPlayer) BasicCommands.setPlayer2Mana(out,gameState.AIPlayer);
			return true;
		}
	}

	public boolean summonCard(ActorRef out, GameState gameState, Card card){
		boolean isEnoughMana = this.tryDeductFromMana(out, gameState, card);
		if(isEnoughMana){		//mana has already been deducted here
			int position = this.cardsInPlayerHand.indexOf(card) + 1;
			//cardsShouldBeRemove.add(card);
			//try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
			this.cardsInPlayerHand.remove(position - 1);
			BasicCommands.deleteCard(out, position);
			this.updateHandCardsView(out);
			//try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
			return true;
		}
		else{
			return false;
		}
	}

	/* Involving effects:
	 * 	id	name
	 *	6	azure_herald	when summoned,+3 HP to avatar
	 * */
	public void summonUnit(ActorRef out, GameState gameState, Card card, Unit unit, Tile tile, Boolean canSummon){
		if(canSummon) {
			unit.setPositionByTile(tile);
			BasicCommands.drawUnit(out, unit, tile);
			BasicCommands.playEffectAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_summon),tile);
			try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
			BasicCommands.setUnitHealth(out, unit, unit.health);
			BasicCommands.setUnitAttack(out, unit, unit.attack);
			gameState.unitList.add(unit);
			gameState.unitPositionMap.put(tile, unit);
			if(this instanceof HumanPlayer) gameState.humanPlayer.map_Unit_human.put(tile, unit);
			if(this instanceof AIPlayer) gameState.AIPlayer.map_Unit_ai.put(tile,unit);
			if(card.getId() == 6 || card.getId() == 16) azureHeraldAbility(out, gameState, 3);
		}
	}

	public void azureHeraldAbility(ActorRef out, GameState gameState, int health){
		BasicCommands.addPlayer1Notification(out,"Azure Herald Ability launch, add 3 HP to your avatar", 2);
		if(gameState.humanPlayer.getHealth() < 20) {
			int h = gameState.humanPlayer.getHealth() < 17 ? gameState.humanPlayer.getHealth() + health : 20;
			gameState.humanPlayer.setHealth(h);
			BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
			for (Unit u : gameState.unitList) {
				if (u.getId() == 0) {
					u.health = gameState.humanPlayer.getHealth();
					BasicCommands.setUnitHealth(out, u, u.health);
				}
			}
		}
	}

	public void determineTilesCanSummon(ActorRef out, GameState gameState){
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		for (Unit u : gameState.unitList ) {
			if(u.getId() < 21) tileList.add(u.getCurrentTile(gameState));
		}
		this.tiles_canSummon.clear();
		for (Tile t : tileList ) {
			int x = t.getTilex();
			int y = t.getTiley();
			try {tiles_canSummon.add(gameState.board[x + 1][y]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x - 1][y]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x][y + 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x][y - 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x + 1][y + 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x + 1][y - 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x - 1][y + 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
			try {tiles_canSummon.add(gameState.board[x - 1][y - 1]);}
			catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}

			LinkedHashSet<Tile> set = new LinkedHashSet<Tile>(this.tiles_canSummon.size());
			set.addAll(this.tiles_canSummon);
			this.tiles_canSummon.clear();
			this.tiles_canSummon.addAll(set);
		}
		ArrayList<Tile> tempList = new ArrayList<Tile>(this.tiles_canSummon.size());
		tempList.addAll(tiles_canSummon);
		for (Tile t : this.tiles_canSummon){
			if(gameState.unitPositionMap.get(t) != null) tempList.remove(t);
		}
		this.tiles_canSummon.clear();
		this.tiles_canSummon.addAll(tempList);
		for (Tile t : this.tiles_canSummon){
			BasicCommands.drawTile(out, t, 1);
		}
	}

	public void determineTilesCanSummon(ActorRef out, GameState gameState, HashMap map){
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Tile tile = (Tile) entry.getKey();
			int x = tile.getTilex();
			int y = tile.getTiley();
			if (x < 8 && gameState.unitPositionMap.get(gameState.board[x + 1][y]) == null) {
				tiles_canSummon.add(gameState.board[x + 1][y]);
				BasicCommands.drawTile(out, gameState.board[x + 1][y], 1);
			}
			if (0 < x && gameState.unitPositionMap.get(gameState.board[x - 1][y]) == null) {
				tiles_canSummon.add(gameState.board[x - 1][y]);
				BasicCommands.drawTile(out, gameState.board[x - 1][y], 1);
			}
			if (y < 4 && gameState.unitPositionMap.get(gameState.board[x][y + 1]) == null) {
				tiles_canSummon.add(gameState.board[x][y + 1]);
				BasicCommands.drawTile(out, gameState.board[x][y + 1], 1);
			}
			if (0 < y && gameState.unitPositionMap.get(gameState.board[x][y - 1]) == null) {
				tiles_canSummon.add(gameState.board[x][y - 1]);
				BasicCommands.drawTile(out, gameState.board[x][y - 1], 1);
			}

			if(x < 8 && y < 4 && gameState.unitPositionMap.get(gameState.board[x+1][y+1]) == null) {
				tiles_canSummon.add(gameState.board[x+1][y+1]);
				BasicCommands.drawTile(out, gameState.board[x+1][y+1], 1);
			}
			if(x < 8 && 0 < y && gameState.unitPositionMap.get(gameState.board[x+1][y-1]) == null) {
				tiles_canSummon.add(gameState.board[x+1][y-1]);
				BasicCommands.drawTile(out, gameState.board[x+1][y-1], 1);
			}
			if(0 < x && y < 4 && gameState.unitPositionMap.get(gameState.board[x-1][y+1]) == null) {
				tiles_canSummon.add(gameState.board[x-1][y+1]);
				BasicCommands.drawTile(out, gameState.board[x-1][y+1], 1);
			}
			if(0 < x && 0 < y && gameState.unitPositionMap.get(gameState.board[x-1][y-1]) == null) {
				tiles_canSummon.add(gameState.board[x-1][y-1]);
				BasicCommands.drawTile(out, gameState.board[x-1][y-1], 1);
			}
		}
	}

	public void deleteTilesCanSummon(ActorRef out){
		for(Tile tile : this.tiles_canSummon){
			BasicCommands.drawTile(out,tile,0);
		}
		this.tiles_canSummon.clear();
	}
	
	
	
}
