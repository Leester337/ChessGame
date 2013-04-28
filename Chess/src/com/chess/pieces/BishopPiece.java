package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;

/**
 * BishopPiece Class
 */
public class BishopPiece extends Piece {

    /**
     * Constructor that initializes my Bishop
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
	public BishopPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
		super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "bishopwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "bishopblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}

    /**
     * Implementation of the getPossibleMoves(checkIfCheck) function of Piece
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

        return possibleSquares;
	}
	
	

}
