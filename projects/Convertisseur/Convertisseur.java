import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Convertisseur de Devises – Java Swing
 * Devises : EUR, USD, GBP, JPY, CHF, CAD, MAD, DZD
 * Compile : javac Convertisseur.java
 * Lance   : java Convertisseur
 *
 * Taux de référence (avril 2026).
 */
public class Convertisseur extends JFrame {

    // ── Données devises ──────────────────────────────────────
    private static final String[] DEVISES  = {"EUR","USD","GBP","JPY","CHF","CAD","MAD","DZD"};
    private static final String[] SYMBOLES = {"€","$","£","¥","₣","C$","MAD","DZD"};
    // Taux par rapport à 1 EUR
    private static final double[] TAUX_EUR = {1.0, 1.08, 0.856, 162.5, 0.97, 1.47, 10.82, 145.50};

    // ── Composants ───────────────────────────────────────────
    private JTextField   tfMontant;
    private JComboBox<String> cbSource, cbCible;
    private JLabel       lblResultat, lblTaux;
    private JTextArea    taHistorique;

    private final DecimalFormat dfRes  = new DecimalFormat("#,##0.00");
    private final DecimalFormat dfTaux = new DecimalFormat("0.0000");

    // ════════════════════════════════════════════════════════
    public Convertisseur() {
        super("Convertisseur de Devises");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        buildUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(16, 18, 12, 18));
        root.setBackground(new Color(240, 248, 255));
        setContentPane(root);

        root.add(buildTitre(),       BorderLayout.NORTH);
        root.add(buildFormulaire(),  BorderLayout.CENTER);
        root.add(buildHistorique(),  BorderLayout.SOUTH);
    }

    // ── Titre ────────────────────────────────────────────────
    private JLabel buildTitre() {
        JLabel l = new JLabel("Convertisseur de Devises", SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        l.setForeground(new Color(20, 80, 160));
        l.setBorder(new EmptyBorder(0, 0, 10, 0));
        return l;
    }

    // ── Formulaire principal ──────────────────────────────────
    private JPanel buildFormulaire() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Montant
        g.gridy = 0; g.gridx = 0; g.weightx = 0;
        p.add(bold("Montant :"), g);
        tfMontant = new JTextField("100", 14);
        tfMontant.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfMontant.setHorizontalAlignment(JTextField.RIGHT);
        g.gridx = 1; g.weightx = 1;
        p.add(tfMontant, g);

        // Devise source
        g.gridy = 1; g.gridx = 0; g.weightx = 0;
        p.add(bold("De :"), g);
        cbSource = new JComboBox<>(items());
        cbSource.setSelectedIndex(0);
        g.gridx = 1; g.weightx = 1;
        p.add(cbSource, g);

        // Devise cible
        g.gridy = 2; g.gridx = 0; g.weightx = 0;
        p.add(bold("Vers :"), g);
        cbCible = new JComboBox<>(items());
        cbCible.setSelectedIndex(1); // USD par défaut
        g.gridx = 1; g.weightx = 1;
        p.add(cbCible, g);

        // Boutons
        g.gridy = 3; g.gridx = 0; g.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bp.setOpaque(false);
        JButton bConv = btn("Convertir",  new Color(30, 120, 200));
        JButton bInv  = btn("Inverser",   new Color(100, 160, 60));
        JButton bEff  = btn("Effacer",    new Color(180, 60, 60));
        bConv.addActionListener(e -> convertir());
        bInv .addActionListener(e -> inverser());
        bEff .addActionListener(e -> effacer());
        bp.add(bConv); bp.add(bInv); bp.add(bEff);
        p.add(bp, g);

        // Résultat
        g.gridy = 4;
        lblResultat = new JLabel(" ", SwingConstants.CENTER);
        lblResultat.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblResultat.setForeground(new Color(10, 100, 10));
        lblResultat.setOpaque(true);
        lblResultat.setBackground(Color.WHITE);
        lblResultat.setBorder(new CompoundBorder(
            new LineBorder(new Color(180, 210, 240), 1, true),
            new EmptyBorder(10, 10, 10, 10)));
        p.add(lblResultat, g);

        // Taux
        g.gridy = 5;
        lblTaux = new JLabel(" ", SwingConstants.CENTER);
        lblTaux.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblTaux.setForeground(Color.GRAY);
        p.add(lblTaux, g);

        return p;
    }

    // ── Historique ────────────────────────────────────────────
    private JPanel buildHistorique() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        taHistorique = new JTextArea(5, 40);
        taHistorique.setEditable(false);
        taHistorique.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane sc = new JScrollPane(taHistorique);
        sc.setBorder(new TitledBorder("Historique des conversions"));
        p.add(sc);
        return p;
    }

    // ── Logique ───────────────────────────────────────────────
    private void convertir() {
        double montant;
        try {
            montant = Double.parseDouble(tfMontant.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Montant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (montant < 0) {
            JOptionPane.showMessageDialog(this, "Le montant doit être positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int iSrc  = cbSource.getSelectedIndex();
        int iDest = cbCible.getSelectedIndex();

        // src → EUR → dest
        double resultat   = (montant / TAUX_EUR[iSrc]) * TAUX_EUR[iDest];
        double tauxDirect = TAUX_EUR[iDest] / TAUX_EUR[iSrc];

        String res = dfRes.format(montant) + " " + SYMBOLES[iSrc]
                   + "  =  " + dfRes.format(resultat) + " " + SYMBOLES[iDest];
        lblResultat.setText(res);
        lblTaux.setText("1 " + DEVISES[iSrc] + " = " + dfTaux.format(tauxDirect) + " " + DEVISES[iDest]
                       + "  (taux indicatifs – avril 2026)");

        taHistorique.append(res + "\n");
        taHistorique.setCaretPosition(taHistorique.getDocument().getLength());
    }

    private void inverser() {
        int tmp = cbSource.getSelectedIndex();
        cbSource.setSelectedIndex(cbCible.getSelectedIndex());
        cbCible.setSelectedIndex(tmp);
    }

    private void effacer() {
        tfMontant.setText("");
        lblResultat.setText(" ");
        lblTaux.setText(" ");
        tfMontant.requestFocus();
    }

    // ── Helpers ───────────────────────────────────────────────
    private String[] items() {
        String[] a = new String[DEVISES.length];
        for (int i = 0; i < a.length; i++) a[i] = DEVISES[i] + "  " + SYMBOLES[i];
        return a;
    }

    private JLabel bold(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        return l;
    }

    private JButton btn(String t, Color c) {
        JButton b = new JButton(t);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 14, 8, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ── Point d'entrée ────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(Convertisseur::new);
    }
}
