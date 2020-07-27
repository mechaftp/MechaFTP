package edu.pdx.cs;

import java.util.List;

public abstract class Command implements ICommand{
    protected Client client;

    protected Command(Client client) { this.client = client; }

    public abstract boolean execute();

    public abstract void assignInput(List<String> subcommands);
}
