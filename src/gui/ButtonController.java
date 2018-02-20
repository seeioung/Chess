package gui;

import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pieces.Piece.PieceColor.WHITE;

/**
 * the controller of buttons and menu items in the chess game
 */
public class ButtonController implements ActionListener {
    private Game game;
    private View view;

    @Override
    public void actionPerformed(ActionEvent e) {
//        JOptionPane.showMessageDialog(null,"button clicked");
        view.getBoardView().unhighlight();

        Object obj = e.getSource();
        if (obj instanceof JButton) {
            String buttonString = ((JButton) obj).getText();
            if (buttonString.equals("Start!")) { /* Start button clicked*/
                startWithNewPlayer();
            } else if (buttonString.equals("Start another game!")) {
                restartWithOption();
            } else {
                // Undo button clicked
                Piece.PieceColor playerToUndo = ((View.ScorePanel)(((JButton) obj).getParent())).getColor();
                if (game.getCurrentTurn() != playerToUndo) {
                    game.undo();
                }
            }
        } else {   // menu clicked
            String menuString = ((JMenuItem) obj).getText();
            switch (menuString) {
                case "Start with New Players":
                    startWithNewPlayer();
                    break;
                case "Restart":
                    restartWithOption();
                    break;
                default: /* forfeit clicked*/
                    String name = game.getCurrentTurn() == WHITE ? view.getBlackName() : view.getWhiteName();
                    int dialogResult = JOptionPane.showConfirmDialog(view.getWindow(), "Hey, " + name + ". \nDo you agree to forfeit?", "Forfeit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        restartWithOption();
                    }
                    break;
            }
        }
    }


    private void startWithNewPlayer() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(3, 2, 2, 2));

        JTextField whitePlayer = new JTextField(5);
        JTextField blackPlayer = new JTextField(5);

        pane.add(new JLabel("Name of White player: "));
        pane.add(whitePlayer);

        pane.add(new JLabel("Name of Black player: "));
        pane.add(blackPlayer);

        JCheckBox checkbox = new JCheckBox();
        pane.add(new JLabel("Custom Piece ? "));
        pane.add(checkbox);

        int option = JOptionPane.showConfirmDialog(view.getWindow(), pane, "Let's start a new game", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {

            String whitePlayerName = whitePlayer.getText();
            String blackPlayerName = blackPlayer.getText();
            if (blackPlayerName == null || blackPlayerName.equals("") || whitePlayerName == null || whitePlayerName.equals("")) {
                return;
            }
            view.setWhiteName(whitePlayerName);
            view.setBlackName(blackPlayerName);
            view.setHasStarted(true);

            if (checkbox.isSelected()) {
                game.initCustomGame();
            } else {
                game.initStandardGame();
            }
        }
    }

    private void restartWithOption() {
        int dialogResult = JOptionPane.showConfirmDialog (view.getWindow(), "Play with custom pieces?","Setting?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {
            game.restart(false);

        } else {
            game.restart(true);
        }
        view.setHasStarted(true);

    }

    public void addModel(Game game){
        System.out.println("Controller: adding game");
        this.game = game;
    }

    public void addView(View v){
        System.out.println("Controller: adding view");
        this.view = v;
    }


}
