package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.*;

import static events.TileClicked.unit_Click;

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
	public ArrayList<Tile> tiles_canSummon = new ArrayList<>();
	public ArrayList<Card> cardsShouldBeRemove = new ArrayList<>();
	
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
	 *
	 * @param out
	 * @param card
	 * @param unit
	 * @param gameState
	 */
	public void summonCardToUnit(ActorRef out, GameState gameState, Card card, Unit unit, Tile tile, Player player,int position, int mode){
		if(unit_Click == null){
			if(card.getId() == 9){
				boolean canSummon = summonCard(out, gameState, card,player,position);
				summonUnit(out, gameState, card, unit, tile,canSummon,mode);
			}
			if(card.getId() == 19){
				boolean canSummon = summonCard(out, gameState, card,player,position);
				summonUnit(out, gameState, card, unit, tile,canSummon,mode);
			}else if(!tiles_canSummon.contains(tile)){
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
				BasicCommands.addPlayer1Notification(out,"Can't summon",2);

			}else{
				boolean canSummon = summonCard(out,gameState,card,player,position);
				summonUnit(out, gameState, card, unit, tile,canSummon,mode);
			}
		}
	}

	public boolean summonCard(ActorRef out, GameState gameState, Card card, Player player,int position){
		int mana = player.getMana() - card.getManacost();
		if(mana < 0){
			BasicCommands.addPlayer1Notification(out,"not enough mana",2);
			return false;
		} else{
			player.setMana(mana);
			if(player instanceof HumanPlayer)BasicCommands.setPlayer1Mana(out,gameState.humanPlayer);
			if(player instanceof AIPlayer)BasicCommands.setPlayer2Mana(out,gameState.humanPlayer);
			cardsShouldBeRemove.add(player.cardsInPlayerHand.get(position - 1));
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.deleteCard(out,position);
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			return true;
		}
	}

	public void summonUnit(ActorRef out, GameState gameState, Card card, Unit unit, Tile tile, Boolean canSummon, int mode){
		if(canSummon) {
			unit.setPositionByTile(tile);
			BasicCommands.drawUnit(out, unit, tile);
			BasicCommands.playEffectAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_summon),tile);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BasicCommands.setUnitHealth(out, unit, unit.health);
			BasicCommands.setUnitAttack(out, unit, unit.attack);
			gameState.unitList.add(unit);
			gameState.map_Unit.put(tile, unit);
			if(mode == 0)gameState.humanPlayer.map_Unit_human.put(tile, unit);
			if(mode == 1)gameState.AIPlayer.map_Unit_ai.put(tile,unit);
			if(card.getId() == 5)summonAddHealth(out,gameState,3);
			if(card.getId() == 15)summonAddHealth(out,gameState,3);
		}
	}

	public void summonAddHealth(ActorRef out, GameState gameState,int health){
			BasicCommands.addPlayer1Notification(out,"Ability launch", 2);
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

	public void determineTilesCanSummon(ActorRef out, GameState gameState, HashMap map){
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Tile tile = (Tile) entry.getKey();
			int x = tile.getTilex();
			int y = tile.getTiley();
			if (x < 8 && gameState.map_Unit.get(gameState.board[x + 1][y]) == null) {
				tiles_canSummon.add(gameState.board[x + 1][y]);
				BasicCommands.drawTile(out, gameState.board[x + 1][y], 1);
			}
			if (0 < x && gameState.map_Unit.get(gameState.board[x - 1][y]) == null) {
				tiles_canSummon.add(gameState.board[x - 1][y]);
				BasicCommands.drawTile(out, gameState.board[x - 1][y], 1);
			}
			if (y < 4 && gameState.map_Unit.get(gameState.board[x][y + 1]) == null) {
				tiles_canSummon.add(gameState.board[x][y + 1]);
				BasicCommands.drawTile(out, gameState.board[x][y + 1], 1);
			}
			if (0 < y && gameState.map_Unit.get(gameState.board[x][y - 1]) == null) {
				tiles_canSummon.add(gameState.board[x][y - 1]);
				BasicCommands.drawTile(out, gameState.board[x][y - 1], 1);
			}

			if(x < 8 && y < 4 && gameState.map_Unit.get(gameState.board[x+1][y+1]) == null) {
				tiles_canSummon.add(gameState.board[x+1][y+1]);
				BasicCommands.drawTile(out, gameState.board[x+1][y+1], 1);
			}
			if(x < 8 && 0 < y && gameState.map_Unit.get(gameState.board[x+1][y-1]) == null) {
				tiles_canSummon.add(gameState.board[x+1][y-1]);
				BasicCommands.drawTile(out, gameState.board[x+1][y-1], 1);
			}
			if(0 < x && y < 4 && gameState.map_Unit.get(gameState.board[x-1][y+1]) == null) {
				tiles_canSummon.add(gameState.board[x-1][y+1]);
				BasicCommands.drawTile(out, gameState.board[x-1][y+1], 1);
			}
			if(0 < x && 0 < y && gameState.map_Unit.get(gameState.board[x-1][y-1]) == null) {
				tiles_canSummon.add(gameState.board[x-1][y-1]);
				BasicCommands.drawTile(out, gameState.board[x-1][y-1], 1);
			}
		}
	}

	public void deleteTilesCanSummon(ActorRef out, ArrayList<Tile> list){
		for(Tile tile : list){
			BasicCommands.drawTile(out,tile,0);
		}
		list.clear();
	}
	
	
	
}
