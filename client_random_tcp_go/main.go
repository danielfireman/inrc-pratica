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

	// Enviando mensagem seguindo o protocolo do servidor (o \n é importante)
	msg := fmt.Sprintf("%s\n", os.Args[2])
	if _, err := conn.Write([]byte(msg)); err != nil {
		log.Fatalf("Erro escrevendo na conexão TCP %s:%v", os.Args[1], err)
	}
	fmt.Println("Mensagem:", msg, "escritos em", os.Args[1])

	// Lendo resposta do servidor.
	buf := make([]byte, 1024)
	if _, err := conn.Read(buf); err != nil {
		log.Fatalf("Erro lendo da conexão TCP %s:%v", os.Args[1], err)
	}
	fmt.Println("Resposta:", string(buf), "lida de", os.Args[1])

	// Fechando conexão.
	conn.Close()
}
