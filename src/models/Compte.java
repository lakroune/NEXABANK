package models;

import java.util.HashSet;

public class Compte {
    private String numCompte;
    private double solde;
    private String typeCompte;

    private HashSet<Transaction> transactions ;
    public Compte(String numCompte, double solde, String typeCompte) {
        this.numCompte = numCompte;
        this.solde = solde;
        this.typeCompte = typeCompte;
        this.transactions = new HashSet<>();
    }

    public String getNumCompte() {
        return numCompte;
    }

    public double getSolde() {
        return solde;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    public String toString() {
        return "Compte{" +
                "numCompte=" + numCompte +
                ", solde=" + solde +
                ", typeCompte='" + typeCompte + '\'' +
                '}';
    }

}
