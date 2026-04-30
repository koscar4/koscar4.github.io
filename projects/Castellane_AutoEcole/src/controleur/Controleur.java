package controleur;

import java.util.ArrayList;
import modele.Modele;

public class Controleur {

    // -----------------------------------------------------------------------
    // USER
    // -----------------------------------------------------------------------
    public static User selectWhereUser(String email, String mdp) {
        return Modele.selectWhereUser(email, mdp);
    }

    // -----------<------------------------------------------------------------
    // CANDIDAT
    // -----------------------------------------------------------------------
    public static void insertCandidat(Candidat c) {
        Modele.insertCandidat(c);
    }

    public static void updateCandidat(Candidat c) {
        Modele.updateCandidat(c);
    }

    public static void deleteCandidat(int idcandidat) {
        Modele.deleteCandidat(idcandidat);
    }

    public static ArrayList<Candidat> selectAllCandidats(String filtre) {
        return Modele.selectAllCandidats(filtre);
    }

    // -----------------------------------------------------------------------
    // MONITEUR
    // -----------------------------------------------------------------------
    public static void insertMoniteur(Moniteur m) {
        Modele.insertMoniteur(m);
    }

    public static void updateMoniteur(Moniteur m) {
        Modele.updateMoniteur(m);
    }

    public static void deleteMoniteur(int idmoniteur) {
        Modele.deleteMoniteur(idmoniteur);
    }

    public static ArrayList<Moniteur> selectAllMoniteurs() {
        return Modele.selectAllMoniteurs();
    }

    // -----------------------------------------------------------------------
    // VEHICULE
    // -----------------------------------------------------------------------
    public static void insertVehicule(Vehicule v) {
        Modele.insertVehicule(v);
    }

    public static void updateVehicule(Vehicule v) {
        Modele.updateVehicule(v);
    }

    public static void deleteVehicule(int idvehicule) {
        Modele.deleteVehicule(idvehicule);
    }

    public static ArrayList<Vehicule> selectAllVehicules(String filtre) {
        return Modele.selectAllVehicules(filtre);
    }

    // -----------------------------------------------------------------------
    // LECON
    // -----------------------------------------------------------------------
    public static void insertLecon(Lecon l) {
        Modele.insertLecon(l);
    }

    public static void updateLecon(Lecon l) {
        Modele.updateLecon(l);
    }

    public static void deleteLecon(int idlecon) {
        Modele.deleteLecon(idlecon);
    }

    public static ArrayList<Lecon> selectAllLecons(String filtre) {
        return Modele.selectAllLecons(filtre);
    }

    // -----------------------------------------------------------------------
    // STATISTIQUES
    // -----------------------------------------------------------------------
    public static int countCandidats()  { return Modele.countCandidats(); }
    public static int countMoniteurs()  { return Modele.countMoniteurs(); }
    public static int countVehicules()  { return Modele.countVehicules(); }
    public static int countLecons()     { return Modele.countLecons(); }

    public static ArrayList<String[]> countCandidatsByPermis()  { return Modele.countCandidatsByPermis(); }
    public static ArrayList<String[]> countLeconsByMoniteur()   { return Modele.countLeconsByMoniteur(); }
}
