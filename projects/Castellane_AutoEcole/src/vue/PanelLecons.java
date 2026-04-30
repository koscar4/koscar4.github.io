package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Lecon;
import controleur.Tableau;

public class PanelLecons extends PanelPrincipal implements ActionListener {

    private JPanel     panelForm   = new JPanel();
    private JTextField txtDate       = new JTextField();     // format : YYYY-MM-DD
    private JTextField txtHeureDebut = new JTextField();     // format : HH:mm
    private JTextField txtHeureFin   = new JTextField();     // format : HH:mm
    private JTextField txtIdCandidat = new JTextField();     // ID du candidat
    private JTextField txtIdMoniteur = new JTextField();     // ID du moniteur

    private JButton btAnnuler   = new JButton("Annuler");
    private JButton btValider   = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier  = new JButton("Modifier");

    private JPanel     panelFiltre = new JPanel();
    private JTextField txtFiltre   = new JTextField();
    private JButton    btFiltrer   = new JButton("Filtrer");

    private JTable      tableLecons;
    private JScrollPane scrollLecons;
    private Tableau     unTableau;
    private JLabel      lbNbLecons = new JLabel("");

    public PanelLecons(String titre) {
        super(titre);

        // --- Filtre ---
        this.panelFiltre.setBounds(450, 80, 450, 30);
        this.panelFiltre.setBackground(Color.gray);
        this.panelFiltre.setLayout(new GridLayout(1, 3, 10, 10));
        this.panelFiltre.add(new JLabel("Filtrer par date :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);

        // --- Formulaire ---
        this.panelForm.setBounds(50, 80, 300, 280);
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setLayout(new GridLayout(7, 2, 10, 10));

        this.panelForm.add(new JLabel("Date (YYYY-MM-DD) :"));
        this.panelForm.add(this.txtDate);
        this.panelForm.add(new JLabel("Heure début (HH:mm) :"));
        this.panelForm.add(this.txtHeureDebut);
        this.panelForm.add(new JLabel("Heure fin (HH:mm) :"));
        this.panelForm.add(this.txtHeureFin);
        this.panelForm.add(new JLabel("ID Candidat :"));
        this.panelForm.add(this.txtIdCandidat);
        this.panelForm.add(new JLabel("ID Moniteur :"));
        this.panelForm.add(this.txtIdMoniteur);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btSupprimer);
        this.panelForm.add(this.btModifier);

        this.btSupprimer.setEnabled(false);
        this.btModifier.setEnabled(false);
        this.add(this.panelForm);

        // --- Écouteurs ---
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btModifier.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.btFiltrer.addActionListener(this);
        this.txtFiltre.addActionListener(this);

        // --- Table ---
        String[] entetes = {"ID", "Date", "Heure début", "Heure fin", "ID Candidat", "ID Moniteur"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableLecons = new JTable(this.unTableau);
        this.scrollLecons = new JScrollPane(this.tableLecons);
        this.scrollLecons.setBounds(450, 120, 490, 240);
        this.add(this.scrollLecons);

        // --- Mouse listener ---
        this.tableLecons.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableLecons.getSelectedRow();
                if (row >= 0) {
                    txtDate.setText(unTableau.getValueAt(row, 1).toString());
                    txtHeureDebut.setText(unTableau.getValueAt(row, 2).toString());
                    txtHeureFin.setText(unTableau.getValueAt(row, 3).toString());
                    txtIdCandidat.setText(unTableau.getValueAt(row, 4).toString());
                    txtIdMoniteur.setText(unTableau.getValueAt(row, 5).toString());
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
                }
            }
        });

        // --- Compteur ---
        this.lbNbLecons.setBounds(450, 370, 400, 20);
        this.lbNbLecons.setText("Nombre de leçons : " + unTableau.getRowCount());
        this.add(this.lbNbLecons);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Lecon> liste = Controleur.selectAllLecons(filtre);
        Object[][] matrice = new Object[liste.size()][6];
        int i = 0;
        for (Lecon l : liste) {
            matrice[i][0] = l.getIdlecon();
            matrice[i][1] = l.getDatelecon();
            matrice[i][2] = l.getHeuredebut();
            matrice[i][3] = l.getHeurefin();
            matrice[i][4] = l.getIdcandidat();
            matrice[i][5] = l.getIdmoniteur();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btValider) {
            this.insertChamps();
        } else if (e.getSource() == this.btModifier) {
            this.updateChamps();
        } else if (e.getSource() == this.btSupprimer) {
            this.deleteChamps();
        } else if (e.getSource() == this.btFiltrer || e.getSource() == this.txtFiltre) {
            this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
        }
    }

    public void viderChamps() {
        txtDate.setText(""); txtHeureDebut.setText(""); txtHeureFin.setText("");
        txtIdCandidat.setText(""); txtIdMoniteur.setText("");
        btModifier.setEnabled(false);
        btSupprimer.setEnabled(false);
    }

    public void insertChamps() {
        if (txtDate.getText().isEmpty() || txtHeureDebut.getText().isEmpty()
                || txtIdCandidat.getText().isEmpty() || txtIdMoniteur.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir : Date, Heure début, ID Candidat, ID Moniteur.");
            return;
        }
        try {
            int idCandidat = Integer.parseInt(txtIdCandidat.getText().trim());
            int idMoniteur = Integer.parseInt(txtIdMoniteur.getText().trim());
            Lecon l = new Lecon(txtDate.getText(), txtHeureDebut.getText(),
                    txtHeureFin.getText(), idCandidat, idMoniteur);
            Controleur.insertLecon(l);
            JOptionPane.showMessageDialog(this, "Leçon ajoutée avec succès.");
            this.unTableau.setDonnes(this.obtenirDonnees(""));
            this.lbNbLecons.setText("Nombre de leçons : " + unTableau.getRowCount());
            this.viderChamps();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Candidat et ID Moniteur doivent être des nombres entiers.");
        }
    }

    public void updateChamps() {
        int row = tableLecons.getSelectedRow();
        if (row < 0) return;
        try {
            int id         = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
            int idCandidat = Integer.parseInt(txtIdCandidat.getText().trim());
            int idMoniteur = Integer.parseInt(txtIdMoniteur.getText().trim());
            Lecon l = new Lecon(id, txtDate.getText(), txtHeureDebut.getText(),
                    txtHeureFin.getText(), idCandidat, idMoniteur);
            Controleur.updateLecon(l);
            JOptionPane.showMessageDialog(this, "Leçon modifiée avec succès.");
            this.unTableau.setDonnes(this.obtenirDonnees(""));
            this.viderChamps();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Candidat et ID Moniteur doivent être des nombres entiers.");
        }
    }

    public void deleteChamps() {
        int row = tableLecons.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        int rep = JOptionPane.showConfirmDialog(this,
                "Supprimer cette leçon ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep == JOptionPane.YES_OPTION) {
            Controleur.deleteLecon(id);
            JOptionPane.showMessageDialog(this, "Leçon supprimée.");
            this.unTableau.setDonnes(this.obtenirDonnees(""));
            this.lbNbLecons.setText("Nombre de leçons : " + unTableau.getRowCount());
            this.viderChamps();
        }
    }
}
