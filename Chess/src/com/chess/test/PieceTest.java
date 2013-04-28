package com.chess.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.chess.Board;
import com.chess.Square;
import com.chess.exceptions.CaptureOwnPieceException;
import com.chess.pieces.Piece;
import com.chess.pieces.RookPiece;
import org.junit.rules.ExpectedException;

public class PieceTest {


    /**
     * Makes sure black isn't in checkmate
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testNotCheckIfCheckMate() throws CaptureOwnPieceException {
		Board myBoard = new Board();
        ExpectedException checkmateExp = ExpectedException.none();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[3][7]); //Move the black king to position
		Piece testRook = myBoard.board[7][0].getOccupiedPiece(); 
		testRook.move(myBoard.board[2][0]); // move white rook to position

		myBoard.board[7][3].getOccupiedPiece().move(myBoard.board[4][0]); //move the queen
        myBoard.board[7][7].getOccupiedPiece().move(myBoard.board[3][0]); //move the other white rook

        assertTrue(!myBoard.getBlack().getCausingCheck().isEmpty());
	}

    /**
     * Puts the black player in checkmate
     * @throws CaptureOwnPieceException
     */

    @Test
    public void testCheckIfCheckMate() throws CaptureOwnPieceException {
        Board myBoard = new Board();
        myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[4][7]); //Move the black king to position
        Piece testRook = myBoard.board[7][0].getOccupiedPiece();
        testRook.move(myBoard.board[3][0]); // move white rook to position

            boolean result = myBoard.board[7][7].getOccupiedPiece().move(myBoard.board[4][0]); //move the other white rook

         assertTrue(!result);



    }


    /**
     * Testing a simple check
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testCheckIfCheckPlayerPlayer() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[2][7]); //move the black king
		Piece testRook = myBoard.board[7][0].getOccupiedPiece();
		testRook.move(myBoard.board[2][3]); //move the white rook
		assertTrue("Black should be checked!!", !myBoard.getBlack().causingCheck.isEmpty());
	}

    /**
     * Puts the king in check, tests to make sure the pawn has only the moves that will
     * get the king out of check, including the ability to capture the rook
     * this test highlights MANY key functions in my implementation
     * 9 total tests here!!
     *
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testThisIsCheck() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[2][7]); //move black king

        Piece testRook = myBoard.board[7][0].getOccupiedPiece(); //get white rook
		testRook.move(myBoard.board[2][3]); //place rook in the row right above pawns, causing a check
		assertTrue("Black should be checked!", !myBoard.getBlack().causingCheck.isEmpty());

        //testing because get PossibleMoves temporarily unchecks king
        List<Square> getPawnPossibleMoves = myBoard.board[1][4].getOccupiedPiece().getPossibleMoves();
		assertTrue("Black should be checked", !myBoard.getBlack().causingCheck.isEmpty());

        //makes sure only the two moves to uncheck the king are available
		assertTrue("contains Square 2x4", getPawnPossibleMoves.contains(myBoard.board[2][4]));
		assertTrue("contains Square 2x3", getPawnPossibleMoves.contains(myBoard.board[2][3]));
		assertTrue("Only has 2 moves", getPawnPossibleMoves.size() == 2);

        //makes sure the other pawn has only 1 move to uncheck (since it cant capture)
        getPawnPossibleMoves = myBoard.board[1][5].getOccupiedPiece().getPossibleMoves();
		assertTrue("contains Square 2x5", getPawnPossibleMoves.contains(myBoard.board[2][5]));
		assertTrue("Only has 1 moves", getPawnPossibleMoves.size() == 1);

        //move the first pawn up 1 rank, this blocks the check
		myBoard.board[1][4].getOccupiedPiece().move(myBoard.board[2][4]);
		assertTrue("Black should NOT be checked", myBoard.getBlack().causingCheck.isEmpty());
		assertTrue("rook exists!", myBoard.board[2][3].getOccupiedPiece() instanceof RookPiece);
	}

    /**
     * More tests
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testThisIsNotCheckGetMoves() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[2][7]); //move black king

		Piece testRook = myBoard.board[7][0].getOccupiedPiece(); //get white rook
		testRook.move(myBoard.board[2][3]); //place rook in the row right above pawns, causing a check
		List<Square> getPawnPossibleMoves = myBoard.board[1][4].getOccupiedPiece().getPossibleMoves();

        //testing because get PossibleMoves temporarily unchecks king
		assertTrue("Black should be checked", !myBoard.getBlack().causingCheck.isEmpty());

        //makes sure only the two moves to uncheck the king are available
		assertTrue("contains Square 2x4", getPawnPossibleMoves.contains(myBoard.board[2][4]));
		assertTrue("contains Square 2x3", getPawnPossibleMoves.contains(myBoard.board[2][3]));
		assertTrue("Only has 2 moves", getPawnPossibleMoves.size() == 2);

        //move the first pawn up 1 rank, this blocks the check
		myBoard.board[1][4].getOccupiedPiece().move(myBoard.board[2][4]);

	}

    /**
     * Even more tests
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testOtherPawnMoves() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[2][7]); //move black king
		Piece testRook = myBoard.board[7][0].getOccupiedPiece(); //get white rook
		testRook.move(myBoard.board[2][3]); //place rook in the row right above pawns, causing a check

        //makes sure the other pawn has only 1 move to uncheck (since it cant capture)
		List<Square> getPawnPossibleMoves = myBoard.board[1][5].getOccupiedPiece().getPossibleMoves();
		assertTrue("contains Square 2x5", getPawnPossibleMoves.contains(myBoard.board[2][5]));
		assertTrue("Only has 1 moves", getPawnPossibleMoves.size() == 1);

        //move the first pawn up 1 rank, this blocks the check
		myBoard.board[1][4].getOccupiedPiece().move(myBoard.board[2][4]);
		assertTrue("Black should NOT be checked", myBoard.getBlack().causingCheck.isEmpty());
		assertTrue("rook exists!", myBoard.board[2][3].getOccupiedPiece() instanceof RookPiece);
	}


    /**
     * Some more tests
     * @throws CaptureOwnPieceException
     */
	@Test
	public void preventCheck() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		myBoard.board[0][4].getOccupiedPiece().move(myBoard.board[2][7]); //move black king
		Piece testRook = myBoard.board[7][0].getOccupiedPiece(); //get white rook
		testRook.move(myBoard.board[2][3]); //place rook in the row right above pawns, causing a check

        //move the first pawn up 1 rank, this blocks the check
		myBoard.board[1][4].getOccupiedPiece().move(myBoard.board[2][4]);
		assertTrue("Black should NOT be checked", myBoard.getBlack().causingCheck.isEmpty());
		assertTrue("rook exists!", myBoard.board[2][3].getOccupiedPiece() instanceof RookPiece);
	}

    /**
     * Makes sure the bishop getPossibleMoves (therefore MultipleMoves) works!
     *
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testBishopPossibleMoves() throws CaptureOwnPieceException {
		Board myBoard = new Board();

        //move a knight in the direction the bishops path
		myBoard.board[0][1].getOccupiedPiece().move(myBoard.board[2][2]);
		myBoard.board[1][6].getOccupiedPiece().move(myBoard.board[2][6]); //move another knight in another direction

		Piece testBishop = myBoard.board[7][2].getOccupiedPiece();
		testBishop.move(myBoard.board[4][4]);
		List<Square> getPossibleMoves = testBishop.getPossibleMoves();

		assertTrue("contains exactly 6 moves", getPossibleMoves.size() == 6);
		assertTrue("contains Square 3x5", getPossibleMoves.contains(myBoard.board[3][5]));
		assertTrue("contains Square 2x6", getPossibleMoves.contains(myBoard.board[2][6]));
		assertTrue("contains Square 3x3", getPossibleMoves.contains(myBoard.board[3][3]));
		assertTrue("contains Square 2x2", getPossibleMoves.contains(myBoard.board[2][2]));
		assertTrue("contains Square 5x3", getPossibleMoves.contains(myBoard.board[5][3]));
		assertTrue("contains Square 5x5", getPossibleMoves.contains(myBoard.board[5][5]));
	}
	
	

}
