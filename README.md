
# Documentação da Aplicação

## Versões do Java e Spring Boot
- **Versão do Java:** 22
- **Versão do Spring Boot:** 3.2.8

## Gerenciamento de Dependências
- **Gerenciador de Dependências:** Maven

## Executando a Aplicação

### Execução Local
- Para executar localmente utilizando sua IDE de preferência, execute o script shell `run_docker_compose_local.sh`.
  Este script criará um container com o banco de dados MySQL.

### Execução Completa com Docker
- Para subir a aplicação totalmente via Docker, execute o script shell `run_docker_compose_prd.sh`.

**Nota:** Ambos os métodos requerem que o Docker esteja instalado em sua máquina.

## Principais Funcionalidades

### 1. Registro de Cliente
- **Endpoint:** `/api/cliente`
- **Método:** POST
- **Corpo da Requisição:**
  ```
  {
      "cpf":"1111111111",
      "nome":"João da Silva",
      "email":"joao@example.com",
      "telefone":"+55 11 91234-5678",
      "rua":"Rua A",
      "cidade":"Cidade",
      "estado":"Estado",
      "cep":"12345-678",
      "pais":"Brasil"
  }
  ```
- **Respostas:**
    - 200: `{"id_cliente":"XXXXXX"}`
    - 401: Não autorizado
    - 500: Erro de negócio
- **Autenticação:** Requer autenticação JWT.

### 2. Geração de Cartão
- **Endpoint:** `/api/cartao`
- **Método:** POST
- **Corpo da Requisição:**
  ```
  {
      "cpf":"1111111111",
      "limite":1000,
      "numero":"**** **** **** 1234",
      "data_validade":"12/24",
      "cvv":"123"
  }
  ```
- **Respostas:**
    - 200: Sucesso
    - 401: Não autorizado
    - 403: Número máximo de cartões atingido
    - 500: Erro de negócio
- **Autenticação:** Requer autenticação JWT.

### 3. Registro de Pagamento
- **Endpoint:** `/api/pagamentos`
- **Método:** POST
- **Corpo da Requisição:**
  ```
  {
      "cpf":"1111111111",
      "numero":"**** **** **** 1234",
      "data_validade":"12/24",
      "cvv":"123",
      "valor":100.00
  }
  ```
- **Respostas:**
    - 200: `{"chave_pagamento":"XXXXXX"}`
    - 401: Não autorizado
    - 402: Limite do cartão excedido
    - 500: Erro de negócio
- **Autenticação:** Requer autenticação JWT.

### 4. Consulta de Pagamentos por Cliente
- **Endpoint:** `/api/pagamentos/cliente/{Chave}`
- **Método:** GET
- **Respostas:**
    - 200: `[{"valor":100.00, "descricao":"Compra de produto X", "metodo_pagamento":"cartao_credito", "status":"aprovado"}]`
    - 401: Não autorizado
    - 500: Erro de negócio
- **Autenticação:** Requer autenticação JWT.

### 5. Autenticação
- **Endpoint:** `/api/autenticacao`
- **Método:** POST
- **Corpo da Requisição:**
  ```
  {
      "usuario":xxxxx,
      "senha":"XXXX"
  }
  ```
- **Respostas:**
    - 200: `{"token":"XXXXXXXXX"}`
    - 401: Não autorizado
    - 500: Erro de negócio

