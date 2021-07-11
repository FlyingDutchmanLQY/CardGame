package structures.basic;

import commands.BasicCommands;
import org.junit.Assert;
import org.junit.Test;
import structures.GameState;
import utils.BasicObjectBuilders;

import static org.junit.Assert.*;

public class UnitTest {


    @Test
    public void isBeingProvoked() {
        GameState gameState = new GameState();
        for(int i=0;i<9;i++){
            for(int j=0;j<5;j++){
                gameState.board[i][j] = BasicObjectBuilders.loadTile(i, j);
            }
        }

        Unit unit = new Unit();
        unit.setPositionByTile(gameState.board[2][2]);

        Unit provokingUnit = new Unit();
        provokingUnit.setId(20);
        provokingUnit.setPositionByTile(gameState.board[2][3]);

        gameState.unitPositionMap.put(unit.getCurrentTile(gameState), unit);
        gameState.unitPositionMap.put(provokingUnit.getCurrentTile(gameState), provokingUnit);

        Unit rs = unit.isBeingProvoked(gameState);
        Assert.assertEquals(provokingUnit,rs);
    }
}