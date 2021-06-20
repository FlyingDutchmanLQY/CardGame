package structures.basic;

import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

public class AIPlayer extends Player{

    public AIPlayer() {
        super();
    }
    public AIPlayer(int health, int mana) {
        super(health, mana);
    }

    public void initDeck(){
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
}
