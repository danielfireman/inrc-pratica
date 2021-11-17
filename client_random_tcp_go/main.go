package main

import (
	"fmt"
	"log"
	"net"
	"os"
)

func main() {
	// Conectando.
	conn, err := net.Dial("tcp", os.Args[1])
	if err != nil {
		log.Fatalf("Erro conectando ao endereço IP %s:%v", os.Args[1], err)
	}
	fmt.Println("Conectado a", os.Args[1])

	for {
		fmt.Printf("Digite a mensagem a ser enviada para o servidor:")
		var msg string
		if _, err := fmt.Scanln(&msg); err != nil {
			log.Fatalf("Erro lendo do teclado:%v", err)
		}

		if msg == "encerra" {
			break
		}

		// Enviando mensagem seguindo o protocolo do servidor (o \n é importante)
		if _, err := conn.Write([]byte(fmt.Sprintf("%s\n", msg))); err != nil {
			log.Fatalf("Erro escrevendo na conexão TCP %s:%v", os.Args[1], err)
		}
		fmt.Println("Mensagem:", msg, "escritos em", os.Args[1])

		// Lendo resposta do servidor.
		buf := make([]byte, 1024)
		if _, err := conn.Read(buf); err != nil {
			log.Fatalf("Erro lendo da conexão TCP %s:%v", os.Args[1], err)
		}
		fmt.Println("Resposta:", string(buf), "lida de", os.Args[1])
	}

	// Fechando conexão.
	conn.Close()
	fmt.Printf("Conexão com %s encerrada.\n", os.Args[1])
}
