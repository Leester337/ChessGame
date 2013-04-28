package com.chess.actions;

import com.chess.Board;
import com.chess.GUI;
import com.chess.Square;
import com.chess.exceptions.CaptureOwnPieceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 2/3/13
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PieceActionListener implements MouseListener {
    Board currBoard;
    GUI currGui;


    /**
     * Constructor takes in the Model and the View
     * @param crrBoard
     * @param crrGui
     */
    public PieceActionListener(Board crrBoard, GUI crrGui)
    {
        currBoard = crrBoard;
        currGui = crrGui;

    }
    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }


    /**
     * Determine which square was clicked
     * If this Square was the first one clicked and it contains myPlayer's Piece, show possibleMoves
     * If this Square was clicked after another one of myPlayer's pieces were clicked, and this square
     * is a valid move, move there
     * @param e MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        JPanel currJPanel = (JPanel)e.getSource();
        float totRankNum = (float)Board.NUMRANK;

        int currFile = Math.round((totRankNum*currJPanel.getX())/(currJPanel.getParent().getWidth()));
        int currRank = Math.round(totRankNum*currJPanel.getY()/currJPanel.getParent().getHeight());

        Square currSelectedSquare = currBoard.board[currRank][currFile];
        Square prevSelectedSquare = currBoard.getSelectedSquare();

        //do nothing, you can't move that piece (because its not yours) or theres nothing there
        if (currSelectedSquare.getOccupiedPiece() == null  ||
                currSelectedSquare.getOccupiedPiece().myPlayer != currBoard.currentTurn)
        {

            //if there wasn't a piece selected before this, then do nothing
            if (prevSelectedSquare == null)
               return;

            //if there was a piece clicked before this, move to this square if it's a valid move
            else
            {
                try {
                    movePiece(prevSelectedSquare, currSelectedSquare);
                } catch (CaptureOwnPieceException e1) {
                    e1.printStackTrace();
                }
            }

        }
        //else if myPlayer clicked it's own piece
        else
        {
            //if myPlayer clicked its own piece before, then reset the board's images and colors
            if (prevSelectedSquare != null)
            {
                currGui.resetBackgrounds(currBoard);
            }

            //if it clicked it's own piece and there was no other piece clicked before this, show possibleMoves
            else
            {
                currBoard.setSelectedSquare(currSelectedSquare);
                showPossibleMoves(currSelectedSquare);
            }
        }


    }

    /**
     * Check to see if currSelectedSquare is a valid square to move to from the piece on prevSelectedSquare
     * If so move there, if not reset colors and images
     * @param prevSelectedSquare
     * @param currSelectedSquare
     * @throws CaptureOwnPieceException
     */
    private void movePiece(Square prevSelectedSquare, Square currSelectedSquare) throws CaptureOwnPieceException {

        currGui.checkLabel.setVisible(false);
        currGui.refreshPlayersPanel();

        //check to make sure the currSelected is a valid move
        List<Square> possibleMoves = prevSelectedSquare.getOccupiedPiece().getPossibleMoves();

        //if currSelectedSquare is not valid, reset colors and images
        if (!possibleMoves.contains(currSelectedSquare))
        {
            currGui.resetBackgrounds(currBoard);
            return;
        }

        prevSelectedSquare.getOccupiedPiece().move(currSelectedSquare);

        currGui.resetBackgrounds(currBoard);

        //alternate turns
        currBoard.currentTurn = currSelectedSquare.getOccupiedPiece().otherPlayer;

        //if otherPlayer is in check, demonstrate that
        currGui.setCheckLabel();

    }



    /**
     *Draw this piece to be green, it's possibleMoves to be cyan
     * @param selectedSquare
     */
    private void showPossibleMoves(Square selectedSquare)
    {
        JPanel[][] panelArray = currGui.panelArray;

        //set this piece to be green
        panelArray[selectedSquare.getRankNum()][selectedSquare.getFileNum()].setBackground(Color.green);

        //set possibleMoves to be cyan
        List<Square> possibleMoves = selectedSquare.getOccupiedPiece().getPossibleMoves();
        for (Square currSquare : possibleMoves)
        {
            panelArray[currSquare.getRankNum()][currSquare.getFileNum()].setBackground(Color.cyan);
        }

    }


}
