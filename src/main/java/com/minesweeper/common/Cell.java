package com.minesweeper.common;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 09.06.2016
 * Time: 23:21
 */
public class Cell
{
  private int row;
  private int column;

  public Cell( int row, int column )
  {
    this.row = row;
    this.column = column;
  }

  public int getRow()
  {
    return row;
  }

  public int getColumn()
  {
    return column;
  }

  @Override
  public boolean equals( Object o )
  {
    if( this == o )
      return true;
    if( o == null || getClass() != o.getClass() )
      return false;
    Cell cell = (Cell) o;
    return Objects.equals( row, cell.row ) && Objects.equals( column, cell.column );
  }

  @Override
  public int hashCode()
  {
    return Objects.hash( row, column );
  }
}
