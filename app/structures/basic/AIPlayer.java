package structures.basic;

import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.HashMap;

public class AIPlayer extends Player{

    public HashMap<Card,Unit> ai_cardToUnit = new HashMap<>();
    public HashMap<Tile,Unit> map_Unit_ai = new HashMap<>();

    public AIPlayer() {
        super();
    }
    public AIPlayer(int health, int mana) {
        super(health, mana);
    }

    public void initDeck(){

        Card cardTemp;
        Unit unit;
        int id = 22;

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_staff_of_ykir, id++, Card.class);
        this.deck.add(cardTemp);
        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_entropic_decay, id++, Card.class);
        this.deck.add(cardTemp);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_planar_scout, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_planar_scout,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_rock_pulveriser, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_rock_pulveriser,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_pyromancer, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_pyromancer,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_bloodshard_golem, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_bloodshard_golem,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_blaze_hound, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_blaze_hound,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_windshrike, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_windshrike,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        //TODO
        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golemR,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_serpenti, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_serpenti,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        ai_cardToUnit.put(cardTemp,unit);


        shuffleDeck();
    }

    public void AI(ActorRef out, GameState gameState){
        BasicCommands.addPlayer1Notification(out,"It's ai",2);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		//AIPlayer aiPlayer = gameState.AIPlayer;
		gameState.AIPlayer.drawACard(out);
		//gameState.AIPlayer.updateHandCardsView(out);
		Card card_ai = gameState.AIPlayer.cardsInPlayerHand.get(0);
		if(card_ai.getId() == 20){
			Tile[] tiles = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
			Random random = new Random();
			Tile tile = tiles[random.nextInt(tiles.length)];
			card_ai.spellOf03(out,gameState,card_ai,tile);
		}else if(card_ai.getId() == 21){
			Tile[] tiles = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
			Random random = new Random();
			Tile tile = tiles[random.nextInt(tiles.length)];
			card_ai.spellOf03(out,gameState,card_ai,tile);
		}else{
			gameState.AIPlayer.determineTilesCanSummon(out,gameState,gameState.AIPlayer.map_Unit_ai);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			Random random = new Random();
			Tile tile = gameState.AIPlayer.tiles_canSummon.get(random.nextInt(gameState.AIPlayer.tiles_canSummon.size()));
			gameState.AIPlayer.summonCardToUnit(out,gameState,card_ai,gameState.AIPlayer.ai_cardToUnit.get(card_ai),tile, gameState.AIPlayer, 1,1);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			gameState.AIPlayer.deleteTilesCanSummon(out,gameState.AIPlayer.tiles_canSummon);
		}

		for(Card card : gameState.AIPlayer.cardsShouldBeRemove){
			gameState.AIPlayer.cardsInPlayerHand.remove(card);
		}
		gameState.AIPlayer.cardsShouldBeRemove.clear();

		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
    }
}
