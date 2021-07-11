<<<<<<< Updated upstream
package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import static events.TileClicked.tiles_canMove;

/**
 * This is a representation of a Unit on the game board.
 * A unit has a unique id (this is used by the front-end.
 * Each unit has a current UnitAnimationType, e.g. move,
 * or attack. The position is the physical position on the
 * board. UnitAnimationSet contains the underlying information
 * about the animation frames, while ImageCorrection has
 * information for centering the unit on the tile. 
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Unit {

	@JsonIgnore
	protected static ObjectMapper mapper = new ObjectMapper(); // Jackson Java Object Serializer, is used to read java objects from a file
	
	int id;
	public int health;
	public int attack;
	public int rawHealth;
	UnitAnimationType animation;
	Position position;
	UnitAnimationSet animations;
	ImageCorrection correction;
	
	public Unit() {}
	
	public Unit(int id, UnitAnimationSet animations, ImageCorrection correction) {
		super();
		this.id = id;
		this.animation = UnitAnimationType.idle;
		
		position = new Position(0,0,0,0);
		this.correction = correction;
		this.animations = animations;
	}
	
	public Unit(int id, UnitAnimationSet animations, ImageCorrection correction, Tile currentTile) {
		super();
		this.id = id;
		this.animation = UnitAnimationType.idle;
		
		position = new Position(currentTile.getXpos(),currentTile.getYpos(),currentTile.getTilex(),currentTile.getTiley());
		this.correction = correction;
		this.animations = animations;
	}
	
	
	
	public Unit(int id, UnitAnimationType animation, Position position, UnitAnimationSet animations,
			ImageCorrection correction) {
		super();
		this.id = id;
		this.animation = animation;
		this.position = position;
		this.animations = animations;
		this.correction = correction;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UnitAnimationType getAnimation() {
		return animation;
	}
	public void setAnimation(UnitAnimationType animation) {
		this.animation = animation;
	}

	public ImageCorrection getCorrection() {
		return correction;
	}

	public void setCorrection(ImageCorrection correction) {
		this.correction = correction;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public UnitAnimationSet getAnimations() {
		return animations;
	}

	public void setAnimations(UnitAnimationSet animations) {
		this.animations = animations;
	}
	
	/**
	 * This command sets the position of the Unit to a specified
	 * tile.
	 * @param tile
	 */
	@JsonIgnore
	public void setPositionByTile(Tile tile) {
		position = new Position(tile.getXpos(),tile.getYpos(),tile.getTilex(),tile.getTiley());
	}

	public boolean isAttack(ActorRef out, Tile start, Tile end){
		int x = Math.abs(start.getTilex() - end.getTilex());
		int y = Math.abs(start.getTiley() - end.getTiley());

		if(( x == 1 && y == 0) || (x == 0 && y == 1) || (x == 1 && y == 1)){
			return true;
		}else{
			return false;
		}
	}

	public void rangeOfMove(ActorRef out, GameState gameState, Tile tile, int n){
		int x = tile.getTilex();
		int y = tile.getTiley();

		if(x < 8 && gameState.map_Unit.get(gameState.board[x+1][y]) == null){
			tiles_canMove.add(gameState.board[x+1][y]);
			BasicCommands.drawTile(out,gameState.board[x+1][y],n);
			if(x+2<9 && gameState.map_Unit.get(gameState.board[x+2][y]) == null){
				tiles_canMove.add(gameState.board[x+2][y]);
				BasicCommands.drawTile(out,gameState.board[x+2][y],n);
			}
		}
		if(0 < x && gameState.map_Unit.get(gameState.board[x-1][y]) == null){
			tiles_canMove.add(gameState.board[x-1][y]);
			BasicCommands.drawTile(out,gameState.board[x-1][y],n);
			if(x-2>-1 && gameState.map_Unit.get(gameState.board[x-2][y]) == null){
				tiles_canMove.add(gameState.board[x-2][y]);
				BasicCommands.drawTile(out,gameState.board[x-2][y],n);
			}
		}
		if(y < 4 && gameState.map_Unit.get(gameState.board[x][y+1]) == null){
			tiles_canMove.add(gameState.board[x][y+1]);
			BasicCommands.drawTile(out,gameState.board[x][y+1],n);
			if(y+2<5 && gameState.map_Unit.get(gameState.board[x][y+2]) == null){
				tiles_canMove.add(gameState.board[x][y+2]);
				BasicCommands.drawTile(out,gameState.board[x][y+2],n);
			}
		}
		if(0 < y && gameState.map_Unit.get(gameState.board[x][y-1]) == null){
			tiles_canMove.add(gameState.board[x][y-1]);
			BasicCommands.drawTile(out,gameState.board[x][y-1],n);
			if(y-2>-1 && gameState.map_Unit.get(gameState.board[x][y-2]) == null){
				tiles_canMove.add(gameState.board[x][y-2]);
				BasicCommands.drawTile(out,gameState.board[x][y-2],n);
			}
		}

		if(x+1<9 && y+1<5 && gameState.map_Unit.get(gameState.board[x+1][y+1]) == null){
			tiles_canMove.add(gameState.board[x+1][y+1]);
			BasicCommands.drawTile(out,gameState.board[x+1][y+1],n);
		}
		if(x+1<9 && y-1 > -1 && gameState.map_Unit.get(gameState.board[x+1][y-1]) == null){
			tiles_canMove.add(gameState.board[x+1][y-1]);
			BasicCommands.drawTile(out,gameState.board[x+1][y-1],n);
		}
		if(x-1>-1 && y+1<5 && gameState.map_Unit.get(gameState.board[x-1][y+1]) == null){
			tiles_canMove.add(gameState.board[x-1][y+1]);
			BasicCommands.drawTile(out,gameState.board[x-1][y+1],n);
		}
		if(x-1>-1 && y-1>-1 && gameState.map_Unit.get(gameState.board[x-1][y-1]) == null){
			tiles_canMove.add(gameState.board[x-1][y-1]);
			BasicCommands.drawTile(out,gameState.board[x-1][y-1],n);
		}

//		if(x+2<9 && GameState.map_Unit.get(GameState.board[x+2][y]) == null){
//			tiles_canMove.add(GameState.board[x+2][y]);
//			BasicCommands.drawTile(out,GameState.board[x+2][y],n);
//		}
//		if(x-2>-1 && GameState.map_Unit.get(GameState.board[x-2][y]) == null){
//			tiles_canMove.add(GameState.board[x-2][y]);
//			BasicCommands.drawTile(out,GameState.board[x-2][y],n);
//		}
//		if(y+2<5 && GameState.map_Unit.get(GameState.board[x][y+2]) == null){
//			tiles_canMove.add(GameState.board[x][y+2]);
//			BasicCommands.drawTile(out,GameState.board[x][y+2],n);
//		}
//		if(y-2>-1 && GameState.map_Unit.get(GameState.board[x][y-2]) == null){
//			tiles_canMove.add(GameState.board[x][y-2]);
//			BasicCommands.drawTile(out,GameState.board[x][y-2],n);
//		}
	}

	public void moveUnit(ActorRef out, GameState gameState,Unit unit, Tile start, Tile end){
		if(tiles_canMove.contains(end)) {
			gameState.map_Unit.remove(start);
			gameState.humanPlayer.map_Unit_human.remove(start);
			BasicCommands.playUnitAnimation(out,unit,UnitAnimationType.move);
			BasicCommands.moveUnitToTile(out, unit, end);
			unit.setPositionByTile(end);
			gameState.map_Unit.put(end, unit);
			gameState.humanPlayer.map_Unit_human.put(end, unit);
		}else{
			BasicCommands.addPlayer1Notification(out,"can't move",2);
		}

	}

	public void attack(ActorRef out, GameState gameState, Tile start, Tile end){
		if(gameState.AIPlayer.map_Unit_ai.containsKey(end)){
			if (gameState.map_Unit.get(start).getId() == 8) {
				attackAnywhere(out, gameState, start, end);
			} else if (gameState.map_Unit.get(start).getId() == 18) {
				attackAnywhere(out, gameState, start, end);
			} else if (!isAttack(out, start, end)) {
				BasicCommands.addPlayer1Notification(out, "can't attack", 2);
			} else {
				mustAttack(out, gameState, start, end);
			}
		}else{
			BasicCommands.addPlayer1Notification(out, "can't attack yourself", 2);
		}
	}


	public void mustAttack(ActorRef out, GameState gameState, Tile start, Tile end){
			Unit unit_Start = gameState.map_Unit.get(start);
			Unit unit_End = gameState.map_Unit.get(end);
			if(unit_Start.getId() == 7){
				twoAttack(out,gameState,start,end);
			}
			if(unit_Start.getId() == 17){
				twoAttack(out,gameState,start,end);
			}else{
				BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
				BasicCommands.playUnitAnimation(out,unit_End,UnitAnimationType.hit);
				changeHealth(out,gameState,end,unit_Start.attack);
			}
		}

		public void changeHealth(ActorRef out, GameState gameState, Tile tile, int health){
			Unit unit = gameState.map_Unit.get(tile);
			unit.health -= health;
			BasicCommands.setUnitHealth(out, unit, unit.health);
			determineUnitStatue(out,gameState,tile);
			if(unit.getId() == 0 && health > 0){
				for(Unit u : gameState.unitList){
					if(u.getId() == 6){
						BasicCommands.addPlayer1Notification(out,"Ability launch",2);
						u.attack += 2;
						BasicCommands.setUnitAttack(out,u,u.attack);
					}
					if(u.getId() == 16){
						BasicCommands.addPlayer1Notification(out,"Ability launch",2);
						u.attack += 2;
						BasicCommands.setUnitAttack(out,u,u.attack);
					}
				}
			}
		}

		public void determineUnitStatue(ActorRef out, GameState gameState,Tile tile){
			Unit unit = gameState.map_Unit.get(tile);
			int id = unit.getId();
			if (id == 0) {
				gameState.humanPlayer.setHealth(unit.health);
				BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
			}
			if (id == 1) {
				gameState.AIPlayer.setHealth(unit.health);
				BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
			}
			if (unit.health <= 0) {
				BasicCommands.playUnitAnimation(out, unit, UnitAnimationType.death);
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				BasicCommands.deleteUnit(out, unit);
				if(id == 0 || id == 1){
					BasicCommands.addPlayer1Notification(out,"Game Over",2);
				}else {
					gameState.map_Unit.remove(tile);
					if(gameState.humanPlayer.map_Unit_human.containsKey(tile)) {
						gameState.humanPlayer.map_Unit_human.remove(tile);
						BasicCommands.addPlayer1Notification(out, "unit died", 2);
					}else if(gameState.AIPlayer.map_Unit_ai.containsKey(tile)){
						gameState.AIPlayer.map_Unit_ai.remove(tile);
						BasicCommands.addPlayer1Notification(out, "unit died", 2);
					}
				}
			}
		}

		public void twoAttack(ActorRef out,GameState gameState,Tile start, Tile end){
			Unit unit_Start = gameState.map_Unit.get(start);
			Unit unit_End = gameState.map_Unit.get(end);
			BasicCommands.addPlayer1Notification(out,"Two attacks",2);
			BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
			BasicCommands.playUnitAnimation(out,unit_End,UnitAnimationType.hit);
			changeHealth(out,gameState,end,(2 * unit_Start.attack));
		}

		public void attackAnywhere(ActorRef out,GameState gameState, Tile start, Tile end){
			BasicCommands.addPlayer1Notification(out,"attack any enemy",2);
			BasicCommands.playProjectileAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_projectiles),0,start,end);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			mustAttack(out, gameState, start, end);
		}

	
	
}
=======
package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.BasicCommands;
import structures.GameState;
import structures.services.DoCheckWinner;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

import java.util.ArrayList;

//import static events.TileClicked.tiles_canMove;

/**
 * This is a representation of a Unit on the game board.
 * A unit has a unique id (this is used by the front-end.
 * Each unit has a current UnitAnimationType, e.g. move,
 * or attack. The position is the physical position on the
 * board. UnitAnimationSet contains the underlying information
 * about the animation frames, while ImageCorrection has
 * information for centering the unit on the tile. 
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Unit {

	@JsonIgnore
	protected static ObjectMapper mapper = new ObjectMapper(); // Jackson Java Object Serializer, is used to read java objects from a file
	
	int id;
	public int health;
	public int attack;
	public int rawHealth;
	UnitAnimationType animation;
	Position position;
	UnitAnimationSet animations;
	ImageCorrection correction;
	public int remainingAttackTimes = 1;
	public int remainingMoveTimes = 1;
	public ArrayList<Tile> tiles_canMove = new ArrayList<>();


	public Unit() {}
	
	public Unit(int id, UnitAnimationSet animations, ImageCorrection correction) {
		super();
		this.id = id;
		this.animation = UnitAnimationType.idle;
		
		position = new Position(0,0,0,0);
		this.correction = correction;
		this.animations = animations;
	}
	
	public Unit(int id, UnitAnimationSet animations, ImageCorrection correction, Tile currentTile) {
		super();
		this.id = id;
		this.animation = UnitAnimationType.idle;
		
		position = new Position(currentTile.getXpos(),currentTile.getYpos(),currentTile.getTilex(),currentTile.getTiley());
		this.correction = correction;
		this.animations = animations;
	}
	
	
	
	public Unit(int id, UnitAnimationType animation, Position position, UnitAnimationSet animations,
			ImageCorrection correction) {
		super();
		this.id = id;
		this.animation = animation;
		this.position = position;
		this.animations = animations;
		this.correction = correction;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UnitAnimationType getAnimation() {
		return animation;
	}
	public void setAnimation(UnitAnimationType animation) {
		this.animation = animation;
	}

	public ImageCorrection getCorrection() {
		return correction;
	}

	public void setCorrection(ImageCorrection correction) {
		this.correction = correction;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public UnitAnimationSet getAnimations() {
		return animations;
	}

	public void setAnimations(UnitAnimationSet animations) {
		this.animations = animations;
	}
	
	/**
	 * This command sets the position of the Unit to a specified
	 * tile.
	 * @param tile
	 */
	@JsonIgnore
	public void setPositionByTile(Tile tile) {
		position = new Position(tile.getXpos(),tile.getYpos(),tile.getTilex(),tile.getTiley());
	}

	public Tile getCurrentTile(GameState gameState){		//possibly cause ArrayOutOfBound error
		return gameState.board[this.getPosition().getTilex()][this.getPosition().getTiley()];
	}

	/* Involving effects:
	 * 	id	name
	 *	8	Azurite Lion	attack twice
	 * 	31	serpenti	attack twice
	 * */
	public void resetRemainingAtkMovTimes(){
		this.remainingAttackTimes = 1;
		this.remainingMoveTimes = 1;
		if(this.getId() == 8 || this.getId() == 18 || this.getId() == 31 || this.getId() == 41){
			this.remainingAttackTimes = 2;
		}
	}

	public boolean isFromHuman(){
		if(this.getId() < 21){
			return true;
		}
		else {
			return false;
		}
	}

	/* Involving effects:
	 * 	id	name
	 *	7	silverguard knight	Provoke
 	 * 	10  c_ironcliff_guardian	Provoke
 	 * 	25	c_rock_pulveriser	Provoke
	 * */
	//This method checks if silverguard knight is around, provoking this unit. Return Unit(the provoking source) if exists
	public Unit isBeingProvoked(GameState gameState){
		boolean isHuman = this.isFromHuman();
		Tile thisTile = this.getCurrentTile(gameState);
		int x = thisTile.tilex;
		int y = thisTile.tiley;
		ArrayList<Tile> adjacentTileList = new ArrayList<Tile>();
		try {adjacentTileList.add(gameState.board[x + 1][y]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x + 1][y + 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x + 1][y - 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x][y + 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x][y - 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x - 1][y]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x - 1][y - 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}
		try {adjacentTileList.add(gameState.board[x - 1][y + 1]);}
		catch (ArrayIndexOutOfBoundsException e) { System.out.println("out of bound");}

		for (Tile t : adjacentTileList ) {
			Unit u = gameState.unitPositionMap.get(t);
			if(u != null && isHuman){
				int id = u.getId();
				if(id == 25 || id == 35)	return u;
			}
			if(u != null && !isHuman){
				int id = u.getId();
				if(id == 7 || id == 17 || id == 10 || id == 20)	return u;
			}
		}
		return null;
	}

	/* Involving effects:
	 * 	id	name
	 * 	9	fire spitter	Ranged attack
	 * 	26	c_pyromancer	Ranged attack
	 * */
	public boolean isInAttackRange(ActorRef out, GameState gameState, Unit target){
		Tile start = this.getCurrentTile(gameState);
		Tile end = target.getCurrentTile(gameState);
		int x = Math.abs(start.getTilex() - end.getTilex());
		int y = Math.abs(start.getTiley() - end.getTiley());

		if(( x == 1 && y == 0) || (x == 0 && y == 1) || (x == 1 && y == 1)){
			return true;
		}else if(this.getId() == 9 || this.getId() == 19 || this.getId() == 26 || this.getId() == 36){
			BasicCommands.addPlayer1Notification(out,"Ranged Attack",2);
			BasicCommands.playProjectileAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_projectiles), 0, start, end);
			return true;
		}
		else {
			BasicCommands.addPlayer1Notification(out, "Out Of Range", 2);
			return false;
		}
	}

	public boolean isInAttackRangeForAI(ActorRef out, GameState gameState, Unit target){
		Tile start = this.getCurrentTile(gameState);
		Tile end = target.getCurrentTile(gameState);
		int x = Math.abs(start.getTilex() - end.getTilex());
		int y = Math.abs(start.getTiley() - end.getTiley());

		if(( x == 1 && y == 0) || (x == 0 && y == 1) || (x == 1 && y == 1)){
			return true;
		}else if(this.getId() == 9 || this.getId() == 19 || this.getId() == 26 || this.getId() == 36){
			BasicCommands.playProjectileAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_projectiles), 0, start, end);
			return true;
		}
		else {
			return false;
		}
	}

	/* Involving effects:
	 * 	id	name
	 * 	29  wind shrike	Flying: Can move anywhere on the board
	 * */
	public void displayRangeOfMovement(ActorRef out, GameState gameState, Tile tile, int n){
		int x = tile.getTilex();
		int y = tile.getTiley();

		this.tiles_canMove.clear();
		if(this.getId() == 29 || this.getId() == 39){
			for(int i=0;i<9;i++){
				for(int j=0;j<5;j++){
					if(gameState.board[i][j] == null){
						this.tiles_canMove.add(gameState.board[i][j]);
						BasicCommands.drawTile(out, gameState.board[i][j], n);
					}
				}
			}
		}
		else{
			if(x < 8 && gameState.unitPositionMap.get(gameState.board[x+1][y]) == null){
				tiles_canMove.add(gameState.board[x+1][y]);
				BasicCommands.drawTile(out,gameState.board[x+1][y],n);
				if(x+2<9 && gameState.unitPositionMap.get(gameState.board[x+2][y]) == null){
					tiles_canMove.add(gameState.board[x+2][y]);
					BasicCommands.drawTile(out,gameState.board[x+2][y],n);
				}
			}
			if(0 < x && gameState.unitPositionMap.get(gameState.board[x-1][y]) == null){
				tiles_canMove.add(gameState.board[x-1][y]);
				BasicCommands.drawTile(out,gameState.board[x-1][y],n);
				if(x-2>-1 && gameState.unitPositionMap.get(gameState.board[x-2][y]) == null){
					tiles_canMove.add(gameState.board[x-2][y]);
					BasicCommands.drawTile(out,gameState.board[x-2][y],n);
				}
			}
			if(y < 4 && gameState.unitPositionMap.get(gameState.board[x][y+1]) == null){
				tiles_canMove.add(gameState.board[x][y+1]);
				BasicCommands.drawTile(out,gameState.board[x][y+1],n);
				if(y+2<5 && gameState.unitPositionMap.get(gameState.board[x][y+2]) == null){
					tiles_canMove.add(gameState.board[x][y+2]);
					BasicCommands.drawTile(out,gameState.board[x][y+2],n);
				}
			}
			if(0 < y && gameState.unitPositionMap.get(gameState.board[x][y-1]) == null){
				tiles_canMove.add(gameState.board[x][y-1]);
				BasicCommands.drawTile(out,gameState.board[x][y-1],n);
				if(y-2>-1 && gameState.unitPositionMap.get(gameState.board[x][y-2]) == null){
					tiles_canMove.add(gameState.board[x][y-2]);
					BasicCommands.drawTile(out,gameState.board[x][y-2],n);
				}
			}

			if(x+1<9 && y+1<5 && gameState.unitPositionMap.get(gameState.board[x+1][y+1]) == null && (tiles_canMove.contains(gameState.board[x+1][y]) || tiles_canMove.contains(gameState.board[x][y+1]))){
				tiles_canMove.add(gameState.board[x+1][y+1]);
				BasicCommands.drawTile(out,gameState.board[x+1][y+1],n);
			}
			if(x+1<9 && y-1 > -1 && gameState.unitPositionMap.get(gameState.board[x+1][y-1]) == null && (tiles_canMove.contains(gameState.board[x+1][y]) || tiles_canMove.contains(gameState.board[x][y-1]))){
				tiles_canMove.add(gameState.board[x+1][y-1]);
				BasicCommands.drawTile(out,gameState.board[x+1][y-1],n);
			}
			if(x-1>-1 && y+1<5 && gameState.unitPositionMap.get(gameState.board[x-1][y+1]) == null && (tiles_canMove.contains(gameState.board[x-1][y]) || tiles_canMove.contains(gameState.board[x][y+1]))){
				tiles_canMove.add(gameState.board[x-1][y+1]);
				BasicCommands.drawTile(out,gameState.board[x-1][y+1],n);
			}
			if(x-1>-1 && y-1>-1 && gameState.unitPositionMap.get(gameState.board[x-1][y-1]) == null && (tiles_canMove.contains(gameState.board[x-1][y]) || tiles_canMove.contains(gameState.board[x][y-1]))){
				tiles_canMove.add(gameState.board[x-1][y-1]);
				BasicCommands.drawTile(out,gameState.board[x-1][y-1],n);
			}
		}

	}

	public void updateMapUnit(GameState gameState,Tile start, Tile end){
		gameState.unitPositionMap.remove(start);
		if(gameState.humanPlayer.map_Unit_human.containsKey(start)){
			gameState.humanPlayer.map_Unit_human.remove(start);
			gameState.humanPlayer.map_Unit_human.put(end, this);
		}else{
			gameState.AIPlayer.map_Unit_ai.remove(start);
			gameState.AIPlayer.map_Unit_ai.put(end, this);
		}
		this.setPositionByTile(end);
		gameState.unitPositionMap.put(end, this);
	}

	public void moveUnit(ActorRef out, GameState gameState, Tile end){
		Tile start = this.getCurrentTile(gameState);

		if(this.remainingMoveTimes <= 0){
			BasicCommands.addPlayer1Notification(out,"This Minion Has Moved Before In This Round",2);
			return;
		}

		if(tiles_canMove.contains(end)) {
			BasicCommands.playUnitAnimation(out, this, UnitAnimationType.move);

			if(Math.abs(end.tilex - start.tilex) == 1 && Math.abs(end.tiley - start.tiley) == 1){
				if(tiles_canMove.contains(gameState.board[end.tilex][start.tiley])){
					BasicCommands.moveUnitToTile(out, this, gameState.board[end.tilex][start.tiley]);
					this.updateMapUnit(gameState,start,gameState.board[end.tilex][start.tiley]);
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
					BasicCommands.moveUnitToTile(out, this, end);
					this.updateMapUnit(gameState,gameState.board[end.tilex][start.tiley],end);
				}else{
					BasicCommands.moveUnitToTile(out, this, gameState.board[start.tilex][end.tiley]);
					this.updateMapUnit(gameState,start,gameState.board[start.tilex][end.tiley]);
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
					BasicCommands.moveUnitToTile(out, this, end);
					this.updateMapUnit(gameState,gameState.board[start.tilex][end.tiley],end);
				}
			}else{
				this.updateMapUnit(gameState,start,end);
				BasicCommands.moveUnitToTile(out, this, end);
			}
			this.remainingMoveTimes--;
		}else{
			BasicCommands.addPlayer1Notification(out,"can't move",2);
		}
	}

	/*public void attack(ActorRef out, GameState gameState, Tile start, Tile end){
		if(gameState.AIPlayer.map_Unit_ai.containsKey(end)){
			if (gameState.map_Unit.get(start).getId() == 8) {
				attackAnywhere(out, gameState, start, end);
			} else if (gameState.map_Unit.get(start).getId() == 18) {
				attackAnywhere(out, gameState, start, end);
			} else if (!isInAttackRange(out, start, end)) {
				BasicCommands.addPlayer1Notification(out, "can't attack", 2);
			} else {
				mustAttack(out, gameState, start, end);
			}
		}else{
			BasicCommands.addPlayer1Notification(out, "can't attack yourself", 2);
		}
	}*/

	/* Involving effects:
	* 	id	name
	*	7	silverguard knight	Provoke
	* */
	public void attack(ActorRef out, GameState gameState, Unit targetUnit){  //a new version of attack

		//a unit can only attack 1 time per turn
		if(this.remainingAttackTimes <= 0) {
			BasicCommands.addPlayer1Notification(out, "This Minion Has Attacked Before In This Round", 2);
			return;
		}

		//This is to check if the target is enemy
		if( !( targetUnit.isFromHuman() ^ this.isFromHuman() ) ) {        //id<=20 is human,id>=21 is AI
			BasicCommands.addPlayer1Notification(out, "Can't Attack Your Own Minion", 2);
			return;
		}

		Unit isBeingProvoked = this.isBeingProvoked(gameState);
		//This is to check if being provoked
		if(isBeingProvoked == null) {
			//this is to check if  is in range
			if (isInAttackRange(out, gameState, targetUnit)) {
				//Normally goes in here
				mustAttack(out, gameState, targetUnit);
			}

		} else if(
				targetUnit.getId() != 7 &&
				targetUnit.getId() != 17 &&
				targetUnit.getId() != 10 &&
				targetUnit.getId() != 20 &&
				targetUnit.getId() != 25 &&
				targetUnit.getId() != 35 )
		{ //********************logic error, may need to reconsider
			BasicCommands.addPlayer1Notification(out, "You Must Attack The Minion With Provocation!", 2);
		} else {	//attack the silverguard knight
			mustAttack(out, gameState, targetUnit);
		}
	}

	public void mustAttack(ActorRef out, GameState gameState, Unit targetUnit){
		BasicCommands.playUnitAnimation(out, this, UnitAnimationType.attack);
		BasicCommands.playUnitAnimation(out, targetUnit, UnitAnimationType.hit);
		changeHealth(out, gameState, targetUnit, this.attack);
		this.remainingAttackTimes--;
	}

	/* Involving effects:
	 * 	id	name
	 *	7	silverguard_knight	avatar dealt damage,+2 ATK to itself
	 * */
	public void changeHealth(ActorRef out, GameState gameState, Unit targetUnit, int health){
		targetUnit.health -= health;
		BasicCommands.setUnitHealth(out, targetUnit, targetUnit.health);
		targetUnit.determineUnitStatus(out, gameState);
		if(targetUnit.getId() == 0 && health > 0){
			for(Unit u : gameState.unitList){
				if(u.getId() == 7 || u.getId() == 17){
					BasicCommands.addPlayer1Notification(out,"Silverguard Knight Ability Launch,add 2 ATK",2);
					u.attack += 2;
					BasicCommands.setUnitAttack(out,u,u.attack);
				}
			}
		}
	}

	/*public void changeHealth(ActorRef out, GameState gameState, Tile tile, int health){
		Unit unit = gameState.unitPositionMap.get(tile);
		unit.health -= health;
		BasicCommands.setUnitHealth(out, unit, unit.health);
		determineUnitStatue(out,gameState,tile);
		if(unit.getId() == 0 && health > 0){
			for(Unit u : gameState.unitList){
				if(u.getId() == 7 || u.getId() == 17){
					BasicCommands.addPlayer1Notification(out,"Ability launch",2);
					u.attack += 2;
					BasicCommands.setUnitAttack(out,u,u.attack);
				}
			}
		}
	}*/

	/* Involving effects:
	 * 	id	name
	 *	29  wind shrike	When this unit dies, its owner draws a card
	 * */
	public void determineUnitStatus(ActorRef out, GameState gameState){
		if(this.getId() == 0) {
			gameState.humanPlayer.setHealth(this.health);
			BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		}
		if(this.getId() == 21) {
			gameState.AIPlayer.setHealth(this.health);
			BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
		}
		if(this.health <= 0) {
			BasicCommands.playUnitAnimation(out, this, UnitAnimationType.death);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.deleteUnit(out, this);
			if(this.getId() == 0 || this.getId() == 21){
				DoCheckWinner doCheckWinner = new DoCheckWinner();
				doCheckWinner.checkWinner(out, gameState);
			} else {
				Tile tile = this.getCurrentTile(gameState);
				gameState.unitPositionMap.remove(tile);
				if(gameState.humanPlayer.map_Unit_human.containsKey(tile)) {
					gameState.humanPlayer.map_Unit_human.remove(tile);
					BasicCommands.addPlayer1Notification(out, "unit died", 2);
				}else if(gameState.AIPlayer.map_Unit_ai.containsKey(tile)){
					gameState.AIPlayer.map_Unit_ai.remove(tile);
					BasicCommands.addPlayer1Notification(out, "unit died", 2);
				}
				if(this.getId() == 29 || this.getId() == 39) {
					gameState.AIPlayer.drawACard(out);
					BasicCommands.addPlayer1Notification(out, "Wind Shrike Ability: Deathrattle, Owner Draws A Card", 2);
				}
			}
		}
	}

	/*public void determineUnitStatue(ActorRef out, GameState gameState,Tile tile){
		Unit unit = gameState.unitPositionMap.get(tile);
		int id = unit.getId();
		if (id == 0) {
			gameState.humanPlayer.setHealth(unit.health);
			BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		}
		if (id == 1) {
			gameState.AIPlayer.setHealth(unit.health);
			BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
		}
		if (unit.health <= 0) {
			BasicCommands.playUnitAnimation(out, unit, UnitAnimationType.death);
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.deleteUnit(out, unit);
			if(id == 0 || id == 1){
				BasicCommands.addPlayer1Notification(out,"Game Over",2);
			}else {
				gameState.unitPositionMap.remove(tile);
				if(gameState.humanPlayer.map_Unit_human.containsKey(tile)) {
					gameState.humanPlayer.map_Unit_human.remove(tile);
					BasicCommands.addPlayer1Notification(out, "unit died", 2);
				}else if(gameState.AIPlayer.map_Unit_ai.containsKey(tile)){
					gameState.AIPlayer.map_Unit_ai.remove(tile);
					BasicCommands.addPlayer1Notification(out, "unit died", 2);
				}
			}
		}
	}*/

	/*public void twoAttack(ActorRef out,GameState gameState,Tile start, Tile end){
		Unit unit_Start = gameState.unitPositionMap.get(start);
		Unit unit_End = gameState.unitPositionMap.get(end);
		BasicCommands.addPlayer1Notification(out,"Two attacks",2);
		BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
		BasicCommands.playUnitAnimation(out,unit_End,UnitAnimationType.hit);
		changeHealth(out,gameState,end,(2 * unit_Start.attack));
	}*/

	/*public void attackAnywhere(ActorRef out,GameState gameState, Tile start, Tile end){
		BasicCommands.addPlayer1Notification(out,"attack any enemy",2);
		BasicCommands.playProjectileAnimation(out, BasicObjectBuilders.loadEffect(StaticConfFiles.f1_projectiles),0,start,end);
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		mustAttack(out, gameState, start, end);
	}*/

	public void azureHeraldAbility(ActorRef out, GameState gameState, int health){
		BasicCommands.addPlayer1Notification(out,"Azure Herald Ability Launch. Add 3 HP To Your Avatar", 2);
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

	public void blazeHoundAbility(ActorRef out, GameState gameState){
		BasicCommands.addPlayer1Notification(out,"Blaze Hound Ability Launch. Both Players Draw A Card", 2);
		gameState.humanPlayer.drawACard(out);
		gameState.AIPlayer.drawACard(out);

	}

}

>>>>>>> Stashed changes
