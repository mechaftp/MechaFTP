package edu.pdx.cs;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client
{

    private final static Logger logger = LogManager.getLogger(Client.class);
    public ClientState state;
    private Path logpath;
    FTPClient ftp = new FTPClient();
    BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();

    public Client()
    {
        state = new ClientState();
    }

    public void connect(String server, int port)
    {
        connect(server, Integer.valueOf(port));
    }

    public void connect(String server, Integer port)
    {
        try
        {
            if (port != null)
                ftp.connect(server, port);
            else
                ftp.connect(server);
            // TODO: state.setRemoteCwd to the current working directory on server
            state.setRemoteCwd(Paths.get("/data/aang"));
        }
        catch (IOException e)
        {
            logger.error("Failed to connect to server " + server + " with error:\n" + e.getLocalizedMessage());
            state.setRemoteCwd(Path.of(""));
        }

    }

    /**
     * Logs users into the FTP server
     *
     * @param username the username
     * @param password the user's password
     * @return true if login is successful, false otherwise
     * @throws IOException
     */
    public boolean login(String username, String password) throws IOException
    {
        boolean status = ftp.login(username, password);
        if (status)
        {
            logger.info("Logged in as: ", username);
        } else
        {
            logger.error("Failed login for: ", username);
        }
        return status;
    }

    void setLogfile(Path logpath)
    {
        this.logpath = logpath;
    }

//    void run()
//    {
//        while (true)
//        {
//            try
//            {
//                Map<String, Object> input = ioHandler.getInput();
//                String command = (String) input.keySet().toArray()[0];
//
//                switch (command)
//                {
//                    case "--login":
//                        break;
//                }
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//            break;
//        }
//
//        //cleanup
//    }

}



