package test;

import gameboard.GameBoard;
import players.BlackPlayer;
import players.Player;
import players.WhitePlayer;

/**
 * Test for the operations of a player with black pieces
 * Created by SiyangLiu on 2018/2/4.
 */

public class BlackPlayerTest extends PlayerTest{

    @Override
    protected Player getConcreteSelf(GameBoard board) {
        return new BlackPlayer(board);
    }

    @Override
    protected Player getConcreteOpponent(GameBoard board) {
        return new WhitePlayer(board);
    }
}