package edu.pdx.cs;

import edu.pdx.cs.commands.LoginCommand;

public class CommandFactory {
    public static ICommand createLogin(Client client){
        return new LoginCommand(client);
    }
}
