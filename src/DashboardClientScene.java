import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardClientScene {
    Utilisateur  currentUser;
    UtilisateurDAO utilisateurDAO;
    private Stage primaryStage;

    public DashboardClientScene (Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Dashboard Client
            VBox clientDashboard = new VBox(10);
            Button btnVoirEvenements = new Button("Voir les evenements");
            Button btnHistorique = new Button("Voir l'historique des reservations");
            Button btnSeDeconnecter = new Button("Se Deconnecter");

            btnVoirEvenements.setOnAction(e -> {
                // Simuler l'affichage des �v�nements
                showAlert(Alert.AlertType.INFORMATION, "Evenements", "Liste des evenements disponibles...");
            });

            btnHistorique.setOnAction(e -> {
                // Simuler l'affichage de l'historique des r�servations
                showAlert(Alert.AlertType.INFORMATION, "Historique", "Votre historique de reservations...");
            });

        btnSeDeconnecter.setOnAction(e -> {
            // Affichage d'une alerte pour informer que la déconnexion est réussie
            showAlert(Alert.AlertType.INFORMATION, "Déconnexion", "Vous êtes bien déconnecté");

            // Retour à la scène de connexion
            redirigerVersConnexion();
        });

            clientDashboard.getChildren().addAll(btnVoirEvenements, btnHistorique, btnSeDeconnecter);
            Scene sceneClient = new Scene(clientDashboard, 400, 300);
            primaryStage.setScene(sceneClient);
        }


    public void start(Stage primaryStage) {
        showLoginScreen(primaryStage);
    }
    // Retour � la page de connexion
    private void showLoginScreen (Stage primaryStage){
        start(primaryStage); // Retour � la page de connexion
    }

    // Fonction pour afficher une bo�te de dialogue d'alerte
    private void showAlert (Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour rediriger vers la page de connexion
    private void redirigerVersConnexion() {
        // Créer une nouvelle scène de connexion et l'afficher dans la fenêtre principale

        ConnexionScene connexionScene = new ConnexionScene(primaryStage);
    }
}
