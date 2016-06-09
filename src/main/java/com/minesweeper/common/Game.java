package com.minesweeper.common;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 09.06.2016
 * Time: 10:00
 */
public class Game implements Serializable
{
  private String gameId;
  private int[][] field;
  private int rowsCount;
  private int columnsCount;
  private int minesCount;

  public Game()
  {

  }

  public Game( int rowsCount, int columnsCount, int minesCount )
  {
    this( UUID.randomUUID().toString(), rowsCount, columnsCount, minesCount );
  }

  public Game( String gameId, int rowsCount, int columnsCount, int minesCount )
  {
    this.gameId = gameId;
    this.rowsCount = rowsCount;
    this.columnsCount = columnsCount;
    this.minesCount = minesCount;
    field = new int[rowsCount][columnsCount];
  }

  public boolean checkClickInMine(int row, int column)
  {
    return isCoordinatesInField( row, column ) && isMine( row, column );
  }

  public void initField()
  {
    generateMines();
    for( int row = 0; row < rowsCount; row++ )
    {
      for( int column = 0; column < columnsCount; column++ )
      {
        if( !isMine(row, column) )
          field[row][column] = countNeighbourMines(row, column);
      }
    }
  }

  public Map<Cell, Integer> retrieveAllMines()
  {
    Map<Cell, Integer> mines = new HashMap<>();
    for( int row = 0; row < rowsCount; row++ )
    {
      for( int column = 0; column < columnsCount; column++ )
      {
        if( isMine(row, column) )
          mines.put( new Cell( row, column ), -1 );
      }
    }
    return mines;
  }

  public Map<Cell, Integer> retrieveResultValues( int row, int column )
  {
    Map<Cell,Integer> values = new HashMap<>();
    if(field[row][column] != 0)
    {
      values.put( new Cell( row, column ), field[ row ][ column ] );
    }
    else
    {
      inspectNeighbours(values, row, column);
    }
    return values;
  }

  private void inspectNeighbours(Map<Cell,Integer> values, int row, int column)
  {
    values.put( new Cell( row, column ), field[ row ][ column ] );
    for(int i = row - 1; i <= row + 1; i++)
      for( int j = column - 1; j <= column + 1; j++ )
        if( isCoordinatesInField(i, j) && field[i][j] != 0 )
          values.put( new Cell( i, j ), field[ i ][ j ] );

    inspectNeighboursZeroes(values, row - 1, column);
    inspectNeighboursZeroes(values, row, column - 1);
    inspectNeighboursZeroes(values, row + 1, column);
    inspectNeighboursZeroes(values, row, column + 1);

  }

  private void inspectNeighboursZeroes(Map<Cell,Integer> values, int row, int column)
  {
    if( isCoordinatesInField( row, column ) && field[ row ][ column ] == 0
            && !values.keySet().contains( new Cell( row, column ) ) )
    {
      inspectNeighbours( values, row, column);
    }
  }

  private void generateMines()
  {
    final Random rowsRandom = new Random();
    final Random columnsRandom = new Random();
    for( int mine = 0; mine < minesCount; mine++ )
    {
      generateMine( rowsRandom,columnsRandom );
    }
  }

  private void generateMine( final Random rowsRandom, final Random columnsRandom )
  {
    int x = rowsRandom.nextInt( rowsCount );
    int y = columnsRandom.nextInt( columnsCount );
    if( field[x][y] == -1 )
    {
      generateMine( rowsRandom, columnsRandom );
    }
    else
    {
      field[x][y] = -1;
    }
  }

  private int countNeighbourMines(int row, int column)
  {
    int neighbourMinesCount = 0;
    for(int i = row - 1; i <= row + 1; i++)
      for( int j = column - 1; j <= column + 1; j++ )
        if( isCoordinatesInField(i, j) && isMine(i, j) )
          neighbourMinesCount++;
    return neighbourMinesCount;
  }

  private boolean isMine( int row, int column )
  {
    return field[row][column] == -1;
  }

  private boolean isCoordinatesInField( int row, int column )
  {
    return row >= 0 && row < rowsCount &&
            column >= 0 && column < columnsCount;
  }

  public void updateField( Map<Cell, Integer> values )
  {
    for( Cell cell : values.keySet() )
    {
      field[cell.getRow()][cell.getColumn()] = values.get( cell );
    }
  }

  public String getGameId()
  {
    return gameId;
  }

  public void setGameId( String gameId )
  {
    this.gameId = gameId;
  }

  public int[][] getField()
  {
    return field;
  }

  public void setField( int[][] field )
  {
    this.field = field;
  }

  public int getRowsCount()
  {
    return rowsCount;
  }

  public void setRowsCount( int rowsCount )
  {
    this.rowsCount = rowsCount;
  }

  public int getColumnsCount()
  {
    return columnsCount;
  }

  public void setColumnsCount( int columnsCount )
  {
    this.columnsCount = columnsCount;
  }

  public int getMinesCount()
  {
    return minesCount;
  }

  public void setMinesCount( int minesCount )
  {
    this.minesCount = minesCount;
  }

  @Override
  public String toString()
  {

    return "Game{ id:" + gameId + "," +
            "\nfield:\n" + fieldToString() +
            "}";
  }

  private String fieldToString( )
  {
    String fieldString = "";
    for( int row = 0; row < rowsCount; row++ )
    {
      for( int column = 0; column < columnsCount; column++ )
      {
        if( field[row][column] == -1 )
          fieldString += "*";
        else if( field[row][column] == 10 )
          fieldString += "#";
        else
          fieldString += field[row][column];
        fieldString += " ";
      }
      fieldString += "\n";
    }
    return fieldString;
  }
}
