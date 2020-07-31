package edu.pdx.cs;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.*;
import java.util.Collections;

/**
 * FakeFtpServer is part of the MockFtpServer project. This factory constructs a FakeFtpServer
 * consistent with the examples in the documentation.
 * Documentation & source code available at: http://mockftpserver.sourceforge.net/
 * Licensed under Apache License v2.0 https://apache.org/licenses/LICENSE-2.0.html
 */
public class FakeFtpServerFactory {

    /**
     * FakeFtpServer factory function generally following guidelines documented in {@link FakeFtpServer}
     *
     * Creates a Unix server with two users:
     *
     *  aang    / katara
     *  bumi    / password
     *
     * There are also several directories created with the following directory structure:
     *  dir                         - permissions   - description
     *  /                           - rw-r--r--     - root dir
     *  /.secrets                   - rw-rw----     - text file
     *  /data                       - rw-rw-r--     - dir
     *  /data/aang                  - rw-r--r--     - dir
     *  /data/aang/love_note        - rwxr-xr-x     - text file
     *  /data/aang/momo.gif         - rw-r--r--     - text file
     *  /data/bumi                  - rw-rw-r--     - dir
     *  /data/bumi/my_cabbages.wav  - r--r--r--     - text file
     */
    public static FakeFtpServer createServer() {
        FakeFtpServer server = new FakeFtpServer();

        UserAccount aang_user = new UserAccount("aang", "katara", "/data/aang");
        aang_user.setGroups(Collections.singletonList("users"));

        UserAccount bumi_user = new UserAccount("bumi", "password", "/data/bumi");
        bumi_user.setGroups(Collections.singletonList("users"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        DirectoryEntry root = new DirectoryEntry("/");
        root.setPermissions(new Permissions("rw-r--r--"));
        root.setOwner("aang");
        root.setGroup("users");

        DirectoryEntry data = new DirectoryEntry("/data");
        data.setPermissions(new Permissions("rw-rw-r--"));
        data.setOwner("aang");
        data.setGroup("users");

        DirectoryEntry aang = new DirectoryEntry("/data/aang");
        aang.setPermissions(new Permissions("rw-r--r--"));
        aang.setOwner("aang");
        aang.setGroup("users");

        DirectoryEntry bumi = new DirectoryEntry("/data/bumi");
        bumi.setPermissions(new Permissions("rw-rw-r--"));
        bumi.setOwner("bumi");
        bumi.setGroup("users");

        DirectoryEntry testDir = new DirectoryEntry("/data/aang/testDir");
        bumi.setPermissions(new Permissions("rw-rw-r--"));
        bumi.setOwner("aang");
        bumi.setGroup("users");

        FileEntry file1 = new FileEntry("/.secrets", "how did you find this?");
        file1.setPermissions(new Permissions("rw-rw----"));
        file1.setOwner("bumi");
        file1.setGroup("users");

        FileEntry file2 = new FileEntry("/data/aang/love_note", "oh sokka, you always make me giggle!");
        file2.setPermissions(new Permissions("rwxr-xr-x"));
        file2.setOwner("aang");
        file2.setGroup("users");

        FileEntry file3 = new FileEntry("/data/aang/momo.gif", "animation of momo eating pie");
        file3.setPermissions(new Permissions("rw-r--r--"));
        file3.setOwner("aang");
        file3.setGroup("users");

        FileEntry file4 = new FileEntry("/data/bumi/my_cabbages.wav", "myyyyyyyyyy cabbaggeeeeeeeeessssss!!!!");
        file4.setPermissions(new Permissions("r--r--r--"));
        file4.setOwner("bumi");
        file4.setGroup("users");

        fileSystem.add(root);
        fileSystem.add(data);
        fileSystem.add(aang);
        fileSystem.add(bumi);
        fileSystem.add(testDir);
        fileSystem.add(file1);
        fileSystem.add(file2);
        fileSystem.add(file3);
        fileSystem.add(file4);

        server.setFileSystem(fileSystem);
        server.addUserAccount(aang_user);
        server.addUserAccount(bumi_user);
        server.setServerControlPort(8080);

        return server;
    }

}
