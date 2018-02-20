package players;

import gameboard.GameBoard;
import pieces.Piece;

import java.util.Set;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KING;
import static pieces.Piece.PieceColor.BLACK;


/**
 * Represents a player with black pieces
 * Created by SiyangLiu on 2018/2/2.
 */
public class BlackPlayer extends Player {

    public BlackPlayer(GameBoard board) {
        super(board);
    }

    @Override
    public Piece getKing() {
        for (Piece piece : getOwnPieces()) {
            if (piece.getPieceType() == KING && piece.getPieceColor() == BLACK) {
                return piece;
            }
        }

        return null;
    }

    @Override
    public Set<Piece> getOwnPieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Set<Piece> getOpponentPieces() {
        return this.board.getWhitePieces();

    }

    @Override
    public Piece.PieceColor getColor() {
        return BLACK;
    }

}
