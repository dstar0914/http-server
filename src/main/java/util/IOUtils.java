package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {

    public static Map<String, String> readLine(BufferedReader br, String separator) throws IOException {
        Map<String, String> lines = new HashMap<>();

        String line;
        while (!(line = br.readLine()).equals("")) {
            String[] values = line.split(separator);
            lines.put(values[0], values[1].trim());
        }

        return lines;
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

}
