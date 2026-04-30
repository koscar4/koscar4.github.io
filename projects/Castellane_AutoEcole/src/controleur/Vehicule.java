package controleur;

public class Vehicule {
    private int idvehicule;
    private String marque, modele, immatriculation;

    public Vehicule(int idvehicule, String marque, String modele, String immatriculation) {
        this.idvehicule = idvehicule;
        this.marque = marque;
        this.modele = modele;
        this.immatriculation = immatriculation;
    }

    // Constructeur sans ID (insertion)
    public Vehicule(String marque, String modele, String immatriculation) {
        this.idvehicule = 0;
        this.marque = marque;
        this.modele = modele;
        this.immatriculation = immatriculation;
    }

    public int getIdvehicule()          { return idvehicule; }
    public String getMarque()           { return marque; }
    public String getModele()           { return modele; }
    public String getImmatriculation()  { return immatriculation; }

    public void setIdvehicule(int idvehicule)             { this.idvehicule = idvehicule; }
    public void setMarque(String marque)                  { this.marque = marque; }
    public void setModele(String modele)                  { this.modele = modele; }
    public void setImmatriculation(String immatriculation){ this.immatriculation = immatriculation; }
}
