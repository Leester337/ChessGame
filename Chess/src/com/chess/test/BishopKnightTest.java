package com.chess.test;

import com.chess.Board;
import com.chess.Square;
import com.chess.exceptions.CaptureOwnPieceException;
import com.chess.pieces.BishopKnightPiece;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/31/13
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BishopKnightTest {

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
     * Add a BishopKing and make sure it gets possible moves
     * @throws CaptureOwnPieceException
     */
    @Test
    public void testNumberGetPossibleMoves() throws CaptureOwnPieceException {
        Board myBoard = new Board();
        BishopKnightPiece testPiece = new BishopKnightPiece(4, 4, myBoard.getWhite(),
                myBoard.getBlack(), myBoard);
        myBoard.board[4][4].setOccupiedPiece(testPiece);
        List<Square> possibleMoves = testPiece.getPossibleMoves();
        assertTrue("It should have possible moves!", !possibleMoves.isEmpty());

    }


}
