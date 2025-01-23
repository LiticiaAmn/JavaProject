import javafx.scene.control.Alert;

/**
 * Cette classe est une extension de la classe Utilisateur qui permet
 * l'héritage de ses méthodes et attributs.
 * Une alerte est mise en place dans les méthodes afin d'informer l'utilisateur.
 *
 * @author Sabrina
 * @version 1.0
 * @since Java 17
 */
public class Organisateur extends Utilisateur {

    public Organisateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void seConnecter() {
        System.out.println("Organisateur connecté : " + getNom());
        showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Bienvenue " + getNom());
    }

    @Override
    public void seDeconnecter() {
        System.out.println("Organisateur déconnecté : " + getNom());
        showAlert(Alert.AlertType.INFORMATION, "Vous êtes bien déconnecté", "A bientôt " + getNom());


    }


}
