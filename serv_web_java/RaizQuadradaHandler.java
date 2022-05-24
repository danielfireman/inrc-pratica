import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import static java.net.HttpURLConnection.*;

public class RaizQuadradaHandler implements HttpHandler {

    public static final String PATH = "/raiz";

    @Override
    public void handle(HttpExchange conn) throws IOException {
        byte[] hello = "Hello".getBytes();
        try (conn) {
            conn.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            conn.sendResponseHeaders(HTTP_OK, hello.length);
            try (OutputStream out = conn.getResponseBody()) {
                
                out.write(hello);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}