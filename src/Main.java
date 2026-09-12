import java.util.InputMismatchException;
import java.util.Scanner;

import models.Compte;
import models.Client;
import models.Transaction;
import serveces.CompteService;
import serveces.clinetService;

class Main {
    private static void afficherMenuPrincipal() {
        afficherMenuClient();
        System.out.println("\n=====================================");
        System.out.println("            MENU PRINCIPAL");
        System.out.println("=====================================");
        System.out.println("1. Menu Client");
        System.out.println("2. Menu Gestionnaire");
        System.out.println("0. Quitter");
        System.out.println("=====================================");
        System.out.print("Votre choix : ");
    }

    private static void afficherMenuClient() {
        System.out.println("\n-------------------------------------");
        System.out.println("             MENU CLIENT");
        System.out.println("-------------------------------------");
        System.out.println("1. Consulter le solde du compte actif");
        System.out.println("2. Afficher la liste de tous mes comptes");
        System.out.println("3. Effectuer un dépôt");
        System.out.println("4. Effectuer un retrait");
        System.out.println("5. Effectuer un virement vers un autre compte");
        System.out.println("6. Consulter le relevé bancaire (Historique)");
        System.out.println("--------------------------------------------------");
        System.out.println("0. Quitter l'application");
        System.out.println("-------------------------------------");
        System.out.print("Votre choix : ");
    }

    private static void afficherMenuGestionnaire() {
        System.out.println("\n-------------------------------------");
        System.out.println("        MENU GESTIONNAIRE");
        System.out.println("-------------------------------------");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Modifier un client");
        System.out.println("3. Afficher la liste des clients");
        System.out.println("4. Créer un compte");
        System.out.println("5. Supprimer un compte");
        System.out.println("6. Consulter un relevé");
        System.out.println("7. Lister les comptes");
        System.out.println("0. Retour");
        System.out.println("-------------------------------------");
        System.out.print("Votre choix : ");
    }

    private static double lireSolde(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Solde : ");
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez saisir un nombre valide.");
                scanner.nextLine();
            }
        }
    }

    private static Client loginClient(Scanner scanner, CompteService compteService) {
        System.out.print("Email : ");
        String email = scanner.nextLine().trim();
        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine().trim();
        return compteService.login(email, motDePasse);
    }

    private static Compte trouverCompte(Client client, CompteService compteService, String numeroCompte) {
        if (numeroCompte == null || numeroCompte.trim().isEmpty()) {
            return null;
        }

        if (client != null && client.getComptes().containsKey(numeroCompte.trim())) {
            return client.getComptes().get(numeroCompte.trim());
        }
        return compteService.getCompteParNumero(numeroCompte);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        CompteService compteService = new CompteService();
        clinetService clientService = new clinetService();

        while (running) {

            afficherMenuPrincipal();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "0":
                    running = false;
                    System.out.println("Au revoir !");
                    break;

                case "1":
                    boolean clientMenu = true;
                    Client clientLogin = loginClient(scanner, compteService);
                    if (clientLogin == null) {
                        System.out.println("Email ou mot de passe incorrect.");
                        break;
                    }
                    while (clientMenu) {

                        afficherMenuClient();
                        String c = scanner.nextLine().trim();
                        switch (c) {
                            case "0":
                                clientMenu = false;
                                break;

                            case "1": // consulter le solde
                                System.out.print("Numéro du compte : ");
                                String numCompteSolde = scanner.nextLine().trim();
                                Compte compteSolde = trouverCompte(clientLogin, compteService, numCompteSolde);
                                if (compteSolde == null) {
                                    System.out.println("Compte introuvable.");
                                    break;
                                }
                                System.out.println("Solde : " + compteSolde.getSolde());
                                break;

                            case "2": // afficher la liste de tous mes comptes
                                System.out.println("--- Liste des comptes ---");
                                if (clientLogin.getComptes().isEmpty()) {
                                    System.out.println("Aucun compte trouvé.");
                                } else {
                                    for (Compte compte : clientLogin.getComptes().values()) {
                                        System.out.print("-> Num compte : " + compte.getNumCompte());
                                        System.out.print(" <*> Solde : " + compte.getSolde());
                                        System.out.println();
                                    }
                                }
                                break;

                            case "3": // Effectuer un dépôt
                                System.out.print("Numéro du compte : ");
                                String numCompteDepot = scanner.nextLine().trim();
                                Compte compteDepot = trouverCompte(clientLogin, compteService, numCompteDepot);
                                if (compteDepot == null) {
                                    System.out.println("Compte introuvable.");
                                    break;
                                }
                                try {
                                    System.out.print("Montant du depot : ");
                                    double montantDepot = Double.parseDouble(scanner.nextLine().trim());
                                    clientService.deposer(compteDepot, montantDepot);
                                    System.out.println("Depot effectué avec succès.");
                                    System.out.println("Nouveau solde : " + compteDepot.getSolde());
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
                                } catch (exception.MontantInvalideException | exception.CompteIntrouvableException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                                break;

                            case "4": // retrait
                                System.out.print("Numéro du compte : ");
                                String numCompteRetrait = scanner.nextLine().trim();
                                Compte compteRetrait = trouverCompte(clientLogin, compteService, numCompteRetrait);
                                if (compteRetrait == null) {
                                    System.out.println("Compte introuvable.");
                                    break;
                                }
                                try {
                                    System.out.print("Montant du retrait : ");
                                    double montantRetrait = Double.parseDouble(scanner.nextLine().trim());
                                    clientService.retrait(compteRetrait, montantRetrait);
                                    System.out.println("Retrait effectué avec succès.");
                                    System.out.println("Nouveau solde : " + compteRetrait.getSolde());
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
                                } catch (exception.SoldeInsuffisantException | exception.MontantInvalideException
                                        | exception.CompteIntrouvableException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Erreur inattendue : " + e.getMessage());
                                }
                                break;

                            case "5": // virement
                                System.out.print("Compte source : ");
                                String numCompteSource = scanner.nextLine().trim();
                                System.out.print("Compte destination : ");
                                String numCompteDestination = scanner.nextLine().trim();
                                Compte compteSource = trouverCompte(clientLogin, compteService, numCompteSource);
                                Compte compteDestination = compteService.getCompteParNumero(numCompteDestination);
                                if (compteSource == null || compteDestination == null) {
                                    System.out.println("Compte source ou destination introuvable.");
                                    break;
                                }
                                try {
                                    System.out.print("Montant du virement : ");
                                    double montantVirement = Double.parseDouble(scanner.nextLine().trim());
                                    clientService.virement(compteSource, compteDestination, montantVirement);
                                    System.out.println("Virement effectué avec succès.");
                                    System.out.println("Nouveau solde source : " + compteSource.getSolde());
                                    System.out.println("Nouveau solde destination : " + compteDestination.getSolde());
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
                                } catch (exception.SoldeInsuffisantException | exception.MontantInvalideException
                                        | exception.CompteIntrouvableException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                } catch (exception.CompteException e) {
                                    System.out.println("Erreur compte : " + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Erreur inattendue : " + e.getMessage());
                                }
                                break;

                            case "6": // consulter historique
                                System.out.print("Numéro du compte : ");
                                String numCompteHistorique = scanner.nextLine().trim();
                                Compte compteHist = trouverCompte(clientLogin, compteService, numCompteHistorique);
                                if (compteHist == null) {
                                    System.out.println("Compte introuvable.");
                                    break;
                                }
                                if (compteHist.getTransactions().isEmpty()) {
                                    System.out.println("Aucune transaction trouvée pour ce compte.");
                                } else {
                                    System.out.println("--- Historique des transactions ---");
                                    for (Transaction t : compteHist.getTransactions()) {
                                        System.out.println(t.getIdTransaction() + " | " + t.getType() + " | "
                                                + t.getMontant() + " | " + t.getDate());
                                    }
                                }
                                break;

                            default:
                                System.out.println("Choix invalide.");
                                break;
                        }
                    }
                    break;

                case "2":
                    boolean gestionMenu = true;
                    while (gestionMenu) {

                        afficherMenuGestionnaire();
                        String g = scanner.nextLine().trim();

                        switch (g) {
                            case "0":
                                gestionMenu = false;
                                break;

                            // Clients
                            case "1":
                                System.out.println("\nEntrez les informations du client :");
                                System.out.print("Nom : ");
                                String nom = scanner.nextLine();
                                System.out.print("Prénom : ");
                                String prenom = scanner.nextLine();
                                System.out.print("Email : ");
                                String email = scanner.nextLine();
                                System.out.print("Mot de passe : ");
                                String motDePasse = scanner.nextLine();
                                System.out.print("ID client (nombre) : ");

                                int idClient;
                                try {
                                    idClient = Integer.parseInt(scanner.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("ID invalide. Opération annulée.");
                                    break;
                                }
                                Client client = new Client(nom, prenom, email, motDePasse, idClient);
                                if (compteService.ajouterCompte(client)) {
                                    System.out.println("Client ajouté avec succès.");
                                } else {
                                    System.out.println("Erreur : client invalide ou déjà existant.");
                                }
                                break;

                            case "2":
                                System.out.println("\nModifier un client (simulé) :");
                                System.out.print("ID client à modifier : ");
                                int idToModify;
                                try {
                                    idToModify = Integer.parseInt(scanner.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("ID invalide. Opération annulée.");
                                    break;
                                }

                                System.out.print("Nouveau nom : ");
                                String newNom = scanner.nextLine();
                                System.out.print("Nouveau prénom : ");
                                String newPrenom = scanner.nextLine();
                                System.out.print("Nouveau email : ");
                                String newEmail = scanner.nextLine();
                                System.out.print("Nouveau mot de passe : ");
                                String newMdp = scanner.nextLine();
                                Client modified = new Client(newNom, newPrenom, newEmail, newMdp, idToModify);
                                if (compteService.modifierCompte(modified)) {
                                    System.out.println("Client modifié (opération simulée).");
                                } else {
                                    System.out.println("Erreur lors de la modification.");
                                }
                                break;

                            case "3":
                                compteService.display();
                                break;

                            // Comptes (numérotation déplacée)
                            case "4":
                                System.out.println("\nEntrez les informations du compte :");
                                System.out.print("ID client : ");
                                idClient = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("Numéro de compte : ");
                                String numCompte = scanner.nextLine();

                                System.out.print("Type de compte : ");
                                String typeCompte2 = scanner.nextLine();

                                Compte compte = new Compte(numCompte, 0, typeCompte2);
                                if (compteService.ajouterCompte(compte, idClient)) {
                                    System.out.println("Compte ajouté avec succès.");
                                } else {
                                    System.out.println("Erreur : compte invalide ou déjà existant.");
                                }
                                break;

                            case "5":
                                System.out.println("\nEntrez le numéro de compte à supprimer :");
                                System.out.print("Numéro de compte : ");
                                String numCompte1 = scanner.nextLine();
                                System.out.print("ID client : ");
                                idClient = Integer.parseInt(scanner.nextLine().trim());
                                if (compteService.cluturerCompte(numCompte1, idClient)) {
                                    System.out.println("Compte supprimé avec succès.");
                                } else {
                                    System.out.println("Compte introuvable.");
                                }
                                break;

                            case "6":
                                System.out.println("\nEntrez le numéro de compte à consulter :");
                                System.out.print("Numéro de compte : ");
                                String numCompte2 = scanner.nextLine();
                                System.out.print("ID client : ");
                                idClient = Integer.parseInt(scanner.nextLine().trim());
                                compteService.consulterReleve(numCompte2, idClient);
                                break;

                            case "7":
                                compteService.listerComptes();
                                break;

                            default:
                                System.out.println("Choix invalide dans le menu gestionnaire.");
                                break;
                        }
                    }
                    break;

                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }

        scanner.close();
    }
}
