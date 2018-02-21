package gui;

import gameboard.GameBoard;
import pieces.Move;
import players.Player;
import players.WhitePlayer;
import players.BlackPlayer;

import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import pieces.Piece.PieceColor;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by SiyangLiu on 2018/2/2.
 * Represents a chess game
 */
public class Game extends Observable{
    private GameBoard board;
    private Player whitePlayer;
    private Player blackPlayer;
    private PieceColor currentTurn;
    private Deque<Move> undoStack;



    public Game(int numOfRows, int numOfCols) {
        this.board = new GameBoard(numOfRows, numOfCols);
        this.whitePlayer = new WhitePlayer(this.board);
        this.blackPlayer = new BlackPlayer(this.board);
        this.currentTurn = WHITE;
        this.undoStack = new LinkedList<>();
    }

    public Game(GameBoard board) {
        this.board = board;
        this.whitePlayer = new WhitePlayer(this.board);
        this.blackPlayer = new BlackPlayer(this.board);
        this.currentTurn = WHITE;
        this.undoStack = new LinkedList<>();
    }



    /**
     * checks if one of the players has won the game
     * @return GameResult with winner and final position, return null if game is not over
     */
    private GameResult isGameOver(PieceColor player) {
        Player possibleLoser = player == BLACK ? blackPlayer : whitePlayer;
        Player possibleWinner = player == BLACK ? whitePlayer : blackPlayer;
        if (possibleLoser.isInCheckmate()) {
            return new GameResult(possibleWinner, GamePosition.CHECKMATE);
        } else if (possibleLoser.isInStalemate()){
            return new GameResult(possibleWinner, GamePosition.STALEMATE);
        }

        return null;
    }

    /**
     * Process the move from start to dest
     * @return whether the move is legal
     */
    public boolean process(int[] start, int[] dest) {
        boolean success;
        if (currentTurn == WHITE) {
            success = whiteTurn(new Move(board.getSquare(start[0], start[1]).getPiece(), dest[0], dest[1], board.getSquare(dest[0], dest[1]).getPiece()));
        } else {
            success = blackTurn(new Move(board.getSquare(start[0], start[1]).getPiece(), dest[0], dest[1], board.getSquare(dest[0], dest[1]).getPiece()));
        }

        if (success) {
            currentTurn = currentTurn == BLACK ? WHITE : BLACK;

            setChanged();
            notifyObservers(new int[]{start[0], start[1],dest[0], dest[1]});

            GameResult result = isGameOver(currentTurn);
            if (result != null) {
                Player winner = result.getWinner();
                winner.addScore();
                setChanged();
                notifyObservers(result);
            }
        }


        System.out.println("process: "+success);
        return success;
    }

    /**
     * Initialize the game board with standard chess pieces
     */
    public void initStandardGame() {
        this.board.placeStandardPieces();
        setChanged();
        notifyObservers(true);
    }

    /**
     * Initialize the game board with custom chess pieces
     */
    public void initCustomGame() {
        this.board.placeCustomPieces();
        setChanged();
        notifyObservers(true);
    }

    /**
     * let white player to move
     */
    public boolean whiteTurn(Move move) {
        boolean success = this.whitePlayer.movePiece(move);
        if (success) {
            undoStack.offerFirst(move);
        }
        return success;
    }

    /**
     * let black player to move
     */
    public boolean blackTurn(Move move) {
        boolean success = this.blackPlayer.movePiece(move);
        if (success) {
            undoStack.offerFirst(move);
        }
        return success;
    }

    /**
     * Undo the last move executed
     */
    public void undo() {
        Move move = undoStack.pollFirst();
        if (move == null) {
            return;
        }
        this.board.undoMove(move);
        currentTurn = move.getPiece().getPieceColor();
        setChanged();
        notifyObservers(new int[]{move.getStartRow(), move.getStartCol(), move.getDestRow(), move.getDestCol()});
    }

    /**
     * Restart the game
     * @param standard whether to start a standard game
     */
    public void restart(boolean standard) {
        this.board = new GameBoard(8,8);
        if (standard) {
            this.board.placeStandardPieces();

        } else {
            this.board.placeCustomPieces();

        }
        this.whitePlayer.restart(board);
        this.blackPlayer.restart(board);
        this.currentTurn = WHITE;
        this.undoStack = new LinkedList<>();
        setChanged();
        notifyObservers(Boolean.TRUE);
    }

    /**
     * gets the board of the game
     */
    public GameBoard getBoard(){
        return this.board;
    }

    /**
     * gets the white player of the game
     */
    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    /**
     * gets the black player of the game
     */
    public Player getBlackPlayer(){
        return this.blackPlayer;
    }

    /**
     * gets the current turn of the game
     */
    public PieceColor getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * A GameResult contains a winner and the type of final board position
     * Constructed when a Game is over
     */
    public class GameResult {
        private Player winner;
        private GamePosition position;
        GameResult(Player winner, GamePosition position) {
            this.winner = winner;
            this.position = position;
        }

        /**
         * Gets the winner
         */
        public Player getWinner() {
            return winner;
        }

        /**
         * gets the position
         */
        public GamePosition getPosition() {
            return position;
        }
    }

    /**
     * Enumerates all possible game position that ends a game
     */
    public enum GamePosition {
        CHECKMATE,
        STALEMATE
    }
}
