import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class Servidor {

    // Número máximo de conexões que devem abertas no servidor.
    // Caso o número seja atingido, novas conexões serão rejeitadas.
    public static final int TAMANHO_BACKLOG = 100;

    public static void main(String[] args) throws IOException {
        // Porta que identificará o processo servidor no hospedeiro.
        // Comumente dizemos que o servidor está "ouvindo" essa porta.
        // Se esse valor for zero, a biblioteca irá solicitar uma porta
        // sem uso ao SO.
        InetSocketAddress bindAddr = new InetSocketAddress(36615);
        HttpServer server = HttpServer.create(bindAddr, TAMANHO_BACKLOG);
        server.setExecutor(Executors.newSingleThreadExecutor());

        // Registrando caminhos que devem ser atendidos.
        server.createContext(IndexHandler.PATH, new IndexHandler());
        server.createContext(RaizHandler.PATH, new RaizHandler());
        server.createContext(MaiusculaHandler.PATH, new MaiusculaHandler());

        // Iniciando servidor.
        server.start();
        System.out.printf("Servidor ouvindo requisições na porta %s\n\n", server.getAddress().getPort());
    }
}
