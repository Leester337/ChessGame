package com.chess.actions;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 2/6/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 *
 * Used to execute moves, store most recent move, and determine if an Undo is available
 *
 * This class is heavily based on the tutorial from
 * http://gamedev.tutsplus.com/tutorials/implementation/let-your-players-undo-their-in-game-mistakes-with-the-command-pattern/
 */
public class CommandManager {
    private Command lastCommand;

    public CommandManager()
    {

    }

    public void executeCommand(Command c)
    {
        c.execute();
        lastCommand = c;
    }

    public void undo()
    {
        if (lastCommand != null)
        {
            lastCommand.undo();
            lastCommand = null;
        }
    }

    public boolean isUndoAvailable()
    {
        return lastCommand != null;
    }

}
