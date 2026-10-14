package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("CosturaControl");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 380);
        setLocationRelativeTo(null);

        JPanel botoes = new JPanel(new GridLayout(0, 1, 10, 10));
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        botoes.setBackground(Estilo.FUNDO);

        JButton btTecidos = Estilo.botao("Tecidos");
        JButton btMov = Estilo.botao("Movimentação de Estoque");
        JButton btClientes = Estilo.botao("Clientes");
        JButton btServicos = Estilo.botao("Serviços de Costura");
        JButton btSair = Estilo.botao("Sair");

        btTecidos.addActionListener(e -> new TelaTecidos().setVisible(true));
        btMov.addActionListener(e -> new TelaMovimentacao().setVisible(true));
        btClientes.addActionListener(e -> new TelaClientes().setVisible(true));
        btServicos.addActionListener(e -> new TelaServicos().setVisible(true));
        btSair.addActionListener(e -> System.exit(0));

        botoes.add(btTecidos);
        botoes.add(btMov);
        botoes.add(btClientes);
        botoes.add(btServicos);
        botoes.add(btSair);

        add(Estilo.titulo("CosturaControl"), BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
    }
}
