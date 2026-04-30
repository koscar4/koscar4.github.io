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

import controleur.Candidat;
import controleur.Controleur;
import controleur.Tableau;

public class PanelCandidats extends PanelPrincipal implements ActionListener {

    // Formulaire
    private JPanel     panelForm  = new JPanel();
    private JTextField txtNom       = new JTextField();
    private JTextField txtPrenom    = new JTextField();
    private JTextField txtEmail     = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtAdresse   = new JTextField();
    private JTextField txtTypepermis = new JTextField();

    private JButton btAnnuler  = new JButton("Annuler");
    private JButton btValider  = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier  = new JButton("Modifier");

    // Filtre
    private JPanel     panelFiltre = new JPanel();
    private JTextField txtFiltre   = new JTextField();
    private JButton    btFiltrer   = new JButton("Filtrer");

    // Table
    private JTable      tableCandidats;
    private JScrollPane scrollCandidats;
    private Tableau     unTableau;
    private JLabel      lbNbCandidats = new JLabel("");

    public PanelCandidats(String titre) {
        super(titre);

        // --- Panel filtre ---
        this.panelFiltre.setBounds(450, 80, 450, 30);
        this.panelFiltre.setBackground(Color.gray);
        this.panelFiltre.setLayout(new GridLayout(1, 3, 10, 10));
        this.panelFiltre.add(new JLabel("Filtrer :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);

        // --- Panel formulaire ---
        this.panelForm.setBounds(50, 80, 300, 310);
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setLayout(new GridLayout(8, 2, 10, 10));

        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);
        this.panelForm.add(new JLabel("Prénom :"));
        this.panelForm.add(this.txtPrenom);
        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);
        this.panelForm.add(new JLabel("Téléphone :"));
        this.panelForm.add(this.txtTelephone);
        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);
        this.panelForm.add(new JLabel("Type de permis :"));
        this.panelForm.add(this.txtTypepermis);
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
        String[] entetes = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Adresse", "Type permis"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableCandidats = new JTable(this.unTableau);
        this.scrollCandidats = new JScrollPane(this.tableCandidats);
        this.scrollCandidats.setBounds(450, 120, 490, 240);
        this.add(this.scrollCandidats);

        // --- Mouse listener ---
        this.tableCandidats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableCandidats.getSelectedRow();
                if (row >= 0) {
                    txtNom.setText(unTableau.getValueAt(row, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(row, 2).toString());
                    txtEmail.setText(unTableau.getValueAt(row, 3).toString());
                    txtTelephone.setText(unTableau.getValueAt(row, 4).toString());
                    txtAdresse.setText(unTableau.getValueAt(row, 5).toString());
                    txtTypepermis.setText(unTableau.getValueAt(row, 6).toString());
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
                }
            }
        });

        // --- Compteur ---
        this.lbNbCandidats.setBounds(450, 370, 400, 20);
        this.lbNbCandidats.setText("Nombre de candidats : " + unTableau.getRowCount());
        this.add(this.lbNbCandidats);
    }

    // Récupère les données depuis la BDD
    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Candidat> liste = Controleur.selectAllCandidats(filtre);
        Object[][] matrice = new Object[liste.size()][7];
        int i = 0;
        for (Candidat c : liste) {
            matrice[i][0] = c.getIdcandidat();
            matrice[i][1] = c.getNom();
            matrice[i][2] = c.getPrenom();
            matrice[i][3] = c.getEmail();
            matrice[i][4] = c.getTelephone();
            matrice[i][5] = c.getAdresse();
            matrice[i][6] = c.getTypepermis();
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
        txtNom.setText("");        txtPrenom.setText("");
        txtEmail.setText("");      txtTelephone.setText("");
        txtAdresse.setText("");    txtTypepermis.setText("");
        btModifier.setEnabled(false);
        btSupprimer.setEnabled(false);
    }

    public void insertChamps() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty()
                || txtTelephone.getText().isEmpty() || txtTypepermis.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir au moins : Nom, Prénom, Téléphone, Type permis.");
            return;
        }
        Candidat c = new Candidat(
                txtNom.getText(), txtPrenom.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtAdresse.getText(), txtTypepermis.getText());
        Controleur.insertCandidat(c);
        JOptionPane.showMessageDialog(this, "Candidat ajouté avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees(""));
        this.lbNbCandidats.setText("Nombre de candidats : " + unTableau.getRowCount());
        this.viderChamps();
    }

    public void updateChamps() {
        int row = tableCandidats.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        Candidat c = new Candidat(id,
                txtNom.getText(), txtPrenom.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtAdresse.getText(), txtTypepermis.getText());
        Controleur.updateCandidat(c);
        JOptionPane.showMessageDialog(this, "Candidat modifié avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees(""));
        this.viderChamps();
    }

    public void deleteChamps() {
        int row = tableCandidats.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        int rep = JOptionPane.showConfirmDialog(this,
                "Supprimer ce candidat ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep == JOptionPane.YES_OPTION) {
            Controleur.deleteCandidat(id);
            JOptionPane.showMessageDialog(this, "Candidat supprimé.");
            this.unTableau.setDonnes(this.obtenirDonnees(""));
            this.lbNbCandidats.setText("Nombre de candidats : " + unTableau.getRowCount());
            this.viderChamps();
        }
    }
}
