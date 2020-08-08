package edu.pdx.cs;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.*;

public class ValidatorTest {

    @Test
    public void testBasicFilePath() throws IOException {
        File file = new File("ValidTestFile");
        file.createNewFile();

        String goodPath = "ValidTestFile";
        String badPath = "NotAValidTextFile";
        Validator val = new Validator();

        assertThat(val.validatePath(goodPath), equalTo(true));
        assertThat(val.validatePath(badPath), equalTo(false));
    }

    @Test
    public void testMultiDirectoryPath() throws IOException{
        File file = new File("src/test/ValidTestFile2");
        file.createNewFile();

        String goodPath = "src/test/ValidTestFile2";
        String badPath = "src/test/java/ValidTestFile2";
        Validator val = new Validator();

        assertThat(val.validatePath(goodPath), equalTo(true));
        assertThat(val.validatePath(badPath), equalTo(false));
    }

    @Test
    public void testDirectoryPath() throws IOException{
        String goodPath1 = "src/test";
        String goodPath2 = "resources/wiki";
        String badPath = "src/bad/";
        Validator val = new Validator();

        assertThat(val.validatePath(goodPath1), equalTo(true));
        assertThat(val.validatePath(goodPath2), equalTo(true));
        assertThat(val.validatePath(badPath), equalTo(false));
    }
}
