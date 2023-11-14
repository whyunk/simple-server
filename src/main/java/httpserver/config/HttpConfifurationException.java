package httpserver.config;

public class HttpConfifurationException extends RuntimeException {
    public HttpConfifurationException() {
    }

    public HttpConfifurationException(String message) {
        super(message);
    }

    public HttpConfifurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpConfifurationException(Throwable cause) {
        super(cause);
    }
}
