package util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class FileUtil {
    public static String readFromFileToString(String filePath) throws IOException {
        byte[] byteArray = Files.readAllBytes(Path.of(filePath));
        return new String(byteArray);
    }
}
