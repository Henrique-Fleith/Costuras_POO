-- CosturaControl - esquema do banco (SQLite)
-- As tabelas também são criadas automaticamente pelo sistema na primeira execução.

CREATE TABLE IF NOT EXISTS cliente (
    cliente_id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome       TEXT NOT NULL,
    telefone   TEXT,
    endereco   TEXT
);

CREATE TABLE IF NOT EXISTS tecido (
    tecido_id          INTEGER PRIMARY KEY AUTOINCREMENT,
    nome               TEXT NOT NULL,
    cor                TEXT,
    composicao         TEXT,
    largura            REAL DEFAULT 0,
    quantidade_estoque REAL DEFAULT 0,
    custo_por_metro    REAL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS movimentacao_estoque (
    movimentacao_id INTEGER PRIMARY KEY AUTOINCREMENT,
    tecido_id       INTEGER NOT NULL,
    tipo            TEXT NOT NULL,      -- ENTRADA ou SAIDA
    quantidade      REAL NOT NULL,
    data            TEXT NOT NULL,      -- AAAA-MM-DD
    observacao      TEXT,
    FOREIGN KEY (tecido_id) REFERENCES tecido(tecido_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS servico (
    servico_id   INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id   INTEGER NOT NULL,
    descricao    TEXT NOT NULL,
    data_inicio  TEXT,
    data_entrega TEXT,
    status       TEXT,
    valor        REAL DEFAULT 0,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

CREATE TABLE IF NOT EXISTS consumo_tecido (
    consumo_id        INTEGER PRIMARY KEY AUTOINCREMENT,
    servico_id        INTEGER NOT NULL,
    tecido_id         INTEGER NOT NULL,
    quantidade_metros REAL NOT NULL,
    FOREIGN KEY (servico_id) REFERENCES servico(servico_id) ON DELETE CASCADE,
    FOREIGN KEY (tecido_id)  REFERENCES tecido(tecido_id)
);
