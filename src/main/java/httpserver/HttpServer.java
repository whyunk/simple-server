package httpserver;

import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {

    public static void main(String[] args) {

        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration currentConfiguration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port: " + currentConfiguration.getPort());
        System.out.println("Using Webroot: " + currentConfiguration.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(currentConfiguration.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java HTTP Server</title>" +
                    "</head><body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";

            final String CRLF = "\n\r"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF +  //status Line : HTTP VERSION RESPONSE CODE_RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF +  //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF ;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
