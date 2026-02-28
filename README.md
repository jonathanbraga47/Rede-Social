# Projeto Banco de Dados – Rede Social

## Integrantes
Kévna Tenório Brito Cavalcanti

José Jonathan Soares Braga

Miriã Kelly da Silva Santos

## Descrição do Projeto
Este projeto consiste no desenvolvimento de um banco de dados relacional para uma rede social que permite que o usuário interaja com outros por meio de conversas ou interações e realize publicações.
O banco foi modelado, implementado e povoado utilizando Docker.

## 1. Como Executar o Projeto

### Pré-requisitos
- Docker instalado
- Docker Compose instalado
- Git

### Passos para execução
Após já estar com o Docker Desktop aberto e rodando;

1. Clone o repositório:
```bash
git clone https://github.com/jonathanbraga47/Rede-Social.git
```
2. Acesse a pasta raiz do projeto no terminal:
```bash
cd Rede_Social
```
3. Suba os containers Docker:
```bash
docker compose up --build
```
4. Então a aplicação estará rodando na porta:
```bash
http://localhost:3000
```

## 2. Esquema conceitual e lógico do Banco de Dados
Os esquemas feitos para representar o Bando de Dados encontram-se abaixo, contendo representação visual das tabelas,atributos e relacionamentos.

![Esquema Conceitual do Banco de Dados](/docs/esquema_conceitual.jpg)

![Esquema Lógico do Banco de Dados](/docs/esquema_lógico.jpg)


## 3. Dicionário de Dados

 O dicionário de dados encontra-se no arquivo pdf abaixo, contendo descrição das tabelas, atributos, tipos de dados e restrições.
 [Acessar Dicionário de Dados]
 (./docs/dicionario_dados1.pdf)

## 4. Povoamento do Banco de Dados

O banco de dados é inicializado pelo script shcema.sql e povoado pelo script data.sql de forma automatica.
Esses scripts são executado durante a primeira criação do container do banco de dados, no processo de subida da aplicação via Docker.

O schema.sql é responsável por:
Criar toda a estrutura do banco de dados (tabelas, chaves primárias, estrangeiras e views);

O data.sql é responsável por:
Inserir dados iniciais para fins de teste, como perfis, relacionamentos, conversas, publicações e interações.

O processo ocorre de forma automática quando o container é criado, não sendo necessária nenhuma intervenção manual para a criação ou o povoamento do banco.

Importante:
Os scripts .sql são executados apenas na primeira criação do volume do banco de dados. Caso o container já tenha sido iniciado anteriormente, o script não serão executado novamente. Para recriar o banco e reaplicar o povoamento inicial, é necessário remover os volumes do Docker antes de subir os containers novamente.

Exemplo:
```bash
docker compose down -v
docker compose up --build
```

## * Observação

Para fins apenas de exemplificação, os arquivos mídia(fotos) estão sendo gerados automaticamente pelo sistema. De forma que ao criar uma publicação e optar por inserir um arquivo mídia, é solicitado a inserção de um número(id) escolhido de forma livre pelo usuário, que esteja entre 1 e 1.000, que gera automaticamente uma imagem para aquela publicação.



