import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

public class ServidorUDP {

    public static void main(String[] args) throws IOException {
        // Checando argumentos.
        if (args.length == 0) {
            System.err.println("O parâmetro número da porta é obrigatório.");
            System.exit(1);
        }

        Random rand = new Random();
        rand.setSeed(System.nanoTime());

        // String referente a porta.
        int porta = Integer.parseInt(args[0]);

        // Iniciando servidor, que ouvirá a porta segundo o protocolo
        // UDP. Protocolo não orientado a conexão.
        try (DatagramSocket socket = new DatagramSocket(porta)) {
            System.out.println("Servidor ouvindo porta " + porta);
            while (true) {
                byte[] reqBuf = new byte[256]; // buffer para dados recebidos.
                DatagramPacket requisicao = new DatagramPacket(reqBuf, reqBuf.length);

                // Bloqueia esperando receber dados e preenche reqBuf com o conteúdo da mensagem
                // recebida.
                socket.receive(requisicao);

                // Obtém endereço e porta para contactar o cliente com a resposta.
                InetAddress endCliente = requisicao.getAddress();
                int portaCliente = requisicao.getPort();
                // Processa mensagem, quebrando o string pelo final de linha, como
                // definido pelo protocolo.
                String msg = new String(reqBuf).trim();
                System.out.println("Mensagem recebida de " + endCliente + ":" + portaCliente + " --> \"" + msg + "\"");

                // Recebe parâmetros do cliente (min e max). Retornando o erro para o cliente
                // caso exista erro na passagem de parâmetros.
                int min = 0, max = 0;
                try (Scanner scanner = new Scanner(msg)) {
                    scanner.useDelimiter(",");
                    min = scanner.nextInt();
                    max = scanner.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    byte[] respBuf = new String("Parâmetros inválidos. Deve ser: min,max\n").getBytes();
                    DatagramPacket resposta = new DatagramPacket(respBuf, respBuf.length, endCliente, portaCliente);
                    socket.send(resposta);
                    continue;
                }

                // Retorna número aleatório entre MIN e MAX.
                int n = max;
                if (max != min) { // Se min == max retorna um deles e evita um erro em rand.nextInt
                    n = rand.nextInt(max - min) + min;
                }

                byte[] respBuf = String.format("%d\n", n).getBytes();
                DatagramPacket resposta = new DatagramPacket(respBuf, respBuf.length, endCliente, portaCliente);
                socket.send(resposta);
            }
        }
    }
}