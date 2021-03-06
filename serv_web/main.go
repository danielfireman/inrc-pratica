package main

import (
	"fmt"
	"io"
	"log"
	"net/http"
)

func handler(w http.ResponseWriter, req *http.Request) {
	// Topo da página
	topo := `
<html>
<head>
	<title>Bem-vindas ao Frases Aleatórias Tabajara</title>
</head>
<h1>Bem-vindas ao Frases Aleatórias Tabajara</h1>
<img
	src="https://uploads.spiritfanfiction.com/fanfics/historias/201809/frases-aleatorias-14335368-190920182223.jpg"
	alt="Livro com capa vermelha escrita Frases Aleatórias"
	height=150
	width=150
/>
`
	// Realizando requisição e interpretando resultados.
	http.Get("https://allugofrases.herokuapp.com/frases/random")

	// Final da página
	fim := `
</html>
`
	fmt.Fprintf(w, "%s%s", topo, fim)
}

func reqFraseAleatória() (string, error) {
	resp, err := http.Get("https://allugofrases.herokuapp.com/frases/random")
	if err != nil {
		return "", fmt.Errorf("erro realizando http get:%v", err)
	}
	defer resp.Body.Close()

	dados, err := io.ReadAll(resp.Body)
	if err != nil {
		return "", fmt.Errorf("erro fazendo interpretando resposta:%v", err)
	}
}

func main() {
	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(":8080", nil))
}
