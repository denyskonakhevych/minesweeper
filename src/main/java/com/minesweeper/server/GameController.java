package com.minesweeper.server;

import com.minesweeper.common.Game;
import com.minesweeper.common.GameStatus;
import com.minesweeper.common.TurnResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 09.06.2016
 * Time: 11:35
 */
public class GameController
{
  private static GameController instance = new GameController();

  private GameDao dao = new GameDao();

  private GameController()
  {

  }

  public static GameController getInstance()
  {
    return instance;
  }

  public String createGame(int rowsCount, int columnsCount, int minesCount)
  {
    Game game = new Game( rowsCount, columnsCount, minesCount );
    game.initField();
    dao.saveGame(game);
    return game.getGameId();
  }

  public TurnResult makeClick(String gameId, int row, int column)
  {
    Game game = dao.retrieveGame( gameId );
    if( game.checkClickInMine(row, column) )
    {
      return new TurnResult( GameStatus.LOST, game.retrieveAllMines());
    }
    else
    {
      return new TurnResult( GameStatus.CONTINUE, game.retrieveResultValues( row, column ));
    }
  }
}
