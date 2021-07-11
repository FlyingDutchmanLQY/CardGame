package structures.basic;

import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import java.util.HashMap;

public class HumanPlayer extends Player{


    public HashMap<Card,Unit> human_cardToUnit = new HashMap<>();
    public HashMap<Tile,Unit> map_Unit_human = new HashMap<>();

    public HumanPlayer() {
        super();
    }
    public HumanPlayer(int health, int mana) {
        super(health, mana);
    }

    public void initDeck(){
        Card cardTemp;
        Unit unit;
        int id = 0;

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_truestrike, id++, Card.class);
        this.deck.add(cardTemp);
        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_sundrop_elixir, id++, Card.class);
        this.deck.add(cardTemp);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_comodo_charger, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_comodo_charger,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_pureblade_enforcer, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_pureblade_enforcer,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azure_herald, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azure_herald,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_silverguard_knight, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_silverguard_knight,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azurite_lion, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_fire_spitter, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_fire_spitter,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_ironcliff_guardian, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_ironcliff_guardian,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);


        //second

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_truestrike, id++, Card.class);
        this.deck.add(cardTemp);
        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_sundrop_elixir, id++, Card.class);
        this.deck.add(cardTemp);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_comodo_charger, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_comodo_charger,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_pureblade_enforcer, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_pureblade_enforcer,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azure_herald, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azure_herald,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_silverguard_knight, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_silverguard_knight,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_azurite_lion, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_fire_spitter, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_fire_spitter,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);

        cardTemp = BasicObjectBuilders.loadCard(StaticConfFiles.c_ironcliff_guardian, id, Card.class);
        unit = BasicObjectBuilders.loadUnit(StaticConfFiles.u_ironcliff_guardian,id++,Unit.class);
        unit.attack = cardTemp.bigCard.attack;
        unit.health = cardTemp.bigCard.health;
        unit.rawHealth = unit.health;
        this.deck.add(cardTemp);
        human_cardToUnit.put(cardTemp,unit);


        shuffleDeck();
    }
}
