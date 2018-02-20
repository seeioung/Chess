package gui;

public class Main {

    /**
     * the start of the chess program
     * @param args
     */
    public static void main(String[] args){

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
