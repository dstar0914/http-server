package webserver;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static String HEADER_SEPARATOR = ":";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // Read request info.
            String requestLine = br.readLine();
            log.info("Request line: {}", requestLine);
            if (requestLine == null) {
                return;
            }

            String[] requestLines = requestLine.split(" ");
            Map<String, String> headers = IOUtils.readLine(br, HEADER_SEPARATOR);
            HttpRequest httpRequest = new HttpRequest(HttpMethod.valueOf(requestLines[0]), requestLines[1], headers, null, null);
            log.info("Request info: {}", httpRequest);

            // Set Response.
            responseResource(out, HttpStatus.OK, httpRequest.getUrl());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseResource(OutputStream out, HttpStatus status, String url) {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = FileUtils.readBytes(url);
        response200Header(dos, status, getResponseContentType(url), body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, HttpStatus status, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 " + status.getCode() + " " + status.name() + " \r\n");
            dos.writeBytes("Content-Type: " + contentType + " \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getResponseContentType(String url) {
        // TODO: Enum 형태로 관리하는 건 어떨까?
        if (url.endsWith(".css")) {
            return "text/css";
        } else {
            return "text/html;charset=utf-8";
        }
    }

}
