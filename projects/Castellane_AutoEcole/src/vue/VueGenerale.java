package vue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.Castellane;

public class VueGenerale extends JFrame implements ActionListener {

    private JPanel panelMenu = new JPanel();

    private JButton btCandidats     = new JButton("Candidats");
    private JButton btMoniteurs     = new JButton("Moniteurs");
    private JButton btVehicules     = new JButton("Vehicules");
    private JButton btLecons        = new JButton("Lecons");
    private JButton btStatistiques  = new JButton("Statistiques");
    private JButton btQuitter       = new JButton("Quitter");

    private PanelCandidats     panelCandidats     = new PanelCandidats("Gestion des Candidats");
    private PanelMoniteurs     panelMoniteurs     = new PanelMoniteurs("Gestion des Moniteurs");
    private PanelVehicules     panelVehicules     = new PanelVehicules("Gestion des Vehicules");
    private PanelLecons        panelLecons        = new PanelLecons("Gestion des Lecons");
    private PanelStatistiques  panelStatistiques  = new PanelStatistiques();

    public VueGenerale() {
        this.setTitle("CASTELLANE Auto Ecole");
        this.setSize(1000, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.darkGray);

        // --- Menu centré (tous les boutons dont Quitter) ---
        this.panelMenu.setBounds(215, 10, 570, 40);
        this.panelMenu.setBackground(Color.darkGray);
        this.panelMenu.setLayout(null);

        this.btCandidats.setBounds(0,   0, 90, 35);
        this.btMoniteurs.setBounds(95,  0, 90, 35);
        this.btVehicules.setBounds(190, 0, 90, 35);
        this.btLecons.setBounds(285,    0, 80, 35);
        this.btStatistiques.setBounds(370, 0, 105, 35);
        this.btQuitter.setBounds(480,   0, 90, 35);

        this.panelMenu.add(this.btCandidats);
        this.panelMenu.add(this.btMoniteurs);
        this.panelMenu.add(this.btVehicules);
        this.panelMenu.add(this.btLecons);
        this.panelMenu.add(this.btStatistiques);
        this.panelMenu.add(this.btQuitter);
        this.add(this.panelMenu);

        // --- Panels de gestion ---
        this.add(this.panelCandidats);
        this.add(this.panelMoniteurs);
        this.add(this.panelVehicules);
        this.add(this.panelLecons);
        this.add(this.panelStatistiques);

        // --- Ecouteurs ---
        this.btCandidats.addActionListener(this);
        this.btMoniteurs.addActionListener(this);
        this.btVehicules.addActionListener(this);
        this.btLecons.addActionListener(this);
        this.btStatistiques.addActionListener(this);
        this.btQuitter.addActionListener(this);

        // --- Affichage par defaut ---
        this.afficherPanel(this.panelCandidats);

        this.setVisible(true);
    }

    private void afficherPanel(PanelPrincipal panel) {
        this.panelCandidats.setVisible(false);
        this.panelMoniteurs.setVisible(false);
        this.panelVehicules.setVisible(false);
        this.panelLecons.setVisible(false);
        this.panelStatistiques.setVisible(false);
        panel.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btCandidats) {
            this.afficherPanel(this.panelCandidats);
        } else if (e.getSource() == this.btMoniteurs) {
            this.afficherPanel(this.panelMoniteurs);
        } else if (e.getSource() == this.btVehicules) {
            this.afficherPanel(this.panelVehicules);
        } else if (e.getSource() == this.btLecons) {
            this.afficherPanel(this.panelLecons);
        } else if (e.getSource() == this.btStatistiques) {
            this.panelStatistiques.chargerStats();
            this.afficherPanel(this.panelStatistiques);
        } else if (e.getSource() == this.btQuitter) {
            Castellane.creeDetruireVueGenerale(false);
            Castellane.rentreVisibleConnexion(true);
        }
    }
}
