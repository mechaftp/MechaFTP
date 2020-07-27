package edu.pdx.cs;

import java.util.List;

public interface ICommand {

    boolean execute();

    void assignInput(List<String> subcommands);
}
