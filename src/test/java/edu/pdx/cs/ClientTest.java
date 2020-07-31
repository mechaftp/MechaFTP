package edu.pdx.cs;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Path;

import org.mockftpserver.fake.FakeFtpServer;


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

    @After
    public void teardown() {
        server.stop();
    }

    @Test
    public void testLogoutSuccess() throws IOException{
        //String username = "apple,";
        Logger logger = mock(Logger.class);
        Path path = mock(Path.class);
        FTPClient ftp = mock(FTPClient.class);
        when(ftp.logout()).thenReturn(true);

        Client client = new Client(logger, path, ftp);

        assertTrue(client.logout("apple"));
        verify(logger).info("User apple is logging out!");
    }



}
