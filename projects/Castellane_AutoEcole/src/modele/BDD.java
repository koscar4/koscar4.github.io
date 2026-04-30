package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {
    // Classe BDD : établissement de la connexion/déconnexion avec le serveur BDD

    private String user, mdp, bdd, serveur;
    private Connection maConnexion;

    public BDD(String user, String mdp, String bdd, String serveur) {
        this.user = user;
        this.mdp = mdp;
        this.bdd = bdd;
        this.serveur = serveur;
        this.maConnexion = null;
    }

    public void chargerPilote() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exp) {
            System.out.println("Absence du pilote JDBC : " + exp.getMessage());
        }
    }

    public void seConnecter() {
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Paris";
        this.chargerPilote();
        try {
            this.maConnexion = DriverManager.getConnection(url, this.user, this.mdp);
        } catch (SQLException exp) {
            System.out.println("Connexion impossible à " + url + " : " + exp.getMessage());
        }
    }

    public void seDeConnecter() {
        try {
            if (this.maConnexion != null) {
                this.maConnexion.close();
            }
        } catch (SQLException exp) {
            System.out.println("Erreur de déconnexion : " + exp.getMessage());
        }
    }

    public Connection getMaConnexion() {
        return this.maConnexion;
    }
}
