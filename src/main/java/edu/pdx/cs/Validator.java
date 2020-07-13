package edu.pdx.cs;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains methods for validating i
 */
public class Validator {
    Boolean validatePath(String path) {
        try {
            Path logPath = Paths.get(path);
            File file = logPath.toFile();
            if (!file.exists()) {
                return false;
            }
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

}
