package com.chess;

import com.chess.actions.GameMenuActions;
import com.chess.actions.PieceActionListener;
import com.chess.pieces.Piece;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/29/13
 * Time: 6:44 PM
 *
 * This class is based on the example provided in the specs
 * (https://wiki.engr.illinois.edu/display/cs242sp13/Assignment+1.1)
 *
 * Pieces from http://all-free-download.com/free-vector/vector-clip-art/chess_set_pieces_clip_art_20450.html
 *
 */
public class GUI {
    //panelArray - stores the individual square panels in a 2D array that corresponds to the chess board
    public JPanel[][] panelArray;
    JPanel playerPanel;
    public JFrame window;
    Board currBoard;
    JPanel boardPanel;
    JPanel wholeWindow;
    public JLabel checkLabel;

    /**
     * Initializes the window along with all the panels and chess board. It also sets up the pieces
     *
     *
     * @param myBoard - A reference to the Model
     */
    public GUI(Board myBoard)
    {
        currBoard = myBoard;
        currBoard.currGUI = this;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
       setUpGUI();
    }

    /**
     * Sets up the GUI
     */
    private void setUpGUI()
    {
        //Sets up the Window
        window = new JFrame("Chess");
        window.setSize(800, 500);

        //Creates a JPanel containing the whole Window - this contains my Board JPanel and the Player JPanel
        setUpWindowPanel();

        //create the boardPanel and set its gridlayout
        setUpBoardPanel();

        //create the playerPanel to store the scores of the two players
        setUpPlayerPanel();

        window.setContentPane(wholeWindow);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializePlayerPanel();
        initializeSquarePanels();
        setUpMenu(window);
        wholeWindow.revalidate(); //refresh window to show the menu
    }

    /**
     * Sets up the Window Panel which contains all the contents of the window
     */
    private void setUpWindowPanel()
    {
        wholeWindow = new JPanel();
        wholeWindow.setSize(new Dimension(800,500));
        GridBagLayout windowGridLayout = new GridBagLayout();
        wholeWindow.setLayout(windowGridLayout);
    }

    /**
     * Sets up the boardPanel which contains the chessboard
     */
    private void setUpBoardPanel()
    {
        GridBagConstraints c = new GridBagConstraints();

        boardPanel = new JPanel();
        boardPanel.setSize(new Dimension(450,500));
        GridLayout boardGridLayout = new GridLayout(8,8);
        boardPanel.setLayout(boardGridLayout);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0; //set board to be left of Players
        c.gridy = 0;
        c.weightx = 500; //set its starting width to be 500px

        wholeWindow.add(boardPanel, c);
    }

    /**
     * Sets up the playerPanel which contains the players names, scores and whether a check is happening
     */
    private void setUpPlayerPanel()
    {
        GridBagConstraints c = new GridBagConstraints();

        playerPanel = new JPanel();
        GridLayout playerGridLayout = new GridLayout(3,1);
        playerPanel.setLayout(playerGridLayout);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1; //set it to be right of board JPanel
        c.gridy = 0;
        c.weightx = 100;

        wholeWindow.add(playerPanel, c);
    }

    /**
     * Initializes the individual panels and looks at the board to determine which pieces it should draw,
     * adds the PieceActionListener to each JPanel square, and adds it to the boardPanel
     */
    private void initializeSquarePanels()
    {
         panelArray = new JPanel[Board.NUMRANK][Board.NUMFILE];
        for (int rank = 0; rank < panelArray.length; rank++)
        {
            for (int file = 0; file < panelArray[0].length; file++)
            {

                JPanel tempPanel;
                panelArray[rank][file] = tempPanel = new JPanel();
                boardPanel.add(tempPanel);
                tempPanel.setLayout(new GridLayout(1, 1));

                PieceActionListener myListener = new PieceActionListener(currBoard, this);
                tempPanel.addMouseListener(myListener);

                Piece currPiece = currBoard.board[rank][file].getOccupiedPiece();

                //If there exists a piece on the square, add it to our panel
                if (currPiece != null)
                {
                    panelArray[rank][file].add(currPiece.imageLabel);
                }

                //alternate between light and dark gray backgrounds
                if ((rank + file) % 2 == 0)
                {
                    currBoard.board[rank][file].backgroundColor = Color.lightGray;
                    tempPanel.setBackground(Color.lightGray);
                }
                else
                {
                    currBoard.board[rank][file].backgroundColor = Color.darkGray;
                    tempPanel.setBackground(Color.darkGray);
                }


            }
        }
    }

    /**
     * Notifies the player that the game is over and gives them an option to start a new one
     * @param checkOrStale
     * @return
     */
    public boolean checkmateOrStalemate(String checkOrStale)
    {
        int n = JOptionPane.showConfirmDialog(
                window,
                "Looks like game over buddy.\n"
                + "Would you like to play another?",
                checkOrStale + "!!",
                JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.NO_OPTION)
            return false;
        else return true;
    }

    /**
     * Initializes playerPanel by asking for the name of each player and drawing their name and score
     */
    private void initializePlayerPanel()
    {
        if (currBoard.black.getName() == null)
        {
            //ask for the player's name
            String name = getNameDialog("black");

            //if the name is invalid, set it to Black Player
            if (name == null || name.isEmpty())
            {
                name = "Black Player";
            }
            currBoard.black.setName(name);
        }

        if (currBoard.white.getName() == null)
        {
            //ask for the player's name
            String name = getNameDialog("white");

            //if the name is invalid, set it to White Player
            if (name == null || name.isEmpty())
            {
                name = "White Player";
            }
            currBoard.white.setName(name);
        }

        JLabel blackPlayerLabel = new JLabel(currBoard.black.getName() + ": " + currBoard.black.getScore());

        checkLabel = new JLabel("is checked");
        checkLabel.setForeground(Color.red);
        checkLabel.setVisible(false);

        JLabel whitePlayerLabel = new JLabel(currBoard.white.getName() + ": " + currBoard.white.getScore());

        playerPanel.add(blackPlayerLabel);
        playerPanel.add(checkLabel);
        playerPanel.add(whitePlayerLabel);
    }

    /**
     * Creates a dialog to ask for the Player's name
     * @param player which player is it asking for
     * @return player's name
     */
    private String getNameDialog(String player)
    {

        String s = JOptionPane.showInputDialog(
                playerPanel,
                "Please enter the " + player + " player's name:",
                player.substring(0,1).toUpperCase() + player.substring(1) + " Player's Name",
                JOptionPane.OK_CANCEL_OPTION);
        return s;
    }

    /**
     * repaints the playerPanel
     */
    public void refreshPlayersPanel()
    {
        playerPanel.repaint();
    }

    /**
     * Create menu bar for game options
     * @param window the window it's being drawn on
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        GameMenuActions actionListener = new GameMenuActions(currBoard, this);
        JMenu game = new JMenu("Game");

        JMenuItem forfeitGame = new JMenuItem("Forfeit");
        forfeitGame.addActionListener(actionListener);

        JMenuItem undoMove = new JMenuItem("Undo Move");
        undoMove.addActionListener(actionListener);

        JMenuItem addCustomPieces = new JMenuItem("Add Custom Pieces");
        addCustomPieces.addActionListener(actionListener);

        game.add(forfeitGame);
        game.add(undoMove);
        game.add(addCustomPieces);

        menubar.add(game);
        window.setJMenuBar(menubar);
    }

    /**
     * resets the entire game
     */
    public void resetGame()
    {
        window.dispose();
        currBoard.resetBoard();
        setUpGUI();
    }

    /**
     * Sets the check label to visible and declare which player is in check
     */
    public void setCheckLabel()
    {
        Player currTurn;
        Player opponent;

        if (currBoard.currentTurn == currBoard.white)
        {
            currTurn = currBoard.white;
            opponent = currBoard.black;
        }
        else
        {
            currTurn = currBoard.black;
            opponent = currBoard.white;
        }

        if (currBoard.currentTurn.getMyKing().checkIfCheck(currTurn, opponent))
        //if (!currBoard.currentTurn.getCausingCheck().isEmpty())
        {
            checkLabel.setText(currBoard.currentTurn.getName() + " is in check!!");
            checkLabel.setVisible(true);

        }
        else
        {
            checkLabel.setVisible(false);
        }
        refreshPlayersPanel();
    }

    /**
     * refresh the board to represent the Model
     * @param currBoard
     */
    public void resetBackgrounds(Board currBoard)
    {
        Square[][] board = currBoard.board;

        currBoard.setSelectedSquare(null);

        for (int rank = 0; rank < Board.NUMRANK; rank++)
        {
            for (int file = 0; file < Board.NUMFILE; file++)
            {
                JPanel tempPanel;
                tempPanel = panelArray[rank][file];

                //remove all images of pieces
                tempPanel.removeAll();
                Piece currPiece = currBoard.board[rank][file].getOccupiedPiece();

                //If there exists a piece on the square, add it to our panel
                if (currPiece != null)
                {
                    panelArray[rank][file].add(currPiece.imageLabel);
                    tempPanel.repaint();
                    tempPanel.revalidate();
                }
                panelArray[rank][file].setBackground(board[rank][file].backgroundColor);
            }
        }



    }



}
