package httpserver;

import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;
import httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration currentConfiguration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + currentConfiguration.getPort());
        LOGGER.info("Using Webroot: " + currentConfiguration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(currentConfiguration.getPort(), currentConfiguration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO handle later.
        }


    }
}
