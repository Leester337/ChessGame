package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;

/**
 * Class for Queen piece
 */
public class QueenPiece extends Piece {

    /**
     * Constructor for Queen piece
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
	public QueenPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
		super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "queenwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "queenblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}

    /**
     * Overrides getPossibleMoves of the Piece class
     * @param checkIfCheck
     * @return
     */
	@Override
	public List<Square> getPossibleMoves(boolean checkIfCheck) {
		List<Square> possibleSquares = new ArrayList<Square>();

		//check each of it's diagonals for possible moves
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
