package test;

import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.EMPRESS;

public class EmpressTest extends PieceTest {

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), EMPRESS);
        assertEquals(this.whitePiece.getPieceType(), EMPRESS);
    }

    @Override
    public void computeValidMoves() throws Exception {
        int pieceRow = 6;
        int pieceCol = 3;
        Piece empress = new Empress(BLACK, pieceRow, pieceCol);
        board.placePiece(empress);

        int friendPieceRow = 4;
        int friendPieceCol = 4;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 5;
        int enemyPieceCol = 1;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        int friendPieceRow1 = 6;
        int friendPieceCol1 = 1;
        Piece blackPawn1 = new Pawn(BLACK, friendPieceRow1,friendPieceCol1);
        board.placePiece(blackPawn1);

        int enemyPieceRow1 = 4;
        int enemyPieceCol1 = 3;
        Piece whitePawn1 = new Pawn(WHITE, enemyPieceRow1,enemyPieceCol1);
        board.placePiece(whitePawn1);

        empress.computeValidMoves(board);


        // check if valid moves are correct
        int[][] correctDest = new int[][]{
                /*valid moves of knight: */{4,2},
                                            /*{2,4} blocked*/
                                            {5,1}/*attack*/,
                                            {5,5}, {7,1}, {7,5},
                                            /*{8, 2}, {8, 4} out of bound*/
                /*valid moves of rook: */{6,2},
                                            /*{6,1}, {6,0},blocked by friend*/
                                            {6,4}, {6,5}, {6,6}, {6,7}, {5,3},
                                            {4,3},/*attack*/
                                            /*, {3,3}, {2,3}, {1,3}, {0,3} blocked by enemy*/
                                            {7,3}};
        List<Move> validMoves = empress.getValidMoves();
        testComputeValidMovesHelper(validMoves, correctDest, empress);
    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Empress(pieceColor, coordinateRow, coordinateCol);
    }
}