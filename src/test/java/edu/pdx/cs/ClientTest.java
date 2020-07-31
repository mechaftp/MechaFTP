package edu.pdx.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import org.mockftpserver.fake.FakeFtpServer;
import org.apache.commons.net.ftp.*;


// This should probably be refactored into a different name
public class ClientTest {

    private FakeFtpServer server;
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Before
    public void init() {
        server = FakeFtpServerFactory.createServer();
        server.start();
    }

    @Test
    public void testGetServerControlPort() {
        assertThat(server.getServerControlPort(), equalTo(Integer.parseInt(PORT)));
    }

    @Test
    public void testGetUserInfo() {
        String username = "aang";
        String password = "katara";

        assertThat(server.getUserAccount(username), notNullValue());
        assertThat(server.getUserAccount(username).getPassword(), equalTo(password));
    }

    @Test
    public void testLoginMethod() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));

        assertThat(client.login(username, password), equalTo(true));
    }

    @Test
    public void testListRemoteDirectories() throws IOException {
        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        client.login("aang", "katara");

        FTPFile[] directories = client.listRemoteDirectories();

        assertThat(directories.length, equalTo(1));
        assertThat(directories[0].isDirectory(), equalTo(true));
        assertThat(directories[0].getName(), containsString("testDir"));
    }

    @Test
    public void testListRemoteFiles() throws IOException{
        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        client.login("aang", "katara");

        FTPFile[] files = client.listRemoteFiles();

        assertThat(files.length, equalTo(2));
        assertThat(files[0].isDirectory(), equalTo(false));
        assertThat(files[0].getName(), containsString("love_note"));
        assertThat(files[1].isDirectory(), equalTo(false));
        assertThat(files[1].getName(), containsString("momo.gif"));
    }

    @Test
    public void testPrintWorkingDirectory() throws IOException{
        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        client.login("bumi", "password");

        assertThat(client.printWorkingDirectory(), equalTo("/data/bumi"));
    }

    @Test
    public void testChangeWorkingDirectory() throws IOException {
        Client client = new Client();
        client.connect(HOSTNAME, Integer.parseInt(PORT));
        client.login("bumi", "password");

        assertThat(client.changeDirectory("/data/bumi/testDir"), equalTo(true));
    }

    @After
    public void teardown() {
        server.stop();
    }
}
