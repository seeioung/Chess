package test;

import gameboard.Square;
import org.junit.Test;
import pieces.King;
import pieces.Piece;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;

/**
 * Created by SiyangLiu on 2018/2/3.
 */
public class SquareTest {

    @Test
    public void ValidConstructor() throws Exception {
        int coordinateRow = 0;
        int coordinateCol = 0;
        Square emptySquare = new Square(coordinateRow,coordinateCol, null);
        assertEquals(emptySquare.getRowIndex(), coordinateRow);
        assertEquals(emptySquare.getColIndex(), coordinateCol);
        assertEquals(emptySquare.getPiece(), null);

        coordinateRow = 2;
        coordinateCol = 2;
        Piece piece = new King(BLACK, 0, 3);
        Square occupiedSquare = new Square(coordinateRow,coordinateCol, piece);
        assertEquals(occupiedSquare.getRowIndex(), coordinateRow);
        assertEquals(occupiedSquare.getColIndex(), coordinateCol);
        assertEquals(occupiedSquare.getPiece(), piece);
    }

    @Test
    public void hasPiece() throws Exception {
        int coordinateRow = 0;
        int coordinateCol = 0;
        Square emptySquare = new Square(coordinateRow,coordinateCol, null);
        assertEquals(emptySquare.hasPiece(), false);

        Piece piece = new King(BLACK, 0, 3);
        Square occupiedSquare = new Square(coordinateRow,coordinateCol, piece);
        assertEquals(occupiedSquare.hasPiece(), true);

    }


    @Test
    public void setEmpty() throws Exception {
        int coordinateRow = 0;
        int coordinateCol = 0;

        Piece piece = new King(BLACK, 0, 3);
        Square occupiedSquare = new Square(coordinateRow,coordinateCol, piece);
        occupiedSquare.setEmpty();
        assertEquals(occupiedSquare.hasPiece(), false);
    }

    @Test
    public void setPiece() throws Exception {
        int coordinateRow = 0;
        int coordinateCol = 0;
        Square emptySquare = new Square(coordinateRow,coordinateCol, null);
        Piece piece = new King(BLACK, 0, 3);
        emptySquare.setPiece(piece);
        assertEquals(emptySquare.hasPiece(), true);
        assertEquals(emptySquare.getPiece(), piece);

    }

}