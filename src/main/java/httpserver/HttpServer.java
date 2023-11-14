package httpserver;

import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;

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
    }
}
