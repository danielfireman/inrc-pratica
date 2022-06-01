import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import static java.net.HttpURLConnection.HTTP_OK;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MaiusculaHandler implements HttpHandler {

    public static final String PATH = "/maiuscula";

    @Override
    public void handle(HttpExchange conn) throws IOException {
        // Obtém os objetos que ajudam na manipulação do corpo da requisição e da
        // resposta.
        InputStream fluxoCorpoRequisicao = conn.getRequestBody();
        OutputStream fluxoCorpoResposta = conn.getResponseBody();

        // Criamos um objeto auxiliar para processar o corpo da requisição.
        // A classe Scanner tem como objetivo separar textos (bytes) em blocos.
        // Ela facilita a obtenção de partes separadas por delimitadores. Por exemplo,
        // vírgula ou espaço.
        // Artigo explicando a classe e seu uso:
        // https://www.devmedia.com.br/como-funciona-a-classe-scanner-do-java/28448
        Scanner scanner = new Scanner(fluxoCorpoRequisicao);
        try {
            // Pega uma linha do corpo da requisição.
            String corpoRequisicao = scanner.nextLine();

            // Converte para maiúsculo (toUpperCase()) e obtém os bytes do string maiúsculo.
            byte[] corpoRequisicaoMaiusculo = corpoRequisicao.toUpperCase().getBytes();

            // Inicia envio da resposta com status OK.
            conn.sendResponseHeaders(HTTP_OK, corpoRequisicaoMaiusculo.length);

            // Envia corpo da resposta.
            fluxoCorpoResposta.write(corpoRequisicaoMaiusculo);
        } finally {
            conn.close();
            scanner.close();
        }
    }
}
