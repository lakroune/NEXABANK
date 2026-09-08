package models;

public class Gestionnaire extends Personne {
    private int idGestionnaire;

    public Gestionnaire(String nom, String prenom, String email, String motDePasse, int idGestionnaire) {
        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = idGestionnaire;
    }

    /**
     * Return the ID of this gestionnaire
     * @return the ID of this gestionnaire
     */
    public int getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(int idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }
    public String toString() {
        return "Gestionnaire{" +
                "idGestionnaire=" + idGestionnaire +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", motDePasse='" + getMotDePasse() + '\'' +
                '}';
    }

}
