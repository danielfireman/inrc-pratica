package main

import (
	"flag"
	"fmt"
	"log"
	"strings"
	"time"

	"github.com/google/gopacket"
	"github.com/google/gopacket/pcap"
)

var (
	interf   = flag.String("i", "eth0", "Device to listen to.")
	port     = flag.Int("port", 8080, "Port to listen to.")
	protocol = flag.String("protocol", "tcp", "Protocol to listen to.")
)

var (
	snapshot_len int32 = 1024
	promiscuous  bool  = false
	err          error
	timeout      time.Duration = 10 * time.Second
	handle       *pcap.Handle
)

func main() {
	flag.Parse()

	// Open device
	handle, err = pcap.OpenLive(*interf, snapshot_len, promiscuous, timeout)
	if err != nil {
		log.Fatal(err)
	}
	defer handle.Close()

	// Set filter
	filter := fmt.Sprintf("%s and port %d", *protocol, *port)
	err = handle.SetBPFFilter(filter)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Only capturing device", *interf, "[", *protocol, "] port", *port, "packets.")

	packetSource := gopacket.NewPacketSource(handle, handle.LinkType())
	for packet := range packetSource.Packets() {
		// Do something with a packet here.
		if packet.ApplicationLayer() != nil {
			p := string(packet.ApplicationLayer().Payload())
			fmt.Println("[Datagrama de aplicação] Conteúdo do Carga útil:", strings.TrimRight(p, "\n"))
		} else {
			fmt.Println("[Datagrama de controle]")
		}
		fmt.Println(packet)
	}
}
