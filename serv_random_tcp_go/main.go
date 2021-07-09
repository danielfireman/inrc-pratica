package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"math/rand"
	"net"
	"os"
	"time"
)

func main() {
	// Checando argumentos.
	arguments := os.Args
	if len(arguments) == 1 {
		log.Fatalln("O parâmetro número da porta é obrigatório.")
	}

	// Configurando semente do gerador de números pseudo-aleatórios.
	rand.Seed(time.Now().Unix())

	// String referente a porta.
	porta := ":" + arguments[1]

	// Iniciando servidor, que ouvirá a porta segundo o protocolo
	// TCPV4. Protocolo orientado a conexão e confiável.
	l, err := net.Listen("tcp4", porta)
	if err != nil {
		log.Fatalf("Erro iniciando servidor na porta %s:%v", porta, err)
	}
	defer l.Close()

	for {
		conn, err := l.Accept()
		if err != nil {
			log.Fatalf("Erro aceitando conexão na porta %s:%v", porta, err)
		}
		go atendeNovaConexao(conn)
	}
}

func atendeNovaConexao(conn net.Conn) {
	fmt.Printf("Atendendo conexão de %s\n", conn.RemoteAddr().String())
	for {

		// O protocolo recebe: MIN,MAX\n
		netData, err := bufio.NewReader(conn).ReadString('\n')

		// Checa se a conexão foi encerrada do lado cliente.
		if err == io.EOF {
			fmt.Printf("Conexão %s encerrada\n", conn.RemoteAddr().String())
			break
		}

		// Checa se houve qualquer outro tipo de erro lendo da conexão TCP.
		if err != nil {
			fmt.Printf("Erro lendo da conexão %v:%v", conn, err)
			break
		}

		// Recebe parâmetros do cliente (min e max). Retornando o erro para o cliente
		// caso exista erro na passagem de parâmetros.
		var min, max int
		i, err := fmt.Sscanf(string(netData), "%d,%d", &min, &max)
		if i != 2 || err != nil {
			conn.Write([]byte("Parâmetros inválidos. Deve ser: min,max\n"))
			continue
		}

		// Retorna número aleatório entre MIN e MAX.
		n := max
		if max != min { // Se min == max retorna um deles e evita um fatal em rand.Intn
			n = rand.Intn(max-min) + min
		}
		result := []byte(fmt.Sprintf("%d\n", n))
		conn.Write(result)
	}
	conn.Close()
}
