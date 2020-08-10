package edu.pdx.cs;

import edu.pdx.cs.commands.*;

import java.util.List;

public class CommandFactory {
    public static Command createLogin(Client client, List<String> subarguments){
        return new LoginCommand(client, subarguments);
    }

    public static Command createLogout(Client client, List<String> subarguments){
        return new LogoutCommand(client, subarguments);
    }

    public static Command createListRemoteDirectories(Client client, List<String> subarguments){
        return new ListRemoteDirCommand(client, subarguments);
    }

    public static Command createListRemoteFiles(Client client, List<String> subarguments){
        return new ListRemoteFileCommand(client, subarguments);
    }

    public static Command createListLocalDirectories(Client client, List<String> subarguments){
        return new ListLocalDirCommand(client, subarguments);
    }

    public static Command createListLocalFiles(Client client, List<String> subarguments){
        return new ListLocalFilesCommand(client, subarguments);
    }

    public static Command createUpload(Client client, List<String> subarguments){
        return new UploadCommand(client, subarguments);
    }

    public static Command createNull(Client client, List<String> subarguments)
    {
        return new NullCommand(client, subarguments);
    }
}
