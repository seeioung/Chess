package test;

import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KNIGHT;

/**
 * Test for the operations of a Knight
 * Created by SiyangLiu on 2018/2/4.
 */
public class KnightTest extends PieceTest{

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), KNIGHT);
        assertEquals(this.whitePiece.getPieceType(), KNIGHT);
    }

    @Override
    public void computeValidMoves() throws Exception {
        int pieceRow = 6;
        int pieceCol = 3;
        Piece knight = new Knight(BLACK, pieceRow, pieceCol);
        board.placePiece(knight);

        int friendPieceRow = 4;
        int friendPieceCol = 4;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 5;
        int enemyPieceCol = 1;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        knight.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{{4,2},
                                            /*{2,4} blocked*/
                                            {5,1}/*attack*/,
                                            {5,5}, {7,1}, {7,5},
                                            /*{8, 2}, {8, 4} out of bound*/};
        List<Move> validMoves = knight.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, knight);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Knight(pieceColor, coordinateRow, coordinateCol);
    }
}