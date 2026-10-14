package view;

import controller.ClienteController;
import model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaClientes extends JFrame {
    private final ClienteController controller = new ClienteController();
    private final JTextField txtNome = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEndereco = new JTextField();
    private final DefaultTableModel modelo = Estilo.modelo("ID", "Nome", "Telefone", "Endereço");
    private final JTable tabela = new JTable(modelo);
    private Integer idSelecionado = null;

    public TelaClientes() {
        setTitle("Clientes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("Telefone:"));
        form.add(txtTelefone);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);

        JButton btSalvar = Estilo.botao("Salvar");
        JButton btNovo = Estilo.botao("Novo");
        JButton btExcluir = Estilo.botao("Excluir");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btNovo);
        botoes.add(btSalvar);
        botoes.add(btExcluir);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(form, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        add(Estilo.titulo("Clientes"), BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        btSalvar.addActionListener(e -> salvar());
        btNovo.addActionListener(e -> limpar());
        btExcluir.addActionListener(e -> excluir());
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionar();
        });

        carregar();
    }

    private void carregar() {
        try {
            modelo.setRowCount(0);
            List<Cliente> lista = controller.listar();
            for (Cliente c : lista) {
                modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getTelefone(), c.getEndereco()});
            }
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (Integer) modelo.getValueAt(linha, 0);
        txtNome.setText(String.valueOf(modelo.getValueAt(linha, 1)));
        txtTelefone.setText(valor(linha, 2));
        txtEndereco.setText(valor(linha, 3));
    }

    private String valor(int linha, int coluna) {
        Object o = modelo.getValueAt(linha, coluna);
        return o == null ? "" : o.toString();
    }

    private void salvar() {
        try {
            controller.salvar(idSelecionado, txtNome.getText(), txtTelefone.getText(), txtEndereco.getText());
            limpar();
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Excluir este cliente?", "Confirmação",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            controller.excluir(idSelecionado);
            limpar();
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void limpar() {
        idSelecionado = null;
        txtNome.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }
}
