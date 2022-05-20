# Desafio técnico South

Olá! Bem-vindo ao repositório do desafio técnico para a empresa South Systems.

O projeto como um todo foi construído utilizando as seguintes tecnologias:

 1. Java 8;
 2. Spring Boot;
 3. Banco de dados PostgreSQL;
 4. Eureka Server;
 5. API Gateway;
 6. Mensageria Kafka.

## Repositórios

O projeto foi dividido em **quatro serviços** diferentes onde cada um possui sua própria responsabilidade, e os quatro juntos compõem o desafio técnico. Abaixo são descritos os serviços, suas responsabilidades, parâmetros para execução e repositório para clonar.

### ms-eureka

Este serviço nada mais é do que um **servidor eureka** onde os demais serviços estarão registrados.
Para sua execução, são necessários dois parâmetros:

 - `APPLICATION_NAME`: Parâmetro que recebe e define o nome do serviço dentro do *spring*;
 - `SERVER_PORT`: Parâmetro que recebe e define a porta onde o serviço irá ficar em execução.

Repositório GitHub: [ms-eureka](https://github.com/gabriel-mars/south-ms-eureka);

### ms-gateway

Este serviço é o **API gateway** do projeto, que irá controlar as rotas do demais serviços de acordo com o mapeamento realizado no proxy do zuul.
Para sua execução, são necessários três parâmetros:

 - `APPLICATION_NAME`: Parâmetro que recebe e define o nome do serviço dentro do *spring*;
 - `SERVER_PORT`: Parâmetro que recebe e define a porta onde o serviço irá ficar em execução;
 - `EUREKA_BASE_URL`: URL onde o ms-eureka está rodando, para este serviço possa se registrar.

Repositório GitHub: [ms-gateway](https://github.com/gabriel-mars/ms-gateway);

### ms-vote

Este é o serviço principal do desafio, o qual gerencia os associados, pautas e é por ele que é possível votar. Após o término e fechamento de uma pauta, esse serviço produz uma mensagem no tópico `assembly_result_topic`, que por sua vez será consumido pelo último serviço desta lista. O serviço de votação também precisa de alguns parâmetros a mais para a sua execução e também faz a comunicação com o seu banco de dados.
Este projeto possui documentação de suas rotas no *Swagger* através endereço do serviço + `/swagger-ui.html#/`.
Parâmetros para execução:
- `APPLICATION_NAME`: Parâmetro que recebe e define o nome do serviço dentro do *spring*;
- `DATABASE_URL`: URL de conexão com o banco do PostgreSQL;
- `DATABASE_PORT`: Porta na qual a conexão do banco é realizada;
- `DATABASE_NAME`: Nome do banco. Sugestão: `south_vote`;
- `DATABASE_USERNAME`: Usuário de acesso ao banco;
- `DATABASE_PASSWORD`: Senha de acesso ao banco;
- `KAFKA_BOOTSTRAP_ADDRESS`: URL do Kafka;
- `EUREKA_BASE_URL`: URL onde o ms-eureka está rodando, para este serviço possa se registrar.

Repositório GitHub (este repositório): [ms-vote](https://github.com/gabriel-mars/south-ms-vote);

### ms-assembly

Serviço final do desafio. Ele receberá a mensagem do tópico `assembly_result_topic` e processará os dados para enfim disponibilizar em uma outra rota uma listagem das pautas já fechadas com o seu respectivo resultado.
Este projeto possui documentação de suas rotas no *Swagger* através endereço do serviço + `/swagger-ui.html#/`.
Parâmetros para execução:
- `APPLICATION_NAME`: Parâmetro que recebe e define o nome do serviço dentro do *spring*;
- `DATABASE_URL`: URL de conexão com o banco do PostgreSQL;
- `DATABASE_PORT`: Porta na qual a conexão do banco é realizada;
- `DATABASE_NAME`: Nome do banco. Sugestão: `south_assembly`;
- `DATABASE_USERNAME`: Usuário de acesso ao banco;
- `DATABASE_PASSWORD`: Senha de acesso ao banco;
- `KAFKA_BOOTSTRAP_ADDRESS`: URL do Kafka;
- `EUREKA_BASE_URL`: URL onde o ms-eureka está rodando, para este serviço possa se registrar.

Repositório GitHub: [ms-assembly](https://github.com/gabriel-mars/south-ms-assembly);

## Sugestão de execução

- Primeiramente suba o serviço ms-eureka;
- Suba o serviço ms-gateway;
- Suba o kafka com `docker-compose up -d`;
- Crie os bancos necessários no PostgreSQL para as aplicações rodarem;
- Não precisa criar as tabelas no banco manualmente, visto que os projetos possuem integração com o *Flyway*, que está responsável pela criação;
- Suba o serviço ms-vote;
- Suba o serviço ms-assembly.
