package edu.pdx.cs;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientState
{

    private Path localCwd;
    private Path remoteCwd;

    public ClientState()
    {
        this.localCwd = Paths.get(System.getProperty("user.dir"));
    }

    public Path getLocalCwd()
    {
        return localCwd;
    }

    public String getLocalCwdString()
    {
        return localCwd.toAbsolutePath().toString();
    }

    public void setLocalCwd(Path localCwd)
    {
        this.localCwd = localCwd;
    }

    public Path getRemoteCwd()
    {
        return remoteCwd;
    }

    public String getRemoteCwdString()
    {
        return remoteCwd.toAbsolutePath().toString();
    }

    public void setRemoteCwd(Path remoteCwd)
    {
        this.remoteCwd = remoteCwd;
    }


}
