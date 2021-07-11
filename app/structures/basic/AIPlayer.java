package structures.basic;

<<<<<<< Updated upstream
=======
import commands.BasicCommands;
import structures.GameState;
import structures.services.DoSpellCast;
import structures.services.DoUnitAttack;
import structures.services.DoUnitMovement;
>>>>>>> Stashed changes
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
        int id = 20;

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
<<<<<<< Updated upstream
=======

    public void AI(ActorRef out, GameState gameState){
        BasicCommands.addPlayer1Notification(out,"It's AI",2);
		try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
		//AIPlayer aiPlayer = gameState.AIPlayer;
		gameState.AIPlayer.drawACard(out);

		gameState.AIPlayer.aiUseCard(out,gameState);
		sleep(1000);
        gameState.AIPlayer.aiUnitMove(out, gameState);
        sleep(1000);
        gameState.AIPlayer.aiUnitAttack(out, gameState);
        sleep(300);
    }

    public void aiUseCard(ActorRef out, GameState gameState){
        Random random = new Random();
        Tile[] tilesHuman = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
        Tile tileOfTarget = tilesHuman[random.nextInt(tilesHuman.length)];
        Unit target = gameState.humanPlayer.map_Unit_human.get(tileOfTarget);
        //gameState.AIPlayer.updateHandCardsView(out);
        Card card_ai = this.chooseAValidHandCard();
        if(card_ai != null){
            if(card_ai.getId() == 22 || card_ai.getId() == 32){
                DoSpellCast doSpellCast = new DoSpellCast();
                for(Unit u : gameState.AIPlayer.map_Unit_ai.values()){
                    if(u.getId() == 21){
                        doSpellCast.castSpell(out, gameState, card_ai, u, this);
                        return;
                    }
                }
            }else if(card_ai.getId() == 23 || card_ai.getId() == 33){
                DoSpellCast doSpellCast = new DoSpellCast();
                doSpellCast.castSpell(out, gameState, card_ai, target, this);
            }else{
                gameState.AIPlayer.determineTilesCanSummon(out,gameState,gameState.AIPlayer.map_Unit_ai);
                try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
                Tile tile = gameState.AIPlayer.tiles_canSummon.get(random.nextInt(gameState.AIPlayer.tiles_canSummon.size()));
                gameState.AIPlayer.summonCardToUnit(out, gameState, card_ai, gameState.AIPlayer.ai_cardToUnit.get(card_ai), tile);
                try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
                gameState.AIPlayer.deleteTilesCanSummon(out);
            }
        }

        for(Card card : gameState.AIPlayer.cardsShouldBeRemove){
            gameState.AIPlayer.cardsInPlayerHand.remove(card);
        }
        gameState.AIPlayer.cardsShouldBeRemove.clear();
    }

    public void aiUnitMove(ActorRef out, GameState gameState){
        Tile[] tiles = gameState.AIPlayer.map_Unit_ai.keySet().toArray(new Tile[0]);
        Random random = new Random();
        Tile tileOfUnit = tiles[random.nextInt(tiles.length)];
        Unit unit = gameState.AIPlayer.map_Unit_ai.get(tileOfUnit);

        unit.displayRangeOfMovement(out,gameState,tileOfUnit,2);
        sleep(1000);

        DoUnitMovement doUnitMovement = new DoUnitMovement();
        int n = unit.tiles_canMove.size();
        if(n > 0){
            doUnitMovement.moveUnit(out,gameState,unit,unit.tiles_canMove.get(random.nextInt(n)));
        }else{
            unit.displayRangeOfMovement(out, gameState, unit.getCurrentTile(gameState),0);
            return;
        }
    }

    public void aiUnitAttack(ActorRef out, GameState gameState){
        Tile[] tilesAI = gameState.AIPlayer.map_Unit_ai.keySet().toArray(new Tile[0]);
        Tile[] tilesHuman = gameState.humanPlayer.map_Unit_human.keySet().toArray(new Tile[0]);
        Random random = new Random();
        AIPlayer aiPlayer = gameState.AIPlayer;
        for(int i = 0; i < random.nextInt(aiPlayer.map_Unit_ai.size()); i++){
            Tile tileOfUnit = tilesAI[random.nextInt(tilesAI.length)];
            Unit attacker = gameState.AIPlayer.map_Unit_ai.get(tileOfUnit);
            Tile tileOfTarget = tilesHuman[random.nextInt(tilesHuman.length)];
            Unit target = gameState.humanPlayer.map_Unit_human.get(tileOfTarget);
            if(attacker.isInAttackRangeForAI(out,gameState,target)){
                BasicCommands.addPlayer1Notification(out,"AI attack",2);
                sleep(2000);
                BasicCommands.drawTile(out,tileOfUnit,2);
                sleep(1000);
                BasicCommands.addPlayer1Notification(out,"Target is",2);
                sleep(2000);
                BasicCommands.drawTile(out,tileOfTarget,2);
                sleep(2000);
                DoUnitAttack doUnitAttack = new DoUnitAttack();
                doUnitAttack.attackUnit(out,gameState,attacker,target);
                BasicCommands.drawTile(out,tileOfUnit,0);
                BasicCommands.drawTile(out,tileOfTarget,0);
                sleep(2000);
            }
        }
    }
    
    // thread sleep and exception cath
    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Card chooseAValidHandCard(){
        Card card = null;
        for (Card c : this.cardsInPlayerHand) {
            if(c.getManacost() <= this.mana){
                card = c;
                break;
            }
        }
        return card;
    }
>>>>>>> Stashed changes
}
