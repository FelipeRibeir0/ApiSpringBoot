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
Uma API RESTful robusta, desenvolvida com Spring Boot, para o gerenciamento eficiente de produtos. Projetada para realizar operações completas de CRUD (Create, Read, Update e Delete), a aplicação utiliza Java 21, MySQL como banco de dados e segue as melhores práticas do Spring Framework, garantindo desempenho, segurança e escalabilidade.</p>

<p align="center">
  <br />
  <img src="./_docs/assets/api_preview.png" alt="API Preview" 
height="350"   
/>
</p>
---

## 🚀 Funcionalidades

- Cadastro e autenticação de usuários
- ✅ **Gerenciamento de produtos**: criação, leitura, atualização e remoção.
- 🔍 **Busca de produtos por nome ou ID**.
- 🔑 **Autenticação e autorização com JWT**.
- 👥 **Gerenciamento de usuários** (cadastro, login e promoção para ADMIN).
- 📜 **Documentação interativa com Swagger**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem principal do projeto.
- **Spring Boot** - Framework para desenvolvimento eficiente.
- **Spring Security + JWT** - Autenticação e autorização seguras.
- **Spring Data JPA** - Persistência de dados simplificada.
- **MySQL** - Banco de dados relacional.
- **Swagger** - Documentação interativa da API.
- **JUnit e Mockito** - (Testes unitários)

---

## 🔐 Autenticação com JWT

A API utiliza **JSON Web Token (JWT)** para proteger endpoints.

### 1️⃣ Criar um usuário
**POST** `/auth/signup`

```json
{
  "name": "Usuário Teste",
  "email": "usuario@email.com",
  "password": "senha123",
  "role": "CLIENT"
}
```

### 2️⃣ Login e obtenção do token
**POST** `/auth/login`

```json
{
  "email": "usuario@email.com",
  "password": "senha123"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3️⃣ Acessar endpoints protegidos
Inclua o token no **Header**:
```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📄 Documentação Swagger

A API conta com uma documentação interativa via Swagger. Para acessá-la:

📌 **URL:** [Swagger UI](http://localhost:8080/documentacao)

1. Inicie a aplicação.
2. Acesse o link acima no navegador.
3. Explore e teste os endpoints diretamente na interface.

---

## 🌐 Endpoints Principais

### 🔐 Autenticação

| Método | Endpoint         | Descrição |
|--------|-----------------|-----------|
| POST   | `/auth/signup`  | Cria um novo usuário |
| POST   | `/auth/login`   | Retorna um token JWT após login |

### 🛒 Produtos

| Método | Endpoint          | Descrição |
|--------|------------------|-----------|
| GET    | `/products`      | Lista todos os produtos (com busca por nome opcional) |
| GET    | `/products/{id}` | Busca um produto pelo ID |
| POST   | `/products`      | Cadastra um novo produto (requer ADMIN) |
| PUT    | `/products/{id}` | Atualiza um produto por completo (requer ADMIN) |
| PATCH  | `/products/{id}` | Atualiza parcialmente um produto (requer ADMIN) |
| DELETE | `/products/{id}` | Exclui um produto (requer ADMIN) |

### 🔧 Administração

| Método | Endpoint                   | Descrição |
|--------|---------------------------|-----------|
| PUT    | `/admin/promote/{userId}` | Promove um usuário para ADMIN (requer ADMIN) |

---

## 📂 Estrutura do Projeto

```plaintext
src/
├── main/java/com/productsAPI/
│   ├── 📂 Controller   -> Definições de rotas e lógica básica da API
│   ├── 📂 Service      -> Regras de negócio e validações
│   ├── 📂 Model        -> Modelos de dados (Entidades)
│   ├── 📂 Repository   -> Acesso ao banco de dados
│   ├── 📂 Security     -> Configuração da autenticação JWT
│   ├── 📂 DTO          -> Transferência de dados entre camadas
└── resources/
    ├── application.properties  -> Configuração do banco e autenticação
    └── static/
```

---

## 📌 Como Executar o Projeto

### ✅ Pré-requisitos

- **Java 21** instalado
- **MySQL** rodando localmente
- **Maven** configurado

### ▶️ Rodando a API

1. Clone o repositório:
   ```bash
   git clone https://github.com/FelipeRibeir0/ApiSpringBoot.git
   ```
2. Configure o banco MySQL no `application.properties`.
3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse `http://localhost:8080/documentacao` para explorar a API no Swagger.

---

## 📌 Melhorias Futuras

- ✅ Ampliar a cobertura de **testes unitários** para garantir **mais robustez** ao código.
- ✅ Implementar **testes de integração** para validar a comunicação entre os componentes.
- ✅ Criar um **deploy online** para facilitar demonstrações da API.
- ✅ Melhorar a estrutura dos **DTOs** para maior separação de responsabilidades.
- ✅ Adicionar logs com **Spring Boot Actuator** para melhor monitoramento da aplicação.

---

## 🤝 Contribuições

Sugestões **são bem-vindas!** Sinta-se à vontade para abrir um issue.

### 📌 **Autor:** [Felipe Ribeiro](https://github.com/FelipeRibeir0)

📧 **Contato:** felipecorreiaribeiro@email.com
[Meu Linkedin](https://www.linkedin.com/in/felipe-correia-ribeiro/)
---

## 📌 **Sobre o Projeto**

Este projeto foi desenvolvido como um estudo prático de Spring Boot, aplicando conceitos essenciais de APIs RESTful, autenticação e segurança. Ele pode ser expandido com novas funcionalidades, como cache, testes de integração e melhorias de performance.
