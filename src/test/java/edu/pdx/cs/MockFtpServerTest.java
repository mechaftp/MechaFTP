package edu.pdx.cs;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;

// TODO: Implement logger for FakeFtpServer. See https://www.slf4j.org/ for details.

/**
 * Unit test for simple App.
 */
public class MockFtpServerTest
{
    private FakeFtpServer server;

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
    public void fakeServerIsStartedAfterStarting()
    {
        assertTrue(server.isStarted());
    }


}
