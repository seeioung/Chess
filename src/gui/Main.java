package gui;

import gameboard.GameBoard;
import pieces.King;
import pieces.Piece;
import pieces.Rook;
import players.BlackPlayer;
import players.Player;
import players.WhitePlayer;

public class Main {

    /**
     * the start of the chess program
     * @param args
     */
    public static void main(String[] args){
//        // construct game to test stalemate
//        GameBoard board = new GameBoard(8,8);
//        board.placePiece(new King(Piece.PieceColor.WHITE, 5,2));
//        board.placePiece(new Rook(Piece.PieceColor.WHITE, 1,1));
//        board.placePiece(new King(Piece.PieceColor.BLACK, 7,0));
//        Game game = new Game(board);

        //create Model and View
        Game game = new Game(8, 8);
        View view = new View();

        view.addModel(game); //tell view about model
        game.addObserver(view); //tell Model about View

        //create Controller. tell it about Model and View, initialise model
        Controller myController = new Controller();
        myController.addModel(game);
        myController.addView(view);

        view.addPieceController(myController); //tell view about controller

        ButtonController buttonController = new ButtonController();
        buttonController.addModel(game);
        buttonController.addView(view);
        view.addButtonController(buttonController);


    }


}
