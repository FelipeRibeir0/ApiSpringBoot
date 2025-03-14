<h1 align="center">
<br />
  <img
    src="./_docs/assets/icon.png"
    alt="API SPRING BOOT"
    width="150"
  />
  <br />
  <b>API de Gerenciamento de Produtos</b>
  <br />
  <sub><sup><b>(PRODUCTS-API)</b></sup></sub>
  <br />
</h1>

<p align="center">
  Uma API RESTful desenvolvida com Spring Boot para gerenciamento de produtos. Essa aplicação foi projetada para operações básicas de CRUD (Create, Read, Update e Delete) utilizando Java 21, banco de dados MySQL e as melhores práticas do Spring Framework.
</p>

<p align="center">
  <br />
  <img src="./_docs/assets/api_preview.png" alt="API Preview" 
height="350"   
/>
</p>

---

## 🚀 Descrição Geral

Essa API permite:
- Criar novos produtos no sistema.
- Buscar todos os produtos, buscar um produto específico por ID ou pelo nome.
- Atualizar total ou parcialmente produtos existentes.
- Excluir produtos do banco de dados.
- Criar usuários e autenticar-se via JWT.

Foi implementada usando Spring Data JPA para abstrair as operações de banco de dados e um banco persistente MySQL. A API é totalmente documentada no arquivo README e no Swagger.

---

## 🛠️ Autenticação com JWT

A API utiliza **JSON Web Token (JWT)** para autenticação e autorização. Para acessar endpoints protegidos, siga os passos abaixo:

### 1️⃣ Criar um novo usuário

Faça uma requisição **POST** para `/auth/signup` enviando um JSON com os dados do usuário:

```json
{
  "name": "Usuário Teste",
  "email": "usuario@email.com",
  "password": "senha123",
  "role": "CLIENT"
}
```

Se bem-sucedido, o usuário será criado e retornado no response.

### 2️⃣ Autenticar e obter um token JWT

Faça uma requisição **POST** para `/auth/login` enviando email e senha:

```json
{
  "email": "usuario@email.com",
  "password": "senha123"
}
```

A resposta conterá um token JWT:

```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 3️⃣ Usar o token para acessar endpoints protegidos

Inclua o token no **Header** da requisição:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

Agora você pode acessar os endpoints protegidos da API.

---

# 📄 Documentação da API

Este projeto disponibiliza uma API que pode ser acessada e testada diretamente através da documentação gerada pelo Swagger.

## Acessando a documentação Swagger

Após rodar a aplicação, a documentação do Swagger estará disponível na seguinte URL:
[Swagger](http://localhost:8080/documentacao)

### Passos para acessar a documentação:

1. Certifique-se de que o projeto está rodando localmente ou em um ambiente de desenvolvimento.
2. Abra seu navegador e acesse a URL: http://localhost:8080/documentacao
3. Você verá a interface gráfica do Swagger UI, onde poderá explorar todos os endpoints da API e fazer chamadas de teste diretamente pela interface.

---

## 🌐 Endpoints da API

### 🔐 Autenticação

| Método | Endpoint         | Descrição |
|--------|-----------------|-----------|
| POST   | `/auth/signup`  | Cria um novo usuário |
| POST   | `/auth/login`   | Autentica o usuário e retorna um token JWT |

### 🛒 Gerenciamento de Produtos

| Método | Endpoint          | Descrição |
|--------|------------------|-----------|
| GET    | `/products`      | Retorna a lista de todos os products. Aceita um parâmetro opcional de busca por nome. |
| GET    | `/products/{id}` | Retorna os detalhes de um product específico pelo ID. |
| GET    | `/products/search`| Retorna os detalhes de um produto específico pelo Nome. |
| POST   | `/products`      | Cria um novo product no sistema (requer autenticação). |
| PUT    | `/products/{id}` | Atualiza completamente as informações de um product existente (requer autenticação). |
| PATCH  | `/products/{id}` | Atualiza parcialmente as informações de um product existente (requer autenticação). |
| DELETE | `/products/{id}` | Exclui um product do sistema pelo ID (requer autenticação). |

---

## 🛠️ Técnicas e Tecnologias Utilizadas

  - <b>Spring Boot:</b> Framework principal da aplicação.<br />
  - <b>Spring Security:</b> Implementação de autenticação e autorização.<br />
  - <b>JWT (JSON Web Token):</b> Gerenciamento de autenticação.<br />
  - <b>Spring Data JPA:</b> Gerenciamento de dados usando ORM.<br />
  - <b>Banco de Dados MySQL:</b> Banco de dados persistente.<br />
  - <b>Java 21:</b> Linguagem utilizada.<br />

---

## 📂 Estrutura do Projeto

```🌐
src
├── main/
│   ├── 📂 java/com/productsAPI/
│   │   ├── 📂 Controller        [Definições de rotas e lógica básica da API]
│   │   ├── 📂 Service           [Regras de negócio e validações]
│   │   ├── 📂 Model             [Modelos de dados (Entidades)]
│   │   ├── 📂 Repository        [Repositórios para abstração de acesso ao banco de dados]
│   └── 📂 resources/
│       └── 📂 static/ 
```

# Considerações Finais

Esta API foi desenvolvida como um exemplo prático para quem está aprendendo Spring Boot. Ela é simples, funcional e facilmente extensível.

Possíveis melhorias futuras:

- 🧪 Adicionar testes unitários e de integração.

