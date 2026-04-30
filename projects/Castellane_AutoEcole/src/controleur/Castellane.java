package controleur;

import vue.VueConnexion;
import vue.VueGenerale;

public class Castellane {

    private static VueConnexion uneVueConnexion;
    private static VueGenerale  uneVueGenerale;

    public static void main(String[] args) {
        uneVueConnexion = new VueConnexion();
    }

    public static void rentreVisibleConnexion(boolean action) {
        uneVueConnexion.setVisible(action);
    }

    public static void creeDetruireVueGenerale(boolean action) {
        if (action) {
            uneVueGenerale = new VueGenerale();
        } else {
            uneVueGenerale.dispose();
        }
    }
}
