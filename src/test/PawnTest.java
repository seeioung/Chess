package test;

import pieces.Move;
import pieces.Pawn;
import pieces.Piece;

import static org.junit.Assert.*;

import java.util.List;

import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.PAWN;

/**
 * Test for the operations of a Pawn
 * Created by SiyangLiu on 2018/2/4.
 */
public class PawnTest extends PieceTest{

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), PAWN);
        assertEquals(this.whitePiece.getPieceType(), PAWN);
    }

    @Override
    public void computeValidMoves() throws Exception {
        int pieceRow = 1;
        int pieceCol = 1;
        Piece pawn = new Pawn(BLACK, pieceRow, pieceCol);
        board.placePiece(pawn);

        // first move with no blocking and opponents to attack
        pawn.computeValidMoves(board);
        int[][] correctDest = new int[][]{{2,1},{3,1}
                                            /*{2,0}, {2,2} no opponent to attack*/};
        List<Move> validMoves = pawn.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, pawn);

        int friendPieceRow = 2;
        int friendPieceCol = 1;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);
        int enemyPieceRow = 2;
        int enemyPieceCol = 0;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);
        // first move with piece in front and opponent to attack
        pawn.computeValidMoves(board);
        correctDest = new int[][]{/*{2,1}, {3,1} blocked*/
                                    {2,0}/*attack*/
                                    /*{2,2} no opponent to attack*/};
        validMoves = pawn.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, pawn);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Pawn(pieceColor, coordinateRow, coordinateCol);
    }

}