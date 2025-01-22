import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardOrganisateurScene {
    Utilisateur currentUser;
    UtilisateurDAO utilisateurDAO;

    // Constructeur avec le bon nom
    public DashboardOrganisateurScene(Stage primaryStage) {
        VBox organisateurDashboard = new VBox(10);
        Button btnCreerEvenement = new Button("Créer un événement");
        Button btnVoirEvenements = new Button("Voir mes événements");
        Button btnSeDeconnecter = new Button("Se Déconnecter");

        btnCreerEvenement.setOnAction(e -> {
            // Simuler la création d'un événement
            showAlert(Alert.AlertType.INFORMATION, "Création d'événement", "Formulaire de création d'événement...");
        });

        btnVoirEvenements.setOnAction(e -> {
            // Simuler l'affichage des événements créés
            showAlert(Alert.AlertType.INFORMATION, "Mes événements", "Liste de mes événements...");
        });

        btnSeDeconnecter.setOnAction(e -> {
            ((Organisateur) currentUser).seDeconnecter();
            try {
                showLoginScreen(primaryStage); // Retourner à la fenêtre de connexion
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        organisateurDashboard.getChildren().addAll(btnCreerEvenement, btnVoirEvenements, btnSeDeconnecter);
        Scene sceneOrganisateur = new Scene(organisateurDashboard, 400, 300);
        primaryStage.setScene(sceneOrganisateur);
    }

    public void start(Stage primaryStage) throws Exception {
        showLoginScreen(primaryStage);
    }

    // Retour à la page de connexion
    private void showLoginScreen(Stage primaryStage) throws Exception {
        start(primaryStage); // Retour à la page de connexion
    }

    // Fonction pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
