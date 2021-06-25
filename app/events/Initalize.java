package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Unit;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;


/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * 
 * { 
 *   messageType = “initalize”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Initalize implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		// this executes the command demo, comment out this when implementing your solution
//		CommandDemo.executeDemo(out);


		//画棋盘
		for(int i=0;i<9;i++){
			for(int j=0;j<5;j++){
				gameState.board[i][j] = BasicObjectBuilders.loadTile(i, j);
				BasicCommands.drawTile(out, gameState.board[i][j], 0);
			}
		}

		//属性初始化
//		gameState.humanPlayer = new Player(20, 0);
//		gameState.AIPlayer = new Player(20, 0);
		GameState.humanPlayer.initDeck();
		GameState.AIPlayer.initDeck();
		BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
//		gameState.humanPlayer.setMana(1);
//		BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);

		//画单位
		Unit unit_human = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, GameState.id_Unit++, Unit.class);
		gameState.unitList.add(unit_human);
		unit_human.setPositionByTile(gameState.board[1][2]);
		BasicCommands.drawUnit(out, unit_human, gameState.board[1][2]);
		GameState.map_Unit.put(gameState.board[1][2],unit_human);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out,unit_human,5);
		BasicCommands.setUnitHealth(out,unit_human,20);
		unit_human.health = 20;
		unit_human.attack = 5;


		Unit unit_ai = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, GameState.id_Unit++, Unit.class);
		gameState.unitList.add(unit_ai);
		unit_ai.setPositionByTile(gameState.board[7][2]);
		BasicCommands.drawUnit(out, unit_ai, gameState.board[7][2]);
		GameState.map_Unit.put(gameState.board[7][2],unit_ai);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out,unit_ai,5);
		BasicCommands.setUnitHealth(out,unit_ai,20);
		unit_ai.health = 20;
		unit_ai.attack = 5;

		//init deck
//		int id = 0;
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_truestrike, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_sundrop_elixir, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_comodo_charger, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_azure_herald, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_azurite_lion, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_fire_spitter, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_ironcliff_guardian, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_pureblade_enforcer, id++, Card.class));
//		GameState.cards.add(BasicObjectBuilders.loadCard(StaticConfFiles.c_silverguard_knight, id++, Card.class));

		//init unit
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_comodo_charger);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_hailstone_golem );
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_azure_herald);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_azurite_lion);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_pureblade_enforcer);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_ironcliff_guardian);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_silverguard_knight);
		GameState.unitStaticConfFiles.add(StaticConfFiles.u_fire_spitter);


		BasicCommands.addPlayer1Notification(out,"It's turn " + GameState.turn,2);
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
			//set mana
			GameState.humanPlayer.setMana(GameState.turn);
			BasicCommands.setPlayer1Mana(out,GameState.humanPlayer);

		//drew card

//		Random random = new Random();
//		Card card = GameState.cards.get(random.nextInt(GameState.cards.size()));
		Card card = GameState.humanPlayer.chooseACard();
//		BasicCommands.drawCard(out,card,GameState.humanPlayer.index_cardInHand++,0);
//		GameState.humanPlayer.cardsInPlayerHand.add(card);
		GameState.humanPlayer.updateHandCardsView(out);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}




//		Card hailstone_golem = BasicObjectBuilders.loadCard(StaticConfFiles.c_hailstone_golem, 0, Card.class);
//		BasicCommands.drawCard(out, hailstone_golem, 1, 0);
//		BasicCommands.drawCard(out, hailstone_golem, 2, 0);

//		int index_cardInHand = 1;
//
//		for (int turn = 1; GameState.humanPlayer.getHealth() > 0 && GameState.AIPlayer.getHealth() > 0 ; turn++) {
//			BasicCommands.addPlayer1Notification(out,"It's turn " + turn,2);
//			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
//			//set mana
//			GameState.humanPlayer.setMana(turn);
//			BasicCommands.setPlayer1Mana(out,GameState.humanPlayer);
//
//			//draw card
//			Random random = new Random();
//			BasicCommands.drawCard(out,GameState.cards.get(random.nextInt(GameState.cards.size())),index_cardInHand++,0);
//			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
//
//			//delete card
////			BasicCommands.deleteCard(out,CardClicked.handPosition);
//
//			while(!EndTurnClicked.isOver){
//				if(EndTurnClicked.isOver) continue;
//			}
//			if(turn == 2)break;
//		}


	}

}


