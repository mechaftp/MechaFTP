package edu.pdx.cs;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains methods for validating user input
 */
public class Validator {

    /**
     * Determines whether or not the path passed in as an argument is valid
     * @param path <code>String</code> object containing the code
     * @return true if path is valid, false otherwise
     */
    protected boolean validatePath(String path) {
        boolean valid = true;

        try {
            Path logPath = Paths.get(path);
            File file = logPath.toFile();
            if (!file.exists())
                valid = false;
        } catch (InvalidPathException | NullPointerException ex) {
            valid = false;
        }

        return valid;
    }

    protected boolean dummyValidate() { return true; }
}
