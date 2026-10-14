package view;

import controller.MovimentacaoController;
import controller.TecidoController;
import model.MovimentacaoEstoque;
import model.Tecido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TelaMovimentacao extends JFrame {
    private final MovimentacaoController controller = new MovimentacaoController();
    private final TecidoController tecidoController = new TecidoController();
    private final JComboBox<Tecido> cbTecido = new JComboBox<>();
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"ENTRADA", "SAIDA"});
    private final JTextField txtQuantidade = new JTextField();
    private final JTextField txtData = new JTextField(LocalDate.now().toString());
    private final JTextField txtObs = new JTextField();
    private final DefaultTableModel modelo = Estilo.modelo(
            "ID", "Data", "Tecido", "Tipo", "Quantidade (m)", "Observação");
    private final JTable tabela = new JTable(modelo);

    public TelaMovimentacao() {
        setTitle("Movimentação de Estoque");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(0, 4, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        form.add(new JLabel("Tecido:"));
        form.add(cbTecido);
        form.add(new JLabel("Tipo:"));
        form.add(cbTipo);
        form.add(new JLabel("Quantidade (m):"));
        form.add(txtQuantidade);
        form.add(new JLabel("Data (AAAA-MM-DD):"));
        form.add(txtData);
        form.add(new JLabel("Observação:"));
        form.add(txtObs);

        JButton btRegistrar = Estilo.botao("Registrar");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btRegistrar);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(form, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        add(Estilo.titulo("Movimentação de Estoque"), BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        btRegistrar.addActionListener(e -> registrar());
        carregar();
    }

    private void carregar() {
        try {
            cbTecido.removeAllItems();
            for (Tecido t : tecidoController.listar()) cbTecido.addItem(t);

            modelo.setRowCount(0);
            for (MovimentacaoEstoque m : controller.listar()) {
                modelo.addRow(new Object[]{m.getId(), m.getData(), m.getTecidoNome(),
                        m.getTipo(), m.getQuantidade(), m.getObservacao()});
            }
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void registrar() {
        try {
            controller.registrar((Tecido) cbTecido.getSelectedItem(), (String) cbTipo.getSelectedItem(),
                    txtQuantidade.getText(), txtData.getText(), txtObs.getText());
            txtQuantidade.setText("");
            txtObs.setText("");
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }
}
