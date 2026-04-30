package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Candidat;
import controleur.Lecon;
import controleur.Moniteur;
import controleur.User;
import controleur.Vehicule;

public class Modele {

    // -----------------------------------------------------------------------
    // Connexion BDD  — modifier les identifiants si besoin
    // -----------------------------------------------------------------------
    private static BDD uneBdd = new BDD(
            "root",                  // utilisateur MySQL
            "root",                  // mot de passe MySQL XAMPP
            "castellane_autoecole",  // nom de la base
            "localhost:3306"         // serveur:port
    );

    // -----------------------------------------------------------------------
    // Méthode utilitaire : exécuter une requête sans résultat
    // -----------------------------------------------------------------------
    public static void executerRequete(String requete) {
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            unStat.execute(requete);
            unStat.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur requête : " + requete + " → " + exp.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    // USER
    // -----------------------------------------------------------------------
    public static User selectWhereUser(String email, String mdp) {
        User unUser = null;
        String requete = "SELECT * FROM user WHERE email = ? AND mdp = ?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
            ps.setString(1, email);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                unUser = new User(
                        rs.getInt("iduser"),
                        rs.getString("nom"),     // colonne 'nom' dans la table user
                        rs.getString("prenom"),  // colonne 'prenom' dans la table user
                        rs.getString("email"),
                        rs.getString("mdp")      // colonne 'mdp' dans la table user
                );
            }
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur selectWhereUser : " + exp.getMessage());
        }
        return unUser;
    }

    // -----------------------------------------------------------------------
    // CANDIDAT
    // -----------------------------------------------------------------------
    public static void insertCandidat(Candidat c) {
        String req = "INSERT INTO candidat (nom, prenom, email, telephone, adresse, typepermis) VALUES (?,?,?,?,?,?)";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse());
            ps.setString(6, c.getTypepermis());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur insertCandidat : " + exp.getMessage());
        }
    }

    public static void updateCandidat(Candidat c) {
        String req = "UPDATE candidat SET nom=?, prenom=?, email=?, telephone=?, adresse=?, typepermis=? WHERE idcandidat=?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse());
            ps.setString(6, c.getTypepermis());
            ps.setInt(7, c.getIdcandidat());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur updateCandidat : " + exp.getMessage());
        }
    }

    public static void deleteCandidat(int idcandidat) {
        String req = "DELETE FROM candidat WHERE idcandidat = ?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setInt(1, idcandidat);
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur deleteCandidat : " + exp.getMessage());
        }
    }

    public static ArrayList<Candidat> selectAllCandidats(String filtre) {
        ArrayList<Candidat> liste = new ArrayList<>();
        String req;
        if (filtre.equals("")) {
            req = "SELECT * FROM candidat";
        } else {
            req = "SELECT * FROM candidat WHERE nom LIKE '%" + filtre + "%'"
                    + " OR prenom LIKE '%" + filtre + "%'"
                    + " OR email LIKE '%" + filtre + "%'"
                    + " OR typepermis LIKE '%" + filtre + "%'";
        }
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                liste.add(new Candidat(
                        rs.getInt("idcandidat"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("adresse"),
                        rs.getString("typepermis")
                ));
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur selectAllCandidats : " + exp.getMessage());
        }
        return liste;
    }

    // -----------------------------------------------------------------------
    // MONITEUR
    // -----------------------------------------------------------------------
    public static void insertMoniteur(Moniteur m) {
        String req = "INSERT INTO moniteur (nom, prenom, email, telephone) VALUES (?,?,?,?)";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getTelephone());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur insertMoniteur : " + exp.getMessage());
        }
    }

    public static void updateMoniteur(Moniteur m) {
        String req = "UPDATE moniteur SET nom=?, prenom=?, email=?, telephone=? WHERE idmoniteur=?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getTelephone());
            ps.setInt(5, m.getIdmoniteur());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur updateMoniteur : " + exp.getMessage());
        }
    }

    public static void deleteMoniteur(int idmoniteur) {
        String req = "DELETE FROM moniteur WHERE idmoniteur = ?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setInt(1, idmoniteur);
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur deleteMoniteur : " + exp.getMessage());
        }
    }

    public static ArrayList<Moniteur> selectAllMoniteurs() {
        ArrayList<Moniteur> liste = new ArrayList<>();
        String req = "SELECT * FROM moniteur";
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                liste.add(new Moniteur(
                        rs.getInt("idmoniteur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                ));
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur selectAllMoniteurs : " + exp.getMessage());
        }
        return liste;
    }

    // -----------------------------------------------------------------------
    // VEHICULE
    // -----------------------------------------------------------------------
    public static void insertVehicule(Vehicule v) {
        String req = "INSERT INTO vehicule (marque, modele, immatriculation) VALUES (?,?,?)";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getImmatriculation());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur insertVehicule : " + exp.getMessage());
        }
    }

    public static void updateVehicule(Vehicule v) {
        String req = "UPDATE vehicule SET marque=?, modele=?, immatriculation=? WHERE idvehicule=?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getImmatriculation());
            ps.setInt(4, v.getIdvehicule());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur updateVehicule : " + exp.getMessage());
        }
    }

    public static void deleteVehicule(int idvehicule) {
        String req = "DELETE FROM vehicule WHERE idvehicule = ?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setInt(1, idvehicule);
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur deleteVehicule : " + exp.getMessage());
        }
    }

    public static ArrayList<Vehicule> selectAllVehicules(String filtre) {
        ArrayList<Vehicule> liste = new ArrayList<>();
        String req;
        if (filtre.equals("")) {
            req = "SELECT * FROM vehicule";
        } else {
            req = "SELECT * FROM vehicule WHERE marque LIKE '%" + filtre + "%'"
                    + " OR modele LIKE '%" + filtre + "%'"
                    + " OR immatriculation LIKE '%" + filtre + "%'";
        }
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                liste.add(new Vehicule(
                        rs.getInt("idvehicule"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getString("immatriculation")
                ));
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur selectAllVehicules : " + exp.getMessage());
        }
        return liste;
    }

    // -----------------------------------------------------------------------
    // LECON
    // -----------------------------------------------------------------------
    public static void insertLecon(Lecon l) {
        String req = "INSERT INTO lecon (datelecon, heuredebut, heurefin, idcandidat, idmoniteur) VALUES (?,?,?,?,?)";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, l.getDatelecon());
            ps.setString(2, l.getHeuredebut());
            ps.setString(3, l.getHeurefin());
            ps.setInt(4, l.getIdcandidat());
            ps.setInt(5, l.getIdmoniteur());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur insertLecon : " + exp.getMessage());
        }
    }

    public static void updateLecon(Lecon l) {
        String req = "UPDATE lecon SET datelecon=?, heuredebut=?, heurefin=?, idcandidat=?, idmoniteur=? WHERE idlecon=?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setString(1, l.getDatelecon());
            ps.setString(2, l.getHeuredebut());
            ps.setString(3, l.getHeurefin());
            ps.setInt(4, l.getIdcandidat());
            ps.setInt(5, l.getIdmoniteur());
            ps.setInt(6, l.getIdlecon());
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur updateLecon : " + exp.getMessage());
        }
    }

    public static void deleteLecon(int idlecon) {
        String req = "DELETE FROM lecon WHERE idlecon = ?";
        try {
            uneBdd.seConnecter();
            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(req);
            ps.setInt(1, idlecon);
            ps.execute();
            ps.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur deleteLecon : " + exp.getMessage());
        }
    }

    public static ArrayList<Lecon> selectAllLecons(String filtre) {
        ArrayList<Lecon> liste = new ArrayList<>();
        String req;
        if (filtre.equals("")) {
            req = "SELECT * FROM lecon";
        } else {
            req = "SELECT * FROM lecon WHERE datelecon LIKE '%" + filtre + "%'";
        }
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                liste.add(new Lecon(
                        rs.getInt("idlecon"),
                        rs.getString("datelecon"),
                        rs.getString("heuredebut"),
                        rs.getString("heurefin"),
                        rs.getInt("idcandidat"),
                        rs.getInt("idmoniteur")
                ));
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur selectAllLecons : " + exp.getMessage());
        }
        return liste;
    }

    // -----------------------------------------------------------------------
    // STATISTIQUES
    // -----------------------------------------------------------------------
    public static int countCandidats() {
        int count = 0;
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM candidat");
            if (rs.next()) count = rs.getInt(1);
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countCandidats : " + exp.getMessage());
        }
        return count;
    }

    public static int countMoniteurs() {
        int count = 0;
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM moniteur");
            if (rs.next()) count = rs.getInt(1);
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countMoniteurs : " + exp.getMessage());
        }
        return count;
    }

    public static int countVehicules() {
        int count = 0;
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM vehicule");
            if (rs.next()) count = rs.getInt(1);
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countVehicules : " + exp.getMessage());
        }
        return count;
    }

    public static int countLecons() {
        int count = 0;
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM lecon");
            if (rs.next()) count = rs.getInt(1);
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countLecons : " + exp.getMessage());
        }
        return count;
    }

    // Candidats par type de permis : retourne { typepermis, nb }
    public static ArrayList<String[]> countCandidatsByPermis() {
        ArrayList<String[]> liste = new ArrayList<>();
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT UPPER(typepermis) AS typepermis, COUNT(*) AS nb FROM candidat GROUP BY UPPER(typepermis) ORDER BY nb DESC"
            );
            while (rs.next()) {
                liste.add(new String[]{ rs.getString("typepermis"), String.valueOf(rs.getInt("nb")) });
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countCandidatsByPermis : " + exp.getMessage());
        }
        return liste;
    }

    // Leçons par moniteur : retourne { nom + prenom, nb }
    public static ArrayList<String[]> countLeconsByMoniteur() {
        ArrayList<String[]> liste = new ArrayList<>();
        try {
            uneBdd.seConnecter();
            Statement st = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT m.nom, m.prenom, COUNT(l.idlecon) AS nb " +
                "FROM moniteur m LEFT JOIN lecon l ON m.idmoniteur = l.idmoniteur " +
                "GROUP BY m.idmoniteur, m.nom, m.prenom ORDER BY nb DESC"
            );
            while (rs.next()) {
                String moniteur = rs.getString("nom") + " " + rs.getString("prenom");
                liste.add(new String[]{ moniteur, String.valueOf(rs.getInt("nb")) });
            }
            st.close();
            uneBdd.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur countLeconsByMoniteur : " + exp.getMessage());
        }
        return liste;
    }
}
