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
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BishopKnightPiece extends Piece{

    /**
     * Constructor that initializes my BishopKnight
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
    public BishopKnightPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
        super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "bishoporange.png"), "White BishopKnight");
        else image = new ImageIcon(getClass().getResource(imagePath + "bishoppurple.png"), "Black BishopKnight");
        imageLabel.setIcon(image);
    }

    /**
     * returns a list of all possible moves the BishopKnight can make
     * @param checkIfCheck if true, check to see if the by potentially moving to the possible move it would
     *                     put your king in check
     * @return
     */
    @Override
    public List<Square> getPossibleMoves(boolean checkIfCheck) {
        List<Square> possibleSquares = new ArrayList<Square>();

        //Bishop moves - check each of its diagonals for possible moves
        addToListIfValidMultiple(getRankNum() +1, getFileNum() +1, possibleSquares, checkIfCheck); //BOTTOM RIGHT
        addToListIfValidMultiple(getRankNum() -1, getFileNum() -1, possibleSquares, checkIfCheck); //TOP LEFT
        addToListIfValidMultiple(getRankNum() +1, getFileNum() -1, possibleSquares, checkIfCheck); //BOTTOM LEFT
        addToListIfValidMultiple(getRankNum() -1, getFileNum() +1, possibleSquares, checkIfCheck); //TOP RIGHT

        //Knight moves
        addToListIfValidSingle(getRankNum() +2, getFileNum() +1, possibleSquares, checkIfCheck); //Check DOWN 2 RIGHT 1
        addToListIfValidSingle(getRankNum() +2, getFileNum() -1, possibleSquares, checkIfCheck); //Check DOWN 2 LEFT 1
        addToListIfValidSingle(getRankNum() -2, getFileNum() +1, possibleSquares, checkIfCheck); //Check UP 2 RIGHT 1
        addToListIfValidSingle(getRankNum() -2, getFileNum() -1, possibleSquares, checkIfCheck); //Check UP 2 LEFT 1
        addToListIfValidSingle(getRankNum() +1, getFileNum() +2, possibleSquares, checkIfCheck); //Check DOWN 1 RIGHT 2
        addToListIfValidSingle(getRankNum() +1, getFileNum() -2, possibleSquares, checkIfCheck); //Check DOWN 1 LEFT 2
        addToListIfValidSingle(getRankNum() -1, getFileNum() +2, possibleSquares, checkIfCheck); //Check UP 1 RIGHT 2
        addToListIfValidSingle(getRankNum() -1, getFileNum() -2, possibleSquares, checkIfCheck); //Check UP 1 LEFT 2

        return possibleSquares;
    }
}
