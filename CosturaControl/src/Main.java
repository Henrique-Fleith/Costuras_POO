import database.Conexao;
import view.MenuPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            Conexao.criarTabelas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível abrir o banco de dados.\n"
                            + "Verifique se o driver sqlite-jdbc está na pasta lib/.\n\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
