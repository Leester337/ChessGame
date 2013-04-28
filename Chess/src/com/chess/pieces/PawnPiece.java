package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.Player;
import com.chess.PlayerEnum;
import com.chess.Square;

import javax.swing.*;

/**
 * Class for a Pawn piece
 */
public class PawnPiece extends Piece {

    /**
     * Constructor that initializes the values of the Pawn piece
     * @param rnkNm
     * @param fleNm
     * @param plyr
     * @param otherPlyr
     * @param crrBoard
     */
	public PawnPiece(int rnkNm, int fleNm, Player plyr, Player otherPlyr, Board crrBoard) {
		super(rnkNm, fleNm, plyr, otherPlyr, crrBoard);
        if (myPlayer.getColor() == PlayerEnum.WHITE)
            image = new ImageIcon(getClass().getResource(imagePath + "pawnwhite.png"), "White Bishop");
        else image = new ImageIcon(getClass().getResource(imagePath + "pawnblack.png"), "Black Bishop");
        imageLabel.setIcon(image);
	}

    /**
     * @see Piece#getPossibleMoves()
     * The pawn's direction of movement is determined by whether it's black or white.
     * If it's black, it moves down the board (increase in rank number)
     * If it's white, it moves up the board (decrease in rank number)
     * @param checkIfCheck
     * @return
     */
	@Override
	public List<Square> getPossibleMoves(boolean checkIfCheck) {
		List<Square> possibleSquares = new ArrayList<Square>();
		if (myPlayer.getColor() == PlayerEnum.BLACK)
		{
			testMoves(1, 1, possibleSquares, checkIfCheck);
		}
		else
		{
			testMoves(6, -1, possibleSquares, checkIfCheck);
		}
		
		return possibleSquares;
	}

    /**
     * Pawns have three kinds of moves and are checked for in the following order:
	 * 1. If it's on it's starting row (pawnRank), it can move double)
	 * 2. Otherwise, it can potentially move up one space (assuming there's no piece in the way)
	 * 3. It can capture diagonally
     *
     * @param pawnRank the starting row of pawns and is determined by the color (needed for condition 1)
     * @param offset the direction the pawns travel (also determined by color, needed for all 3 conditions)
     * @param possibleSquares
     * @param checkIfCheck
     */
	private void testMoves(int pawnRank, int offset, List<Square> possibleSquares, boolean checkIfCheck)
	{
		//test condition 1
		if (getRankNum() == pawnRank)
		{
			checkPawnMoves(getRankNum() + (offset * 2), getFileNum(), possibleSquares, false, checkIfCheck);
		}
		
		//test condition 2
		checkPawnMoves(getRankNum() + (offset * 1), getFileNum(), possibleSquares, false, checkIfCheck);
		
		//test condition 3: can capture both diagonally to the left and right
		checkPawnMoves(getRankNum() + (offset * 1), getFileNum() + 1, possibleSquares, true, checkIfCheck);
		checkPawnMoves(getRankNum() + (offset * 1), getFileNum() - 1, possibleSquares, true, checkIfCheck);
	
	}


    /**
     * Precheck for pawn moves before we sent it to addToListIfValid
     * 1. If we're moving forward, we want to make sure there's no enemy piece in that place
     * 2. If we're capturing, we want to make sure there is an enemy piece
     *
     * @param testRank
     * @param testFile
     * @param possSquares
     * @param checkCapture
     * @param checkIfCheck
     */
	private void checkPawnMoves(int testRank, int testFile, List<Square> possSquares,
                                boolean checkCapture, boolean checkIfCheck)
	{
		//make sure rankNum and fileNum aren't out of range
		if (testRank < 0 || testRank >= Board.NUMRANK || testFile < 0 || testFile >= Board.NUMFILE)
		{
			return;
		}
		if ((!checkCapture && getCurrBoard().board[testRank][testFile].getOccupiedPiece() == null) ||
                (checkCapture && getCurrBoard().board[testRank][testFile].getOccupiedPiece() != null))
		{
			addToListIfValidSingle(testRank, testFile, possSquares, checkIfCheck);
		}
	}
	

}
