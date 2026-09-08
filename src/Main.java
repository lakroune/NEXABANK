
import java.util.Scanner;


import models.Client;
 
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

 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
       
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
                    Client clientLogin = null;
                    while (clientMenu) {
                        afficherMenuClient();
                        String c = scanner.nextLine().trim();
                        switch (c) {
                            case "0":
                                clientMenu = false;
                                break;
                            case "1":
                              
                                break;
                            case "5":
                                System.out.print("Numéro du compte : ");
                                break;
                            case "6":
                                System.out.print("Numéro du compte : ");
                                break;
                            case "7":
                                System.out.print("Numéro du compte : ");
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
                                System.out.println("\nAjouter un client (simulé) :");
                                break;

                            case "2":
                                System.out.println("\nModifier un client (simulé) :");
                                break;

                            case "3":
                                System.out.println("\nAfficher la liste des clients (simulé) :");
                                break;

                            // Comptes (numérotation déplacée)
                            case "4":
                                System.out.println("\nCréer un compte (simulé) :");
                                break;

                            case "5":
                                System.out.println("\nSupprimer un compte (simulé) :");
                                break;

                            case "6":
                                break;

                            case "7":
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
