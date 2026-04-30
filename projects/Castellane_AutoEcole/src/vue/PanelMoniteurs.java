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
import controleur.Moniteur;
import controleur.Tableau;

public class PanelMoniteurs extends PanelPrincipal implements ActionListener {

    private JPanel     panelForm   = new JPanel();
    private JTextField txtNom        = new JTextField();
    private JTextField txtPrenom     = new JTextField();
    private JTextField txtEmail      = new JTextField();
    private JTextField txtTelephone  = new JTextField();

    private JButton btAnnuler   = new JButton("Annuler");
    private JButton btValider   = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier  = new JButton("Modifier");

    private JTable      tableMoniteurs;
    private JScrollPane scrollMoniteurs;
    private Tableau     unTableau;
    private JLabel      lbNbMoniteurs = new JLabel("");

    public PanelMoniteurs(String titre) {
        super(titre);

        // --- Formulaire ---
        this.panelForm.setBounds(50, 80, 300, 250);
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setLayout(new GridLayout(6, 2, 10, 10));

        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);
        this.panelForm.add(new JLabel("Prénom :"));
        this.panelForm.add(this.txtPrenom);
        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);
        this.panelForm.add(new JLabel("Téléphone :"));
        this.panelForm.add(this.txtTelephone);
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

        // --- Table ---
        String[] entetes = {"ID", "Nom", "Prénom", "Email", "Téléphone"};
        this.unTableau = new Tableau(this.obtenirDonnees(), entetes);
        this.tableMoniteurs = new JTable(this.unTableau);
        this.scrollMoniteurs = new JScrollPane(this.tableMoniteurs);
        this.scrollMoniteurs.setBounds(450, 120, 490, 240);
        this.add(this.scrollMoniteurs);

        // --- Mouse listener ---
        this.tableMoniteurs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableMoniteurs.getSelectedRow();
                if (row >= 0) {
                    txtNom.setText(unTableau.getValueAt(row, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(row, 2).toString());
                    txtEmail.setText(unTableau.getValueAt(row, 3).toString());
                    txtTelephone.setText(unTableau.getValueAt(row, 4).toString());
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
                }
            }
        });

        // --- Compteur ---
        this.lbNbMoniteurs.setBounds(450, 370, 400, 20);
        this.lbNbMoniteurs.setText("Nombre de moniteurs : " + unTableau.getRowCount());
        this.add(this.lbNbMoniteurs);
    }

    public Object[][] obtenirDonnees() {
        ArrayList<Moniteur> liste = Controleur.selectAllMoniteurs();
        Object[][] matrice = new Object[liste.size()][5];
        int i = 0;
        for (Moniteur m : liste) {
            matrice[i][0] = m.getIdmoniteur();
            matrice[i][1] = m.getNom();
            matrice[i][2] = m.getPrenom();
            matrice[i][3] = m.getEmail();
            matrice[i][4] = m.getTelephone();
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
        }
    }

    public void viderChamps() {
        txtNom.setText(""); txtPrenom.setText("");
        txtEmail.setText(""); txtTelephone.setText("");
        btModifier.setEnabled(false);
        btSupprimer.setEnabled(false);
    }

    public void insertChamps() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty()
                || txtTelephone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir : Nom, Prénom, Téléphone.");
            return;
        }
        Moniteur m = new Moniteur(
                txtNom.getText(), txtPrenom.getText(),
                txtEmail.getText(), txtTelephone.getText());
        Controleur.insertMoniteur(m);
        JOptionPane.showMessageDialog(this, "Moniteur ajouté avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees());
        this.lbNbMoniteurs.setText("Nombre de moniteurs : " + unTableau.getRowCount());
        this.viderChamps();
    }

    public void updateChamps() {
        int row = tableMoniteurs.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        Moniteur m = new Moniteur(id,
                txtNom.getText(), txtPrenom.getText(),
                txtEmail.getText(), txtTelephone.getText());
        Controleur.updateMoniteur(m);
        JOptionPane.showMessageDialog(this, "Moniteur modifié avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees());
        this.viderChamps();
    }

    public void deleteChamps() {
        int row = tableMoniteurs.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        int rep = JOptionPane.showConfirmDialog(this,
                "Supprimer ce moniteur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep == JOptionPane.YES_OPTION) {
            Controleur.deleteMoniteur(id);
            JOptionPane.showMessageDialog(this, "Moniteur supprimé.");
            this.unTableau.setDonnes(this.obtenirDonnees());
            this.lbNbMoniteurs.setText("Nombre de moniteurs : " + unTableau.getRowCount());
            this.viderChamps();
        }
    }
}
