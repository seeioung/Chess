package gui;

import gameboard.GameBoard;
import pieces.Move;
import pieces.Piece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static gui.Controller.Status.NONE;
import static gui.Controller.Status.START_SELECTED;

public class Controller implements MouseListener{
    private Game game;
    private View view;

    private Status status;
    private int[] startCoordinates;


    Controller() {
        status = NONE;
    }

    /**
     * add the model to this controller
     * @param game
     */
    public void addModel(Game game){
        this.game = game;
    }

    /**
     * add the view to this controller
     * @param v
     */
    public void addView(View v){
        this.view = v;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!view.getHasStarted()) {
            System.out.println("not started yet");
            return;
        }

        View.SquarePanel squareView = (View.SquarePanel) e.getSource();
        int[] selectedCoordinates = squareView.getCoordinates();
        GameBoard board = game.getBoard();
        Piece pieceSelected = board.getSquare(selectedCoordinates[0], selectedCoordinates[1]).getPiece();


        if (status == NONE && pieceSelected != null && pieceSelected.getPieceColor() == game.getCurrentTurn()) { // the piece to move is selected
            startCoordinates = selectedCoordinates;
            status = START_SELECTED;

            // highlight all legal destinations
            view.getBoardView().highlight(startCoordinates[0], startCoordinates[1]);
            List<Move> legalMoves = game.getBoard().getSquare(startCoordinates[0], startCoordinates[1]).getPiece().getValidMoves();
            for (Move move : legalMoves) {
                view.getBoardView().highlight(move.getDestRow(),move.getDestCol());
            }
        } else if (status == START_SELECTED) {
            game.process(startCoordinates, selectedCoordinates);
            status = NONE;
            view.getBoardView().unhighlight();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    enum Status{
        START_SELECTED, // wait for the selection of dest square
        NONE // no square selected
    }
}
