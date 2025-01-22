import javafx.scene.control.Alert;

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
        showAlert(Alert.AlertType.INFORMATION, "Connexion reussie", "Bienvenue " + getNom());
    }

    @Override
    public void seDeconnecter() {
        System.out.println("Organisateur déconnecté : " + getNom());
    }
}
