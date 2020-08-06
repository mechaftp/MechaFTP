package edu.pdx.cs;

import java.io.Closeable;
import java.io.PrintStream;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;

public class StatusBar implements Closeable
{
    public PrintStream out;

    private StatusBar(PrintStream out)
    {
        this.out = out;
    }

    public static StatusBar create(PrintStream out)
    {
        AnsiConsole.systemInstall();
        return new StatusBar(out);
    }

    public void render(ClientState state)
    {
        // Print command output???

        // format the status bar
        int localLen = state.getLocalCwdString().length();
        int remoteLen = state.getRemoteCwdString().length();
        String localCwdString = localLen > 35 ?
            "..." + state.getLocalCwdString().substring(localLen - 32) :
            state.getLocalCwd().toString();
        String remoteCwdString = remoteLen > 33 ?
            "..." + state.getRemoteCwdString().substring(remoteLen - 30) :
            state.getRemoteCwdString();
        String status = String.format("%1$-38s  %2$38s", localCwdString, remoteCwdString);

        // print the status bar
        out.println(ansi()
            .fg(Color.CYAN).a("╠ ")
            .fg(Color.GREEN).a("[local] ")
            .fg(Color.WHITE).a(status)
            .fg(Color.GREEN).a(" [remote] ")
            .fg(Color.CYAN).a("╗")
            .reset()
        );

        // print the command prompt
        out.print(ansi().fgCyan().a("╚").reset().a(" mechaftp > ").reset());
    }

    public void close()
    {
        AnsiConsole.systemUninstall();
    }
}