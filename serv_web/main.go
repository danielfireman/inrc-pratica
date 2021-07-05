package main

import (
	"encoding/json"
	"fmt"
	"html/template"
	"io"
	"log"
	"net/http"
	"net/http/httputil"
)

func main() {
	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func handler(w http.ResponseWriter, req *http.Request) {
	// Realizando requisição e interpretando resultados.
	dados, err := reqFraseAleatoria()
	if err != nil {
		log.Printf("erro requisitando frase aleatória:%v", err)
		http.Error(w, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)
		return
	}

	// Renderizando o template e enviando no corpo da resposta.
	// Server-Side Rendering -- Renderização do lado do servidor.
	// Envia para o cliente (navegador) uma versão completa da página.
	// Para tal, precisa requisitar e completar o texto no servidor,
	// como parte do atendimento da requisição.
	if err := index.Execute(w, dados); err != nil {
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
<b>Frase:</b> {{.Frase}}<br>
<b>Autor:</b> {{.Autor}}<br>
<br>
--
<br>
<br>
<b>Requisição HTTP:</b> {{.Req}}<br>
<b>Resposta HTTP:</b> {{.Resp}}
<br>
<hr>
</html>
`))

// dadosIndexTemplate guarda informações utilizadas para renderizar a página
// index.
type dadosIndexTemplate struct {
	Frase string `json:"frase"`
	Autor string `json:"autor"`
	Req   string
	Resp  string
}

// reqFraseAleatoria realiza a chamada a API de frases aleatórias.
func reqFraseAleatoria() (dadosIndexTemplate, error) {
	var dados dadosIndexTemplate

	// Cria nova requisição. Observe o método GET.
	req, err := http.NewRequest("GET", "https://allugofrases.herokuapp.com/frases/random", nil)
	if err != nil {
		return dados, fmt.Errorf("erro criando request:%v", err)
	}

	// Copia dados crus da requisição. Inclui informações sobre método, cabeçalho, status
	// e etc.
	// Não faz parte do funcionamento do servidor. Está aqui para fins didáticos.
	dumpReq, err := httputil.DumpRequest(req, false)
	if err != nil {
		return dados, fmt.Errorf("erro dumping request:%v", err)
	}
	dados.Req = string(dumpReq)

	// Realiza requisição.
	// Inicia conexão TCP, envia dados de acordo com o protocolo HTTP.
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return dadosIndexTemplate{}, fmt.Errorf("erro realizando http get:%v", err)
	}
	defer resp.Body.Close()

	// Copia dados crus da resposta. Inclui informações sobre método, cabeçalho, status
	// e etc.
	// Não faz parte do funcionamento do servidor. Está aqui para fins didáticos.
	dumpResp, err := httputil.DumpResponse(resp, true)
	if err != nil {
		return dados, fmt.Errorf("erro dumping resposta:%v", err)
	}
	dados.Resp = string(dumpResp)

	// Lê todos os bytes da parte de dados (corpo) da resposta.
	corpoResposta, err := io.ReadAll(resp.Body)
	if err != nil {
		return dadosIndexTemplate{}, fmt.Errorf("erro fazendo lendo resposta:%v", err)
	}

	// Realiza o processamento do corpo da resposta e já preenche variável dados.
	// Lembrando que a resposta vem de acordo com o formato JSON. A biblioteca `json`
	// faz essa conversão. Existem bibliotecas análogas na maioria das linguagens
	// mais utilizadas.
	if err := json.Unmarshal(corpoResposta, &dados); err != nil {
		return dadosIndexTemplate{}, fmt.Errorf("erro fazendo interpretando resposta:%v", err)
	}
	return dados, nil
}
