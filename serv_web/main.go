package main

import (
	"fmt"
	"log"
	"net/http"
)

func handler(w http.ResponseWriter, req *http.Request) {
	pagina := `
	<html>
	<h1>Olá Mundo</h1>
	</html>
`
	fmt.Fprint(w, pagina)
}

func main() {
	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(":8080", nil))
}
