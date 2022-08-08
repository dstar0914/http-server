package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private static final String RESOURCE_PATH = "./webapp";

    public static byte[] readBytes(String filePath) {
        File file = new File(RESOURCE_PATH + filePath);

        try {
            if (!file.exists()) {
                return  "File Not found.".getBytes();
            }

            return Files.readAllBytes(file.toPath());

        } catch (IOException e) {
            log.error(e.getMessage());
            // TODO: / 로 들어왔을 떄 redirect 처리 필요.
        }

        return null;
    }

}
