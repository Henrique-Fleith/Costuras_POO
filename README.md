# CosturaControl

Sistema desktop em Java Swing para uma costureira controlar o estoque de tecidos, cadastrar clientes e acompanhar os serviços de costura realizados.

**Tecnologias:** Java Swing, padrão MVC (com DAO), banco de dados relacional (MySQL/PostgreSQL/SQLite).

## Funcionalidades

- Cadastro de tecidos com controle de quantidade em estoque (metros)
- Registro de entradas e saídas de tecido
- Cadastro de clientes
- Cadastro de serviços de costura e dos tecidos consumidos em cada um

## Diagrama de classes

```mermaid
classDiagram
    class Cliente {
        -int id
        -String nome
        -String telefone
        -String endereco
    }
    class Tecido {
        -int id
        -String nome
        -String cor
        -String composicao
        -double largura
        -double quantidadeEstoque
        -double custoPorMetro
    }
    class MovimentacaoEstoque {
        -int id
        -String tipo
        -double quantidade
        -Date data
        -String observacao
    }
    class Servico {
        -int id
        -String descricao
        -Date dataInicio
        -Date dataEntrega
        -String status
        -double valor
    }
    class ConsumoTecido {
        -int id
        -double quantidadeMetros
    }

    Cliente "1" --> "0..*" Servico : solicita
    Tecido "1" --> "0..*" MovimentacaoEstoque : possui
    Servico "1" --> "0..*" ConsumoTecido : consome
    Tecido "1" --> "0..*" ConsumoTecido : é usado em
```

## Diagrama MER

```mermaid
erDiagram
    CLIENTE ||--o{ SERVICO : solicita
    SERVICO ||--o{ CONSUMO_TECIDO : consome
    TECIDO ||--o{ CONSUMO_TECIDO : "é usado em"
    TECIDO ||--o{ MOVIMENTACAO_ESTOQUE : possui

    CLIENTE {
        int cliente_id PK
        varchar nome
        varchar telefone
        varchar endereco
    }
    TECIDO {
        int tecido_id PK
        varchar nome
        varchar cor
        varchar composicao
        decimal largura
        decimal quantidade_estoque
        decimal custo_por_metro
    }
    MOVIMENTACAO_ESTOQUE {
        int movimentacao_id PK
        int tecido_id FK
        varchar tipo
        decimal quantidade
        date data
        varchar observacao
    }
    SERVICO {
        int servico_id PK
        int cliente_id FK
        varchar descricao
        date data_inicio
        date data_entrega
        varchar status
        decimal valor
    }
    CONSUMO_TECIDO {
        int consumo_id PK
        int servico_id FK
        int tecido_id FK
        decimal quantidade_metros
    }
```

## Como executar

Requisitos: JDK 11 ou superior. O driver do SQLite já está em `lib/`, e o banco (`costura.db`) é criado sozinho na primeira execução.

- **Windows:** dê dois cliques em `executar.bat`
- **Linux/Mac:** `./executar.sh`
- **IDE (NetBeans/IntelliJ/Eclipse):** abra a pasta, marque `src` como pasta de código, adicione `lib/sqlite-jdbc-3.36.0.3.jar` às bibliotecas e execute a classe `Main`.

## Estrutura do projeto (MVC)

```
CosturaControl/
├── README.md
├── executar.bat / executar.sh
├── lib/sqlite-jdbc-3.36.0.3.jar
├── sql/schema.sql
└── src/
    ├── Main.java
    ├── model/       (entidades: Cliente, Tecido, MovimentacaoEstoque, Servico, ConsumoTecido)
    ├── view/        (telas Swing)
    ├── controller/  (validações e regras: recebe as ações da view e chama os DAOs)
    ├── dao/         (comandos SQL)
    └── database/    (conexão com o banco)
```

## Regras do sistema

- O estoque do tecido só muda por movimentações (entrada ou saída).
- Não é possível registrar saída maior que o estoque disponível.
- Ao adicionar um tecido a um serviço, o sistema dá baixa automática no estoque.
- Não é possível excluir cliente ou tecido que já esteja em uso em serviços.
