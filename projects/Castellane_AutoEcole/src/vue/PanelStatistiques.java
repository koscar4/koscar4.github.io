package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.Controleur;

public class PanelStatistiques extends PanelPrincipal implements ActionListener {

    // --- Compteurs globaux ---
    private JLabel lbNbCandidats  = new JLabel("0");
    private JLabel lbNbMoniteurs  = new JLabel("0");
    private JLabel lbNbVehicules  = new JLabel("0");
    private JLabel lbNbLecons     = new JLabel("0");

    // --- Tables détaillées ---
    private DefaultTableModel modelPermis    = new DefaultTableModel(new String[]{"Type de permis", "Candidats"}, 0);
    private DefaultTableModel modelMoniteur  = new DefaultTableModel(new String[]{"Moniteur", "Leçons"}, 0);
    private JTable tablePermis    = new JTable(modelPermis);
    private JTable tableMoniteur  = new JTable(modelMoniteur);

    private JButton btActualiser = new JButton("Actualiser");

    public PanelStatistiques() {
        super("Statistiques générales");

        Font fontChiffre = new Font("SansSerif", Font.BOLD, 28);
        Font fontLibelle = new Font("SansSerif", Font.PLAIN, 13);

        // ----------------------------------------------------------------
        // Cartes de comptage (ligne du haut)
        // ----------------------------------------------------------------
        creerCarte("Candidats",  lbNbCandidats,  fontChiffre, fontLibelle, 30,  55);
        creerCarte("Moniteurs",  lbNbMoniteurs,  fontChiffre, fontLibelle, 260, 55);
        creerCarte("Véhicules",  lbNbVehicules,  fontChiffre, fontLibelle, 490, 55);
        creerCarte("Leçons",     lbNbLecons,     fontChiffre, fontLibelle, 720, 55);

        // ----------------------------------------------------------------
        // Table : Candidats par type de permis
        // ----------------------------------------------------------------
        JLabel lbTitrePermis = new JLabel("Candidats par type de permis");
        lbTitrePermis.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbTitrePermis.setBounds(30, 170, 280, 20);
        this.add(lbTitrePermis);

        tablePermis.setEnabled(false);
        JScrollPane scrollPermis = new JScrollPane(tablePermis);
        scrollPermis.setBounds(30, 195, 420, 160);
        this.add(scrollPermis);

        // ----------------------------------------------------------------
        // Table : Leçons par moniteur
        // ----------------------------------------------------------------
        JLabel lbTitreMoniteur = new JLabel("Leçons par moniteur");
        lbTitreMoniteur.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbTitreMoniteur.setBounds(510, 170, 280, 20);
        this.add(lbTitreMoniteur);

        tableMoniteur.setEnabled(false);
        JScrollPane scrollMoniteur = new JScrollPane(tableMoniteur);
        scrollMoniteur.setBounds(510, 195, 420, 160);
        this.add(scrollMoniteur);

        // ----------------------------------------------------------------
        // Bouton Actualiser
        // ----------------------------------------------------------------
        btActualiser.setBounds(400, 375, 130, 30);
        btActualiser.addActionListener(this);
        this.add(btActualiser);

        chargerStats();
    }

    // Crée une mini-carte avec un grand chiffre et un libellé
    private void creerCarte(String libelle, JLabel lbChiffre, Font fontChiffre, Font fontLibelle, int x, int y) {
        JPanel carte = new JPanel();
        carte.setLayout(null);
        carte.setBounds(x, y, 200, 90);
        carte.setBackground(Color.lightGray);
        carte.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

        lbChiffre.setBounds(0, 10, 200, 40);
        lbChiffre.setHorizontalAlignment(JLabel.CENTER);
        lbChiffre.setFont(fontChiffre);
        lbChiffre.setForeground(new Color(30, 60, 120));

        JLabel lbLib = new JLabel(libelle);
        lbLib.setBounds(0, 55, 200, 20);
        lbLib.setHorizontalAlignment(JLabel.CENTER);
        lbLib.setFont(fontLibelle);

        carte.add(lbChiffre);
        carte.add(lbLib);
        this.add(carte);
    }

    // Charge / recharge toutes les statistiques depuis la BDD
    public void chargerStats() {
        lbNbCandidats.setText(String.valueOf(Controleur.countCandidats()));
        lbNbMoniteurs.setText(String.valueOf(Controleur.countMoniteurs()));
        lbNbVehicules.setText(String.valueOf(Controleur.countVehicules()));
        lbNbLecons.setText(String.valueOf(Controleur.countLecons()));

        // Candidats par permis
        modelPermis.setRowCount(0);
        ArrayList<String[]> parPermis = Controleur.countCandidatsByPermis();
        for (String[] ligne : parPermis) {
            modelPermis.addRow(ligne);
        }

        // Leçons par moniteur
        modelMoniteur.setRowCount(0);
        ArrayList<String[]> parMoniteur = Controleur.countLeconsByMoniteur();
        for (String[] ligne : parMoniteur) {
            modelMoniteur.addRow(ligne);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btActualiser) {
            chargerStats();
        }
    }
}
