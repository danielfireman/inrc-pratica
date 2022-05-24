import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Implementa o atendimento a requisições ao path /raiz (raiz quadrada).
 */
public class RaizHandler implements HttpHandler {

    // Path relacionado ao tratador de raiz quadrada.
    public static final String PATH = "/raiz";

    @Override
    public void handle(HttpExchange conn) throws IOException {
        // Primeira coisa a fazer é saber qual é o parâmetro.
        // O que acontece no código abaixo se a URL não continuar o parâmetro? Por exemplo, somente "/raiz".
        String[] partes = conn.getRequestURI().getPath().split("/");

        // O que acontece se o usuário colocar vários parâmetros? Por exemplo, "/raiz/1/2".
        String parametro = partes[2]; // partes[0] = "", partes[1] = "raiz"
        byte[] result = calculateResponse(parametro);
        try (conn) {
            // Lembrando do protocolo HTTP, primeiro o status ...
            conn.sendResponseHeaders(HTTP_OK, result.length);

            // Depois cabeçalhos ...
            Headers headers = conn.getResponseHeaders();
            headers.add("Content-Type", "text/html; charset=UTF-8");

            // Depois envia o resultado para o cliente (navegador ou programa).
            try (OutputStream out = conn.getResponseBody()) {
                out.write(result);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    byte[] calculateResponse(String parametro) {
        // O que acontece se o parâmetro for uma letra? Por exemplo, "/raiz/aaa".
        double pDouble = Double.parseDouble(parametro);

        // O que acontece se o parâmetro for um número negativo? Por exemplo, "/raiz/-10".
        double raiz = Math.sqrt(pDouble);

        // Converte a resposta para um array de bytes. Lembre-se que o sockets enviam e
        // recebem bytes usando a rede (internet).
        return Double.toString(raiz).getBytes();
    }
}