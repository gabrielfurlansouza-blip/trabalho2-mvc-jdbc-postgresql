# Cenário 3 – Sistema de Escola de Cursos Livres

## Tabelas Identificadas

### Tabela: `alunos`
| Campo    | Tipo          | Restrição   | Observação             |
|----------|---------------|-------------|------------------------|
| id       | SERIAL        | PRIMARY KEY | Gerado automaticamente |
| nome     | VARCHAR(100)  | NOT NULL    |                        |
| email    | VARCHAR(100)  |             |                        |
| telefone | VARCHAR(20)   |             |                        |

### Tabela: `cursos`
| Campo              | Tipo          | Restrição   | Observação                        |
|--------------------|---------------|-------------|-----------------------------------|
| id                 | SERIAL        | PRIMARY KEY | Gerado automaticamente            |
| nome               | VARCHAR(100)  | NOT NULL    |                                   |
| descricao          | TEXT          |             |                                   |
| carga_horaria      | INTEGER       |             | Em horas                          |
| vagas_totais       | INTEGER       | NOT NULL    | Capacidade máxima do curso        |
| vagas_disponiveis  | INTEGER       | NOT NULL    | Decrementado a cada matrícula     |

### Tabela: `matriculas`
| Campo           | Tipo          | Restrição                           | Observação                        |
|-----------------|---------------|-------------------------------------|-----------------------------------|
| id              | SERIAL        | PRIMARY KEY                         | Gerado automaticamente            |
| id_aluno        | INTEGER       | FK → alunos(id)                     | Aluno matriculado                 |
| id_curso        | INTEGER       | FK → cursos(id)                     | Curso em que o aluno se matricula |
| data_matricula  | DATE          | NOT NULL                            |                                   |
| valor           | DECIMAL(10,2) | NOT NULL                            | Não pode ser negativo             |
|                 |               | UNIQUE(id_aluno, id_curso)          | Impede matrícula duplicada        |

## Comandos SQL – Criação das Tabelas

```sql
CREATE TABLE alunos (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    email    VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE cursos (
    id                 SERIAL PRIMARY KEY,
    nome               VARCHAR(100) NOT NULL,
    descricao          TEXT,
    carga_horaria      INTEGER,
    vagas_totais       INTEGER NOT NULL,
    vagas_disponiveis  INTEGER NOT NULL
);

CREATE TABLE matriculas (
    id              SERIAL PRIMARY KEY,
    id_aluno        INTEGER REFERENCES alunos(id),
    id_curso        INTEGER REFERENCES cursos(id),
    data_matricula  DATE NOT NULL,
    valor           DECIMAL(10,2) NOT NULL,
    UNIQUE (id_aluno, id_curso)
);
```

## Regras de Negócio

1. **Aluno obrigatório**: Não é permitido matricular um aluno que não esteja cadastrado.
2. **Curso obrigatório**: Não é permitido matricular em um curso que não esteja cadastrado.
3. **Sem vagas disponíveis**: Não é permitido matricular em um curso que já atingiu o limite de vagas.
4. **Matrícula duplicada**: O mesmo aluno não pode ser matriculado duas vezes no mesmo curso.
5. **Valor não negativo**: O valor da matrícula não pode ser negativo.
6. **Controle de vagas**: Ao matricular, `vagas_disponiveis` é decrementado; ao cancelar, é incrementado.
7. **Consulta alunos por curso**: O sistema deve permitir listar todos os alunos matriculados em um curso.
8. **Consulta cursos por aluno**: O sistema deve permitir listar todos os cursos em que um aluno está matriculado.
9. **Nome do aluno obrigatório**: O campo nome do aluno é obrigatório no cadastro.
10. **Vagas totais positivas**: O número de vagas de um curso deve ser maior que zero.

## Estrutura do Projeto (Padrão MVC)

```
src/main/java/com/escola/
├── model/
│   ├── Aluno.java
│   ├── Curso.java
│   └── Matricula.java
├── repository/
│   ├── AlunoRepository.java
│   ├── CursoRepository.java
│   └── MatriculaRepository.java
├── service/
│   ├── AlunoService.java
│   ├── CursoService.java
│   └── MatriculaService.java
├── controller/
│   ├── AlunoController.java
│   ├── CursoController.java
│   └── MatriculaController.java
├── util/
│   └── Conexao.java
└── Main.java
```
