# Api De Pagamento

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Maven
- JPA
- Hibernate
- Postgresql
- Lombok
- Spring Doc/Swagger
- ModelMapper
- Gson
- Docker
- Junit
- Mockito
- Hamcrest

## Arquitetura

- A estrutura foi baseada no DDD (Design direcionado ao domínio).
- Portanto, 4 camadas foram utilizadas:
    1. Controller [Camada de interface - Presetantion] 
    2. Domain [Camada de domínio - Domain]
    3. Service [Camada de aplicação - Application]
          1. Dto [Camada de regras de negócio]
          2. Model [Camada de acesso ao banco de dados]
          3. Util [Camada de utilitários]
          4. Validators [Camada de validações]
    5. Infra [Camada de infraestrutura - Infraestructure]
```
  ├── controller
  ├── domain
  │   ├── annotation
  │   │   ├── http
  │   ├── constant
  │   │   ├── divider
  │   │   ├── formatter
  │   │   ├── http
  │   │   │    ├── code
  │   │   │    ├── message
  │   │   │    ├── type
  │   ├── converter
  │   ├── dto
  │   │   ├── request
  │   │   └── response
  │   ├── enumeration
  │   └── exception
  │   │   ├── handler
  |   |   |   └── http
  |   |   |   └── util
  │   │   ├── http
  │   ├── model
  │   ├── repository
  ├── service
  │   ├── dto
  │   ├── model
  │   ├── util
  │   ├── validator
  ├── infra
  │   ├── config
```

## Modelagem

![Screenshot 2024-08-06 224607](https://github.com/user-attachments/assets/9d6d48c2-21c1-4d51-9e98-6cd69015939c)

## Configuração

### Docker
1. Clone o repósitorio
2. Instale o docker (https://www.docker.com/products/docker-desktop/)
3. Abra o docker
4. Abra o terminal
5. Navegue até api_pagamento_2024
6. Digite docker-compose up -d
    1. A imagem do postgresql será executada, ou seja, se tornará um container
    2. O jar da aplicaçào será gerado, executado e inserido em uma imagem
    3. A imagem da aplicação será executada, ou seja, se tornará um container

## Execução

### Docker
1. A api está disponível no http://localhost:8081/transacao/v1
2. Se o código for modificado:
    - Digite docker-compose build --no-cache para uma nova imagem da aplicação ser gerada

### Spring Boot

#### Ide
1. Abra a pasta api_pagamento_2024 em uma IDE (Ex: IntelliJ IDEA) 
2. Navegue pela IDE até ApiPagamentoApplication
3. Aperte o botão play localizado ao lado de "public class ApiPagamentoApplication"
4. A api está disponível no http://localhost:8080/transacao/v1

#### Mvn
1. Abra o cmd
2. Navegue até a pasta api_pagamento_2024
3. Rode o comando ./mvnw spring-boot:run
4. A api está disponível no http://localhost:8080/transacao/v1

## Utilização

### Swagger
 - Os endpoints documentados para testes estão disponíveis no host:
    -  Docker: http://localhost:8081/swagger-ui/index.html
    -  Spring Boot: http://localhost:8080/swagger-ui/index.html
  
### Postman
- Os endpoints para testes estão disponíveis na collection do postman: api_pagamento.postman_collection.json
- Para usar a collection:
    - Acesse https://www.postman.com/
    - Realize o login
    - Baixe o postman agent
    - Importe a collection
    - Faça a requisição desejada
      
### EndPoints

#### Realizar pagamento
- Endpoint: POST {{host}}/transacao/v1/pagar
- Validações:
  1. Valida se não falta nenhum campo
  2. Valida se todos os campos foram preenchidos
  3. Valida se o campo cartao tem 16 caracteres
  4. Valida se o campo valor é maior do que 0
  5. Valida se o campo parcelas é maior do que 0
  6. Valida se o valor do campo descricao.dataHora corresponde ao formato "01/01/2000 01:01:01"
  7. Valida se o valor do campo formaPagamento.tipo é válido [AVISTA, PARCELADO_LOJA, PARCELADO_EMISSOR]
  8. Valida se o valor da parcela é 1 se o tipo de pagamento for AVISTA
- Regras de negócio:
  1. Nsu é um número gerado randomicamente
  2. Código de transação é um número gerado randomicamente
  3. O status é determinado randomicamente entre duas opções [AUTORIZADO, NEGADO] 
- Request:
```
  { 
   "cartao": "4444********1234",
   "descricao": {
      "valor": "500.55",
      "dataHora": "01/05/2021 18:00:00",
      "estabelecimento": "PetShop Mundo cão"
    },
    "formaPagamento":{
        "tipo":"AVISTA",
        "parcelas": "1"
    }
  }
```
- Response:
```
  {
    "id": 1,
    "cartao": "4444********1234",
    "descricao": {
        "valor": "500.55",
        "dataHora": "01/05/2021 18:00:00",
        "estabelecimento": "PetShop Mundo cão",
        "nsu": "1234567890",
        "codigoAutorizacao": "147258369",
        "status": "AUTORIZADO"
    },
    "formaPagamento": {
        "tipo": "AVISTA",
        "parcelas": "1"
    }
  }
```
  
#### Realizar estorno
- Endpoint: PUT {{host}}/transacao/v1/estornar/{{id}
- Validações:
  1. Valida se a transação que será estornada existe
  2. Valida se a transação não foi estornada anteriormente
  3. Valida se a transação não foi cancelada anteriormente
- Response:   
```
  {
    "id": 1,
    "cartao": "4444********1234",
    "descricao": {
        "valor": "500.55",
        "dataHora": "01/05/2021 18:00:00",
        "estabelecimento": "PetShop Mundo cão",
        "nsu": "1234567890",
        "codigoAutorizacao": "147258369",
        "status": "NEGADO"
    },
    "formaPagamento": {
        "tipo": "AVISTA",
        "parcelas": "1"
    }
  }
```
  
#### Buscar transacao
- Endpoint: GET {{host}}/transacao/v1/buscar/{{id}}
- Response:
```
  {
    "id": 1,
    "cartao": "4444********1234",
    "descricao": {
        "valor": "500.55",
        "dataHora": "01/05/2021 18:00:00",
        "estabelecimento": "PetShop Mundo cão",
        "nsu": "1234567890",
        "codigoAutorizacao": "147258369",
        "status": "NEGADO"
    },
    "formaPagamento": {
        "tipo": "AVISTA",
        "parcelas": "1"
    }
  }
```
  
#### Buscar transações
- Endpoint: GET {{host}}/transacao/v1/listar
- Response:
```
  [
    {
        "id": 1,
        "cartao": "4444********1234",
        "descricao": {
            "valor": "500.55",
            "dataHora": "01/05/2021 18:00:00",
            "estabelecimento": "PetShop Mundo cão",
            "nsu": "1234567890",
            "codigoAutorizacao": "147258369",
            "status": "NEGADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    },
    {
        "id": 2,
        "cartao": "4444********1234",
        "descricao": {
            "valor": "500.55",
            "dataHora": "01/05/2021 18:00:00",
            "estabelecimento": "PetShop Mundo cão",
            "nsu": "1234567890",
            "codigoAutorizacao": "147258369",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": "1"
        }
    }
  ]
```

## Testes 

### Testes Unitários
- Para garantir que o fluxo do controller e do service estão corretos, testes unitários foram feitos para simulá-los

##### Controller
- QuandoUmaTransacaoEhSolicitadaElaDeveSerRealizada
- QuandoUmaTransacaoEhBuscadaPeloIdENaoEhEncontradaUmaExcecaoDeveSerRetornada
- QuandoUmPagamentoEhSolicitadoComCamposPreenchidosIncorretamenteUmaExcecaoDeveSerRetornada
- QuandoUmaTransacaoEhBuscadaPeloIdElaDeveSerRetornada
- QuandoTransacoesSaoBuscadasElasDevemSerRetornadas
- QuandoUmEstornoEhSolicitadoEleEhRealizado
    
##### Service
- QuandoUmaTransacaoEhSolicitadaElaDeveSerRealizada
- QuandoUmaTransacaoEhBuscadaPeloIdENaoEhEncontradaUmaExcecaoDeveSerRetornada
- QuandoUmPagamentoEhSolicitadoComTipoAvistaEMaisDeUmaParcelaUmaExcecaoDeveSerRetornada
- QuandoUmaTransacaoEhBuscadaPeloIdElaDeveSerRetornada
- QuandoTransacoesSaoBuscadasElasDevemSerRetornadas
- QuandoUmEstornoEhSolicitadoEleEhRealizado

### Execução

#### Ide
  - Aperte o botão play localizado ao lado de:
    - "public class TransacaoDtoControllerTest"
    - "public class TransacaoDtoServiceTest"

#### Mvn
 - Abra o cmd
 - Navegue até a pasta api_pagamento_2024
 - Rode o comando ./mvnw spring-boot:test
