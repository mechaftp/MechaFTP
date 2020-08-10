package edu.pdx.cs;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ConnectionTest {

    /**
     * Valid ports are in the range 0-65,535
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidPort() throws UnknownHostException {
        new Connection("12.16.254.1", "70000");
    }

    @Test
    public void getHostWorksAsExpected() throws UnknownHostException {
        String host = "12.16.254.1";
        Connection c = new Connection(host, "22");
        assertThat(c.getHost(), equalTo(InetAddress.getByName(host)));
    }

    @Test
    public void getPortWorksAsExpected() throws UnknownHostException {
        String port = "42";
        Connection c = new Connection("12.16.254.1", port);
        assertThat(c.getPort(), equalTo(Integer.parseInt(port)));
    }

    @Test
    public void toStringProducesReadableString() throws UnknownHostException {
        Connection c = new Connection("12.16.254.1", "20");
        assertThat(c.toString(), equalTo("12.16.254.1:20"));
    }
}
