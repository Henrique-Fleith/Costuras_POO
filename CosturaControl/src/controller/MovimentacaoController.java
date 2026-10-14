package controller;

import dao.MovimentacaoDAO;
import dao.TecidoDAO;
import model.MovimentacaoEstoque;
import model.Tecido;
import java.sql.SQLException;
import java.util.List;

public class MovimentacaoController {
    private final MovimentacaoDAO dao = new MovimentacaoDAO();
    private final TecidoDAO tecidoDao = new TecidoDAO();

    public List<MovimentacaoEstoque> listar() throws SQLException {
        return dao.listar();
    }

    public void registrar(Tecido tecido, String tipo, String quantidade, String data, String obs)
            throws SQLException {
        if (tecido == null) throw new IllegalArgumentException("Selecione um tecido.");
        double qtd = Validacao.numero(quantidade, "Quantidade");
        if (qtd <= 0) throw new IllegalArgumentException("A quantidade deve ser maior que zero.");

        Tecido atual = tecidoDao.buscar(tecido.getId());
        if (tipo.equals("SAIDA") && atual.getQuantidadeEstoque() < qtd) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: "
                    + atual.getQuantidadeEstoque() + " m.");
        }

        MovimentacaoEstoque m = new MovimentacaoEstoque();
        m.setTecidoId(tecido.getId());
        m.setTipo(tipo);
        m.setQuantidade(qtd);
        m.setData(Validacao.data(data, "Data"));
        m.setObservacao(obs.trim());
        dao.registrar(m);
    }
}
