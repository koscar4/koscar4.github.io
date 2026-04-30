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
import controleur.Tableau;
import controleur.Vehicule;

public class PanelVehicules extends PanelPrincipal implements ActionListener {

    private JPanel     panelForm        = new JPanel();
    private JTextField txtMarque         = new JTextField();
    private JTextField txtModele         = new JTextField();
    private JTextField txtImmatriculation = new JTextField();

    private JButton btAnnuler   = new JButton("Annuler");
    private JButton btValider   = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier  = new JButton("Modifier");

    private JPanel     panelFiltre = new JPanel();
    private JTextField txtFiltre   = new JTextField();
    private JButton    btFiltrer   = new JButton("Filtrer");

    private JTable      tableVehicules;
    private JScrollPane scrollVehicules;
    private Tableau     unTableau;
    private JLabel      lbNbVehicules = new JLabel("");

    public PanelVehicules(String titre) {
        super(titre);

        // --- Filtre ---
        this.panelFiltre.setBounds(450, 80, 450, 30);
        this.panelFiltre.setBackground(Color.gray);
        this.panelFiltre.setLayout(new GridLayout(1, 3, 10, 10));
        this.panelFiltre.add(new JLabel("Filtrer :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);

        // --- Formulaire ---
        this.panelForm.setBounds(50, 80, 300, 220);
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setLayout(new GridLayout(5, 2, 10, 10));

        this.panelForm.add(new JLabel("Marque :"));
        this.panelForm.add(this.txtMarque);
        this.panelForm.add(new JLabel("Modèle :"));
        this.panelForm.add(this.txtModele);
        this.panelForm.add(new JLabel("Immatriculation :"));
        this.panelForm.add(this.txtImmatriculation);
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
        String[] entetes = {"ID", "Marque", "Modèle", "Immatriculation"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableVehicules = new JTable(this.unTableau);
        this.scrollVehicules = new JScrollPane(this.tableVehicules);
        this.scrollVehicules.setBounds(450, 120, 490, 240);
        this.add(this.scrollVehicules);

        // --- Mouse listener ---
        this.tableVehicules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableVehicules.getSelectedRow();
                if (row >= 0) {
                    txtMarque.setText(unTableau.getValueAt(row, 1).toString());
                    txtModele.setText(unTableau.getValueAt(row, 2).toString());
                    txtImmatriculation.setText(unTableau.getValueAt(row, 3).toString());
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
                }
            }
        });

        // --- Compteur ---
        this.lbNbVehicules.setBounds(450, 370, 400, 20);
        this.lbNbVehicules.setText("Nombre de véhicules : " + unTableau.getRowCount());
        this.add(this.lbNbVehicules);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Vehicule> liste = Controleur.selectAllVehicules(filtre);
        Object[][] matrice = new Object[liste.size()][4];
        int i = 0;
        for (Vehicule v : liste) {
            matrice[i][0] = v.getIdvehicule();
            matrice[i][1] = v.getMarque();
            matrice[i][2] = v.getModele();
            matrice[i][3] = v.getImmatriculation();
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
        txtMarque.setText(""); txtModele.setText(""); txtImmatriculation.setText("");
        btModifier.setEnabled(false);
        btSupprimer.setEnabled(false);
    }

    public void insertChamps() {
        if (txtMarque.getText().isEmpty() || txtModele.getText().isEmpty()
                || txtImmatriculation.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }
        Vehicule v = new Vehicule(txtMarque.getText(), txtModele.getText(), txtImmatriculation.getText());
        Controleur.insertVehicule(v);
        JOptionPane.showMessageDialog(this, "Véhicule ajouté avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees(""));
        this.lbNbVehicules.setText("Nombre de véhicules : " + unTableau.getRowCount());
        this.viderChamps();
    }

    public void updateChamps() {
        int row = tableVehicules.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        Vehicule v = new Vehicule(id, txtMarque.getText(), txtModele.getText(), txtImmatriculation.getText());
        Controleur.updateVehicule(v);
        JOptionPane.showMessageDialog(this, "Véhicule modifié avec succès.");
        this.unTableau.setDonnes(this.obtenirDonnees(""));
        this.viderChamps();
    }

    public void deleteChamps() {
        int row = tableVehicules.getSelectedRow();
        if (row < 0) return;
        int id = Integer.parseInt(unTableau.getValueAt(row, 0).toString());
        int rep = JOptionPane.showConfirmDialog(this,
                "Supprimer ce véhicule ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep == JOptionPane.YES_OPTION) {
            Controleur.deleteVehicule(id);
            JOptionPane.showMessageDialog(this, "Véhicule supprimé.");
            this.unTableau.setDonnes(this.obtenirDonnees(""));
            this.lbNbVehicules.setText("Nombre de véhicules : " + unTableau.getRowCount());
            this.viderChamps();
        }
    }
}
