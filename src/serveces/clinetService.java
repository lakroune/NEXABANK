package serveces;

import exception.CompteException;
import exception.CompteIntrouvableException;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import models.Compte;
import models.Transaction;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class clinetService {

    public boolean deposer(Compte compte, double montant) throws CompteIntrouvableException, MontantInvalideException {
        if (compte == null) {
            throw new CompteIntrouvableException("Compte introuvable.");
        }
        if (montant <= 0) {
            throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
        }

        compte.setSolde(compte.getSolde() + montant);
        // enregistrer la transaction
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Transaction t = new Transaction("DEPOT", montant, date, null, compte);
        compte.addTransaction(t);
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

        return true;
    }
}
