import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ClienteUDP {
    public static void main(String[] args) throws IOException {
        // Checando argumentos.
        if (args.length < 2) {
            System.err.println("O parâmetro mensagem e número da porta são obrigatórios.");
            System.exit(1);
        }

        String msg = args[0];
        int portaServidor = Integer.parseInt(args[1]);

        // Iniciando servidor, que ouvirá a porta segundo o protocolo
        // UDP. Protocolo não orientado a conexão.
        //
        // O aplicativo cliente precisa ouvir uma porta para receber a resposta
        //
        // Como não foi passado porta para criação do DatagramSocket, é utilizada
        // uma porta cedida pelo sistema operacional.
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] reqBuf = (msg + "\n").getBytes();
            
            InetSocketAddress endServidor = new InetSocketAddress("localhost", portaServidor);
            DatagramPacket requisicao = new DatagramPacket(reqBuf, reqBuf.length, endServidor.getAddress(), endServidor.getPort());
            socket.send(requisicao);

            byte[] respBuf = new byte[256]; // buffer para dados recebidos.
            DatagramPacket resposta = new DatagramPacket(respBuf, respBuf.length);
            socket.receive(resposta);

            // Imprimir resposta.
            String respMsg = new String(respBuf).trim();
            System.out.println("Mensagem recebida de " + endServidor.getAddress() + ":" + endServidor.getPort()+ " --> \"" + respMsg + "\"");

            System.out.println();
        }
    }
}
