package edu.pdx.cs;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.filesystem.*;
import org.mockftpserver.fake.UserAccount;
import java.nio.file.FileSystem;
import java.util.Optional;

public class ClientTest {
    @Test
    public void testGetServerControlPort(){
        FakeFtpServerFactory mock = new FakeFtpServerFactory();
        FakeFtpServer mockServer = mock.createServer();

        assertThat(mockServer.getServerControlPort(), equalTo(8080));
    }

    @Test
    public void testGetUserInfo(){
        FakeFtpServerFactory mock = new FakeFtpServerFactory();
        FakeFtpServer mockServer = mock.createServer();
        String username = "aang";
        String password = "katara";

        assertThat(mockServer.getUserAccount(username), notNullValue());
        assertThat(mockServer.getUserAccount(username).getPassword(), equalTo(password));
    }

    @Test
    public void testLoginMethod() throws IOException {
        FakeFtpServerFactory mock = new FakeFtpServerFactory();
        FakeFtpServer mockServer = mock.createServer();
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);

        assertThat(client.login(username, password), equalTo(true));
    }
}
