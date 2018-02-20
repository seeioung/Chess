package pieces;

import gameboard.GameBoard;
import gameboard.Square;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.HOPPER;

/**
 * A hopper is a piece that moves by jumping over another piece (called a hurdle).
 * The hurdle can be any piece of any color.
 * Unless it can jump over a piece, a hopper cannot move.
 * It captures by taking the enemy piece on the destination square,
 */
public class Hopper extends Piece {
    // the coordinate offset of all possible moves of a knight
    private final int[][] moveCandidates = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};

    public Hopper(PieceColor pieceColor, int rowIndex, int colIndex) {
        super(pieceColor, rowIndex, colIndex);
        this.pieceType = HOPPER;
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        for (int[] moveCandidate : moveCandidates) {        /* try all possible directions */
            int destRow = getRowIndex();
            int destCol = getColIndex();
            boolean hasJump = false;
            while (true) { /* try all possible number of steps */

                destRow = destRow + moveCandidate[0];
                destCol = destCol + moveCandidate[1];
                System.out.println(destRow + ", " + destCol);
                if (!board.isValidCoordinates(destRow, destCol)) {      /* out of bound */
                    break;
                }

                Square destSquare = board.getSquare(destRow, destCol);
                if (!hasJump && destSquare.hasPiece()) {
                    hasJump = true;
                } else if (hasJump && !destSquare.hasPiece()) {               /* valid to move the piece */
                    Move move = new Move(this, destRow, destCol, null);
                    this.validMoves.add(move);
                } else if (hasJump && destSquare.getPiece().getPieceColor() != getPieceColor()) {      /* valid to attack the piece in the destination */
                    Move move = new Move(this, destRow, destCol, destSquare.getPiece());
                    this.validMoves.add(move);
                    break;
                } else if (hasJump && destSquare.getPiece().getPieceColor() == getPieceColor()){ /* cannot leap over self's pieces */
                    break;
                }

            }
        }

        return this.getValidMoves();
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_hopper" : "black_hopper";
    }
}
