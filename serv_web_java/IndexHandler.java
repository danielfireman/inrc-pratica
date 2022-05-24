import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Implementa o atendimento a requisições a página inicial do servidor.
 */
public class IndexHandler implements HttpHandler {

    public static final String PATH = "/";

    @Override
    public void handle(HttpExchange conn) throws IOException {
        byte[] hello = "Seja bem-vindo(a) a API calculadora!".getBytes();
        try (conn) {
            // Lembrando do protocolo HTTP, primeiro o status ...
            conn.sendResponseHeaders(HTTP_OK, hello.length);

            // Depois cabeçalhos ...
            Headers headers = conn.getResponseHeaders();
            headers.add("Content-Type", "text/html; charset=UTF-8");
            
            // Depois is dados.
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