package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Comparator;

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

    public List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<>(transactions);
        // Trier par id croissant (ordre d'insertion logique via ID auto-incrémenté)
        Collections.sort(list, Comparator.comparingInt(Transaction::getIdTransaction));
        return Collections.unmodifiableList(list);
    }

    public void addTransaction(Transaction t) {
        if (t != null) {
            this.transactions.add(t);
        }
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
