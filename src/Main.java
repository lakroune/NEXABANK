
import java.util.InputMismatchException;
import java.util.Scanner;

import models.Compte;
import models.Client;
import serveces.CompteService;
import serveces.clinetService;

class Main {
    private static void afficherMenuPrincipal() {
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
        System.out.println("1. Créer un nouveau compte");
        System.out.println("2. Sélectionner/Changer de compte actif");
        System.out.println("3. Consulter le solde du compte actif");
        System.out.println("4. Afficher la liste de tous mes comptes");
        System.out.println();
        System.out.println("--- OPÉRATIONS FINANCIÈRES ---");
        System.out.println("5. Effectuer un dépôt");
        System.out.println("6. Effectuer un retrait");
        System.out.println("7. Effectuer un virement vers un autre compte");
        System.out.println();
        System.out.println("--- SUIVI & HISTORIQUE ---");
        System.out.println("8. Consulter le relevé bancaire (Historique)");
        System.out.println("9. Exporter / Vérifier le fichier journal (.txt)");
        System.out.println();
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
        System.out.println("-------------------------------------");
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
                            case "1":
                                System.out.println("\nEntrez les informations du compte :");
                                System.out.print("Numéro de compte : ");
                                String numCompte = scanner.nextLine().trim();
                                System.out.print("Solde initial : ");
                                double solde = lireSolde(scanner);
                                scanner.nextLine();
                                System.out.print("Type de compte : ");
                                String typeCompte = scanner.nextLine();
                                Compte compte = new Compte(numCompte, solde, typeCompte);
                                if (compteService.ajouterCompte(compte, clientLogin.getIdClient())) {
                                    System.out.println("Compte créé avec succès.");
                                } else {
                                    System.out.println("Erreur : compte invalide ou déjà existant.");
                                }
                                break;
                            case "5":
                                System.out.print("Numéro du compte : ");
                                String numCompteDepot = scanner.nextLine().trim();
                                Compte compteDepot = trouverCompte(clientLogin, compteService, numCompteDepot);
                                if (compteDepot == null) {
                                    System.out.println("Compte introuvable.");
                                    break;
                                }
                                try {
                                    System.out.print("Montant du dépôt : ");
                                    double montantDepot = Double.parseDouble(scanner.nextLine().trim());
                                    if (clientService.deposer(compteDepot, montantDepot)) {
                                        System.out.println("Dépôt effectué avec succès.");
                                        System.out.println("Nouveau solde : " + compteDepot.getSolde());
                                    } else {
                                        System.out.println("Montant invalide.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
                                }
                                break;
                            case "6":
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
                                    if (clientService.retrait(compteRetrait, montantRetrait)) {
                                        System.out.println("Retrait effectué avec succès.");
                                        System.out.println("Nouveau solde : " + compteRetrait.getSolde());
                                    } else {
                                        System.out.println("Retrait impossible : solde insuffisant ou montant invalide.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
                                }
                                break;
                            case "7":
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
                                    if (clientService.virement(compteSource, compteDestination, montantVirement)) {
                                        System.out.println("Virement effectué avec succès.");
                                        System.out.println("Nouveau solde source : " + compteSource.getSolde());
                                        System.out.println("Nouveau solde destination : " + compteDestination.getSolde());
                                    } else {
                                        System.out.println("Virement impossible : solde insuffisant ou montant invalide.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Montant invalide.");
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
                                double solde = lireSolde(scanner);
                                scanner.nextLine();
                                System.out.print("Type de compte : ");
                                String typeCompte = scanner.nextLine();

                                Compte compte = new Compte(numCompte, solde, typeCompte);
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
