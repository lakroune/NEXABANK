package serveces;

import exception.CompteException;
import exception.CompteIntrouvableException;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import models.Compte;

public class clinetService {

    public boolean deposer(Compte compte, double montant) {
        try {
            if (compte == null) {
                throw new CompteIntrouvableException("Compte introuvable.");
            }
            if (montant <= 0) {
                throw new MontantInvalideException("Montant invalide : le montant doit être supérieur à 0.");
            }

            compte.setSolde(compte.getSolde() + montant);
            return true;
        } catch (CompteException e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public boolean retrait(Compte compte, double montant) {
        try {
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
            return true;
        } catch (CompteException e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }

    public boolean virement(Compte source, Compte destination, double montant) {
        try {
            if (source == null) {
                throw new CompteIntrouvableException("Compte source introuvable.");
            }
            if (destination == null) {
                throw new CompteIntrouvableException("Compte destination introuvable.");
            }
            if (source == destination) {
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
            return true;
        } catch (CompteException e) {
            System.out.println("Erreur : " + e.getMessage());
            return false;
        }
    }
}
