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

    @Before
    public void init() {
        server = FakeFtpServerFactory.createServer();
        server.start();
    }

    @Test
    public void testGetServerControlPort(){
        assertThat(server.getServerControlPort(), equalTo(8080));
    }

    @Test
    public void testGetUserInfo(){
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
        client.connect("localhost", 8080);

        assertThat(client.login(username, password), equalTo(true));
    }

    @Test
    public void testListDirectories() throws IOException{
        Client client = new Client();
        client.connect("localhost", 8080);
        client.login("aang", "katara");

        client.changeDirectory("/users/");

        FTPFile[] directories = client.listRemoteDirectories();
        FTPFile[] files = client.listRemoteFiles();

        //***debug, asserts***
        for(FTPFile dir:directories)
            System.out.print(dir.getName() + " ");
        for(FTPFile file:files)
            System.out.print(file.getName() + " ");
    }

    @After
    public void teardown() {
        server.stop();
    }


}
