package dao;

import database.Conexao;
import model.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public void inserir(Servico s) throws SQLException {
        String sql = "INSERT INTO servico (cliente_id, descricao, data_inicio, data_entrega, status, valor) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getClienteId());
            ps.setString(2, s.getDescricao());
            ps.setString(3, s.getDataInicio());
            ps.setString(4, s.getDataEntrega());
            ps.setString(5, s.getStatus());
            ps.setDouble(6, s.getValor());
            ps.executeUpdate();
        }
    }

    public void atualizar(Servico s) throws SQLException {
        String sql = "UPDATE servico SET cliente_id = ?, descricao = ?, data_inicio = ?, data_entrega = ?, "
                + "status = ?, valor = ? WHERE servico_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getClienteId());
            ps.setString(2, s.getDescricao());
            ps.setString(3, s.getDataInicio());
            ps.setString(4, s.getDataEntrega());
            ps.setString(5, s.getStatus());
            ps.setDouble(6, s.getValor());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM servico WHERE servico_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Servico> listar() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT s.*, c.nome AS cliente_nome FROM servico s "
                + "JOIN cliente c ON c.cliente_id = s.cliente_id ORDER BY s.servico_id DESC";
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("servico_id"));
                s.setClienteId(rs.getInt("cliente_id"));
                s.setClienteNome(rs.getString("cliente_nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setDataInicio(rs.getString("data_inicio"));
                s.setDataEntrega(rs.getString("data_entrega"));
                s.setStatus(rs.getString("status"));
                s.setValor(rs.getDouble("valor"));
                lista.add(s);
            }
        }
        return lista;
    }
}
