package dao;

import database.Conexao;
import model.Tecido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecidoDAO {

    public void inserir(Tecido t) throws SQLException {
        String sql = "INSERT INTO tecido (nome, cor, composicao, largura, quantidade_estoque, custo_por_metro) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNome());
            ps.setString(2, t.getCor());
            ps.setString(3, t.getComposicao());
            ps.setDouble(4, t.getLargura());
            ps.setDouble(5, t.getQuantidadeEstoque());
            ps.setDouble(6, t.getCustoPorMetro());
            ps.executeUpdate();
        }
    }

    public void atualizar(Tecido t) throws SQLException {
        String sql = "UPDATE tecido SET nome = ?, cor = ?, composicao = ?, largura = ?, custo_por_metro = ? "
                + "WHERE tecido_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNome());
            ps.setString(2, t.getCor());
            ps.setString(3, t.getComposicao());
            ps.setDouble(4, t.getLargura());
            ps.setDouble(5, t.getCustoPorMetro());
            ps.setInt(6, t.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM tecido WHERE tecido_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Tecido> listar() throws SQLException {
        List<Tecido> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tecido ORDER BY nome")) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Tecido buscar(int id) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tecido WHERE tecido_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    private Tecido montar(ResultSet rs) throws SQLException {
        Tecido t = new Tecido();
        t.setId(rs.getInt("tecido_id"));
        t.setNome(rs.getString("nome"));
        t.setCor(rs.getString("cor"));
        t.setComposicao(rs.getString("composicao"));
        t.setLargura(rs.getDouble("largura"));
        t.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
        t.setCustoPorMetro(rs.getDouble("custo_por_metro"));
        return t;
    }
}
