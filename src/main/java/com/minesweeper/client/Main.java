package com.minesweeper.client;

import com.minesweeper.common.Cell;
import com.minesweeper.common.Game;
import com.minesweeper.common.GameStatus;
import com.minesweeper.common.TurnResult;
import com.minesweeper.server.GameController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 10.06.2016
 * Time: 0:23
 */
public class Main
{
  private static final int ROWS = 2;
  private static final int COLUMNS = 2;
  private static final int MINES = 2;

  public static void main( String[] args )
  {
    String gameId = GameController.getInstance().createGame( ROWS, COLUMNS, MINES );
    ClientGame game = new ClientGame(gameId, ROWS, COLUMNS, MINES);

    new Main().startGame( game );
  }

  private void startGame(ClientGame game)
  {
    try(Scanner input = new Scanner(System.in))
    {
      System.out.println( game.toString() );
      while(!game.isFinished())
      {
        printStartMessageText();
        int[] values = parseInputString( game, input.nextLine() );
        if( values == null )
        {
          System.out.println( "Wrong input data" );
          continue;
        }
        makeTurn( game, values );
        System.out.println( game.toString() );
      }
    }
  }

  private void makeTurn( ClientGame game, int[] values )
  {
    if(values[0] == 1)
    {
      GameStatus gameStatus = clickCell( game, values[ 1 ], values[ 2 ] );
      if( GameStatus.LOST == gameStatus )
      {
        System.out.println("You have lost");
        game.finishGame();
      }
      else
      {
        if( game.checkEntireFieldIsOpened() )
        {
          System.out.println("You have won");
          game.finishGame();
        }
      }
    }
    if(values[0] == 2)
    {
      Map<Cell, Integer> mine = new HashMap<>(  );
      mine.put( new Cell( values[ 1 ], values[ 2 ] ), -1 );
      game.updateField( mine );
      if( game.checkEntireFieldIsOpened() )
      {
        System.out.println("You have won");
        game.finishGame();
      }
    }
  }

  private void printStartMessageText()
  {
    System.out.println("Please, choose action as: #action;row;column (for example, 1;0;0):");
    System.out.println("1) Click cell");
    System.out.println("2) Mark mine");
  }

  private int[] parseInputString(Game game, String inputString)
  {
    try
    {
      if( inputString != null && !inputString.isEmpty() )
      {
        String[] values = inputString.split( ";" );
        if( values.length == 3 )
        {
          int[] parsedValues = new int[3];
          parsedValues[0] = Integer.parseInt( values[0] );
          parsedValues[1] = Integer.parseInt( values[1] );
          parsedValues[2] = Integer.parseInt( values[2] );
          if( parsedValues[0] < 0 || parsedValues[0] > 3)
            return null;
          if(parsedValues[1] < 0 || parsedValues[1] >= game.getRowsCount())
            return null;
          if(parsedValues[2] < 0 || parsedValues[2] >= game.getColumnsCount())
            return null;
          return parsedValues;
        }
      }
    }
    catch( NumberFormatException e )
    {
      return null;
    }
    return null;
  }

  private GameStatus clickCell(ClientGame game, int row, int column)
  {
    TurnResult turnResult = GameController.getInstance().makeClick( game.getGameId(), row, column );
    game.updateField( turnResult.getCells() );
    return turnResult.getStatus();
  }
}
