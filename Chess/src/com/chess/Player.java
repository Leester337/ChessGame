package com.chess;
import java.util.ArrayList;
import java.util.List;

import com.chess.pieces.KingPiece;
import com.chess.pieces.Piece;


public class Player {

	private PlayerEnum color;
	public List<Piece> myPieces;
	private KingPiece myKing; //used to determine if this player is in check
	public List<Piece> causingCheck; //List of opposing pieces that are checking my king
    private String name;
    private int score;
    public List<Piece> customPieces;


    /**
     * initialize a new player
     * @param clr color of the player
     */
	public Player(PlayerEnum clr) {
		setColor(clr);
		setMyPieces(new ArrayList<Piece>());
		setCausingCheck(new ArrayList<Piece>());
        name = null;
        score = 0;
        customPieces = new ArrayList<Piece>();
	}

	public PlayerEnum getColor() {
		return color;
	}

	public void setColor(PlayerEnum color) {
		this.color = color;
	}

	public List<Piece> getMyPieces() {
		return myPieces;
	}

	public void setMyPieces(List<Piece> myPieces) {
		this.myPieces = myPieces;
	}

	public KingPiece getMyKing() {
		return myKing;
	}

	public void setMyKing(KingPiece myKing) {
		this.myKing = myKing;
	}

	public List<Piece> getCausingCheck() {
		return causingCheck;
	}

	public void setCausingCheck(List<Piece> causingCheck) {
		this.causingCheck = causingCheck;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
