# **API de Gerenciamento de Produtos**

## **Descrição Geral**
Esta API foi desenvolvida em **Java 21** com **Spring Boot** para gerenciar produtos. Ela oferece endpoints RESTful que permitem realizar operações CRUD completas (Criar, Ler, Atualizar, Excluir) em um banco de dados em memória (**H2**).

⚠️ **Nota**: A API não inclui autenticação ou segurança, sendo destinada para fins de aprendizado e testes.

---

## **Configuração do Ambiente**

### Pré-requisitos
- **Java 21** instalado
- **Maven** instalado
- IDE recomendada: **IntelliJ IDEA** (opcional)

### Configuração Inicial
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/gerenciamento-produtos.git
   cd gerenciamento-produtos
   ```

2. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

3. A API estará disponível em:
   ```
   http://localhost:8080
   ```

4. Para acessar o console do banco de dados H2:
   ```
   http://localhost:8080/h2-console
   ```
    - **JDBC URL**: `jdbc:h2:mem:produtosdb`
    - **Usuário**: `sa`
    - **Senha**: *(deixe vazio)*

---

## **Endpoints Disponíveis**

### **Base URL**
```
http://localhost:8080/produtos
```

### 1️⃣ **Criar Produto**
**Endpoint**: `POST /produtos`  
**Descrição**: Adiciona um novo produto ao banco de dados.  
**Cabeçalhos**:
- `Content-Type`: `application/json`

**Body**:
```json
{
  "nome": "Notebook",
  "preco": 2500.0,
  "quantidade": 10
}
```

**Resposta** (201 Created):
```json
{
  "id": 1,
  "nome": "Notebook",
  "preco": 2500.0,
  "quantidade": 10
}
```

---

### 2️⃣ **Listar Todos os Produtos**
**Endpoint**: `GET /produtos`  
**Descrição**: Retorna todos os produtos cadastrados.

**Resposta** (200 OK):
```json
[
  {
    "id": 1,
    "nome": "Notebook",
    "preco": 2500.0,
    "quantidade": 10
  },
  {
    "id": 2,
    "nome": "Mouse",
    "preco": 100.0,
    "quantidade": 50
  }
]
```

---

### 3️⃣ **Buscar Produtos por Nome**
**Endpoint**: `GET /produtos?nome={nome}`  
**Descrição**: Retorna produtos que contenham o nome informado (não diferencia maiúsculas de minúsculas).

**Exemplo de Requisição**:
```
GET /produtos?nome=notebook
```

**Resposta** (200 OK):
```json
[
  {
    "id": 1,
    "nome": "Notebook",
    "preco": 2500.0,
    "quantidade": 10
  }
]
```

**Resposta se nenhum produto for encontrado** (404 Not Found):
```json
{
  "error": "No products found with the given name."
}
```

---

### 4️⃣ **Buscar Produto por ID**
**Endpoint**: `GET /produtos/{id}`  
**Descrição**: Retorna o produto correspondente ao ID informado.

**Exemplo de Requisição**:
```
GET /produtos/1
```

**Resposta** (200 OK):
```json
{
  "id": 1,
  "nome": "Notebook",
  "preco": 2500.0,
  "quantidade": 10
}
```

**Resposta se o produto não for encontrado** (404 Not Found):
```json
{
  "error": "Product not found."
}
```

---

### 5️⃣ **Atualizar Produto (Completo)**
**Endpoint**: `PUT /produtos/{id}`  
**Descrição**: Atualiza completamente os dados de um produto com base no ID.

**Body**:
```json
{
  "nome": "Notebook Gamer",
  "preco": 3000.0,
  "quantidade": 5
}
```

**Resposta** (200 OK):
```json
{
  "id": 1,
  "nome": "Notebook Gamer",
  "preco": 3000.0,
  "quantidade": 5
}
```

---

### 6️⃣ **Atualizar Produto (Parcial)**
**Endpoint**: `PATCH /produtos/{id}`  
**Descrição**: Atualiza parcialmente os dados de um produto com base no ID.

**Body**:
```json
{
  "preco": 2800.0
}
```

**Resposta** (200 OK):
```json
{
  "id": 1,
  "nome": "Notebook Gamer",
  "preco": 2800.0,
  "quantidade": 5
}
```

---

### 7️⃣ **Excluir Produto**
**Endpoint**: `DELETE /produtos/{id}`  
**Descrição**: Remove o produto correspondente ao ID informado.

**Exemplo de Requisição**:
```
DELETE /produtos/1
```

**Resposta** (204 No Content):  
Nenhum corpo na resposta.

**Resposta se o produto não for encontrado** (404 Not Found):
```json
{
  "error": "Product not found."
}
```

---

## **Modelo de Produto**

O modelo usado na API é definido pela classe `Produto`:
```java
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;

    // Getters e Setters
}
```

---

## **Estrutura do Projeto**
O projeto segue uma estrutura padrão para aplicações Spring Boot:
```
src/
├── main/
│   ├── java/com/firstAPI/
│   │   ├── controller/        # Controllers da API
│   │   ├── model/             # Modelos de dados (entidades)
│   │   ├── repository/        # Interfaces de repositórios (JPA)
│   │   └── service/           # Regras de negócio
│   └── resources/
│       └── application.properties  # Configurações da aplicação
```

---

## **Considerações Finais**
Esta API foi desenvolvida como um exemplo prático para quem está aprendendo Spring Boot. Ela é simples, funcional e facilmente extensível.

Possíveis melhorias futuras:
- Adicionar autenticação com JWT.
- Migrar para um banco de dados persistente (PostgreSQL, MongoDB, MySQL).
- Adicionar testes unitários e de integração.
- Implementar o Swagger na documentação.