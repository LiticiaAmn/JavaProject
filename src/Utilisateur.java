/**
 * Cette classe est une classe abstraite qui permet
 * d'hériter de ses méthodes et attributs pour la gestion des Utilisateurs.
 *
 * @author Sabrina
 * @version 1.0
 * @since Java 17
 */
public abstract class Utilisateur {
    private String nom;
    private String email;
    private String motDePasse;

    public Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Utilisateur(String email) {
        this.email=email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public abstract void seConnecter();

    public abstract void seDeconnecter();
}
