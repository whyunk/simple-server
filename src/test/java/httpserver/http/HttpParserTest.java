package httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private static HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }


    @Test
    void parserHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateValidTestCase());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertNotNull(request);
        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/", request.getRequestTarget());
        assertEquals("HTTP/1.1",request.getOriginalHttpVersion());
        assertEquals(HttpVersion.HTTP_1_1,request.getBestCompatibleHttpVersion());

    }

    @Test
    @DisplayName("wrong http method")
    void parseHttpRequestBadMethod1() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("long and wrong http method")
    void parseHttpRequestBadMethod2() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("invalid start line format")
    void parseHttpRequestBadMethod3() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase3());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("empty request line")
    void parseHttpRequestBadMethod4() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase4());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("only carriage return. no LF")
    void parseHttpRequestBadMethod5() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase5());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("bad http version")
    void parseHttpRequestBadMethod6() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase6());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("unsupported http version")
    void parseHttpRequestBadMethod7() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase7());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED,e.getErrorCode());
        }
    }

    @Test
    @DisplayName("higher http version")
    void parseHttpRequestBadMethod8() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(generateBadTestCase8());
            assertNotNull(request);
            assertEquals(HttpVersion.HTTP_1_1,request.getBestCompatibleHttpVersion());
            assertEquals("HTTP/1.3",request.getOriginalHttpVersion());
        } catch (HttpParsingException e) {
            e.printStackTrace();
            fail();
        }
    }

    private InputStream generateValidTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Connection: keep-alive\r\n" +
                "sec-ch-ua: \"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Linux\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase1() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase2() {
        String rawData = "GETTTTT / HTTP/1.1\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase3() {
        String rawData = "GET / aaa HTTP/1.1\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase4() {
        String rawData = "\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase5() {
        String rawData = "GET / HTTP/1.1\r" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase6() {
        String rawData = "GET / HTP/1.1\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase7() {
        String rawData = "GET / HTTP/2.3\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCase8() {
        String rawData = "GET / HTTP/1.3\r\n" +
                "Host: localhost:816:11:14.327 [Thread-0] INFO httpserver.core.ServerListenerThread --  * Connection accepted: /0:0:0:0:0:0:0:1\r\n" +
                "080\r\n" +
                "Accept-Language: ko,en-US;q=0.9,en;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}