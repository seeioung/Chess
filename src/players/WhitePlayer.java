package players;

import gameboard.GameBoard;
import pieces.Piece;

import java.util.Set;

import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KING;

/**
 * Represents a player with white pieces
 * Created by SiyangLiu on 2018/2/2.
 */
public class WhitePlayer extends Player {

    public WhitePlayer(GameBoard board) {
        super(board);
    }

    @Override
    public Piece getKing() {
        for (Piece piece : getOwnPieces()) {
            if (piece.getPieceType() == KING && piece.getPieceColor() == WHITE) {
                return piece;
            }
        }

        return null;
    }

    @Override
    public Set<Piece> getOwnPieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Set<Piece> getOpponentPieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Piece.PieceColor getColor() {
        return WHITE;
    }

}
