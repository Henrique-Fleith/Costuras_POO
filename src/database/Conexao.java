package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexao {

    private static final String URL = "jdbc:sqlite:costura.db";

    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(URL);
        try (Statement s = c.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");
        }
        return c;
    }

    /** Cria as tabelas caso ainda não existam */
    public static void criarTabelas() throws SQLException {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS cliente ("
                    + "cliente_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nome TEXT NOT NULL, telefone TEXT, endereco TEXT)");
            s.execute("CREATE TABLE IF NOT EXISTS tecido ("
                    + "tecido_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nome TEXT NOT NULL, cor TEXT, composicao TEXT,"
                    + "largura REAL DEFAULT 0, quantidade_estoque REAL DEFAULT 0,"
                    + "custo_por_metro REAL DEFAULT 0)");
            s.execute("CREATE TABLE IF NOT EXISTS movimentacao_estoque ("
                    + "movimentacao_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "tecido_id INTEGER NOT NULL, tipo TEXT NOT NULL,"
                    + "quantidade REAL NOT NULL, data TEXT NOT NULL, observacao TEXT,"
                    + "FOREIGN KEY (tecido_id) REFERENCES tecido(tecido_id) ON DELETE CASCADE)");
            s.execute("CREATE TABLE IF NOT EXISTS servico ("
                    + "servico_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "cliente_id INTEGER NOT NULL, descricao TEXT NOT NULL,"
                    + "data_inicio TEXT, data_entrega TEXT, status TEXT, valor REAL DEFAULT 0,"
                    + "FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id))");
            s.execute("CREATE TABLE IF NOT EXISTS consumo_tecido ("
                    + "consumo_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "servico_id INTEGER NOT NULL, tecido_id INTEGER NOT NULL,"
                    + "quantidade_metros REAL NOT NULL,"
                    + "FOREIGN KEY (servico_id) REFERENCES servico(servico_id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (tecido_id) REFERENCES tecido(tecido_id))");
        }
    }
}
