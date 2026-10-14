package dao;

import database.Conexao;
import model.ConsumoTecido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDAO {

    public void inserir(ConsumoTecido c) throws SQLException {
        String sql = "INSERT INTO consumo_tecido (servico_id, tecido_id, quantidade_metros) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getServicoId());
            ps.setInt(2, c.getTecidoId());
            ps.setDouble(3, c.getQuantidadeMetros());
            ps.executeUpdate();
        }
    }

    public List<ConsumoTecido> listarPorServico(int servicoId) throws SQLException {
        List<ConsumoTecido> lista = new ArrayList<>();
        String sql = "SELECT c.*, t.nome AS tecido_nome FROM consumo_tecido c "
                + "JOIN tecido t ON t.tecido_id = c.tecido_id WHERE c.servico_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, servicoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsumoTecido c = new ConsumoTecido();
                    c.setId(rs.getInt("consumo_id"));
                    c.setServicoId(rs.getInt("servico_id"));
                    c.setTecidoId(rs.getInt("tecido_id"));
                    c.setTecidoNome(rs.getString("tecido_nome"));
                    c.setQuantidadeMetros(rs.getDouble("quantidade_metros"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }
}
