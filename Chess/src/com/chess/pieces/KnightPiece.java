package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;


/**
 * Class for a Knight piece
 */
public class KnightPiece extends Piece {

    /**
     * Constructor for a KnightPiece that initializes it's values
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
	public KnightPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
		super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "knightwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "knightblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}

    /**
     * Implements Piece's getPossibleMoves
     * @param checkIfCheck
     * @return
     */
	@Override
	public List<Square> getPossibleMoves(boolean checkIfCheck) {
		List<Square> possibleSquares = new ArrayList<Square>();

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
