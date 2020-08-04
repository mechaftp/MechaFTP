package edu.pdx.cs;

import edu.pdx.cs.commands.Command;
import edu.pdx.cs.commands.LoginCommand;
import edu.pdx.cs.commands.NullCommand;

import java.util.List;

public class CommandFactory {
    public static Command createLogin(Client client, List<String> subarguments){
        return new LoginCommand(client, subarguments);
    }

    public static Command createNull(Client client, List<String> subarguments)
    {
        return new NullCommand(client, subarguments);
    }
}
