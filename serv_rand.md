# Servidor de Números Aleatórios

Esta página contém contém links para os arquivos utilizados para criar servidores de aplicações de rede baseados no protocolo TCP.

O objetivo desta prática é fixar conceitos relacionados a protocolos da camada de aplicação. Para tal, implementaremos uma aplicação de rede com o mesmo comportamento utilizando diferentes linguagens de programação. O protocolo será descrito abaixo.

- [Descrição](#descrição)
- [Código Fonte](#código-fonte)
- [Executando Servidor](#executando-servidor)
- [Testando](#testando)

## Descrição

Está prática consistirá na criação de um servidor que retornará números inteiros aleatórios entre dois números. O protocolo da camada de aplicação define o formato da requisição e da resposta, o significado dos campos e as ações realizada em função de eventos ou da ausência deles.

### Requisição

A requisição deve conter dois valores inteiros separados por uma vírgula: o mínimo e o máximo da faixa em que o número aleatório gerado deve estar contido. 

> MIN,MAX\n

Exemplos de requisições válidas:

- 10,10\n
- 10,20\n

Exemplos de requisições inválidas

- \n
- 10.10,10.5\n
- abc\n

### Resposta

Caso a requisição seja processada com sucesso, um número aleatório inteiro é retornado:

> INT\n

Caso contrário, uma mensagem de erro será retornada.

## Código fonte

- [Servidor baseado em TCP escrito em Go](https://github.com/danielfireman-ifal/inrc-pratica/blob/main/serv_random_tcp_go/main.go)
- [Servidor baseado em TCP escrito em Java](https://github.com/danielfireman-ifal/inrc-pratica/blob/main/serv_random_tcp_java/ServidorTCP.java)

## Executando servidor

Para executar qualquer um dos servidores, você precisará baixar o [repositório da prática](https://github.com/danielfireman-ifal/inrc-pratica).


### Servidor Go

Para executar este servidor, você precisará [instalar](https://www.alura.com.br/conteudo/golang#:~:text=Se%20queremos%20aprender%20a%20trabalhar,para%20o%20nosso%20sistema%20operacional.) a linguagem de programação Go.

Depois, em um novo terminal:
```sh
$ # Assumindo que estamos na raiz do repositório
$ cd serv_random_tcp_go
$ go run main.go 9090
```

### Servidor Java

Para executar este servidor, você precisará o [kit de desenvolvimento java](https://www.java.com/pt-BR/download/help/develop.html).

```sh
$ sudo apt install openjdk-16-jdk
```

Depois, em um novo terminal:
```sh
$ # Assumindo que estamos na raiz do repositório
$ cd serv_random_tcp_java
$ javac ServidorTCP.java
$ java ServidorTCP 9090
```


## Testando

Usaremos o programa [Netcat](https://pt.wikipedia.org/wiki/Netcat) para demonstrar a aplicação de rede. Este programa será o cliente da aplicação, ou seja, aquele que realiza a requisição. Este papel é análogo ao que o navegador realiza na Web.

Uma vez que todos os servidores listados acima tem o mesmo comportamento, o teste abaixo pode ser executado com qualquer um. Execute os comandos em um novo terminal.

```sh
$ nc localhost 9090
10,10
10

Parâmetros inválidos. Deve ser: min,max

Parâmetros inválidos. Deve ser: min,max
10,30
10
10,30
29
10,30
17
10,30
18
10,30
14
^C
```
