package main

import (
	"encoding/json"
	"fmt"
	"html/template"
	"io"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func handler(w http.ResponseWriter, req *http.Request) {
	// Realizando requisição e interpretando resultados.
	frase, err := reqFraseAleatória()
	if err != nil {
		log.Printf("erro requisitando frase aleatória:%v", err)
		http.Error(w, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)
		return
	}
	if err := index.Execute(w, frase); err != nil {
		log.Printf("erro renderizando template %s:%v", index.Name(), err)
		http.Error(w, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)
		return
	}
}

var index = template.Must(template.New("index").Parse(`
<html>
<head>
	<title>Bem-vindas ao Frases Aleatórias Tabajara</title>
</head>
<h1 align="center">Bem-vindas ao Frases Aleatórias Tabajara</h1>
<center>
	<img
		src="https://uploads.spiritfanfiction.com/fanfics/historias/201809/frases-aleatorias-14335368-190920182223.jpg"
		alt="Livro com capa vermelha escrita Frases Aleatórias"
		height=150
		width=150
	/>
</center>
<br>
<br>
<hr>
<br>
Frase: {{.Frase}}<br>
Autor: {{.Autor}}<br>
<br>
<hr>
</html>
`))

type frase struct {
	Frase string `json:"frase"`
	Autor string `json:"autor"`
}

func reqFraseAleatória() (frase, error) {
	resp, err := http.Get("https://allugofrases.herokuapp.com/frases/random")
	if err != nil {
		return frase{}, fmt.Errorf("erro realizando http get:%v", err)
	}
	defer resp.Body.Close()

	dados, err := io.ReadAll(resp.Body)
	if err != nil {
		return frase{}, fmt.Errorf("erro fazendo lendo resposta:%v", err)
	}

	var f frase
	if err := json.Unmarshal(dados, &f); err != nil {
		return frase{}, fmt.Errorf("erro fazendo interpretando resposta:%v", err)
	}
	return f, nil
}
