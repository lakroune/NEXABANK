package serveces;

import java.util.HashMap;
import java.util.Map;

import exception.ClientDejaExistantException;
import exception.ClientIntrouvableException;
import exception.CompteDejaExistantException;
import exception.CompteIntrouvableException;
import exception.ParametreInvalideException;
import models.Client;
import models.Compte;

public class CompteService {

    Map<Integer, Client> clientsArray;

    public CompteService() {
        this.clientsArray = new HashMap<>();
    }

    public Client login(String email, String motDePasse) {
        for (Client client : this.clientsArray.values()) {
            if (client.getEmail().equals(email) && client.getMotDePasse().equals(motDePasse)) {
                return client;
            }
        }
        return null;
    }

    public boolean ajouterCompte(Client client) {
        try {
            if (client == null) {
                throw new ParametreInvalideException("Client invalide.");
            }
            if (this.clientsArray.containsKey(client.getIdClient())) {
                throw new ClientDejaExistantException("Client déjà existant.");
            }
            this.clientsArray.put(client.getIdClient(), client);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierCompte(Client client) {
        try {
            if (client == null) {
                throw new ParametreInvalideException("Client invalide.");
            }
            if (!this.clientsArray.containsKey(client.getIdClient())) {
                throw new ClientIntrouvableException("Client introuvable.");
            }
            this.clientsArray.put(client.getIdClient(), client);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public void display() {
        if (this.clientsArray.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }
        for (Client client : this.clientsArray.values()) {
            System.out.println(client.toString());
        }
    }

    public Map<Integer, Client> getClientsArray() {
        return this.clientsArray;
    }

    public Compte getCompteParNumero(String numCompte) {
        if (numCompte == null || numCompte.trim().isEmpty()) {
            return null;
        }

        String compteRecherche = numCompte.trim();
        for (Client client : this.clientsArray.values()) {
            if (client.getComptes().containsKey(compteRecherche)) {
                return client.getComptes().get(compteRecherche);
            }
        }
        return null;
    }

    public boolean ajouterCompte(Compte eCompte, int idClient) {
        try {
            if (eCompte == null
                    || eCompte.getNumCompte() == null
                    || eCompte.getNumCompte().trim().isEmpty()) {
                throw new ParametreInvalideException("Compte invalide.");
            }
            if (!this.clientsArray.containsKey(idClient)) {
                throw new ClientIntrouvableException("Client introuvable pour ce compte.");
            }
            if (this.clientsArray.get(idClient).getComptes().containsKey(eCompte.getNumCompte())) {
                throw new CompteDejaExistantException("Compte déjà existant pour ce client.");
            }

            this.clientsArray.get(idClient).setComptes(eCompte);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public boolean cluturerCompte(String numCompte, int idClient) {
        try {
            if (numCompte == null || numCompte.trim().isEmpty()) {
                throw new ParametreInvalideException("Numéro de compte invalide.");
            }
            if (!this.clientsArray.containsKey(idClient)) {
                throw new ClientIntrouvableException("Client introuvable.");
            }
            if (!this.clientsArray.get(idClient).getComptes().containsKey(numCompte)) {
                throw new CompteIntrouvableException("Compte introuvable.");
            }

            this.clientsArray.get(idClient).getComptes().remove(numCompte);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public void consulterReleve(String numCompte, int idClient) {
        try {
            if (numCompte == null || numCompte.trim().isEmpty()) {
                throw new ParametreInvalideException("Numéro de compte invalide.");
            }
            if (!this.clientsArray.containsKey(idClient)) {
                throw new ClientIntrouvableException("Client introuvable.");
            }
            if (!this.clientsArray.get(idClient).getComptes().containsKey(numCompte)) {
                throw new CompteIntrouvableException("Compte introuvable.");
            }

            Client client = this.clientsArray.get(idClient);
            Compte compte = client.getComptes().get(numCompte);
            System.out.println("--- Relevé de compte ---");
            System.out.println("nom : " + client.getNom());
            System.out.println("solde : " + compte.getSolde());
            System.out.println("numCompte : " + compte.getNumCompte());
            System.out.println("typeCompte : " + compte.getTypeCompte());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void listerComptes() {
        System.out.println("--- Liste des comptes ---");

        if (this.clientsArray.isEmpty()) {
            System.out.println("Aucun compte trouvé.");
            return;
        }
        for (Client client : this.clientsArray.values()) {
            for (Compte compte : client.getComptes().values()) {
                System.out.print("-> Nom : " + client.getNom());
                System.out.print(" <*> Num compte : " + compte.getNumCompte());
                System.out.print(" <*> Type compte : " + compte.getTypeCompte());
                System.out.println();
            }
        }
    }

}
