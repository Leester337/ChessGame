package com.chess;

import com.chess.pieces.Piece;

import java.awt.*;

/**
 * Square class
 */
public class Square {

	private int rankNum;
	private int fileNum;
	private Piece occupiedPiece;
    public Color backgroundColor;
    public Board currBoard;
	
	public Square() {
		
	}

	public Piece getOccupiedPiece() {
		return occupiedPiece;
	}

	public void setOccupiedPiece(Piece occupiedPiece) {
		this.occupiedPiece = occupiedPiece;
	}

	public int getRankNum() {
		return rankNum;
	}

	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

}
