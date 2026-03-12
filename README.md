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

 O dicionário de dados encontra-se no arquivo pdf abaixo, contendo descrição das tabelas, atributos, tipos de dados, restrições e views.[Acessar Dicionário de Dados](./docs/dicionario_dados2.pdf)

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

## 5. Triggers e Procedures

O banco de dados utiliza triggers para garantir a integridade dos dados e aplicar regras de negócio diretamente no banco, executando validações automáticas antes de determinadas operações de inserção.

---

### Procedure valida_campos_perfil
Procedure responsável por validar se os campos obrigatórios necessários para a criação ou atualização de um perfil foram informados corretamente.
Parâmetros:
(p_nome, p_email, p_senha)

Regras de negócio aplicadas:
* O campo email é obrigatório.
* O campo nome é obrigatório.
* O campo senha é obrigatório.
* Nenhum desses campos pode ser NULL.
* Nenhum desses campos pode ser string vazia ou composta apenas por espaços.

Caso alguma dessas condições não seja atendida, a procedure interrompe a operação utilizando SIGNAL SQLSTATE '45000', retornando uma mensagem de erro.

---

### Procedure valida_senha_forte
Procedure responsável por garantir que a senha cadastrada atenda aos critérios mínimos de segurança.
Parâmetro:
(p_senha)

Regras de negócio aplicadas:
* A senha é obrigatória e não pode ser nula ou vazia.
* A senha deve possuir no mínimo 5 caracteres.
* A senha deve conter pelo menos uma letra.
* A senha deve conter pelo menos um número ou um símbolo (!@#$%&*_).
Caso alguma dessas regras não seja atendida, a procedure interrompe a operação e retorna uma mensagem de erro.

---

### trg_curtida_unica

Executada antes da inserção (BEFORE INSERT) na tabela Interacao. Essa trigger verifica se já existe uma curtida associada ao mesmo perfil e à mesma publicação, impedindo duplicidade de interações.

Gatilho que aciona a trigger:

* A trigger é acionada quando um usuário tenta registrar uma nova curtida em uma publicação.
Caso o usuário tente curtir a mesma publicação mais de uma vez, a trigger entra em ação.

Regras de negócio aplicadas:

* Um perfil só pode curtir uma mesma publicação uma única vez.
* Antes de registrar uma nova curtida, o sistema verifica se já existe uma interação de curtida para o mesmo perfil e publicação.
* Caso já exista uma curtida registrada, a nova tentativa de inserção é bloqueada.
* O sistema retorna uma mensagem informando que o usuário já curtiu a publicação.

---
### trg_valida_perfil_insert

Trigger executada antes da inserção (BEFORE INSERT) na tabela Perfil.
Gatilho que aciona a trigger:
Sempre que um novo registro é inserido na tabela Perfil.

Ações executadas:
* Chama a procedure valida_campos_perfil para verificar se os campos obrigatórios foram informados.
* Chama a procedure valida_senha_forte para validar a segurança da senha cadastrada.
Caso alguma validação falhe, o cadastro do perfil é interrompido.

---
### trg_valida_perfil_update

Trigger executada antes da atualização (BEFORE UPDATE) na tabela Perfil.

Gatilho que aciona a trigger:
Sempre que um perfil existente é atualizado.

Ações executadas:
* Chama a procedure valida_campos_perfil para garantir que os campos obrigatórios continuem válidos.
* Caso a senha tenha sido alterada (NEW.senha <> OLD.senha), a trigger chama a procedure valida_senha_forte para validar a nova senha.
Caso alguma regra seja violada, a atualização do perfil é bloqueada.

## 7. Melhorias

Segundo as observações retornadas nas correções das entregas, foram implementadas as seguintes melhorias:

### 1. Deixar de seguir
Agora é possível, ao estar seguindo um usuário, deixar de seguí-lo. Fazendo com que seja removido da lista de seguidores do que era seguido e da lista de seguidos de quem o seguia.

### 2. Remover interação
Agora é possível, ao realizar uma interação em uma publicação de um determinado usuário, seja ela de curtida ou comentário, remover a interação feita. Fazendo com que seja removido da lista de interações da publicação do usuário a interação antes recebida.

## 3. Exibição de usuários
Ao entrar na aba de seguir usuários ou de interagir em publicações, agora é possível ver a lista de usuários/publicações cadastradas no sistema de forma que torna-se mais fácil e visual executar essas ações.

## 4. Apagar mensagem
Ao enviar uma mensagem, é preciso *clicar* na mensagem que deseja excluir, assim removendo da lista de conversas e do histórico de mensagens.

## 6. Observação

Para fins apenas de exemplificação, os arquivos mídia(fotos) estão sendo gerados automaticamente pelo sistema. De forma que ao criar uma publicação e optar por inserir um arquivo mídia, é solicitado a inserção de um número(id) escolhido de forma livre pelo usuário, que esteja entre 1 e 1.000, que gera automaticamente uma imagem para aquela publicação.



