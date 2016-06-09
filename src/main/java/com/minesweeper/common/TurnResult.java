package com.minesweeper.common;

import com.minesweeper.common.Cell;
import com.minesweeper.common.GameStatus;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 09.06.2016
 * Time: 23:34
 */
public class TurnResult
{
  private GameStatus status;
  private Map<Cell, Integer> cells;

  public TurnResult()
  {

  }

  public TurnResult( GameStatus status, Map<Cell, Integer> cells )
  {
    this.status = status;
    this.cells = cells;
  }

  public GameStatus getStatus()
  {
    return status;
  }

  public void setStatus( GameStatus status )
  {
    this.status = status;
  }

  public Map<Cell, Integer> getCells()
  {
    return cells;
  }

  public void setCells( Map<Cell, Integer> cells )
  {
    this.cells = cells;
  }
}
