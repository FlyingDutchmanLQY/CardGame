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

	public Tile getCurrentTile(GameState gameState){		//possibly cause ArrayOutOfBound error
		return gameState.board[this.getPosition().getTilex()][this.getPosition().getTiley()];
	}

	public boolean isFromHuman(){
		if(this.getId() < 21){
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isInAttackRange(ActorRef out, Tile start, Tile end){
		int x = Math.abs(start.getTilex() - end.getTilex());
		int y = Math.abs(start.getTiley() - end.getTiley());

		if(( x == 1 && y == 0) || (x == 0 && y == 1) || (x == 1 && y == 1)){
			return true;
		}else{
			return false;
		}
	}

	public void displayRangeOfMovement(ActorRef out, GameState gameState, Tile tile, int n){
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
			} else if (!isInAttackRange(out, start, end)) {
				BasicCommands.addPlayer1Notification(out, "can't attack", 2);
			} else {
				mustAttack(out, gameState, start, end);
			}
		}else{
			BasicCommands.addPlayer1Notification(out, "can't attack yourself", 2);
		}
	}

	public void attack(ActorRef out, GameState gameState, Unit targetUnit){  //a new version of attack
		if(targetUnit.isFromHuman() ^ this.isFromHuman()){		//id<20 is human,id>=20 is AI   //
			if(this.getId() == 8) {
				attackAnywhere(out, gameState, this.getCurrentTile(gameState), targetUnit.getCurrentTile(gameState));
			} else if (this.getId() == 18) {
				attackAnywhere(out, gameState, this.getCurrentTile(gameState), targetUnit.getCurrentTile(gameState));
			} else if (!isInAttackRange(out, this.getCurrentTile(gameState), targetUnit.getCurrentTile(gameState))) {
				BasicCommands.addPlayer1Notification(out, "Out Of Range", 2);
			} else {
				mustAttack(out, gameState, this.getCurrentTile(gameState), targetUnit.getCurrentTile(gameState));
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

