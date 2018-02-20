package players;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gameboard.GameBoard;
import pieces.Piece;
import pieces.Move;
import pieces.Piece.PieceColor;

/**
 * Represents a player of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public abstract class Player {
    protected GameBoard board;
    private int score;

    public Player(GameBoard board) {
        this.board = board;
    }

    /**
     * Moves a piece of the player
     * @param move the move that the player want to execute (may not be valid)
     * @return whether the move is successfully executed
     */
    public boolean movePiece(Move move) {
        if (!move.getPiece().isValidMove(move)) {   /* invalid move */
            return false;
        } else if (isInCheckAfterMove(move)) {      /* puts the king in check */
            return false;
        }
        // the move is guaranteed to be valid here
        board.executeMove(move);
        return true;
    }

    /**
     * Checks if the player put its king in check by making the given move
     * @param move the move that the player want to execute (guaranteed be valid)
     * @return whether the move makes the king in check
     */
    private boolean isInCheckAfterMove(Move move) {
        boolean retval = false;
        Piece capturedPiece = board.executeMove(move);
        if (isInCheck()) {
            retval = true;
        }
        board.undoMove(move, capturedPiece);
        return retval;
    }


    /**
     * Checks if the player has lost the game
     */
    public boolean hasLost() {
        return isInCheckmate() || isInStalemate();
    }

    /**
     * Checks if the player is in checkmate
     */
    public boolean isInCheckmate() {
        return isInCheck() && !canEscape();
    }

    /**
     * Checks if the player is in stalemate
     */
    public boolean isInStalemate() {
        return !isInCheck() && !canEscape();
    }

    /**
     * Checks if the player is in check
     */
    private boolean isInCheck() {
        Piece king = getKing();
        List<Piece> opponentPieces = new ArrayList<>(getOpponentPieces());
        for (int p = 0; p < opponentPieces.size(); p++) {
            Piece piece = opponentPieces.get(p);
            List<Move> validMoves = piece.getValidMoves();

            for (int i = 0; i < validMoves.size(); i++) {
                Move move = validMoves.get(i);
                if (move.getDestRow() == king.getRowIndex() && move.getDestCol() == king.getColIndex()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the king will not be in check after one move
     */
    private boolean canEscape() {
//        System.out.println("Beginning of canEscape");
//        String begin = "";
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (board.getSquare(i, j).getPiece() != null) {
//                    begin += board.getSquare(i, j).getPiece().toString() + " ";
//                } else {
//                    begin += "null ";
//
//                }
//            }
//            begin += "\n";
//        }


        boolean retval = false;
        List<Piece> ownPieces = new ArrayList<>(getOwnPieces());
        for (int i = 0; i < ownPieces.size(); i++)  {
            Piece piece = ownPieces.get(i);
            List<Move> validMoves = piece.getValidMoves();
            for (int j = 0; j < validMoves.size(); j++) {
                Move move = validMoves.get(j);
                Piece capturedPiece = board.executeMove(move);
                if (!isInCheck()) {
                    retval = true;
                }
                board.undoMove(move, capturedPiece);
            }
        }

//        System.out.println("End of canEscape");
//
//        String end = "";
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (board.getSquare(i, j).getPiece() != null) {
//                    end += board.getSquare(i, j).getPiece().toString() + " ";
//                } else {
//                    end += "null ";
//
//                }
//            }
//            end += "\n";
//        }
//
//        if (!begin.equals(end)) {
//            System.out.println("BUGGGGGGGGG!!!!!");
//            System.out.println(begin);
//            System.out.println();
//
//            System.out.println(end);
//
//        }


        return retval;
    }

    public void restart(GameBoard board) {
        this.board = board;
    }

    public void addScore() {
        this.score += 10;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * gets the king of the player
     */
    public abstract Piece getKing();

    /**
     * gets the set of alive pieces of the player
     */
    public abstract Set<Piece> getOwnPieces();

    /**
     * gets the set of alive pieces of the opponent player
     */
    public abstract Set<Piece> getOpponentPieces();

    /**
     * gets the color of the piece played by the player
     */
    public abstract PieceColor getColor();

}

