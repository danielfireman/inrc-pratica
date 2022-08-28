import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.Console;  

public class ClientRandomTCP {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Os parâmetros host e porta são obrigatórios.");
            System.exit(1);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Console console = System.console();
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String msg = console.readLine("Digite a mensagem a ser enviada para o servidor: ");
                if (msg.equals("encerra")) {
                    break;
                }
                out.println(msg);
                console.format("Mensagem %s escrita em %s:%d\n", msg, host, port);
                String resposta = in.readLine();
                console.format("Resposta %s lida de %s:%d\n", resposta, host, port);
            }                
        }
    }
}