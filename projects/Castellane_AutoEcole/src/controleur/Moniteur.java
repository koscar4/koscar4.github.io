package controleur;

public class Moniteur {
    private int idmoniteur;
    private String nom, prenom, email, telephone;

    public Moniteur(int idmoniteur, String nom, String prenom, String email, String telephone) {
        this.idmoniteur = idmoniteur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Constructeur sans ID (insertion)
    public Moniteur(String nom, String prenom, String email, String telephone) {
        this.idmoniteur = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public int getIdmoniteur()     { return idmoniteur; }
    public String getNom()         { return nom; }
    public String getPrenom()      { return prenom; }
    public String getEmail()       { return email; }
    public String getTelephone()   { return telephone; }

    public void setIdmoniteur(int idmoniteur)  { this.idmoniteur = idmoniteur; }
    public void setNom(String nom)             { this.nom = nom; }
    public void setPrenom(String prenom)       { this.prenom = prenom; }
    public void setEmail(String email)         { this.email = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
