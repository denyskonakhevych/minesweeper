package com.minesweeper.client;

import com.minesweeper.common.Cell;
import com.minesweeper.common.Game;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 10.06.2016
 * Time: 1:14
 */
public class ClientGame extends Game
{
  private int reveledCells = 0;
  private boolean isFinished;

  public ClientGame( int rowsCount, int columnsCount, int minesCount )
  {
    super( rowsCount, columnsCount, minesCount );
  }

  public ClientGame( String gameId, int rowsCount, int columnsCount, int minesCount )
  {
    super( gameId, rowsCount, columnsCount, minesCount );
    for( int row = 0; row < rowsCount; row++ )
    {
      for( int column = 0; column < columnsCount; column++ )
      {
        getField()[row][column] = 10;
      }
    }
  }

  public void finishGame()
  {
    isFinished = true;
  }

  public void increaseReveledCellsCount( int cellsNumber)
  {
    reveledCells += cellsNumber;
  }

  @Override
  public void updateField( Map<Cell, Integer> values )
  {
    increaseReveledCellsCount( values.entrySet().size() );
    super.updateField( values );
  }

  public boolean checkEntireFieldIsOpened()
  {
    return getRowsCount() * getColumnsCount() == reveledCells;
  }

  public int getReveledCells()
  {
    return reveledCells;
  }

  public boolean isFinished()
  {
    return isFinished;
  }
}
