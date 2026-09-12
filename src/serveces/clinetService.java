package serveces;

import exception.CompteException;
import exception.CompteIntrouvableException;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import models.Compte;
import models.Transaction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class clinetService {

    private void enregistrerReleveCompte(Compte compte) {
        if (compte == null || compte.getNumCompte() == null || compte.getNumCompte().trim().isEmpty()) {
            return;
        }

        String dossier = "releves";
        File dir = new File(dossier);
        dir.mkdirs();

        String fichier = dossier + File.separator + "releve_" + compte.getNumCompte().trim() + ".txt";
        List<Transaction> transactions = compte.getTransactions();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, false))) {
            writer.write("Date | Type | Montant | Compte Source | Compte Destination");
            writer.newLine();
            for (Transaction t : transactions) {
                String date = t.getDate() != null && t.getDate().contains("T") ? t.getDate().split("T")[0] : t.getDate();
                String type = t.getType() == null ? "" : t.getType();
                String montant = String.format("%.0f €", t.getMontant());
                String source = t.getIdCompteSource() != null ? t.getIdCompteSource().getNumCompte() : "null";
                String destination = t.getIdCompteDestination() != null ? t.getIdCompteDestination().getNumCompte() : "null";
                writer.write(date + " | " + type + " | " + montant + " | " + source + " | " + destination);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur d'écriture du relevé : " + e.getMessage());
        }
    }

    public boolean deposer(Compte compte, double montant) throws CompteIntrouvableException, MontantInvalideException {
        if (compte == null) {
            throw new CompteIntrouvableException("Compte introuvable.");
        }
        if (montant <= 0) {
            throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
        }

        compte.setSolde(compte.getSolde() + montant);
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Transaction t = new Transaction("DEPOT", montant, date, null, compte);
        compte.addTransaction(t);
        enregistrerReleveCompte(compte);
        return true;
    }

    public boolean soldeSuffisant(Compte compte, double montant) throws CompteIntrouvableException, MontantInvalideException {
        if (compte == null) {
            throw new CompteIntrouvableException("Compte introuvable.");
        }
        if (montant <= 0) {
            throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
        }
        return compte.getSolde() >= montant;
    }

    public boolean retrait(Compte compte, double montant) throws CompteIntrouvableException, MontantInvalideException, SoldeInsuffisantException {
        if (compte == null) {
            throw new CompteIntrouvableException("Compte introuvable.");
        }
        if (montant <= 0) {
            throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
        }
        if (montant > compte.getSolde()) {
            throw new SoldeInsuffisantException("Solde insuffisant pour effectuer ce retrait.");
        }

        compte.setSolde(compte.getSolde() - montant);
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Transaction t = new Transaction("RETRAIT", montant, date, compte, null);
        compte.addTransaction(t);
        enregistrerReleveCompte(compte);
        return true;
    }

    public boolean virement(Compte source, Compte destination, double montant) throws CompteException, MontantInvalideException, SoldeInsuffisantException, CompteIntrouvableException {
        if (source == null) {
            throw new CompteIntrouvableException("Compte source introuvable.");
        }
        if (destination == null) {
            throw new CompteIntrouvableException("Compte destination introuvable.");
        }
        if (source.getNumCompte() != null && source.getNumCompte().equals(destination.getNumCompte())) {
            throw new CompteException("Le compte source et le compte destination doivent être différents.");
        }
        if (montant <= 0) {
            throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
        }
        if (source.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour effectuer ce virement.");
        }

        source.setSolde(source.getSolde() - montant);
        destination.setSolde(destination.getSolde() + montant);

        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Transaction t = new Transaction("VIREMENT", montant, date, source, destination);
        source.addTransaction(t);
        destination.addTransaction(t);
        enregistrerReleveCompte(source);
        enregistrerReleveCompte(destination);

        return true;
    }
}
