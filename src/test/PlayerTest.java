package test;

import gameboard.GameBoard;
import org.junit.Before;
import org.junit.Test;
import pieces.*;
import players.Player;

import static org.junit.Assert.*;

import java.util.Set;

import static pieces.Piece.PieceType.KING;

/**
 * Test for the operations of a Player
 * Created by SiyangLiu on 2018/2/4.
 */
public abstract class PlayerTest {

    protected GameBoard board;
    private Player self;
    private Player opponent;

    @Before
    public void setupBoardAndPlayers() {
        board = new GameBoard(8,8);
        self = getConcreteSelf(board);
        opponent = getConcreteOpponent(board);
    }

    /**
     * Checks if moves of a pawn can be executed correctly
     * first move and non-first move are executed
     */
    @Test
    public void movePiece1() throws Exception {
        board.placeStandardPieces();

        // move a black pawn (first updatePieceCoordinates)
        int startRow = 1;
        int startCol = 0;
        Piece blackPawn = board.getSquare(startRow, startCol).getPiece();
        int destRow = 3;
        int destCol = 0;
        Move move = new Move(blackPawn, destRow, destCol, board.getSquare(destRow,destCol).getPiece());
        assert self.movePiece(move);

        assertEquals(board.getSquare(destRow, destCol).getPiece(), blackPawn);
        assertEquals(board.getSquare(startRow, startCol).getPiece(), null);

        assertEquals(blackPawn.getRowIndex(), destRow);
        assertEquals(blackPawn.getColIndex(), destCol);

        // continue to move the black pawn two squares away (not first move) - should fail
        startRow = destRow;
        startCol = destCol;
        destRow = 5;
        destCol = 0;
        move = new Move(blackPawn, destRow, destCol, board.getSquare(destRow,destCol).getPiece());
        assert !self.movePiece(move);

        assertEquals(board.getSquare(destRow, destCol).getPiece(), null);
        assertEquals(board.getSquare(startRow, startCol).getPiece(), blackPawn);

        assertEquals(blackPawn.getRowIndex(), startRow);
        assertEquals(blackPawn.getColIndex(), startCol);
    }

    /**
     * Checks if moves can be executed correctly
     */
    @Test
    public void movePiece2() throws Exception {
        board.placeStandardPieces();

        // move a black knight
        int startRow = 0;
        int startCol = 1;
        Piece blackKnight = board.getSquare(startRow, startCol).getPiece();
        int destRow = 2;
        int destCol = 0;
        Move move = new Move(blackKnight, destRow, destCol, board.getSquare(destRow,destCol).getPiece());
        assert self.movePiece(move);

        assertEquals(board.getSquare(destRow, destCol).getPiece(), blackKnight);
        assertEquals(board.getSquare(startRow, startCol).getPiece(), null);

        assertEquals(blackKnight.getRowIndex(), destRow);
        assertEquals(blackKnight.getColIndex(), destCol);
    }

    /**
     * Checks if moves failure can be detected with occupied destination squares
     */
    @Test
    public void movePiece3() throws Exception {
        board.placeStandardPieces();

        // move a white knight - should fail because dest is occupied
        int startRow = 7;
        int startCol = 1;
        Piece whiteKnight = board.getSquare(startRow, startCol).getPiece();
        int destRow = 6;
        int destCol = 3;
        Move move = new Move(whiteKnight, destRow, destCol, board.getSquare(destRow,destCol).getPiece());
        assert !self.movePiece(move);

        assertNotEquals(board.getSquare(destRow, destCol).getPiece(), null);
        assertEquals(board.getSquare(startRow, startCol).getPiece(), whiteKnight);

        assertEquals(whiteKnight.getRowIndex(), startRow);
        assertEquals(whiteKnight.getColIndex(), startCol);

    }

    /**
     * Checks if moves failure can be detected when it is illegal

     * Checks if moves can be executed correctly
     */
    @Test
    public void movePiece4() throws Exception {
        board.placeStandardPieces();

        // updatePieceCoordinates a white knight - should fail because not in legal move
        int startRow = 7;
        int startCol = 1;
        Piece whiteKnight = board.getSquare(startRow, startCol).getPiece();
        int destRow = 1;
        int destCol = 1;
        Move move = new Move(whiteKnight, destRow, destCol, board.getSquare(destRow,destCol).getPiece());
        assert !self.movePiece(move);

        assertNotEquals(board.getSquare(destRow, destCol).getPiece(), whiteKnight);
        assertEquals(board.getSquare(startRow, startCol).getPiece(), whiteKnight);

        assertEquals(whiteKnight.getRowIndex(), startRow);
        assertEquals(whiteKnight.getColIndex(), startCol);
    }

    /**
     * Test if a checkmate can be detected
     */
    @Test
    public void isInCheckmate1() throws Exception {
        Piece selfKing = new King(self.getColor(), 7,7);
        board.placePiece(selfKing);
        board.placePiece(new King(opponent.getColor(), 5,7));
        board.placePiece(new Bishop(opponent.getColor(), 5,4));
        board.placePiece(new Bishop(opponent.getColor(), 5,5));
        assert self.isInCheckmate();
        assert !opponent.isInCheckmate();
    }

    /**
     * Test if a checkmate can be detected
     */
    @Test
    public void isInCheckmate2() throws Exception {
        Piece selfKing = new King(self.getColor(), 7,7);
        board.placePiece(selfKing);
        board.placePiece(new Queen(opponent.getColor(), 7,4));
        board.placePiece(new King(opponent.getColor(), 5,6));
        assert self.isInCheckmate();
        assert !opponent.isInCheckmate();
    }

    /**
     * Test if a stalemate can be detected
     */
    @Test
    public void isInStalemate1() throws Exception {
        board.placePiece(new King(opponent.getColor(), 5,2));
        board.placePiece(new Rook(opponent.getColor(), 6,1));
        board.placePiece(new King(self.getColor(), 7,0));
        assert self.isInStalemate();
    }

    @Test
    public void isInStalemate2() throws Exception {
        board.placePiece(new King(opponent.getColor(), 4,2));
        board.placePiece(new King(self.getColor(), 3,0));
        board.placePiece(new Queen(opponent.getColor(), 2,2));
        assert self.isInStalemate();
    }

    /**
     * Test if the king can be get
     */
    @Test
    public void getKing() throws Exception {
        Piece selfKing = self.getKing();
        assertEquals(selfKing, null);

        board.placeStandardPieces();
        selfKing = self.getKing();
        assertEquals(selfKing.getPieceType(), KING);
        assertEquals(selfKing.getPieceColor(), self.getColor());
    }

    /**
     * Test if all own pieces can be get correctly
     */
    @Test
    public void getOwnPiece() throws Exception {
        board.placeStandardPieces();
        Set<Piece> pieces = self.getOwnPieces();
        assertEquals(pieces.size(), 16);
        for (Piece piece : pieces) {
            assertEquals(piece.getPieceColor(), self.getColor());
        }
    }

    /**
     * Test if all opponent's pieces can be get correctly
     */
    @Test
    public void getOpponentPieces() throws Exception{
        board.placeStandardPieces();
        Set<Piece> pieces = self.getOpponentPieces();
        assertEquals(pieces.size(), 16);
        for (Piece piece : pieces) {
            assertEquals(piece.getPieceColor(), opponent.getColor());
        }
    }

    /**
     * Construct a concrete Player
     */
    protected abstract Player getConcreteSelf(GameBoard board);

    /**
     * Construct a concrete opponent Player
     */
    protected abstract Player getConcreteOpponent(GameBoard board);

}