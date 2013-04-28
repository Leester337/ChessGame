package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;

/**
 * Class for a King piece
 */
public class KingPiece extends Piece{


    /**
     * Constructor: use Piece constructor to assign rankNum, fileNum, player (i.e. BLACK or WHITE)
     * and the board
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param board
     */
	public KingPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board board) {
		super(rnkNm, fleNm, plyr, otherPlyr, board);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "kingwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "kingblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}


    /**
     * See Piece.getPossibleMoves()
     * This will search all the neighboring cells to the current location of the king
     * @param checkIfCheck
     * @return
     */
	@Override
	public List<Square> getPossibleMoves(boolean checkIfCheck) {
		List<Square> possibleSquares = new ArrayList<Square>();

		addToListIfValidSingle(getRankNum() +1, getFileNum(), possibleSquares, checkIfCheck); //Check the DOWN cell
		addToListIfValidSingle(getRankNum() +1, getFileNum() +1, possibleSquares, checkIfCheck); //Check the DOWN and RIGHT cell
		addToListIfValidSingle(getRankNum(), getFileNum() +1, possibleSquares, checkIfCheck); //Check the RIGHT cell
		addToListIfValidSingle(getRankNum() -1, getFileNum(), possibleSquares, checkIfCheck); //Check the UP cell
		addToListIfValidSingle(getRankNum() -1, getFileNum() -1, possibleSquares, checkIfCheck); //Check the UP and LEFT cell
		addToListIfValidSingle(getRankNum(), getFileNum() -1, possibleSquares, checkIfCheck); //Check the LEFT cell
		addToListIfValidSingle(getRankNum() +1, getFileNum() -1, possibleSquares, checkIfCheck); //Check the DOWN and LEFT cell
		addToListIfValidSingle(getRankNum() -1, getFileNum() +1, possibleSquares, checkIfCheck); //Check the UP and RIGHT cell

		return possibleSquares;
	}
	
	
	
	

}
