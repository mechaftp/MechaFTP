package edu.pdx.cs.commands;

import edu.pdx.cs.Client;
import edu.pdx.cs.ClientState;

import java.util.List;

public class QuitCommand extends BaseCommand {
    public QuitCommand(Client client, List<String> subarguments) {
        super(client, subarguments);
        expectNSubarguments(0);
    }

    @Override
    public boolean execute(ClientState state) {
        state.setQuitting();
        return true;
    }
}
