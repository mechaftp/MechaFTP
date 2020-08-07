package edu.pdx.cs;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.Permissions;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;


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

    @Test
    public void testUploadFile() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);
        client.login(username, password);

        DirectoryEntry aang = new DirectoryEntry("/data/aang");
        aang.setPermissions(new Permissions("rw-r--r--"));
        aang.setOwner("aang");
        aang.setGroup("users");


        FileInputStream fileInputStream = new FileInputStream("Filenname.txt");


        boolean ret = client.uploadFile("\\data\\aang", "Filename.txt", "\\data\\aang");
        assertThat(ret, equalTo(true));
    }

    @Ignore
    public void testupload() throws URISyntaxException, IOException {


        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);
        client.login(username, password);

        DirectoryEntry aang = new DirectoryEntry("/data/aang");
        aang.setPermissions(new Permissions("rw-r--r--"));
        aang.setOwner("aang");
        aang.setGroup("users");

        File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
       boolean ret = client.uploadAFile(file, "/test.txt");
        assertThat(ret, equalTo(true));

     //  assertThat(FakeFtpServer.getFileSystem().exists("/test.txt")).isTrue();
    }


    @Test
    public void testDownloadFile() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);
        client.login(username, password);

        boolean ret = client.downloadFile();
        assertThat(ret, equalTo(true));
//        assertThat(client.downloadFile(), equalTo(true));
    }


    @Ignore
    public void testDownloadFileFromServerToRemote() throws IOException{

       // Start server
        FakeFtpServerFactory fakeFtpServerFactory = new FakeFtpServerFactory();
        server = fakeFtpServerFactory.createServer();
        server.start();

        // Client login
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);
        client.login(username, password);

        // client todo
        client.downloadAFile("/momo.gif", "downloaded_momo.gif");
     // assertThat(new File("downloaded_momo.gif")).exists();
       // new File("downloaded_momo.gif").delete();



        // close server

    }




    @Test
    public void testCreateDirectory() throws IOException {
        String username = "aang";
        String password = "katara";

        Client client = new Client();
        client.connect("localhost", 8080);
        client.login(username, password);

        boolean ret = client.createDirectory();
        assertThat(ret, equalTo(true));
//        assertThat(client.login(username, password), equalTo(true));
    }
}
