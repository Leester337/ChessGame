package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;


/**
 * RookPiece class
 */
public class RookPiece extends Piece {

    /**
     * Rook Piece constructor
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
	public RookPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
		super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "rookwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "rookblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}


    /**
     * returns a list of Posssible moves
     * @param checkIfCheck
     * @return
     */
	@Override
	public List<Square> getPossibleMoves(boolean checkIfCheck) {
		List<Square> possibleSquares = new ArrayList<Square>();

		//check each of it's diagonals for possible moves
		addToListIfValidMultiple(getRankNum(), getFileNum() +1, possibleSquares, checkIfCheck); //RIGHT
		addToListIfValidMultiple(getRankNum(), getFileNum() -1, possibleSquares, checkIfCheck); //LEFT
		addToListIfValidMultiple(getRankNum() -1, getFileNum(), possibleSquares, checkIfCheck); //TOP
		addToListIfValidMultiple(getRankNum() +1, getFileNum(), possibleSquares, checkIfCheck); //BOTTOM
		
		return possibleSquares;
	}

}
