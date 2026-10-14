package controller;

import dao.TecidoDAO;
import model.Tecido;
import java.sql.SQLException;
import java.util.List;

public class TecidoController {
    private final TecidoDAO dao = new TecidoDAO();

    public List<Tecido> listar() throws SQLException {
        return dao.listar();
    }

    public void salvar(Integer id, String nome, String cor, String composicao,
                       String largura, String estoqueInicial, String custo) throws SQLException {
        if (nome.isBlank()) throw new IllegalArgumentException("Informe o nome do tecido.");
        Tecido t = new Tecido();
        t.setNome(nome.trim());
        t.setCor(cor.trim());
        t.setComposicao(composicao.trim());
        t.setLargura(Validacao.numeroOpcional(largura, "Largura"));
        t.setCustoPorMetro(Validacao.numeroOpcional(custo, "Custo por metro"));
        if (id == null) {
            t.setQuantidadeEstoque(Validacao.numeroOpcional(estoqueInicial, "Estoque inicial"));
            dao.inserir(t);
        } else {
            t.setId(id);
            dao.atualizar(t);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }
}
