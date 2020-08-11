package edu.pdx.cs.commands;

import edu.pdx.cs.ClientState;

public interface Command {
    boolean execute(ClientState state);
}
