package controller;

import dao.ConsumoDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.ConsumoTecido;
import model.Servico;
import model.Tecido;
import java.sql.SQLException;
import java.util.List;

public class ServicoController {
    private final ServicoDAO dao = new ServicoDAO();
    private final ConsumoDAO consumoDao = new ConsumoDAO();
    private final MovimentacaoController movimentacao = new MovimentacaoController();

    public List<Servico> listar() throws SQLException {
        return dao.listar();
    }

    public List<ConsumoTecido> listarConsumos(int servicoId) throws SQLException {
        return consumoDao.listarPorServico(servicoId);
    }

    /** id == null cadastra um novo serviço; caso contrário, atualiza. */
    public void salvar(Integer id, Cliente cliente, String descricao, String inicio,
                       String entrega, String status, String valor) throws SQLException {
        if (cliente == null) throw new IllegalArgumentException("Selecione um cliente.");
        if (descricao.isBlank()) throw new IllegalArgumentException("Informe a descrição do serviço.");
        Servico s = new Servico();
        s.setClienteId(cliente.getId());
        s.setDescricao(descricao.trim());
        s.setDataInicio(Validacao.dataOpcional(inicio, "Data de início"));
        s.setDataEntrega(Validacao.dataOpcional(entrega, "Data de entrega"));
        s.setStatus(status);
        s.setValor(Validacao.numeroOpcional(valor, "Valor"));
        if (id == null) {
            dao.inserir(s);
        } else {
            s.setId(id);
            dao.atualizar(s);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    /** Registra o tecido usado no serviço e dá baixa no estoque (movimentação de SAIDA). */
    public void adicionarConsumo(int servicoId, Tecido tecido, String metros, String data)
            throws SQLException {
        movimentacao.registrar(tecido, "SAIDA", metros, data, "Usado no serviço #" + servicoId);
        ConsumoTecido c = new ConsumoTecido();
        c.setServicoId(servicoId);
        c.setTecidoId(tecido.getId());
        c.setQuantidadeMetros(Validacao.numero(metros, "Quantidade"));
        consumoDao.inserir(c);
    }
}
