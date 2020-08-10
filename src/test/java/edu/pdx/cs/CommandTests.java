package edu.pdx.cs;

import edu.pdx.cs.commands.Command;
import edu.pdx.cs.commands.LoginCommand;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.mockftpserver.fake.FakeFtpServer;

import java.util.ArrayList;
import java.util.List;


public class CommandTests {

    private FakeFtpServer server;
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");
    private static final CommandFactory commandFactory = new CommandFactory();



    @Before
    public void init() {
        server = FakeFtpServerFactory.createServer();
        server.start();


    }

    @After
    public void teardown() {
        server.stop();
    }

    @Test
    public void testLoginCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();

        assertThat(client.state.getLoggedIn(), equalTo(false));
        loginCommand.execute(client.state);
        assertThat(client.state.getLoggedIn(), equalTo(true));
    }

    @Test
    public void testLogoutCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();

        assertThat(client.state.getLoggedIn(), equalTo(false));
        loginCommand.execute(client.state);
        assertThat(client.state.getLoggedIn(), equalTo(true));

        ioHandler.tokens = List.of("logout", username);
        Command logoutCommand = ioHandler.parseInput();

        assertThat(client.state.getLoggedIn(), equalTo(true));
        logoutCommand.execute(client.state);
        assertThat(client.state.getLoggedIn(), equalTo(false));
    }

    @Test
    public void testListRemoteDirCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("listRemoteDirectories");
        Command listRemoteDirsCommand = ioHandler.parseInput();
        boolean success = listRemoteDirsCommand.execute(client.state);

        assertThat(success, equalTo(true));
    }

    @Test
    public void testListRemoteFilesCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("listRemoteFiles");
        Command listRemoteFilesCommand = ioHandler.parseInput();
        boolean success = listRemoteFilesCommand.execute(client.state);

        assertThat(success, equalTo(true));
    }

    @Test
    public void testListLocalDirsCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("listLocalDirectories");
        Command listLocalDirCommand = ioHandler.parseInput();
        boolean success = listLocalDirCommand.execute(client.state);

        assertThat(success, equalTo(true));
    }

    @Test
    public void testListLocalFilesCommand() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("listLocalFiles");
        Command listLocalFilesCommand = ioHandler.parseInput();
        boolean success = listLocalFilesCommand.execute(client.state);

        assertThat(success, equalTo(true));
    }

    @Test
    public void testUploadCommand() throws IOException {
        String username = "aang";
        String password = "katara";
        String local = "upload_file.txt";
        File file = new File(local);
        file.createNewFile();

        FileWriter fw = new FileWriter(local);
        fw.write("Testing File uploading to server");
        fw.close();

        file.deleteOnExit();


        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("uploadFile", local);
        Command listLocalFilesCommand = ioHandler.parseInput();
        boolean success = listLocalFilesCommand.execute(client.state);

        assertThat(success, equalTo(true));
    }

    @Test
    public void testDownloadCommand() throws IOException {
        String username = "aang";
        String password = "katara";
        String local = "upload_file.txt";
        File file = new File(local);
        file.createNewFile();

        FileWriter fw = new FileWriter(local);
        fw.write("Testing File uploading to server");
        fw.close();

        file.deleteOnExit();

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        IOHandler ioHandler = new IOHandler(client);

        ioHandler.tokens = List.of("login", username, password);
        Command loginCommand = ioHandler.parseInput();
        loginCommand.execute(client.state);

        ioHandler.tokens = List.of("uploadFile", local);
        Command UploadCommand = ioHandler.parseInput();
        boolean uploadSuccess = UploadCommand.execute(client.state);
        assertThat(uploadSuccess, equalTo(true));

        ioHandler.tokens = List.of("downloadFile", local);
        Command downloadCommand = ioHandler.parseInput();
        boolean downloadSuccess = downloadCommand.execute(client.state);
        assertThat(downloadSuccess, equalTo(true));
    }
}
