package com.chess;

import com.chess.actions.Command;
import com.chess.actions.CommandManager;
import com.chess.pieces.BishopPiece;
import com.chess.pieces.KingPiece;
import com.chess.pieces.KnightPiece;
import com.chess.pieces.PawnPiece;
import com.chess.pieces.QueenPiece;
import com.chess.pieces.RookPiece;

import javax.swing.*;
/**
 * 
 */

/**
 * @author Lee
 *
 */
public class Board {

	public static int NUMRANK = 8;
	public static int NUMFILE = 8;

	// We will assume the TOP LEFT corner is location 0x0 
	public Square[][] board; 
	Player white;
	Player black;
    private Square selectedSquare; //used to determine if a square was clicked before another square was clicked
    public GUI currGUI;
    public Player currentTurn;
    public CommandManager commandManager;
	
	public Player getWhite() {
		return white;
	}
	
	public Player getBlack() {
		return black;
	}

    /**
     * Constructor: this will create a new board, initialize all the squares, and will populate
     * the board with the typical chess set up
     */
	public Board() {
		board = new Square[NUMRANK][NUMFILE];
        setSelectedSquare(null);
        currGUI = null;

        initializeSquares();

		white = new Player(PlayerEnum.WHITE);
		black = new Player(PlayerEnum.BLACK);
        currentTurn = white;
        commandManager = new CommandManager();
		
		populateBoard();
	}



    /**
     * populateBoard sets the board up with the default starting configuration
     */
	public void populateBoard()
	{
		//Black pieces on the top row
		initializeChessPieces(black, white, 0);
		//White pieces on the bottom row
		initializeChessPieces(white, black, 7);
		
	}

    /**
     * Create new chess pieces in their proper squares
     * @param plyr
     * @param otherPlyr
     * @param rankNum
     */
	private void initializeChessPieces(Player plyr, Player otherPlyr, int rankNum)
	{
		board[rankNum][0].setOccupiedPiece(new RookPiece(rankNum, 0, plyr, otherPlyr, this));
		board[rankNum][1].setOccupiedPiece(new KnightPiece(rankNum, 1, plyr, otherPlyr, this));
		board[rankNum][2].setOccupiedPiece(new BishopPiece(rankNum, 2, plyr, otherPlyr, this));
		board[rankNum][3].setOccupiedPiece(new QueenPiece(rankNum, 3, plyr, otherPlyr, this));
		board[rankNum][4].setOccupiedPiece(new KingPiece(rankNum, 4, plyr, otherPlyr, this));
		board[rankNum][5].setOccupiedPiece(new BishopPiece(rankNum, 5, plyr, otherPlyr, this));
		board[rankNum][6].setOccupiedPiece(new KnightPiece(rankNum, 6, plyr, otherPlyr, this));
		board[rankNum][7].setOccupiedPiece(new RookPiece(rankNum, 7, plyr, otherPlyr, this));
		
		//Add the newly created pieces to the player's myPieces list (to be used later for checking check and stalemate)
		for (int currPiece = 0; currPiece < NUMFILE; currPiece++)
		{
			plyr.getMyPieces().add(board[rankNum][currPiece].getOccupiedPiece());
		}
		
		//Store the player's king to check for possible moves for determining checkmate
		plyr.setMyKing((KingPiece)board[rankNum][4].getOccupiedPiece());
		
		//Move to the next row to populate the rank of Pawns
		int pawnRank;
		//if we were populating pieces on the top row, the rank for Pawns will be the next rank (rank 1)
		if (rankNum == 0)
			pawnRank = rankNum + 1;
		//if we were populating pieces on the bottom row, the rank for Pawns will be the previous rank (rank 7)
		else pawnRank = rankNum - 1;
		
		//populate that entire rank with Pawns
		for (int currFile = 0; currFile < NUMFILE; currFile++)
		{
			board[pawnRank][currFile].setOccupiedPiece(new PawnPiece(pawnRank, currFile , plyr, otherPlyr, this));
			plyr.getMyPieces().add(board[pawnRank][currFile].getOccupiedPiece());
		}
	}

    private void initializeSquares()
    {
        for (int currRank = 0; currRank < NUMRANK; currRank++)
        {
            for (int currFile = 0; currFile < NUMFILE; currFile++)
            {
                //initialize each square with its location
                Square currSquare = board[currRank][currFile] = new Square();
                currSquare.setFileNum(currFile);
                currSquare.setRankNum(currRank);
                currSquare.currBoard = this;
                //set occupiedPiece to null, indicating no piece is on that square
                currSquare.setOccupiedPiece(null);
            }
        }
    }

    /**
     * Determines if an Undo is available
     * @return
     */
    public boolean undoMove()
    {
        if (!commandManager.isUndoAvailable())
            return false;

        commandManager.undo();
        return true;
    }


    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(Square selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    /**
     * Resets the Model, everything but the Players' points
     */
    public void resetBoard()
    {
        board = new Square[NUMRANK][NUMFILE];

        currentTurn = white;
        white.getCausingCheck().clear();
        white.myPieces.clear();
        black.getCausingCheck().clear();
        black.myPieces.clear();
        setSelectedSquare(null);

        initializeSquares();
        populateBoard();
    }


}
