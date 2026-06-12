# Cenário 2 – Sistema de Oficina Mecânica

## Tabelas Identificadas

### Tabela: `clientes`
| Campo    | Tipo          | Restrição   | Observação             |
|----------|---------------|-------------|------------------------|
| id       | SERIAL        | PRIMARY KEY | Gerado automaticamente |
| nome     | VARCHAR(100)  | NOT NULL    |                        |
| telefone | VARCHAR(20)   |             |                        |

### Tabela: `veiculos`
| Campo      | Tipo         | Restrição          | Observação                     |
|------------|--------------|--------------------|--------------------------------|
| id         | SERIAL       | PRIMARY KEY        | Gerado automaticamente         |
| placa      | VARCHAR(10)  | NOT NULL, UNIQUE   | Identificador único do veículo |
| modelo     | VARCHAR(100) |                    |                                |
| ano        | INTEGER      |                    |                                |
| id_cliente | INTEGER      | FK → clientes(id)  | Vinculado ao cliente           |

### Tabela: `ordens_servico`
| Campo      | Tipo          | Restrição          | Observação                    |
|------------|---------------|--------------------|-------------------------------|
| id         | SERIAL        | PRIMARY KEY        | Gerado automaticamente        |
| id_veiculo | INTEGER       | FK → veiculos(id)  | Veículo que entrou na oficina |
| descricao  | TEXT          |                    | Problema relatado             |
| valor      | DECIMAL(10,2) | NOT NULL           | Não pode ser negativo         |
| status     | VARCHAR(20)   | DEFAULT 'ABERTA'   | ABERTA ou CONCLUIDA           |

## Comandos SQL – Criação das Tabelas

```sql
CREATE TABLE clientes (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE veiculos (
    id         SERIAL PRIMARY KEY,
    placa      VARCHAR(10) NOT NULL UNIQUE,
    modelo     VARCHAR(100),
    ano        INTEGER,
    id_cliente INTEGER REFERENCES clientes(id)
);

CREATE TABLE ordens_servico (
    id         SERIAL PRIMARY KEY,
    id_veiculo INTEGER REFERENCES veiculos(id),
    descricao  TEXT,
    valor      DECIMAL(10,2) NOT NULL,
    status     VARCHAR(20) NOT NULL DEFAULT 'ABERTA'
);
```

## Regras de Negócio

1. **Veículo obrigatório para OS**: Não é permitido abrir uma ordem de serviço para um veículo que não esteja cadastrado.
2. **Valor não negativo**: O valor do serviço não pode ser negativo.
3. **Cliente obrigatório para veículo**: Não é permitido cadastrar um veículo sem vinculá-lo a um cliente já cadastrado.
4. **Nome do cliente obrigatório**: O campo nome do cliente é obrigatório no cadastro.
5. **Placa obrigatória e única**: A placa é obrigatória e não pode se repetir no sistema.
6. **Status da OS**: A ordem de serviço nasce com status ABERTA e pode ser concluída (CONCLUIDA).
7. **Histórico por veículo**: O sistema deve permitir consultar todas as ordens de serviço de um veículo específico.
8. **Veículos por cliente**: O sistema deve permitir listar todos os veículos de um determinado cliente.

## Como Executar

### Pré-requisitos
- Java 17+
- PostgreSQL 14+

### 1. Criar o banco de dados
Conecte ao PostgreSQL e execute:
```sql
CREATE DATABASE oficina_db;
```

### 2. Criar as tabelas
Execute os comandos `CREATE TABLE` listados acima no banco `oficina_db`.

### 3. Configurar a conexão
Edite `src/main/java/com/oficina/util/Conexao.java` com suas credenciais:
```java
private static final String URL  = "jdbc:postgresql://localhost:5432/oficina_db";
private static final String USER = "postgres";
private static final String PASSWORD = "sua_senha";
```

### 4. Compilar e executar
```bash
mvn compile exec:java -Dexec.mainClass="com.oficina.Main"
```

---

## Estrutura do Projeto (Padrão MVC)

```
src/main/java/com/oficina/
├── model/
│   ├── Cliente.java
│   ├── Veiculo.java
│   └── OrdemServico.java
├── repository/
│   ├── ClienteRepository.java
│   ├── VeiculoRepository.java
│   └── OrdemServicoRepository.java
├── service/
│   ├── ClienteService.java
│   ├── VeiculoService.java
│   └── OrdemServicoService.java
├── controller/
│   ├── ClienteController.java
│   ├── VeiculoController.java
│   └── OrdemServicoController.java
├── util/
│   └── Conexao.java
└── Main.java
```
