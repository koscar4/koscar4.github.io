package controleur;

public class User {
    private int iduser;
    private String nom, prenom, email, mdp;

    public User(int iduser, String nom, String prenom, String email, String mdp) {
        this.iduser = iduser;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    public User(String nom, String prenom, String email, String mdp) {
        this.iduser = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    public int getIduser()    { return iduser; }
    public String getNom()    { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail()  { return email; }
    public String getMdp()    { return mdp; }

    public void setIduser(int iduser)    { this.iduser = iduser; }
    public void setNom(String nom)       { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email)   { this.email = email; }
    public void setMdp(String mdp)       { this.mdp = mdp; }
}
