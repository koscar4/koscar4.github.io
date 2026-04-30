import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Jeu de Morpion (Tic-Tac-Toe) – Jeu 2026
 * Modes : 2 Joueurs ou vs IA
 * Compile : javac TicTacToe.java
 * Lance   : java TicTacToe
 */
public class TicTacToe extends JFrame {

    // ── Constantes ──────────────────────────────────────────
    private static final int    TAILLE   = 3;
    private static final String SYMBOLE_X = "X";
    private static final String SYMBOLE_O = "O";

    // ── Composants UI ────────────────────────────────────────
    private JTextField   tfJoueur1, tfJoueur2;
    private JTextArea    taHistorique;
    private JButton[][]  grilleBtn  = new JButton[TAILLE][TAILLE];
    private JRadioButton rbDeuxJoueurs, rbIA;
    private JLabel       lblScore, lblTour;

    // ── État du jeu ──────────────────────────────────────────
    private String[][]   grille         = new String[TAILLE][TAILLE];
    private boolean      tourX          = true;
    private int          scoreJ1        = 0;
    private int          scoreJ2        = 0;
    private int          nbCoups        = 0;
    private boolean      partieTerminee = false;

    // ════════════════════════════════════════════════════════
    public TicTacToe() {
        super("Jeu 2026 – Morpion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initUI();
        reinitGrille();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Construction de l'interface ──────────────────────────
    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));
        root.setBackground(new Color(245, 245, 240));
        setContentPane(root);

        root.add(panneauNord(),   BorderLayout.NORTH);
        root.add(panneauCentre(), BorderLayout.CENTER);
        root.add(panneauSud(),    BorderLayout.SOUTH);
    }

    // ── Panneau NORD : noms des joueurs + mode de jeu ────────
    private JPanel panneauNord() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(4, 6, 4, 6);
        g.anchor  = GridBagConstraints.WEST;
        g.fill    = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titre = new JLabel("Jeu 2026 – Morpion");
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(new Color(40, 40, 40));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        p.add(titre, g);
        g.gridwidth = 1;

        // Joueur 1 (label + textfield)
        g.gridy = 1; g.gridx = 0; g.weightx = 0;
        p.add(new JLabel("Joueur 1 :"), g);
        tfJoueur1 = new JTextField("Alice", 12);
        g.gridx = 1; g.weightx = 0.5;
        p.add(tfJoueur1, g);

        // Joueur 2 (label + textfield)
        g.gridy = 2; g.gridx = 0; g.weightx = 0;
        p.add(new JLabel("Joueur 2 :"), g);
        tfJoueur2 = new JTextField("Bob", 12);
        g.gridx = 1; g.weightx = 0.5;
        p.add(tfJoueur2, g);

        // Mode (radio buttons)
        JLabel lblMode = new JLabel("Mode :");
        g.gridy = 1; g.gridx = 2; g.weightx = 0;
        p.add(lblMode, g);

        rbDeuxJoueurs = new JRadioButton("2 Joueurs", true);
        rbIA          = new JRadioButton("vs IA");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbDeuxJoueurs);
        bg.add(rbIA);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        modePanel.setOpaque(false);
        modePanel.add(rbDeuxJoueurs);
        modePanel.add(rbIA);
        g.gridx = 3;
        p.add(modePanel, g);

        // Tour + Score
        lblTour  = new JLabel("Tour : X");
        lblScore = new JLabel("Score : 0 – 0");
        lblTour.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblScore.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblScore.setForeground(new Color(180, 50, 50));

        g.gridy = 2; g.gridx = 2; g.gridwidth = 2;
        p.add(lblTour, g);
        g.gridy = 3;
        p.add(lblScore, g);

        return p;
    }

    // ── Panneau CENTRE : historique + grille 3×3 ─────────────
    private JPanel panneauCentre() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(6, 6, 6, 6);
        g.fill    = GridBagConstraints.BOTH;
        g.weighty = 1.0;

        // Historique – textarea dans un scrollpane
        taHistorique = new JTextArea(10, 18);
        taHistorique.setEditable(false);
        taHistorique.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(taHistorique);
        scroll.setBorder(new TitledBorder("Historique des coups"));
        g.gridx = 0; g.weightx = 0.4;
        p.add(scroll, g);

        // Grille de jeu
        JPanel grillePan = new JPanel(new GridLayout(TAILLE, TAILLE, 4, 4));
        grillePan.setBackground(new Color(60, 120, 60));
        grillePan.setBorder(new LineBorder(new Color(60, 120, 60), 4));

        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                JButton btn = new JButton();
                btn.setFont(new Font("SansSerif", Font.BOLD, 30));
                btn.setBackground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setPreferredSize(new Dimension(90, 90));
                final int row = r, col = c;
                btn.addActionListener(e -> jouer(row, col));
                grilleBtn[r][c] = btn;
                grillePan.add(btn);
            }
        }

        g.gridx = 1; g.weightx = 0.6;
        p.add(grillePan, g);
        return p;
    }

    // ── Panneau SUD : boutons Save / Rejouer / Quitter ───────
    private JPanel panneauSud() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 6));
        p.setOpaque(false);

        JButton btnSave    = creerBouton("💾 Save",    new Color(70, 130, 180));
        JButton btnRejouer = creerBouton("🔄 Rejouer", new Color(60, 160, 80));
        JButton btnQuitter = creerBouton("🚪 Quitter", new Color(200, 60, 60));

        btnSave.addActionListener(e -> sauvegarder());
        btnRejouer.addActionListener(e -> nouvellePartie());
        btnQuitter.addActionListener(e -> {
            int rep = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment quitter ?", "Quitter",
                JOptionPane.YES_NO_OPTION);
            if (rep == JOptionPane.YES_OPTION) System.exit(0);
        });

        p.add(btnSave);
        p.add(btnRejouer);
        p.add(btnQuitter);
        return p;
    }

    private JButton creerBouton(String texte, Color fond) {
        JButton b = new JButton(texte);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBackground(fond);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ── Logique : jouer un coup ───────────────────────────────
    private void jouer(int row, int col) {
        if (partieTerminee || grille[row][col] != null) return;

        String sym = tourX ? SYMBOLE_X : SYMBOLE_O;
        grille[row][col] = sym;
        nbCoups++;

        // Affichage dans la grille
        grilleBtn[row][col].setText(sym);
        grilleBtn[row][col].setForeground(
            sym.equals(SYMBOLE_X) ? new Color(200, 50, 50) : new Color(30, 80, 200));

        // Ajout dans l'historique
        String nom = nomJoueur(sym);
        taHistorique.append(String.format("%-12s (%d,%d)%n", nom, row + 1, col + 1));

        // Victoire ?
        if (verifierVictoire(sym)) {
            partieTerminee = true;
            surligner(sym);
            if (sym.equals(SYMBOLE_X)) scoreJ1++; else scoreJ2++;
            mettreAJourScore();
            JOptionPane.showMessageDialog(this,
                "🎉 " + nom + " a gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Match nul ?
        if (nbCoups == TAILLE * TAILLE) {
            partieTerminee = true;
            JOptionPane.showMessageDialog(this,
                "Match nul !", "Égalité", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Changer de joueur
        tourX = !tourX;
        lblTour.setText("Tour : " + (tourX ? SYMBOLE_X : SYMBOLE_O));

        // IA joue automatiquement si mode activé et c'est son tour
        if (rbIA.isSelected() && !tourX && !partieTerminee) {
            Timer t = new Timer(300, e -> jouerIA());
            t.setRepeats(false);
            t.start();
        }
    }

    // ── IA : stratégie gagner > bloquer > centre > coin ──────
    private void jouerIA() {
        int[] c = chercherCoup(SYMBOLE_O);      // peut gagner ?
        if (c == null) c = chercherCoup(SYMBOLE_X); // peut bloquer ?
        if (c == null && grille[1][1] == null) c = new int[]{1, 1}; // centre
        if (c == null) {
            int[][] coins = {{0,0},{0,2},{2,0},{2,2}};
            for (int[] co : coins) {
                if (grille[co[0]][co[1]] == null) { c = co; break; }
            }
        }
        if (c == null) { // case libre quelconque
            outer:
            for (int r = 0; r < TAILLE; r++)
                for (int col = 0; col < TAILLE; col++)
                    if (grille[r][col] == null) { c = new int[]{r, col}; break outer; }
        }
        if (c != null) jouer(c[0], c[1]);
    }

    /** Cherche un coup gagnant (ou à bloquer) pour le symbole donné. */
    private int[] chercherCoup(String sym) {
        for (int r = 0; r < TAILLE; r++)
            for (int c = 0; c < TAILLE; c++)
                if (grille[r][c] == null) {
                    grille[r][c] = sym;
                    boolean gagne = verifierVictoire(sym);
                    grille[r][c] = null;
                    if (gagne) return new int[]{r, c};
                }
        return null;
    }

    /** Vérifie si le symbole donné a gagné. */
    private boolean verifierVictoire(String sym) {
        for (int i = 0; i < TAILLE; i++) {
            // Ligne i
            if (sym.equals(grille[i][0]) && sym.equals(grille[i][1]) && sym.equals(grille[i][2])) return true;
            // Colonne i
            if (sym.equals(grille[0][i]) && sym.equals(grille[1][i]) && sym.equals(grille[2][i])) return true;
        }
        // Diagonales
        if (sym.equals(grille[0][0]) && sym.equals(grille[1][1]) && sym.equals(grille[2][2])) return true;
        if (sym.equals(grille[0][2]) && sym.equals(grille[1][1]) && sym.equals(grille[2][0])) return true;
        return false;
    }

    /** Colorie en jaune les cases de la combinaison gagnante. */
    private void surligner(String sym) {
        Color jaune = new Color(255, 220, 50);
        for (int i = 0; i < TAILLE; i++) {
            if (sym.equals(grille[i][0]) && sym.equals(grille[i][1]) && sym.equals(grille[i][2])) {
                for (int c = 0; c < TAILLE; c++) grilleBtn[i][c].setBackground(jaune); return;
            }
            if (sym.equals(grille[0][i]) && sym.equals(grille[1][i]) && sym.equals(grille[2][i])) {
                for (int r = 0; r < TAILLE; r++) grilleBtn[r][i].setBackground(jaune); return;
            }
        }
        if (sym.equals(grille[0][0]) && sym.equals(grille[1][1]) && sym.equals(grille[2][2])) {
            for (int i = 0; i < TAILLE; i++) grilleBtn[i][i].setBackground(jaune);
        } else if (sym.equals(grille[0][2]) && sym.equals(grille[1][1]) && sym.equals(grille[2][0])) {
            grilleBtn[0][2].setBackground(jaune);
            grilleBtn[1][1].setBackground(jaune);
            grilleBtn[2][0].setBackground(jaune);
        }
    }

    private String nomJoueur(String sym) {
        if (sym.equals(SYMBOLE_X)) {
            String n = tfJoueur1.getText().trim();
            return n.isEmpty() ? "Joueur 1" : n;
        } else {
            if (rbIA.isSelected()) return "IA";
            String n = tfJoueur2.getText().trim();
            return n.isEmpty() ? "Joueur 2" : n;
        }
    }

    private void mettreAJourScore() {
        lblScore.setText("Score : " + scoreJ1 + " – " + scoreJ2);
    }

    // ── Nouvelle partie (scores conservés) ───────────────────
    private void nouvellePartie() {
        reinitGrille();
        taHistorique.append("── Nouvelle partie ──\n");
    }

    private void reinitGrille() {
        for (int r = 0; r < TAILLE; r++)
            for (int c = 0; c < TAILLE; c++) {
                grille[r][c] = null;
                grilleBtn[r][c].setText("");
                grilleBtn[r][c].setBackground(Color.WHITE);
            }
        tourX = true; nbCoups = 0; partieTerminee = false;
        lblTour.setText("Tour : X");
    }

    // ── Sauvegarde de l'historique dans un fichier texte ─────
    private void sauvegarder() {
        try (PrintWriter pw = new PrintWriter("historique.txt")) {
            pw.println("=== Historique – Jeu 2026 ===");
            pw.print(taHistorique.getText());
            JOptionPane.showMessageDialog(this,
                "Sauvegardé dans historique.txt", "Sauvegarde OK",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Point d'entrée ────────────────────────────────────────
    public static void main(String[] args) {
        // Essaie d'utiliser le look & feel du système
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
