import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Client extends Utilisateur {

    public Client(String nom, String email, String motDePasse) {
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
        System.out.println("Client connecté : " + getNom());
        showAlert(Alert.AlertType.INFORMATION, "Connexion reussie", "Bienvenue " + getNom());
    }

    @Override
    public void seDeconnecter() {
        System.out.println("Client déconnecté : " + getNom());
        showAlert(Alert.AlertType.INFORMATION, "Vous êtes bien déconnecté", "A bientôt " + getNom());

    }
}
