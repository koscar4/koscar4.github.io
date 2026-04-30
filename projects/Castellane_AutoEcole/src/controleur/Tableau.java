package controleur;

import javax.swing.table.AbstractTableModel;

public class Tableau extends AbstractTableModel {

    private Object[][] donnees;
    private String[] entetes;

    public Tableau(Object[][] donnees, String[] entetes) {
        this.donnees = donnees;
        this.entetes = entetes;
    }

    @Override
    public int getRowCount()        { return this.donnees.length; }

    @Override
    public int getColumnCount()     { return this.entetes.length; }

    @Override
    public Object getValueAt(int i, int j) { return this.donnees[i][j]; }

    @Override
    public String getColumnName(int i)     { return this.entetes[i]; }

    public void setDonnes(Object[][] matrice) {
        this.donnees = matrice;
        this.fireTableDataChanged();
    }
}
