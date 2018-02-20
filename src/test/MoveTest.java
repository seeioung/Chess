package test;

import org.junit.Test;
import pieces.King;
import pieces.Move;
import pieces.Piece;

import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;

/**
 * Test for the operations of a Move object
 * Created by SiyangLiu on 2018/2/4.
 */
public class MoveTest {

    /**
     * Tests if equals() is correctly overridden for Move object
     */
    @Test
    public void testEquals(){
        Piece piece1 = new King(BLACK, 0,4);
        Piece piece2 = new King(WHITE, 0,4);
        Move move1 = new Move(piece1, 0, 5, null);
        Move move2 = new Move(piece1, 0, 5, null);
        Move move3 = new Move(piece2, 0, 5, null);
        assert move1.equals(move1);
        assert move1.equals(move2);
        assert move2.equals(move1);
        assert !move1.equals(move3);
        assert !move2.equals(move3);
        assert !move3.equals(move1);
        assert !move3.equals(move2);
        assert !move1.equals(piece1);

    }

}