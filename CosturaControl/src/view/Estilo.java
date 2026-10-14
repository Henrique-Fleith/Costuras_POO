package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Estilo {
    public static final Color VERDE = new Color(27, 77, 62);
    public static final Color FUNDO = new Color(245, 245, 242);

    public static JButton botao(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(VERDE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        return b;
    }

    public static JLabel titulo(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(VERDE);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 18f));
        l.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return l;
    }

    public static DefaultTableModel modelo(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    public static void erro(Component pai, Exception e) {
        String msg = e.getMessage();
        if (e instanceof java.sql.SQLException) {
            msg = msg != null && msg.contains("FOREIGN KEY")
                    ? "Não é possível excluir"
                    : "Erro no banco de dados: " + msg;
        }
        JOptionPane.showMessageDialog(pai, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
