package test;

import pieces.Bishop;
import pieces.Move;
import pieces.Pawn;
import pieces.Piece;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.BISHOP;

/**
 * Test for the operations of a Bishop
 * Created by SiyangLiu on 2018/2/4.
 */
public class BishopTest extends PieceTest{
    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), BISHOP);
        assertEquals(this.whitePiece.getPieceType(), BISHOP);
    }

    @Override
    public void computeValidMoves() throws Exception {
        int pieceRow = 3;
        int pieceCol = 3;
        Piece bishop = new Bishop(BLACK, pieceRow, pieceCol);
        board.placePiece(bishop);

        int friendPieceRow = 4;
        int friendPieceCol = 4;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 1;
        int enemyPieceCol = 5;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        bishop.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{{2,2}, {1,1}, {0,0}, {2,4},
                                            {1,5}, /*attack*/
                                            /*{0,6} blocked*/
                                            {4,2}, {5,1}, {6,0}
                                            /*{4,4},{5,5},{6,6},{7,7} blocked*/};
        List<Move> validMoves = bishop.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, bishop);
    }


    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Bishop(pieceColor, coordinateRow, coordinateCol);
    }
}