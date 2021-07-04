# Criando um Servidor Web

Esta página contém contém links para os arquivos utilizados para criar um servidor web. O objetivo desta prática é fixar conceitos relacionados a aplicações de rede e protocolos da camada de aplicação. Usaremos a web e o HTTP como exemplos:

- [Descrição](#descrição)
- [Código Fonte](#código-fonte)

## Descrição

Está prática consistirá na criação de um servidor web que apresentará frases aleatórias extraídas de livros. Para ilustrar o tratamento de requisições HTTP, o servidor responderá requisições com um texto bem-formado na linguagem de marcação [HTML](https://developer.mozilla.org/pt-BR/docs/Web/HTML), que é a interpretada pelos navegadores.

Para ilustrar a realização de requisições HTTP, este servidor obter as frases [deste site](https://allugofrases.herokuapp.com/frases/random). A resposta obedece ao formato [JSON](https://developer.mozilla.org/pt-BR/docs/Learn/JavaScript/Objects/JSON) e possui os seguintes campos:

```json
{
    "id":1,
    "frase":"frase",
    "livro":"livro",
    "autor":"autor"
}
```

Assim, para atender a requisição do navegador, o servidor precisará atuar como cliente HTTP. Ou seja, precisará realizar uma requisição para outro servidor.

## Código Fonte

- [Frases Aleatórias Tabajara](https://github.com/danielfireman-ifal/inrc-pratica/blob/main/serv_web/main.go)