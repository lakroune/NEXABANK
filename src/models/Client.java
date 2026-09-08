package models;

import java.util.HashMap;
import java.util.Map;

public class Client extends Personne {
    private int idClient;
    private Map<String, Compte> comptes;

    public Client(String nom, String prenom, String email, String motDePasse, int idClient) {
        super(nom, prenom, email, motDePasse);
        this.comptes = new HashMap<>();
        this.idClient = idClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Map<String, Compte> getComptes() {
        return comptes;
    }

    public void setComptes(Compte compte) {
        this.comptes.put(compte.getNumCompte(), compte);
    }

    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", motDePasse='" + getMotDePasse() + '\'' +
                '}';
    }

}
