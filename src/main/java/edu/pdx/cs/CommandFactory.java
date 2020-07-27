package edu.pdx.cs;

public class CommandFactory {
    public static ICommand createLogin(){
        return new LoginCommand();
    }
}
