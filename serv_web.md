# Criando um Servidor Web

Esta página contém contém links para os arquivos utilizados para criar um servidor web. O objetivo desta prática é fixar conceitos relacionados a aplicações web. Mostramos alguns exemplos abaixo:

- [Criando um Servidor Web](#criando-um-servidor-web)
  - [Gerador de Frases Aleatórias](#gerador-de-frases-aleatórias)
  - [API Balaio de Gato](#api-balaio-de-gato)

## Gerador de Frases Aleatórias

Este servidor web que apresentará frases aleatórias extraídas de livros. Para ilustrar o tratamento de requisições HTTP, o servidor responderá requisições com um texto bem-formado na linguagem de marcação [HTML](https://developer.mozilla.org/pt-BR/docs/Web/HTML), que é a interpretada pelos navegadores.

Para ilustrar a realização de requisições HTTP, este servidor obterá as frases [deste site](https://allugofrases.herokuapp.com/frases/random). A resposta obedece ao formato [JSON](https://developer.mozilla.org/pt-BR/docs/Learn/JavaScript/Objects/JSON) e possui os seguintes campos:

```json
{
    "id":1,
    "frase":"frase",
    "livro":"livro",
    "autor":"autor"
}
```

Assim, para atender a requisição do navegador, o servidor precisará atuar como cliente HTTP. Ou seja, precisará realizar uma requisição para outro servidor.

**Código fonte**

- [Frases Aleatórias Tabajara](https://github.com/danielfireman/inrc-pratica/blob/main/serv_web/main.go)

## API Balaio de Gato

Este servidor web ilustra uma interface de programação de aplicações (API), muito comuns atualmente. O protocolo HTTP é utilizado para que clientes (outros programas de computador) realizem requisições específicas. 

Note que o programa faz extenso uso do tratamento de exceções e de aspectos da linguagem como herança e constantes estáticas. Também apresenta um exemplo de uso da classe [Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html), importante para tratamento de entrada do usuário. 

Apresentamos algumas rota, também chamadas de endpoints:

- Index ("/"): Apresenta uma mensagem de boas-vindas e e imprime os cabeçalhos da requisição. Exemplo de como usar a biblioteca [HttpExchange](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html) para acessar cabeçalho requisição e escrever no corpo da resposta;
- Raiz ("/raiz"): Calcula a raiz quadrada de um número passado como parâmetro da URL. Exemplo de como tratar a URL requisitada e utilizar a biblioteca [HttpExchange](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html) para escrever cabeçalhos e corpo da resposta;
- Maiúscula ("/maiuscula"): Converte um texto passado no corpo da requisição para maiúscula. Exemplo de como obter e processar o corpo da requisição, além de como escrever no corpo da resposta.

**Executando servidor**

No gitpod.io, basta executar os seguintes comandos no terminal:

```
git checkout main
cd serv_web_java
./run.sh
```

**Acessando a API**

Você pode acessar as API usando o comando [curl](https://curl.se/download.html). Exemplos:

```
curl http://{{DOMINIO_ESPECIFICO_POD}}.gitpod.io/
```

```
curl http://{{DOMINIO_ESPECIFICO_POD}}.gitpod.io/raiz/4
```

```
curl http://{{DOMINIO_ESPECIFICO_POD}}.gitpod.io/maiuscula -d minuscula
```
