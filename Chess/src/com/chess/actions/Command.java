package com.chess.actions;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 2/6/13
 * Time: 11:05 AM
 *
 * Abstract command interface used to execute moves and undo the most recent move
 *
 * This is heavily based on the tutorial from
 * http://gamedev.tutsplus.com/tutorials/implementation/let-your-players-undo-their-in-game-mistakes-with-the-command-pattern/

 */
public interface Command {
    public void execute();
    public void undo();
}
