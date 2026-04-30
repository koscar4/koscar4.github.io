package vue;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class PanelPrincipal extends JPanel {
    // Panel générique : base commune à tous les panels de gestion

    public PanelPrincipal(String titre) {
        this.setBackground(Color.gray);
        this.setBounds(10, 60, 960, 400);
        this.setLayout(null);

        JLabel lbTitre = new JLabel(titre);
        lbTitre.setBounds(350, 10, 500, 20);
        this.add(lbTitre);

        this.setVisible(false);
    }
}
