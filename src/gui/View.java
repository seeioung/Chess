package gui;

import gameboard.GameBoard;
import pieces.Piece;
import pieces.Piece.PieceColor;
import players.Player;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.LineBorder;

import static java.awt.Font.SANS_SERIF;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;


public class View extends JPanel implements Observer {
    private Game game;

    private JFrame window;
    private BoardPanel boardView;
    private ScorePanel whiteScorePanel;
    private ScorePanel blackScorePanel;
    private List<JButton> buttons = new ArrayList<>();
    private JMenuItem[] menuItems;
    private JLabel checkMateLabel;
    private JLabel stalemateLabel;
    private String whiteName;
    private String blackName;

    private boolean hasStarted; // whether the board can be clicked


    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        window = new JFrame("Chess");
        window.setSize(700, 510);
        window.setBackground(Color.lightGray);
        window.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        JPanel myPanel = initializePanel();
        myPanel.setBounds(0,0,700,470);
        layeredPane.add(myPanel);

        // initialize the checkmate sign
        ImageIcon checkmateIcon = new ImageIcon("assets/checkmate.png");
        Image smallImg = checkmateIcon.getImage(); // transform it
        smallImg = smallImg.getScaledInstance(300, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        checkmateIcon = new ImageIcon(smallImg);  // transform it back
        checkMateLabel = new JLabel(checkmateIcon);
        checkMateLabel.setBounds(200,150,300,100);
        layeredPane.add(checkMateLabel, new Integer(1));
        checkMateLabel.setVisible(false);

        // initialize the stalemate sign
        ImageIcon stalemateIcon = new ImageIcon("assets/stalemate.png");
        smallImg = stalemateIcon.getImage(); // transform it
        smallImg = smallImg.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        stalemateIcon = new ImageIcon(smallImg);  // transform it back
        stalemateLabel = new JLabel(stalemateIcon);
        stalemateLabel.setBounds(200,30,300,300);
        layeredPane.add(stalemateLabel, new Integer(1));
        stalemateLabel.setVisible(false);


        // initialize the Start button
        JButton startButton = new JButton("Start!");
        startButton.setMargin(new Insets(10,0,10,0));
        startButton.setFont(new Font(SANS_SERIF , Font.BOLD, 20));
        startButton.setForeground(new Color(230, 116, 81));
        startButton.setBounds(275,225,150,50);
        layeredPane.add(startButton, new Integer(1));
        buttons.add(startButton);

        // initialize the Start button after a game is over
        JButton startAnotherButton = new JButton("Start another game!");
        startAnotherButton.setMargin(new Insets(10,0,10,0));
        startAnotherButton.setFont(new Font(SANS_SERIF , Font.BOLD, 20));
        startAnotherButton.setForeground(new Color(230, 116, 81));
        startAnotherButton.setBounds(210,310,280,50);
        layeredPane.add(startAnotherButton, new Integer(1));
        buttons.add(startAnotherButton);
        startAnotherButton.setVisible(false);


        setUpMenu(window);

        window.setContentPane(layeredPane);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setHasStarted(false);
    }

    /**
     * Initialize the panel with the game board and two score panels
     * @return
     */
    private JPanel initializePanel() {
        JPanel myPanel = new JPanel();
        myPanel.setSize(new Dimension(700,500));
        myPanel.setLayout(new BorderLayout());
        this.boardView = new BoardPanel();
        myPanel.add(boardView);
        initializeScorePanels(myPanel);

        return myPanel;
    }

    /**
     * Initialize a score panel for each player
     * @param myPanel
     */
    private void initializeScorePanels(JPanel myPanel) {
        whiteScorePanel = new ScorePanel(WHITE);
        blackScorePanel = new ScorePanel(BLACK);
        myPanel.add(whiteScorePanel, BorderLayout.WEST);
        myPanel.add(blackScorePanel , BorderLayout.EAST);
    }

    /**
     * Set the the menu
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem start = new JMenuItem("Start with New Players");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem forfeit = new JMenuItem("Forfeit");

        menu.add((start));
        menu.add(restart);
        menu.add(forfeit);
        menuItems = new JMenuItem[]{start, restart, forfeit};

        menubar.add(menu);
        window.setJMenuBar(menubar);
    }


    @Override
    /**
     *  Override from Observer, called by the game
     */
    public void update(Observable o, Object arg) {
//        System.out.println("to update the view!!!!!!!");
        if (arg instanceof Boolean) {
            boardView.placePieces(game.getBoard());
            return;
        }

        if (arg instanceof Game.GameResult) { // game has ended
            Game.GameResult result = (Game.GameResult) arg;
            Player winner = result.getWinner();
            ScorePanel scorePanel = winner.getColor() == WHITE ? whiteScorePanel : blackScorePanel;
            scorePanel.setWin();
            scorePanel.setScore(winner.getScore());

            Game.GamePosition position = result.getPosition();
            if (position == Game.GamePosition.CHECKMATE) {
                // TODO show the checkmate panel
                checkMateLabel.setVisible(true);
            } else {
                // TODO show the stalemate panel
                stalemateLabel.setVisible(true);

            }
            buttons.get(3).setVisible(true);
            setHasStarted(false);
            return;
        }

        int[] move = (int[]) arg;
        System.out.println(move[0] + " " + move[1] + " " + move[2] + ' ' + move[3]);
        if (move[0] == 4 && move[1] == 4 && move[2] == 3 && move[3] == 5){
            if (game.getBoard().getSquare(3, 5).getPiece().getPieceColor() == BLACK) {
                System.out.println("black");
            }else{
                System.out.println("white");
            }
        }
        boardView.getSquareView(move[0], move[1]).placePiece(game.getBoard());
        boardView.getSquareView(move[2], move[3]).placePiece(game.getBoard());
    }


    /**
     * Adds the model to the view
     */
    public void addModel(Game game){
        this.game = game;
        boardView.placePieces(game.getBoard());
    }

    /**
     * Add controller to buttons and menu items
     * @param buttonController
     */
    public void addButtonController(ActionListener buttonController) {
        for (JMenuItem item : menuItems) {
            item.addActionListener(buttonController);
        }

        for (JButton button : buttons) {
            button.addActionListener(buttonController);
        }

        validate();
        repaint();
    }

    /**
     * Add controller to each square in the board
     * @param controller
     */
    public void addPieceController(MouseListener controller){
        this.boardView.addController(controller);	//need instance of controller before can add it as a listener
    }

    /**
     * Get the board panel
     */
    public BoardPanel getBoardView(){
        return this.boardView;
    }

    /**
     * Get the JFrame
     */
    public Component getWindow() {
        return this.window;
    }

    /**
     * set the status which indicate whether the game has started or not
     * @param hasStarted the status to update
     */
    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;

        for (int i = 1; i < menuItems.length; i++) {
            menuItems[i].setEnabled(hasStarted);
        }

        if (hasStarted) {
            blackScorePanel.resetWin();
            whiteScorePanel.resetWin();
            System.out.println("Hide buttons");
            buttons.get(2).setVisible(false); // hid the start button in the middle of the board
            buttons.get(3).setVisible(false); // hid the start button in the middle of the board
            checkMateLabel.setVisible(false);
            stalemateLabel.setVisible(false);

        }

    }

    /**
     * Returns if the game has started
     */
    public boolean getHasStarted() {
        return hasStarted;
    }

    /**
     * Sets the name of the white player
     * @param whiteName
     */
    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
        whiteScorePanel.setName(whiteName);
    }

    /**
     * Sets the name of the black player
     * @param blackName
     */
    public void setBlackName(String blackName) {
        this.blackName = blackName;
        blackScorePanel.setName(blackName);
    }

    /**
     * Gets the name of the white player
     */
    public String getBlackName() {
        return blackName;
    }

    /**
     * Gets the name of the black player
     */
    public String getWhiteName() {
        return whiteName;
    }

    /**
     * A BoardPanel is a JPanel which has a grid layout and contains all the components of a chess board
     */
    class BoardPanel extends JPanel {
        private SquarePanel[][] squareViews = new SquarePanel[8][8];
        BoardPanel() {
            super(new GridLayout(8,8));
            setSize(new Dimension(500,500));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel squarePanel = new SquarePanel(i, j);
                    add(squarePanel);
                    squareViews[i][j] = squarePanel;
                }
            }
            setBorder(new LineBorder(Color.black, 10));
        }

        /**
         * Place the piece icons into the board
         */
        void placePieces(GameBoard board) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    squareViews[i][j].placePiece(board);
                }
            }
        }

        /**
         * highlight the square at the given location
         */
        void highlight(int row, int col) {
            squareViews[row][col].select();
        }

        /**
         * unhighlight the square at the given location
         */
        void unhighlight() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    squareViews[i][j].unSelect();


                }
            }
        }

        /**
         * Add controller to each squares in the board
         * @param controller
         */
        void addController(MouseListener controller){
            System.out.println("board view      : adding controller");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    this.squareViews[i][j].addMouseListener(controller);	//need instance of controller before can add it as a listener
                }
            }
        }


        SquarePanel getSquareView(int i, int j) {
            return squareViews[i][j];
        }
    }

    /**
     * A SquarePanel is a JPanel which contains the components of each square in a chess board
     */
    public class SquarePanel extends JLayeredPane {
        private JLabel piece;
        private JLabel selectLabel;
        private int rowIndex;
        private int colIndex;

        SquarePanel(int rowIndex, int colIndex) {
            super();
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            setOpaque(true);
            setBackground(getSquareColor(rowIndex, colIndex));
            setSelectIcon();
        }

        private void setSelectIcon() {

            String imgFilename = "assets/select.png";
            ImageIcon icon = new ImageIcon(imgFilename);
            Image smallImg = icon.getImage(); // transform it
            smallImg = smallImg.getScaledInstance(53, 53,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(smallImg);  // transform it back
            selectLabel = new JLabel(icon);
            selectLabel.setBounds(1,2,53,53);
            add(selectLabel,JLayeredPane.POPUP_LAYER);
            unSelect();
            validate();
            repaint();
        }

        /**
         * get the color of the square based on its coordinated
         */
        Color getSquareColor(int i, int j) {
            if ((i + j) % 2 == 0) {
                return Color.lightGray;
            }
            return Color.darkGray;
        }

        /**
         * gets the chess piece icon of the square in a initial chess board
         */
        ImageIcon getPieceIcon(GameBoard board, int i, int j) {

            String imgFilename = "assets/";
            Piece piece = board.getSquare(i,j).getPiece();
            if (piece == null) {
                return null;
            }
            imgFilename += piece.getString();
            imgFilename += ".png";

            ImageIcon icon = new ImageIcon(imgFilename);
            Image smallImg = icon.getImage(); // transform it
            smallImg = smallImg.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(smallImg);  // transform it back

            return icon;
        }


        void placePiece(GameBoard board) {
            ImageIcon icon = getPieceIcon(board, rowIndex, colIndex);
            if (piece != null) {
                remove(piece);
                piece = null;
            }
            if (icon != null){
                piece = new JLabel(icon);
                piece.setBounds(3,3,50,50);
                add(piece, JLayeredPane.DEFAULT_LAYER);
            }

            validate();
            repaint();
        }

        /**
         * highlight the square with the icon
         */
        void select() {
            selectLabel.setVisible(true);

            validate();
            repaint();
        }

        /**
         * unhighlight the square with the icon
         */
        void unSelect() {
            selectLabel.setVisible(false);

            validate();
            repaint();
        }

        /**
         * Gets the coordinates of the square in the board
         */
        public int[] getCoordinates() {
            return new int[]{rowIndex, colIndex};
        }
    }

    /**
     * A Panel with the profile, name and score of a player
     */
    public class ScorePanel extends JPanel{
        private PieceColor color;
        private JLabel winLabel;
        private JLabel nameLabel;
        private JLabel scoreLabel;


        ScorePanel(PieceColor color) {
            super();
            this.color = color;
            setLayout(new GridBagLayout());
            setSize(new Dimension(200,500));
            GridBagConstraints c = new GridBagConstraints();

            c.ipadx = 30;
            c.gridx = 0;
            c.gridy = 0;

            ImageIcon icon = new ImageIcon("assets/winner.png");
            Image smallImg = icon.getImage(); // transform it
            smallImg = smallImg.getScaledInstance(80, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(smallImg);  // transform it back
            winLabel = new JLabel(icon);
            winLabel.setPreferredSize(new Dimension(80,40));
            winLabel.setMinimumSize(new Dimension(80,40));
            add(winLabel, c);
            winLabel.setVisible(false);


            c.ipadx = 40;
            c.gridx = 0;
            c.gridy = 2;
            c.weighty = 0;
            String imgName = color == WHITE ? "assets/white_profile.png" : "assets/black_profile.png";
            icon = new ImageIcon(imgName);smallImg = icon.getImage(); // transform it
            smallImg = smallImg.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(smallImg);  // transform it back
            JLabel profileIcon = new JLabel(icon);
            add(profileIcon, c);

            c.gridy = 3;
            c.ipady = 20;
            c.fill = GridBagConstraints.HORIZONTAL;
            nameLabel = new JLabel("???", SwingConstants.CENTER);
            add(nameLabel, c);


            c.gridy = 4;
            c.ipady = 60;
            scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
            add(scoreLabel, c);

            c.gridy = 5;
            c.ipady = 0;
            JButton undoButton = new JButton("Undo");
            undoButton.setPreferredSize(new Dimension(40, 40));
            add(undoButton, c);
            buttons.add(undoButton);
        }

        /**
         * get the owner of the score panel
         */
        public PieceColor getColor() {
            return color;
        }

        void setScore(int score) {
            scoreLabel.setText("Score: " + score);
            validate();
            repaint();

        }

        /**
         * Sets the name of the player to display it in the score panel
         * @param name
         */
        public void setName(String name) {
            nameLabel.setText(name);
            validate();
            repaint();

        }

        /**
         * set the panel to show that the player is the winner
         */
        void setWin() {
            winLabel.setVisible(true);
            validate();
            repaint();
        }

        /**
         * reset the panel when a new game has start and the winner is not decided
         */
        void resetWin() {
            winLabel.setVisible(false);
            validate();
            repaint();
        }
    }
}