package com.chess.pieces;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/31/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueenKnightPiece extends Piece {

    /**
     * Constructor for the QueenKnight piece
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
    public QueenKnightPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
        super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "knightorange.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "knightpurple.png"), "Black Bishop");
        imageLabel.setIcon(image);
    }

    /**
     * Returns possible moves of what a Queen and a Knight could do
     * @param checkIfCheck
     * @return
     */
    @Override
    public List<Square> getPossibleMoves(boolean checkIfCheck) {

        List<Square> possibleSquares = new ArrayList<Square>();

        //Knight moves
        addToListIfValidSingle(getRankNum() +2, getFileNum() +1, possibleSquares, checkIfCheck); //Check DOWN 2 RIGHT 1
        addToListIfValidSingle(getRankNum() +2, getFileNum() -1, possibleSquares, checkIfCheck); //Check DOWN 2 LEFT 1
        addToListIfValidSingle(getRankNum() -2, getFileNum() +1, possibleSquares, checkIfCheck); //Check UP 2 RIGHT 1
        addToListIfValidSingle(getRankNum() -2, getFileNum() -1, possibleSquares, checkIfCheck); //Check UP 2 LEFT 1
        addToListIfValidSingle(getRankNum() +1, getFileNum() +2, possibleSquares, checkIfCheck); //Check DOWN 1 RIGHT 2
        addToListIfValidSingle(getRankNum() +1, getFileNum() -2, possibleSquares, checkIfCheck); //Check DOWN 1 LEFT 2
        addToListIfValidSingle(getRankNum() -1, getFileNum() +2, possibleSquares, checkIfCheck); //Check UP 1 RIGHT 2
        addToListIfValidSingle(getRankNum() -1, getFileNum() -2, possibleSquares, checkIfCheck); //Check UP 1 LEFT 2

        //Queen moves
        addToListIfValidMultiple(getRankNum() +1, getFileNum() +1, possibleSquares, checkIfCheck); //BOTTOM RIGHT
        addToListIfValidMultiple(getRankNum() -1, getFileNum() -1, possibleSquares, checkIfCheck); //TOP LEFT
        addToListIfValidMultiple(getRankNum() +1, getFileNum() -1, possibleSquares, checkIfCheck); //BOTTOM LEFT
        addToListIfValidMultiple(getRankNum() -1, getFileNum() +1, possibleSquares, checkIfCheck); //TOP RIGHT
        addToListIfValidMultiple(getRankNum(), getFileNum() +1, possibleSquares, checkIfCheck); //RIGHT
        addToListIfValidMultiple(getRankNum(), getFileNum() -1, possibleSquares, checkIfCheck); //LEFT
        addToListIfValidMultiple(getRankNum() -1, getFileNum(), possibleSquares, checkIfCheck); //TOP
        addToListIfValidMultiple(getRankNum() +1, getFileNum(), possibleSquares, checkIfCheck); //BOTTOM

        return possibleSquares;
    }
}
