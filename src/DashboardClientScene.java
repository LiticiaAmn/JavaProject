import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est une scène permettant d'afficher l'interface dashboard du client
 * afin qu'il puisse visualiser l'ensemble des informations nécessaires.
 *
 * @author Mady
 * @version 1.0
 * @since Java 17
 */
public class DashboardClientScene {
    Utilisateur  currentUser;
    UtilisateurDAO utilisateurDAO;
    private Stage primaryStage;
    boolean isOrganisateur;
    private String utilisateurEmail;

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
                new AffichageEvenementClient(primaryStage, isOrganisateur);
            });

            btnHistorique.setOnAction(e -> {
                // Simuler l'affichage de l'historique des r�servations
                showAlert(Alert.AlertType.INFORMATION, "Historique", "Votre historique de reservations...");
                new HistoriqueReservationsScene(primaryStage, utilisateurEmail);
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
