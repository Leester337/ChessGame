package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.actions.Command;
import com.chess.exceptions.CaptureOwnPieceException;
import com.chess.Player;
import com.chess.Square;

import javax.swing.*;


/**
 * Abstract Piece class, super class of each of the pieces
 * @author Lee
 *
 */
public abstract class Piece {

	protected int rankNum; //i.e. row number - where is the piece
	protected int fileNum; //i.e. column number
	public Player myPlayer; // PlayerEnum = {WHITE, BLACK} - whose piece is it?
	public Player otherPlayer; //used to check whether my king is in check before each move
	private Board currBoard; //reference to the chess board
    public JLabel imageLabel; //Used to contain the image of the piece
    public ImageIcon image; //The actual image of the piece

    protected String imagePath = "/com/chess/images/"; //the directory for the images

    /**
     * Only one constructor requiring all this data because you can't make a piece without knowing whose
     * it is and where it's placed. It also stores the board so when I move pieces I can know which cells are taken
     * and if it jeopardizes my king
     * @param rnkNm
     * @param fleNm
     * @param playr
     * @param otherPlyr
     * @param crrBoard
     */
	public Piece(int rnkNm, int fleNm, Player playr, Player otherPlyr, Board crrBoard) {
		rankNum = rnkNm;
		fileNum = fleNm;
		myPlayer = playr;
		otherPlayer = otherPlyr;
		setCurrBoard(crrBoard);
        imageLabel = new JLabel();
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM); //initialize standard JLabel options
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setEnabled(true);

	}

    /**
     * Wrapper method for getPossibleMoves, it checks to see if the move would compromise their king.
     * If I am in check, I can only allow moves that will take my king out of check.
     * @return
     */
	public List<Square> getPossibleMoves()
	{
		return getPossibleMoves(true);


    }

    /**
     * returns a list of possible cells a piece can move to. I hope to use this to know which cells
     * to light up when a piece is selected. It's abstract because each piece has different mechanisms to know
     * which cells are available
     *
     * It has a checkIfCheck parameter that, when true, will check to see if by moving to the
     * potential square would result in their own king being compromised
     * @param checkIfCheck
     * @return
     */
	public abstract List<Square> getPossibleMoves(boolean checkIfCheck);

    /**
     * calls @checkIfValidMove for all the squares in a certain direction specified by the difference
     * in the parameters to the piece's location.
     *
     * We will add each square to possSquares if it is valid and is in the direction we're checking
     *
     * Ex. a rook moving up a file will use this to determine how many squares are available for him
     * to move by having rankNm the same as his current rank, but fileNm being 1 more than his current file
     * @param rankNm
     * @param fileNm
     * @param possSquares
     * @param checkIfCheck
     */
	protected void addToListIfValidMultiple(int rankNm, int fileNm, List<Square> possSquares, boolean checkIfCheck)
	{
		int testRankNum = rankNm;
		int testFileNum = fileNm;
		//Diff will be our incrementor/decrementor, allowing us to continue in the direction
		//that the first squared called on is from the square the piece is on
		//for example: If the TOP LEFT square is called, then we'll continue checking the TOP LEFT direction
		int rankDiff = testRankNum - getRankNum();
		int fileDiff = testFileNum - getFileNum();
		Square testSquare = checkIfValidMove(testRankNum, testFileNum, checkIfCheck);
		//keep checking until you hit a piece or go off the board
		while (testSquare != null)
		{
			possSquares.add(testSquare);
			//if there is a piece there, stop checking for moves beyond it
			if (testSquare.getOccupiedPiece() != null)
				break;
			testRankNum = testRankNum + rankDiff; //increment/decrement to test next square
			testFileNum = testFileNum + fileDiff;
			testSquare = checkIfValidMove(testRankNum, testFileNum, checkIfCheck);
		}
	}

    /**
     * determine if the single square provided is available for a piece to move to
     * It passes the potential rankNm and fileNm to @checkIfValidMove and adds it to possSquares if it is
     * @param rankNm
     * @param fileNm
     * @param possSquares
     * @param checkIfCheck
     */
	protected void addToListIfValidSingle(int rankNm, int fileNm, List<Square> possSquares, boolean checkIfCheck)
	{
		Square testSquare = checkIfValidMove(rankNm, fileNm, checkIfCheck);
		if (testSquare != null)
		{
			possSquares.add(testSquare);
		}
			
	}

    /**
     * Determines whether the calling piece can move to a square by:
     * making sure the location of the square isn't out of bounds and
     * checking whether a piece of the same color is already there
     *
     * returns Square if it's available or null it it's not
     * @param rankNum
     * @param fileNum
     * @param checkIfCheck
     * @return
     */
	public Square checkIfValidMove(int rankNum, int fileNum, boolean checkIfCheck)
	{
		//make sure rankNum and fileNum aren't out of range
		if (rankNum < 0 || rankNum >= Board.NUMRANK || fileNum < 0 || fileNum >= Board.NUMFILE)
		{
			return null;
		}
		Piece attackedPiece = getCurrBoard().board[rankNum][fileNum].getOccupiedPiece();
		//check if a piece of the same color is already there
		if (attackedPiece == null || !attackedPiece.myPlayer.equals(this.myPlayer))
		{
			//see if my king is now in check due to my piece moving
			if (!checkIfCheck || (checkIfCheck && !checkIfCheck(myPlayer, otherPlayer, rankNum, fileNum)))
			{
				return getCurrBoard().board[rankNum][fileNum];
			}
						
		}
		return null;
	}

    /**
     * A wrapper function that moves a piece through the Command
     * @param destination
     * @return
     */
    public boolean move(Square destination){
        try
        {
            currBoard.commandManager.executeCommand(
                    new movePieceCommand(this, destination, currBoard.currentTurn, destination.getOccupiedPiece()));
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * Moves a piece and automatically checks if will place a king in check
     * @param destination
     * @return
     * @throws CaptureOwnPieceException
     */
    public boolean moveCommand(Square destination) throws CaptureOwnPieceException {
        return moveCommand(destination, true);
    }

    /**
     * Moves a piece to destination
     * returns true if it worked, false otherwise
     * @param destination
     * @param checkIfCheck - if I'm moving a piece due to an undo, don't check if it will place the king
     *                     in check because in some cases it might temporarily when the piece captured was
     *                     blocking
     * @return
     * @throws CaptureOwnPieceException
     */
	public boolean moveCommand(Square destination, boolean checkIfCheck) throws CaptureOwnPieceException {

        //this move may possibly get me out of check, so clear opposingCheck and let it fill up if kings still in check
        myPlayer.causingCheck.clear();
		if (destination.getOccupiedPiece() != null)
		{
			Piece attackedPiece = destination.getOccupiedPiece();
			if (attackedPiece.myPlayer.equals(this.myPlayer))
			{
				throw new CaptureOwnPieceException();
			}
			else
			{
				Piece toBeDestroyed = destination.getOccupiedPiece();
				otherPlayer.getMyPieces().remove(toBeDestroyed); //remove piece from otherPlayer's pieces
				
				
			}
		}
		
		destination.setOccupiedPiece(this);
		getCurrBoard().board[getRankNum()][getFileNum()].setOccupiedPiece(null);
		rankNum = destination.getRankNum();
		fileNum = destination.getFileNum();


        if (checkIfCheck)
        {

            if (checkIfCheck(otherPlayer, myPlayer))
            {
                if (checkIfStalemate(otherPlayer, myPlayer))
                {
                    boolean result = currBoard.currGUI.checkmateOrStalemate("Checkmate");

                    //if checkmate, increment myPlayer and offer to reset the game
                    if (result)
                    {
                        myPlayer.setScore(myPlayer.getScore() + 1);
                        currBoard.currGUI.resetGame();
                    }
                }
                return true;

            }

            if (checkIfStalemate(otherPlayer, myPlayer))
            {
                boolean result = currBoard.currGUI.checkmateOrStalemate("Stalemate");
                if (result)
                {
                    myPlayer.setScore(myPlayer.getScore() + 1);
                    currBoard.currGUI.resetGame();
                }
            }
        }
		return true;
	
	}

    /**
     * Checks whether the current state of the board puts myPlyr in check
     * @param myPlyr
     * @param otherPlyr
     * @return
     */
	public boolean checkIfCheck(Player myPlyr, Player otherPlyr)
	{
		 
		//check to see if any of the opponents pieces place myPlyr's king in check
		for (Piece currPieceBeingTested : otherPlyr.myPieces)
		{
			if (currPieceBeingTested == null)
				continue;
			List<Square> possibleMoves = currPieceBeingTested.getPossibleMoves(false);
			for (Square currSquare : possibleMoves) //check if my king is in any of the possible moves of curr
			{
				Piece currPiecePossiblyAttacked = currSquare.getOccupiedPiece();
				if (currPiecePossiblyAttacked != null && currPiecePossiblyAttacked instanceof KingPiece &&
                        currPiecePossiblyAttacked.myPlayer.equals(myPlyr)) //if my king potentially is under attack
				{
					if (!myPlyr.causingCheck.contains(currPieceBeingTested))
						myPlyr.causingCheck.add(currPieceBeingTested); //add this piece to those checking my king
				}
			}
		}
		if (myPlyr.getCausingCheck().size() != 0)
			return true;
		else return false;
		
	}

    /**
     * This does the same as checkIfCheck but checks is a potential move puts myPlyr's king in check before it goes there
     * @param myPlyr
     * @param otherPlyr
     * @param potentialRank
     * @param potentialFile
     * @return
     */
	public boolean checkIfCheck(Player myPlyr, Player otherPlyr, int potentialRank, int potentialFile)
	{
		 List<Piece> beforeCausingCheck = new ArrayList<Piece>();
		 beforeCausingCheck.addAll(myPlayer.causingCheck); //save the pieces that are causing my king to be in check
		 myPlayer.causingCheck.clear(); //clear current list before checking possible move
		boolean retval = false;
		//store current location to set back after checking if the move places myPlyr's king in check
		int origRank = this.getRankNum();
		int origFile = this.getFileNum();
		
		//temporarily move the piece to potentialRank x potentialFile
		Piece storePiece = getCurrBoard().board[potentialRank][potentialFile].getOccupiedPiece();
		otherPlayer.myPieces.remove(storePiece);
		getCurrBoard().board[potentialRank][potentialFile].setOccupiedPiece(this);
		getCurrBoard().board[origRank][origFile].setOccupiedPiece(null);
		
		retval = checkIfCheck(myPlyr, otherPlyr);

        //restore the board to its state before checking
		getCurrBoard().board[origRank][origFile].setOccupiedPiece(this);
		getCurrBoard().board[potentialRank][potentialFile].setOccupiedPiece(storePiece);
		otherPlayer.myPieces.add(storePiece);
		myPlayer.causingCheck = beforeCausingCheck;
		return retval;
	}

    /**
     * Determines whether myPlyr is in stalemate by looking to see if any of myPlyr's pieces can make a legal move
     * @param myPlyr
     * @param otherPlyr
     * @return
     */
	public boolean checkIfStalemate(Player myPlyr, Player otherPlyr)
	{
        List<Piece> checkList = myPlyr.getMyPieces();
		for (Piece currPieceBeingTested : checkList)
        {
            if (currPieceBeingTested == null)
                continue;
			if (currPieceBeingTested.getPossibleMoves().size() != 0)
				return false;
		}
		return true;
	}


    public Board getCurrBoard() {
        return currBoard;
    }

    public void setCurrBoard(Board currBoard) {
        this.currBoard = currBoard;
    }

    public int getRankNum() {
        return rankNum;
    }

    public int getFileNum() {
        return fileNum;
    }

    /**
     * Private class that implements Command. It moves a piece and offers an undo
     */
    private class movePieceCommand implements Command
    {
        private Piece pieceMoving;
        private Square squareMovedTo;
        private Square squareMovedFrom;
        private Player currTurn;
        private Piece capturedPiece;

        /**
         * Constructor
         * @param pMoving - the Piece that is moving
         * @param sqMovedTo - the Square it is moved to
         * @param crrTurn
         * @param capPiece - the piece that was captured by the move
         */
        private movePieceCommand(Piece pMoving, Square sqMovedTo, Player crrTurn, Piece capPiece)
        {
            pieceMoving = pMoving;
            squareMovedFrom = currBoard.board[pieceMoving.getRankNum()][pieceMoving.getFileNum()];
            squareMovedTo = sqMovedTo;
            currTurn = crrTurn;
            capturedPiece = capPiece;
        }

        /**
         * moves the piece
         */
        public void execute()
        {
            try {
                pieceMoving.moveCommand(squareMovedTo);
            } catch (CaptureOwnPieceException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        /**
         * Undo's the move if there is one available
         */
        public void undo()
        {
            try {
                pieceMoving.moveCommand(squareMovedFrom, false);
                currBoard.currentTurn = pieceMoving.myPlayer;
                currBoard.board[squareMovedTo.getRankNum()][squareMovedTo.getFileNum()].setOccupiedPiece(capturedPiece);
                if (capturedPiece != null)
                {
                    capturedPiece.myPlayer.myPieces.add(capturedPiece);
                }
                currBoard.currGUI.setCheckLabel();
            } catch (CaptureOwnPieceException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }
}
