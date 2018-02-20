package test;

import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KING;

/**
 * Test for the operations of a King
 * Created by SiyangLiu on 2018/2/4.
 */
public class KingTest extends PieceTest{
    @Override
    public void computeValidMoves() throws Exception {
        int pieceRow = 0;
        int pieceCol = 3;
        Piece king = new King(BLACK, pieceRow, pieceCol);
        board.placePiece(king);

        int friendPieceRow = 1;
        int friendPieceCol = 4;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 0;
        int enemyPieceCol = 2;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);
        king.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{/*{-1,2}, {-1,3}, {-1,4} out of bound*/
                                            {0,2}/*attack*/,
                                            {0,4}, {1,2}, {1,3} /*empty squares*/
                                            /*{1,4} blocked*/};
        List<Move> validMoves = king.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, king);
    }

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), KING);
        assertEquals(this.whitePiece.getPieceType(), KING);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new King(pieceColor, coordinateRow, coordinateCol);
    }


}