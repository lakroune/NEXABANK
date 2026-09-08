package models;

/**
 * Personne
 */
public class Personne {
    private String nom, prenom, email, motDePasse;

    public Personne(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    /**
     * Return the nom of this personne
     * @return the nom of this personne
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Return the prenom of this personne
     * @return the prenom of this personne
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Return the email of this personne
     * @return the email of this personne
     */
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


    /**
     * Return the mot de passe of this personne
     * @return the mot de passe of this personne
     */
    public String getMotDePasse() {
        return this.motDePasse;
    }

    public String toString() {
        return "Personne{" +
                "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", motDePasse='" + getMotDePasse() + '\'' +
                '}';
    }
}