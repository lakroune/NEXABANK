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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import models.Transaction;

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

    private String formaterDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return "";
        }
        return date.contains("T") ? date.split("T")[0] : date;
    }

    private String formaterMontant(double montant) {
        return String.format("%.0f €", montant);
    }

    private String construireContenuReleve(Client client, Compte compte) {
        StringBuilder contenu = new StringBuilder();
        contenu.append("Date").append(" | ").append("Type").append(" | ").append("Montant").append(" | ")
                .append("Compte Source").append(" | ").append("Compte Destination").append(System.lineSeparator());

        List<Transaction> txs = compte.getTransactions();
        for (Transaction t : txs) {
            String date = formaterDate(t.getDate());
            String type = t.getType() == null ? "" : t.getType();
            String montant = formaterMontant(t.getMontant());
            String source = t.getIdCompteSource() != null ? t.getIdCompteSource().getNumCompte() : "null";
            String destination = t.getIdCompteDestination() != null ? t.getIdCompteDestination().getNumCompte() : "null";

            contenu.append(date).append(" | ")
                    .append(type).append(" | ")
                    .append(montant).append(" | ")
                    .append(source).append(" | ")
                    .append(destination).append(System.lineSeparator());
        }

        return contenu.toString();
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
            System.out.println("Client : " + client.getNom() + " " + client.getPrenom());
            System.out.println("Compte : " + compte.getNumCompte());
            System.out.println("Type : " + compte.getTypeCompte());
            System.out.println("Solde : " + compte.getSolde());
            System.out.println(construireContenuReleve(client, compte));
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void exporterReleve(String numCompte, int idClient, String filepath) throws Exception, IOException {
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
        File file = new File(filepath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(construireContenuReleve(client, compte));
        }
    }

    public String exporterReleveParCompte(String numCompte, int idClient) throws Exception, IOException {
        if (numCompte == null || numCompte.trim().isEmpty()) {
            throw new ParametreInvalideException("Numéro de compte invalide.");
        }
        if (!this.clientsArray.containsKey(idClient)) {
            throw new ClientIntrouvableException("Client introuvable.");
        }
        if (!this.clientsArray.get(idClient).getComptes().containsKey(numCompte)) {
            throw new CompteIntrouvableException("Compte introuvable.");
        }

        String dossier = "releves";
        File dir = new File(dossier);
        dir.mkdirs();

        String path = dossier + File.separator + "releve_" + numCompte.trim() + ".txt";
        exporterReleve(numCompte, idClient, path);
        return path;
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
