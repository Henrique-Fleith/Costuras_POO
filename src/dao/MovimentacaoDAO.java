package dao;

import database.Conexao;
import model.MovimentacaoEstoque;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    public void registrar(MovimentacaoEstoque m) throws SQLException {
        String insert = "INSERT INTO movimentacao_estoque (tecido_id, tipo, quantidade, data, observacao) "
                + "VALUES (?, ?, ?, ?, ?)";
        String update = "UPDATE tecido SET quantidade_estoque = quantidade_estoque + ? WHERE tecido_id = ?";
        double delta = m.getTipo().equals("ENTRADA") ? m.getQuantidade() : -m.getQuantidade();

        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(insert);
                 PreparedStatement ps2 = con.prepareStatement(update)) {
                ps1.setInt(1, m.getTecidoId());
                ps1.setString(2, m.getTipo());
                ps1.setDouble(3, m.getQuantidade());
                ps1.setString(4, m.getData());
                ps1.setString(5, m.getObservacao());
                ps1.executeUpdate();

                ps2.setDouble(1, delta);
                ps2.setInt(2, m.getTecidoId());
                ps2.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<MovimentacaoEstoque> listar() throws SQLException {
        List<MovimentacaoEstoque> lista = new ArrayList<>();
        String sql = "SELECT m.*, t.nome AS tecido_nome FROM movimentacao_estoque m "
                + "JOIN tecido t ON t.tecido_id = m.tecido_id ORDER BY m.data DESC, m.movimentacao_id DESC";
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                MovimentacaoEstoque m = new MovimentacaoEstoque();
                m.setId(rs.getInt("movimentacao_id"));
                m.setTecidoId(rs.getInt("tecido_id"));
                m.setTecidoNome(rs.getString("tecido_nome"));
                m.setTipo(rs.getString("tipo"));
                m.setQuantidade(rs.getDouble("quantidade"));
                m.setData(rs.getString("data"));
                m.setObservacao(rs.getString("observacao"));
                lista.add(m);
            }
        }
        return lista;
    }
}
