package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private final ClienteDAO dao = new ClienteDAO();

    public List<Cliente> listar() throws SQLException {
        return dao.listar();
    }

    public void salvar(Integer id, String nome, String telefone, String endereco) throws SQLException {
        if (nome.isBlank()) throw new IllegalArgumentException("Informe o nome do cliente.");
        Cliente c = new Cliente();
        c.setNome(nome.trim());
        c.setTelefone(telefone.trim());
        c.setEndereco(endereco.trim());
        if (id == null) {
            dao.inserir(c);
        } else {
            c.setId(id);
            dao.atualizar(c);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }
}
