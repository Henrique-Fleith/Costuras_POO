package view;

import controller.ClienteController;
import controller.ServicoController;
import controller.TecidoController;
import model.Cliente;
import model.ConsumoTecido;
import model.Servico;
import model.Tecido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TelaServicos extends JFrame {
    private final ServicoController controller = new ServicoController();
    private final ClienteController clienteController = new ClienteController();
    private final TecidoController tecidoController = new TecidoController();

    private final JComboBox<Cliente> cbCliente = new JComboBox<>();
    private final JTextField txtDescricao = new JTextField();
    private final JTextField txtInicio = new JTextField(LocalDate.now().toString());
    private final JTextField txtEntrega = new JTextField();
    private final JComboBox<String> cbStatus = new JComboBox<>(
            new String[]{"Pendente", "Em andamento", "Concluído", "Entregue"});
    private final JTextField txtValor = new JTextField();

    private final DefaultTableModel modeloServicos = Estilo.modelo(
            "ID", "Cliente", "Descrição", "Início", "Entrega", "Status", "Valor (R$)");
    private final JTable tabelaServicos = new JTable(modeloServicos);

    private final JComboBox<Tecido> cbTecido = new JComboBox<>();
    private final JTextField txtMetros = new JTextField(6);
    private final DefaultTableModel modeloConsumo = Estilo.modelo("Tecido", "Metros usados");
    private final JTable tabelaConsumo = new JTable(modeloConsumo);

    private Integer idSelecionado = null;

    public TelaServicos() {
        setTitle("Serviços de Costura");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 680);
        setLocationRelativeTo(null);

        // Formulário do serviço
        JPanel form = new JPanel(new GridLayout(0, 4, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        form.add(new JLabel("Cliente:"));
        form.add(cbCliente);
        form.add(new JLabel("Descrição:"));
        form.add(txtDescricao);
        form.add(new JLabel("Início (AAAA-MM-DD):"));
        form.add(txtInicio);
        form.add(new JLabel("Entrega (AAAA-MM-DD):"));
        form.add(txtEntrega);
        form.add(new JLabel("Status:"));
        form.add(cbStatus);
        form.add(new JLabel("Valor (R$):"));
        form.add(txtValor);

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

        // Tecidos usados no serviço selecionado
        JButton btConsumo = Estilo.botao("Adicionar tecido ao serviço");
        JPanel addConsumo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addConsumo.add(new JLabel("Tecido:"));
        addConsumo.add(cbTecido);
        addConsumo.add(new JLabel("Metros:"));
        addConsumo.add(txtMetros);
        addConsumo.add(btConsumo);

        JPanel painelConsumo = new JPanel(new BorderLayout());
        painelConsumo.setBorder(BorderFactory.createTitledBorder("Tecidos usados no serviço selecionado"));
        painelConsumo.add(addConsumo, BorderLayout.NORTH);
        painelConsumo.add(new JScrollPane(tabelaConsumo), BorderLayout.CENTER);
        painelConsumo.setPreferredSize(new Dimension(0, 200));

        add(Estilo.titulo("Serviços de Costura"), BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabelaServicos), BorderLayout.CENTER);
        centro.add(painelConsumo, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        btSalvar.addActionListener(e -> salvar());
        btNovo.addActionListener(e -> limpar());
        btExcluir.addActionListener(e -> excluir());
        btConsumo.addActionListener(e -> adicionarConsumo());
        tabelaServicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionar();
        });

        carregarCombos();
        carregar();
    }

    private void carregarCombos() {
        try {
            cbCliente.removeAllItems();
            for (Cliente c : clienteController.listar()) cbCliente.addItem(c);
            cbTecido.removeAllItems();
            for (Tecido t : tecidoController.listar()) cbTecido.addItem(t);
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void carregar() {
        try {
            modeloServicos.setRowCount(0);
            for (Servico s : controller.listar()) {
                modeloServicos.addRow(new Object[]{s.getId(), s.getClienteNome(), s.getDescricao(),
                        s.getDataInicio(), s.getDataEntrega(), s.getStatus(), s.getValor()});
            }
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void carregarConsumos() {
        modeloConsumo.setRowCount(0);
        if (idSelecionado == null) return;
        try {
            for (ConsumoTecido c : controller.listarConsumos(idSelecionado)) {
                modeloConsumo.addRow(new Object[]{c.getTecidoNome(), c.getQuantidadeMetros()});
            }
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private String valor(int linha, int coluna) {
        Object o = modeloServicos.getValueAt(linha, coluna);
        return o == null ? "" : o.toString();
    }

    private void selecionar() {
        int linha = tabelaServicos.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (Integer) modeloServicos.getValueAt(linha, 0);
        String nomeCliente = valor(linha, 1);
        for (int i = 0; i < cbCliente.getItemCount(); i++) {
            if (cbCliente.getItemAt(i).getNome().equals(nomeCliente)) {
                cbCliente.setSelectedIndex(i);
                break;
            }
        }
        txtDescricao.setText(valor(linha, 2));
        txtInicio.setText(valor(linha, 3));
        txtEntrega.setText(valor(linha, 4));
        cbStatus.setSelectedItem(valor(linha, 5));
        txtValor.setText(valor(linha, 6));
        carregarConsumos();
    }

    private void salvar() {
        try {
            controller.salvar(idSelecionado, (Cliente) cbCliente.getSelectedItem(), txtDescricao.getText(),
                    txtInicio.getText(), txtEntrega.getText(), (String) cbStatus.getSelectedItem(),
                    txtValor.getText());
            limpar();
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Excluir este serviço?", "Confirmação",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            controller.excluir(idSelecionado);
            limpar();
            carregar();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void adicionarConsumo() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela primeiro.");
            return;
        }
        try {
            controller.adicionarConsumo(idSelecionado, (Tecido) cbTecido.getSelectedItem(),
                    txtMetros.getText(), LocalDate.now().toString());
            txtMetros.setText("");
            carregarCombos(); // atualiza o estoque mostrado nos tecidos
            carregarConsumos();
        } catch (Exception e) {
            Estilo.erro(this, e);
        }
    }

    private void limpar() {
        idSelecionado = null;
        txtDescricao.setText("");
        txtInicio.setText(LocalDate.now().toString());
        txtEntrega.setText("");
        txtValor.setText("");
        cbStatus.setSelectedIndex(0);
        tabelaServicos.clearSelection();
        modeloConsumo.setRowCount(0);
    }
}
