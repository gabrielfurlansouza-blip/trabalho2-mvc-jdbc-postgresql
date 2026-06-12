# Cenário 1 – Sistema de Clínica Veterinária

## Tabelas Identificada

### Tabela: `tutores`
| Campo    | Tipo          | Restrição  | Observação              |
|----------|---------------|------------|-------------------------|
| id       | SERIAL        | PRIMARY KEY| Gerado automaticamente  |
| nome     | VARCHAR(100)  | NOT NULL   |                         |
| endereco | VARCHAR(200)  |            |                         |
| telefone | VARCHAR(20)   |            |                         |

### Tabela: `animais`
| Campo    | Tipo          | Restrição         | Observação                        |
|----------|---------------|-------------------|-----------------------------------|
| id       | SERIAL        | PRIMARY KEY       | Gerado automaticamente            |
| nome     | VARCHAR(100)  | NOT NULL          |                                   |
| especie  | VARCHAR(50)   |                   |                                   |
| raca     | VARCHAR(50)   |                   |                                   |
| id_tutor | INTEGER       | FK → tutores(id)  | Vinculado ao tutor                |

### Tabela: `consultas`
| Campo     | Tipo           | Restrição         | Observação                    |
|-----------|----------------|-------------------|-------------------------------|
| id        | SERIAL         | PRIMARY KEY       | Gerado automaticamente        |
| id_animal | INTEGER        | FK → animais(id)  | Animal que foi atendido       |
| data      | DATE           | NOT NULL          |                               |
| motivo    | TEXT           |                   |                               |
| valor     | DECIMAL(10,2)  | NOT NULL          | Não pode ser negativo         |

## Comandos SQL – Criação das Tabelas

```sql
CREATE TABLE tutores (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

CREATE TABLE animais (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    especie   VARCHAR(50),
    raca      VARCHAR(50),
    id_tutor  INTEGER REFERENCES tutores(id)
);

CREATE TABLE consultas (
    id        SERIAL PRIMARY KEY,
    id_animal INTEGER REFERENCES animais(id),
    data      DATE NOT NULL,
    motivo    TEXT,
    valor     DECIMAL(10,2) NOT NULL
);
```

## Regras de Negócio

1. **Animal obrigatório para consulta**: Não é permitido registrar uma consulta para um animal que não esteja cadastrado no sistema.
2. **Valor não negativo**: O valor da consulta não pode ser negativo.
3. **Tutor obrigatório para animal**: Não é permitido cadastrar um animal sem vinculá-lo a um tutor já cadastrado.
4. **Nome do tutor obrigatório**: O campo nome do tutor é obrigatório no cadastro.
5. **Nome do animal obrigatório**: O campo nome do animal é obrigatório no cadastro.
6. **Histórico por animal**: O sistema deve permitir consultar todas as consultas de um animal específico.
7. **Animais por tutor**: O sistema deve permitir listar todos os animais cadastrados de um determinado tutor.

## Estrutura do Projeto (Padrão MVC)

```
src/main/java/com/clinica/
├── model/
│   ├── Tutor.java
│   ├── Animal.java
│   └── Consulta.java
├── repository/
│   ├── TutorRepository.java
│   ├── AnimalRepository.java
│   └── ConsultaRepository.java
├── service/
│   ├── TutorService.java
│   ├── AnimalService.java
│   └── ConsultaService.java
├── controller/
│   ├── TutorController.java
│   ├── AnimalController.java
│   └── ConsultaController.java
├── util/
│   └── Conexao.java
└── Main.java
```
