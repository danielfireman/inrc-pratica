# Criando uma Aplicação de Rede

Esta página contém contém links para os arquivos utilizados para criar servidores de aplicações de rede baseados nos protocolos TCP e UDP. O objetivo desta prática é fixar conceitos relacionados a protocolos da camada de aplicação. Para tal, implementaremos uma aplicação de rede com o mesmo comportamento utilizando diferentes protocolos de transporte. O protocolo será descrito abaixo.

- [Descrição](#descrição)
- [Código Fonte](#código-fonte)
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

## Testando

Usaremos o programa [Netcat](https://pt.wikipedia.org/wiki/Netcat) para demonstrar a aplicação de rede. Este programa será o cliente da aplicação, ou seja, aquele que realiza a requisição. Este papel é análogo ao que o navegador realiza na Web.

Serão necessário, pelo menos, dois terminais para realizar este teste.

Terminal 1 (servidor):
```sh
$ # Assumindo que estamos na raiz do repositório
$ cd serv_random_tcp_go
$ go run main.go 9090
```

Terminal 2 (cliente):
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
