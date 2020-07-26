package edu.pdx.cs;

import java.io.Closeable;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;

public class CLIStatusBar implements Closeable
{
    public PrintStream out;
    public Path localCwd;
    public Path remoteCwd;

    private CLIStatusBar(PrintStream out)
    {
        this.out = out;
        this.localCwd = Paths.get(System.getProperty("user.dir"));
    }

    public static CLIStatusBar create(PrintStream out, String remoteCwd)
    {
        AnsiConsole.systemInstall();
        CLIStatusBar bar = new CLIStatusBar(out);
        bar.setRemoteCwd(remoteCwd);
        return bar;
    }

    public void setRemoteCwd(String remoteCwd)
    {
        this.remoteCwd = Paths.get(remoteCwd);
    }

    public void render()
    {
        this.localCwd.toAbsolutePath().toString();
        int localLen = this.localCwd.toString().length();
        int remoteLen = this.remoteCwd.toString().length();
        String localCwdString = localLen > 35 ?
            "..." + this.localCwd.toString().substring(localLen - 32) :
            this.localCwd.toString();
        String remoteCwdString = remoteLen > 33 ?
            "..." + this.remoteCwd.toString().substring(remoteLen - 30) :
            this.remoteCwd.toString();
        String status = String.format("%1$-38s  %2$38s", localCwdString, remoteCwdString);

        out.println(ansi().a("╔ ")
            .fg(Color.GREEN).a("[local] ")
            .fg(Color.WHITE).a(status)
            .fg(Color.GREEN).a(" [remote] ")
            .reset().a("╗"));
    }

    public void close()
    {
        AnsiConsole.systemUninstall();
    }
}
