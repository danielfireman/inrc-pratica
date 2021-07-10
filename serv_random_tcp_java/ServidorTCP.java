import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorTCP {
    public static void main(String[] args) {
        // Checando argumentos.
        if (args.length == 0) {
            fatal("O parâmetro número da porta é obrigatório.");
        }

        // String referente a porta.
        int porta = Integer.parseInt(args[0]);

        // Iniciando servidor, que ouvirá a porta segundo o protocolo
        // TCPV4. Protocolo orientado a conexão e confiável.
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Cria uma Thread para executar a função atendeNovaConexao
                    // Note que o comportamento da thread é descrito por Runnable.
                    // Nesse exemplo usamos funções lambda para tornar a sintaxe
                    // mais concisa.
                    // Mais a respeito de funções lambda em java:
                    // https://www.devmedia.com.br/como-usar-funcoes-lambda-em-java/32826
                    new Thread(() -> {
                        atendeNovaConexao(clientSocket);
                    }).start();
                } catch (IOException ioe) {
                    fatal(String.format("Erro aceitando conexão na porta %d:%s", porta, ioe.getMessage()));
                }
            }
        } catch (IOException ioe) {
            fatal(String.format("Erro iniciando servidor na porta %d:%s", porta, ioe.getMessage()));
        }
    }

    private static void atendeNovaConexao(Socket socket) {
        // Configurando semente do gerador de números pseudo-aleatórios.
        Random rand = new Random();
        rand.setSeed(System.nanoTime());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                // O protocolo recebe: MIN,MAX\n
                String netData = in.readLine();
                if (netData == null) {
                    System.out.printf("Conexão %s encerrada\n", socket.getRemoteSocketAddress());
                    break;
                }

                // Recebe parâmetros do cliente (min e max). Retornando o erro para o cliente
                // caso exista erro na passagem de parâmetros.
                int min = 0, max = 0;
                try (Scanner scanner = new Scanner(netData)) {
                    scanner.useDelimiter(",");
                    min = scanner.nextInt();
                    max = scanner.nextInt();
                } catch (Exception e) {
                    out.write("Parâmetros inválidos. Deve ser: min,max\n");
                    out.flush();
                    continue;
                }

                // Retorna número aleatório entre MIN e MAX.
                int n = max;
                if (max != min) { // Se min == max retorna um deles e evita um erro em rand.nextInt
                    n = rand.nextInt(max - min) + min;
                }
                out.write(String.format("%d\n", n));
                out.flush();
            }
        } catch (IOException ioe) {
            fatal(String.format("Erro lendo ou escrevendo do socket cliente:%s", ioe.getMessage()));
        } finally {
            try {
                socket.close();
            } catch (IOException ioe) {
                fatal(String.format("Erro fechando socket cliente %s:%s", socket.toString(), ioe.getMessage()));
            }
        }
    }

    private static void fatal(String msg) {
        System.err.println(msg);
        System.exit(1);
    }
}