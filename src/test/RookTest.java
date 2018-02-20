package test;

import gameboard.GameBoard;
import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.ROOK;

/**
 * Test for the operations of a Rook
 * Created by SiyangLiu on 2018/2/4.
 */
public class RookTest extends PieceTest{

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(blackPiece.getPieceType(), ROOK);
        assertEquals(whitePiece.getPieceType(), ROOK);
    }

    @Override
    public void computeValidMoves() throws Exception {
        GameBoard board = new GameBoard(8,8);

        int pieceRow = 3;
        int pieceCol = 3;
        Piece rook = new Rook(BLACK, pieceRow, pieceCol);
        board.placePiece(rook);

        int friendPieceRow = 3;
        int friendPieceCol = 1;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 6;
        int enemyPieceCol = 3;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        rook.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{{3,2},
                                        /*{3,1}, {3,0},blocked by friend*/
                                        {3,4}, {3,5}, {3,6}, {3,7}, {2,3}, {1,3}, {0,3}, {4,3}, {5,3},
                                        {6,3}/*attack*/
                                        /*, {7,3} blocked by enemy*/};
        List<Move> validMoves = rook.getValidMoves();

        testComputeValidMovesHelper(validMoves, correctDest, rook);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Rook(pieceColor, coordinateRow, coordinateCol);
    }
}