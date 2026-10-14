package view;

import controller.TecidoController;
import model.Tecido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTecidos extends JFrame {
    private final TecidoController controller = new TecidoController();
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCor = new JTextField();
    private final JTextField txtComposicao = new JTextField();
    private final JTextField txtLargura = new JTextField();
    private final JTextField txtEstoque = new JTextField();
    private final JTextField txtCusto = new JTextField();
    private final DefaultTableModel modelo = Estilo.modelo(
            "ID", "Nome", "Cor", "Composição", "Largura (m)", "Estoque (m)", "Custo/m (R$)");
    private final JTable tabela = new JTable(modelo);
    private Integer idSelecionado = null;

    public TelaTecidos() {
        setTitle("Tecidos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(0, 4, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("Cor:"));
        form.add(txtCor);
        form.add(new JLabel("Composição:"));
        form.add(txtComposicao);
        form.add(new JLabel("Largura (m):"));
        form.add(txtLargura);
        form.add(new JLabel("Estoque inicial (m):"));
        form.add(txtEstoque);
        form.add(new JLabel("Custo por metro:"));
        form.add(txtCusto);

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

        add(Estilo.titulo("Cadastro de Tecidos"), BorderLayout.NORTH);
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
            for (Tecido t : controller.listar()) {
                modelo.addRow(new Object[]{t.getId(), t.getNome(), t.getCor(), t.getComposicao(),
                        t.getLargura(), t.getQuantidadeEstoque(), t.getCustoPorMetro()});
            }
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private String valor(int linha, int coluna) {
        Object o = modelo.getValueAt(linha, coluna);
        return o == null ? "" : o.toString();
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (Integer) modelo.getValueAt(linha, 0);
        txtNome.setText(valor(linha, 1));
        txtCor.setText(valor(linha, 2));
        txtComposicao.setText(valor(linha, 3));
        txtLargura.setText(valor(linha, 4));
        txtEstoque.setText(valor(linha, 5));
        txtEstoque.setEnabled(false); // estoque só muda por movimentação
        txtCusto.setText(valor(linha, 6));
    }

    private void salvar() {
        try {
            controller.salvar(idSelecionado, txtNome.getText(), txtCor.getText(), txtComposicao.getText(),
                    txtLargura.getText(), txtEstoque.getText(), txtCusto.getText());
            limpar();
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um tecido na tabela.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Excluir este tecido?", "Confirmação",
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
        txtCor.setText("");
        txtComposicao.setText("");
        txtLargura.setText("");
        txtEstoque.setText("");
        txtEstoque.setEnabled(true);
        txtCusto.setText("");
        tabela.clearSelection();
    }
}
