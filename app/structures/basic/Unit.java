package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.BasicCommands;
import structures.GameState;

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
//						rangeOfMove(out,start_move_tile,0);
//						tiles_canMove.clear();
		}else{
			BasicCommands.addPlayer1Notification(out,"can't move",2);
		}

//					rangeOfMove(out,start_move_tile,0);
	}

	public void attack(ActorRef out, GameState gameState, Tile start, Tile end){
		if(!isAttack(out, start, end)){
			BasicCommands.addPlayer1Notification(out,"can't attack",2);
		}else{
			Unit unit_Start = gameState.map_Unit.get(start);
			Unit unit_End = gameState.map_Unit.get(end);
			BasicCommands.playUnitAnimation(out, unit_Start, UnitAnimationType.attack);
			BasicCommands.playUnitAnimation(out,unit_End,UnitAnimationType.hit);
			unit_End.health -= unit_Start.attack;
			BasicCommands.setUnitHealth(out, unit_End, unit_End.health);
			int id = unit_End.getId();
			if (id == 0) {
				gameState.humanPlayer.setHealth(unit_End.health);
				BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
			}
			if (id == 1) {
				gameState.AIPlayer.setHealth(unit_End.health);
				BasicCommands.setPlayer2Health(out, gameState.AIPlayer);
			}
//					index_move = 0;
//					isMoveUnit = false;
			if (unit_End.health <= 0) {
				BasicCommands.playUnitAnimation(out, unit_End, UnitAnimationType.death);
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				BasicCommands.deleteUnit(out, unit_End);
				gameState.map_Unit.remove(end);

				BasicCommands.addPlayer1Notification(out, "unit died", 2);
			}
		}
	}
	
	
}
