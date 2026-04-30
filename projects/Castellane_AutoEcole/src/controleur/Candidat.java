package controleur;

public class Candidat {
    private int idcandidat;
    private String nom, prenom, email, telephone, adresse, typepermis;

    public Candidat(int idcandidat, String nom, String prenom, String email,
                    String telephone, String adresse, String typepermis) {
        this.idcandidat = idcandidat;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.typepermis = typepermis;
    }

    // Constructeur sans ID (pour l'insertion, l'ID est auto_increment)
    public Candidat(String nom, String prenom, String email,
                    String telephone, String adresse, String typepermis) {
        this.idcandidat = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.typepermis = typepermis;
    }

    public int getIdcandidat()        { return idcandidat; }
    public String getNom()            { return nom; }
    public String getPrenom()         { return prenom; }
    public String getEmail()          { return email; }
    public String getTelephone()      { return telephone; }
    public String getAdresse()        { return adresse; }
    public String getTypepermis()     { return typepermis; }

    public void setIdcandidat(int idcandidat)    { this.idcandidat = idcandidat; }
    public void setNom(String nom)               { this.nom = nom; }
    public void setPrenom(String prenom)         { this.prenom = prenom; }
    public void setEmail(String email)           { this.email = email; }
    public void setTelephone(String telephone)   { this.telephone = telephone; }
    public void setAdresse(String adresse)       { this.adresse = adresse; }
    public void setTypepermis(String typepermis) { this.typepermis = typepermis; }
}
