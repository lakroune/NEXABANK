package models;

import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private int idTransaction;
    private String type;
    private double montant;
    private String date;
    private Compte idCompteSource;
    private Compte idCompteDestination;

    public Transaction(int idTransaction, String type, double montant, String date, Compte idCompteSource,
            Compte idCompteDestination) {
        this.idTransaction = idTransaction;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.idCompteSource = idCompteSource;
        this.idCompteDestination = idCompteDestination;
    }

    public Transaction(String type, double montant, String date, Compte idCompteSource,
            Compte idCompteDestination) {
        this.idTransaction = ID_GENERATOR.incrementAndGet();
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.idCompteSource = idCompteSource;
        this.idCompteDestination = idCompteDestination;
    }

    /**
     * Return the ID of this transaction
     * 
     * @return the ID of this transaction
     */
    public int getIdTransaction() {
        return idTransaction;
    }

    /**
     * Return the type of this transaction
     * 
     * @return the type of this transaction
     */
    public String getType() {
        return type;
    }

    /**
     * Return the montant of this transaction
     * 
     * @return the montant of this transaction
     */
    public double getMontant() {
        return montant;
    }

    /**
     * Return the date of this transaction
     * 
     * @return the date of this transaction
     */
    public String getDate() {
        return date;
    }

    /**
     * Return the source compte of this transaction
     * 
     * @return the source compte of this transaction
     */
    public Compte getIdCompteSource() {
        return idCompteSource;
    }

    /**
     * Return the destination compte of this transaction
     * 
     * @return the destination compte of this transaction
     */
    public Compte getIdCompteDestination() {
        return idCompteDestination;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdCompteSource(Compte idCompteSource) {
        this.idCompteSource = idCompteSource;
    }

    public void setIdCompteDestination(Compte idCompteDestination) {
        this.idCompteDestination = idCompteDestination;
    }
}
