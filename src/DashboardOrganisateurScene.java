import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardOrganisateurScene {
    private Stage primaryStage;
    private EvenementDAO evenementDAO;
    boolean isOrganisateur;

    public DashboardOrganisateurScene(Stage primaryStage) {
        this.primaryStage = primaryStage;  // On garde une référence à la fenêtre principale

        VBox organisateurDashboard = new VBox(10);
        Button btnCreerEvenement = new Button("Créer un événement");
        Button btnVoirEvenements = new Button("Voir mes événements");
        Button btnSeDeconnecter = new Button("Se Déconnecter");

        // Bouton pour créer un événement
        btnCreerEvenement.setOnAction(e -> {
            // Simuler la création d'un événement
            showAlert(Alert.AlertType.INFORMATION, "Création d'événement", "Formulaire de création d'événement...");
           // GestionEvenement gestionEvenement = new GestionEvenement();

            new FormulaireCreationEvenement(primaryStage);

        });

        // Bouton pour voir les événements
        btnVoirEvenements.setOnAction(e -> {
            // Simuler l'affichage des événements créés
            showAlert(Alert.AlertType.INFORMATION, "Mes événements", "Liste de mes événements...");
            new AffichageEvenements(primaryStage,isOrganisateur);
        });

        // Bouton pour se déconnecter
        btnSeDeconnecter.setOnAction(e -> {
            // Affichage d'une alerte pour informer que la déconnexion est réussie
            showAlert(Alert.AlertType.INFORMATION, "Déconnexion", "Vous êtes bien déconnecté");

            // Retour à la scène de connexion
            redirigerVersConnexion();
        });

        // Ajouter les boutons à la VBox
        organisateurDashboard.getChildren().addAll(btnCreerEvenement, btnVoirEvenements, btnSeDeconnecter);

        // Créer la scène pour le tableau de bord de l'organisateur
        Scene sceneOrganisateur = new Scene(organisateurDashboard, 400, 300);
        primaryStage.setScene(sceneOrganisateur); // Définir cette scène pour le Stage actuel
    }

    // Fonction pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
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
