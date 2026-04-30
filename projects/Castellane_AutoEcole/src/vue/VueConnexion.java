package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.Castellane;
import controleur.Controleur;
import controleur.User;

public class VueConnexion extends JFrame implements ActionListener, KeyListener {

    private JPanel panelForm = new JPanel();
    private JTextField txtEmail     = new JTextField("admin@castellane.fr");
    private JPasswordField txtMdp   = new JPasswordField("admin");
    private JButton btAnnuler       = new JButton("Annuler");
    private JButton btValider       = new JButton("Valider");

    public VueConnexion() {
        this.setTitle("CASTELLANE Auto École – Connexion");
        this.setBounds(300, 10, 300, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);

        // Logo
        ImageIcon uneImage = new ImageIcon("src/images/logo.png");
        JLabel lbLogo = new JLabel(uneImage);
        lbLogo.setBounds(25, 20, 250, 200);
        this.add(lbLogo);

        // Titre
        JLabel lbTitre = new JLabel("CASTELLANE Auto École", JLabel.CENTER);
        lbTitre.setForeground(Color.white);
        lbTitre.setBounds(25, 225, 250, 25);
        this.add(lbTitre);

        // Formulaire
        this.panelForm.setBounds(25, 260, 250, 150);
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setLayout(new GridLayout(3, 2, 5, 5));

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);
        this.panelForm.add(new JLabel("Mot de passe :"));
        this.panelForm.add(this.txtMdp);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        // Écouteurs
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.txtEmail.addKeyListener(this);
        this.txtMdp.addKeyListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        } else if (e.getSource() == this.btValider) {
            this.traitement();
        }
    }

    public void viderChamps() {
        this.txtEmail.setText("");
        this.txtMdp.setText("");
    }

    public void traitement() {
        String email = this.txtEmail.getText().trim();
        String mdp   = new String(this.txtMdp.getPassword()).trim();

        if (email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
        } else {
            User unUser = Controleur.selectWhereUser(email, mdp);
            if (unUser == null) {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
            } else {
                JOptionPane.showMessageDialog(this, "Bienvenue, " + unUser.getNom()
                        + " " + unUser.getPrenom() + " !");
                Castellane.rentreVisibleConnexion(false);
                Castellane.creeDetruireVueGenerale(true);
            }
        }
    }

    @Override public void keyTyped(KeyEvent e)    {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            traitement();
        }
    }
}
