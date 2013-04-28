package com.chess.actions;

import com.chess.Board;
import com.chess.GUI;
import com.chess.Player;
import com.chess.Square;
import com.chess.pieces.BishopKnightPiece;
import com.chess.pieces.Piece;
import com.chess.pieces.QueenKnightPiece;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 2/5/13
 * Time: 3:35 PM
 *
 *
 * These are the Controllers for the Menu items
 */
public class GameMenuActions implements ActionListener {

    Board currBoard;
    GUI currGui;


    /**
     * Constructor - takes in the Board (the Model) and the GUI (the View)
     * @param crrBd
     * @param crrGui
     */
    public GameMenuActions(Board crrBd, GUI crrGui)
    {
        currBoard = crrBd;
        currGui = crrGui;

    }

    /**
     * Determines which function to do based on which MenuItem was clicked
     * @param e - the menu item
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        char menuItemText = ((JMenuItem)e.getSource()).getText().charAt(0);

        //determines which menu item based on the first letter of it's text
        switch (menuItemText)
        {
            case 'F': //F -> Forfeit game
                forfeitGame();
                break;

            case 'U': //U -> Undo move
                undoMove();
                break;

            case 'A': //A -> Add Custom Pieces
                addCustomPieces(e);
                break;

            case 'R': //R -> Remove Custom Pieces
                removeCustomPieces(e);
                break;
        }
    }

    /**
     * Removes White and Black's custom pieces from the board
     * Changes the MenuItem's text to Add Custom Pieces
     * @param e - the Menu item to be changed
     */
    private void removeCustomPieces(ActionEvent e) {
        removeCustomPiecesFromPlayer(currBoard.getWhite());
        removeCustomPiecesFromPlayer(currBoard.getBlack());

        currGui.resetBackgrounds(currBoard);

        JMenuItem addPieceMenuItem = (JMenuItem)e.getSource();
        addPieceMenuItem.setText("Add Custom Pieces");
        addPieceMenuItem.getParent().revalidate();
    }

    /**
     * Remove's myPlayer's custom pieces from the board
     * @param myPlayer
     */
    private void removeCustomPiecesFromPlayer(Player myPlayer)
    {
        int numPieces = myPlayer.customPieces.size();

        //for each custom piece
        while (numPieces != 0)
        {
            Piece customPiece = myPlayer.customPieces.get(0);
            Square currSquare = currBoard.board[customPiece.getRankNum()][customPiece.getFileNum()];

            //remove it from all the places that references it
            currSquare.setOccupiedPiece(null);
            myPlayer.customPieces.remove(customPiece);
            myPlayer.myPieces.remove(customPiece);

            numPieces--;
        }
    }

    /**
     * Adds White's and Black's custom pieces, changes menu item text to Remove Custom Piece
     * @param e - Menu Item
     */
    private void addCustomPieces(ActionEvent e) {

        addPlayersCustomPieces(currBoard.getWhite(), currBoard.getBlack(), 7, -1);
        addPlayersCustomPieces(currBoard.getBlack(), currBoard.getWhite(), 0, 1);

        currGui.resetBackgrounds(currBoard);

        JMenuItem addPieceMenuItem = (JMenuItem)e.getSource();
        addPieceMenuItem.setText("Remove Custom Pieces");
        addPieceMenuItem.getParent().revalidate();
    }

    /**
     * This searches for the first available Square to place a custom piece by searching left to right from
     * myPlayer's back rank (i.e. rank 0 for black and 7 for white)
     * Creates the custom piece for myPlayer
     * @param myPlayer
     * @param otherPlyr
     * @param rank rank to start searching at
     * @param direction should the next rank be rank-- or rank++ - white = -1, black = 1
     */
    private void addPlayersCustomPieces(Player myPlayer, Player otherPlyr, int rank, int direction) {
        Square currSquare = findAvailSquare(rank, direction);
        BishopKnightPiece newBishopKnight =
                new BishopKnightPiece(currSquare.getRankNum(), currSquare.getFileNum(), myPlayer, otherPlyr, currBoard);
        addPieceToModel(newBishopKnight, myPlayer, currSquare);

        currSquare = findAvailSquare(rank, direction);
        QueenKnightPiece newQueenKnight =
                new QueenKnightPiece(currSquare.getRankNum(), currSquare.getFileNum(), myPlayer, otherPlyr, currBoard);
        addPieceToModel(newQueenKnight, myPlayer, currSquare);
    }

    /**
     * Find the first available square to place, see addPlayersCustomPieces
     * @param rank - the rank it should start searching at
     * @param direction - the direction of upcoming ranks it should search
     * @return the first square that doesn't have a piece on it
     */
    private Square findAvailSquare(int rank, int direction)
    {
        int file = 0;
        Square currSquare = currBoard.board[rank][file];

        //search until you find an empty square
        while (currSquare.getOccupiedPiece() != null)
        {
            file = (file+1)%8;

            //if you're starting a new rank, increment/decrement the rank number
            if (file == 0)
            {
                rank = rank + direction;
            }
            currSquare = currBoard.board[rank][file];
        }
        return currSquare;
    }

    /**
     * Adds the new custom Piece to the lists it needs to be added to
     * @param newPiece
     * @param myPlayer
     * @param currSquare
     */
    private void addPieceToModel(Piece newPiece, Player myPlayer, Square currSquare)
    {
        currSquare.setOccupiedPiece(newPiece);
        myPlayer.myPieces.add(newPiece);
        myPlayer.customPieces.add(newPiece);
    }

    /**
     * If theres a possible undo, ask the player if he'd like to do it
     */
    private void undoMove() {
        if (!currBoard.undoMove())
        {
            JOptionPane.showMessageDialog(
                    currGui.window,
                    "No move has been recorded, so Undo is\n" +
                            "unavaiable at this time.",
                    "There is no Undo",
                    JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            currGui.resetBackgrounds(currBoard);
        }
    }


    /**
     * Forfeit the game by first asking the current player and then asking the opponent
     * If the opponent agrees, the board resets, the score stays the same
     * If the opponent disagrees, the opponent's score increases by 1
     */
    private void forfeitGame() {
        String currTurn;
        String opponent;
        Player myPlayer;

        //Set myPlayer based on who's turn it is
        if (currBoard.currentTurn == currBoard.getWhite())
        {
            currTurn = currBoard.getWhite().getName();
            myPlayer = currBoard.getWhite();
            opponent = currBoard.getBlack().getName();

        }
        else
        {
            currTurn = currBoard.getBlack().getName();
            myPlayer = currBoard.getBlack();
            opponent = currBoard.getWhite().getName();
        }

        //Ask myPlayer if he's sure he'd like to forfeit
        int n = JOptionPane.showConfirmDialog(
                currGui.window,
                "If your opponent would also like to forfeit,\n"
                + "the score will be unchanged. If he refuses,\n"
                + "we will award him a point.",
                "Would " + currTurn + " Like to Forfeit?",
                JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.NO_OPTION)
            return;

        //Ask the opponent if he'd like to forfeit
        n = JOptionPane.showConfirmDialog(
                currGui.window,
                "If " + opponent + " forfeits the score will,\n"
                 + "be unchanged. If not,\n"
                 + "we will give you a point.",
                "Would " + opponent + " Like to Forfeit?",
                JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.NO_OPTION)
            myPlayer.setScore(myPlayer.getScore() + 1);

        currGui.resetGame();


    }


}
