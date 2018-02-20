package pieces;

import gameboard.GameBoard;
import gameboard.Square;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.PAWN;

/**
 * Represents a Pawn piece of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class Pawn extends Piece{
    // the coordinate offset of all possible moves of a white pawn
    private final int[][] moveCandidatesWhite = new int[][]{{-1,0}, {-2,0}, {-1,-1}, {-1,1}};
    // the coordinate offset of all possible moves of a black pawn
    private final int[][] moveCandidatesBlack = new int[][]{{1,0}, {2,0}, {1,-1}, {1,1}};

    private int[] initialPosition;

    public Pawn(PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        super(pieceColor, coordinateRow, coordinateCol);
        this.initialPosition = new int[]{coordinateRow, coordinateCol};
        this.pieceType = PAWN;
    }

    @Override
    public void updatePieceCoordinates(int destRow, int destCol) {
        super.updatePieceCoordinates(destRow, destCol);
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        int[][] moveCandidates;
        if (this.getPieceColor() == WHITE) {
            moveCandidates = this.moveCandidatesWhite;
        } else {
            moveCandidates = this.moveCandidatesBlack;
        }

        this.validMoves = new ArrayList<>();
        for (int i = 0; i < moveCandidates.length; i++) {
            int destRow = this.getRowIndex() + moveCandidates[i][0];
            int destCol = this.getColIndex() + moveCandidates[i][1];
            if (!board.isValidCoordinates(destRow, destCol) /* out of bound*/) {
                continue;
            }
            Square destSquare = board.getSquare(destRow, destCol);
            if (i == 0 && !destSquare.hasPiece()) { // updatePieceCoordinates one step forward
                Move move = new Move(this, destRow, destCol, null);
                this.validMoves.add(move);
            } else if (i == 1 && this.isFirstMove()) {
                int midRow = this.getRowIndex() + moveCandidates[0][0];
                int midCol = this.getColIndex() + moveCandidates[0][1];
                Square middleSquare = board.getSquare(midRow, midCol);
                if (!middleSquare.hasPiece() && !destSquare.hasPiece()) {
                    Move move = new Move(this, destRow, destCol, null);
                    this.validMoves.add(move);

                }
            } else if ((i == 2 || i == 3) && destSquare.hasPiece() && destSquare.getPiece().getPieceColor() != this.getPieceColor()) {
                // add updatePieceCoordinates to list - attack
                Move move = new Move(this, destRow, destCol, destSquare.getPiece());
                this.validMoves.add(move);

            }
        }

        return this.validMoves;
    }

    public boolean isFirstMove(){
        return initialPosition[0] == getRowIndex() &&initialPosition[1] == getColIndex();
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_pawn" : "black_pawn";
    }

}
