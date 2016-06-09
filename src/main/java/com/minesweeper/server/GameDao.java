package com.minesweeper.server;

import com.minesweeper.common.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 10.06.2016
 * Time: 0:31
 */
public class GameDao
{
  private static Map<String, Game> games = new HashMap<>();

  public void saveGame( Game game )
  {
    games.put(game.getGameId(), game);
  }

  public Game retrieveGame(String gameId)
  {
    return games.get( gameId );
  }
}
