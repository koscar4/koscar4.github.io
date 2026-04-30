package controleur;

public class Lecon {
    private int idlecon;
    private String datelecon, heuredebut, heurefin;
    private int idcandidat, idmoniteur;

    public Lecon(int idlecon, String datelecon, String heuredebut, String heurefin,
                 int idcandidat, int idmoniteur) {
        this.idlecon = idlecon;
        this.datelecon = datelecon;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.idcandidat = idcandidat;
        this.idmoniteur = idmoniteur;
    }

    // Constructeur sans ID (insertion)
    public Lecon(String datelecon, String heuredebut, String heurefin,
                 int idcandidat, int idmoniteur) {
        this.idlecon = 0;
        this.datelecon = datelecon;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.idcandidat = idcandidat;
        this.idmoniteur = idmoniteur;
    }

    public int getIdlecon()        { return idlecon; }
    public String getDatelecon()   { return datelecon; }
    public String getHeuredebut()  { return heuredebut; }
    public String getHeurefin()    { return heurefin; }
    public int getIdcandidat()     { return idcandidat; }
    public int getIdmoniteur()     { return idmoniteur; }

    public void setIdlecon(int idlecon)          { this.idlecon = idlecon; }
    public void setDatelecon(String datelecon)   { this.datelecon = datelecon; }
    public void setHeuredebut(String heuredebut) { this.heuredebut = heuredebut; }
    public void setHeurefin(String heurefin)     { this.heurefin = heurefin; }
    public void setIdcandidat(int idcandidat)    { this.idcandidat = idcandidat; }
    public void setIdmoniteur(int idmoniteur)    { this.idmoniteur = idmoniteur; }
}
