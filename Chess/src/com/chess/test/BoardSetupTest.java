package com.chess.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chess.Board;

public class BoardSetupTest {

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
     * Makes sure a board is initialized and populated correctly!
     */
	@Test
	public void testBoard() {
		Board myBoard = new Board();
		assertNotNull("A black pawn really does exist!", myBoard.board[1][5]);
	}

}
