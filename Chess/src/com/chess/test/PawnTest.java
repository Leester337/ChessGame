package com.chess.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chess.Board;
import com.chess.Square;
import com.chess.exceptions.CaptureOwnPieceException;
import com.chess.pieces.Piece;

public class PawnTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


    /**
     * Makes sure a pawn has two moves to begin with, and only one move after that
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testNumberGetPossibleMoves() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		Piece testPawn = myBoard.board[1][4].getOccupiedPiece();
		List<Square> possibleMoves = testPawn.getPossibleMoves();
		assertEquals("The Pawn can move in 2 places", 2, possibleMoves.size()); //starting position should have 2 moves
		testPawn.move(possibleMoves.get(1));
		possibleMoves = testPawn.getPossibleMoves(); //it moves, and since its no longer starting, it only has 1
		assertEquals("The Pawn can move in 1 place", 1, possibleMoves.size());
	}


    /**
     * Makes sure when a capture option is available, it has 3 moves from the starting position
     * @throws CaptureOwnPieceException
     */
	@Test
	public void testPawnCapture() throws CaptureOwnPieceException {
		Board myBoard = new Board();
		Piece testPawn = myBoard.board[1][4].getOccupiedPiece();
		myBoard.board[7][0].getOccupiedPiece().move(myBoard.board[2][3]); //move white rook to a capture square
		List<Square> possibleMoves = testPawn.getPossibleMoves();
		assertEquals("The Pawn can move in 3 places", 3, possibleMoves.size()); //since its starting position and it can capture, theres 3 possible moves
	}

}
