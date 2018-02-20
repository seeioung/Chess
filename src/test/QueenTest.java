package test;

import gameboard.GameBoard;
import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.QUEEN;

/**
 * Test for the operations of a Queen
 * Created by SiyangLiu on 2018/2/4.
 */
public class QueenTest extends PieceTest{

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), QUEEN);
        assertEquals(this.whitePiece.getPieceType(), QUEEN);
    }

    @Override
    public void computeValidMoves() throws Exception {
        GameBoard board = new GameBoard(8,8);

        int pieceRow = 3;
        int pieceCol = 3;
        Piece queen = new Queen(BLACK, pieceRow, pieceCol);
        board.placePiece(queen);

        int friendPieceRow = 5;
        int friendPieceCol = 5;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 3;
        int enemyPieceCol = 1;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        queen.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{{3,2},
                                            {3,1},/*attack*/
                                            /*{3,0},blocked by enemy*/
                                            {3,4}, {3,5}, {3,6}, {3,7}, {2,3}, {1,3}, {0,3}, {4,3}, {5,3}, {6,3}, {7,3}, {2,2}, {1,1}, {0,0}, {4,4},
                                            /*{5,5}, {6,6}, {7,7} blocked by friend*/
                                            {2,4}, {1,5}, {0, 6}, {4,2}, {5,1}, {6,0}};
        List<Move> validMoves = queen.getValidMoves();

        testComputeValidMovesHelper(validMoves, correctDest, queen);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Queen(pieceColor, coordinateRow, coordinateCol);
    }
}