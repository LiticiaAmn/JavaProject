import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardClientScene {
    Utilisateur  currentUser;
    UtilisateurDAO utilisateurDAO;

    public DashboardClientScene (Stage primaryStage) {

        // Dashboard Client
            VBox clientDashboard = new VBox(10);
            Button btnVoirEvenements = new Button("Voir les �v�nements");
            Button btnHistorique = new Button("Voir l'historique des r�servations");
            Button btnSeDeconnecter = new Button("Se D�connecter");

            btnVoirEvenements.setOnAction(e -> {
                // Simuler l'affichage des �v�nements
                showAlert(Alert.AlertType.INFORMATION, "�v�nements", "Liste des �v�nements disponibles...");
            });

            btnHistorique.setOnAction(e -> {
                // Simuler l'affichage de l'historique des r�servations
                showAlert(Alert.AlertType.INFORMATION, "Historique", "Votre historique de r�servations...");
            });

            btnSeDeconnecter.setOnAction(e -> {
                ((Client) currentUser).seDeconnecter();
                showLoginScreen(primaryStage); // Retourner � la fen�tre de connexion
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
}
