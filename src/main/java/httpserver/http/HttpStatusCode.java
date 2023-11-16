package httpserver.http;

public enum HttpStatusCode {

    /* CLIENT ERRORS */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method not allowed"),
    CLIENT_ERROR_405_BAD_REQUEST(414, "URI Too Long"),
    /* SERVER ERRORS */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal server error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not implemented"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "Http version not supported");


    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
