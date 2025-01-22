import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnexionScene {
    public ConnexionScene(Stage primaryStage) {
        Label labelEmail = new Label("Email:");
        TextField fieldEmail = new TextField();
        Label labelPassword = new Label("Mot de passe:");
        PasswordField fieldPassword = new PasswordField();

        Button btnConnexion = new Button("Se connecter");
        Button btnCreerCompte = new Button("Créer un compte");

        btnConnexion.setOnAction(e -> {
            String email = fieldEmail.getText();
            String motDePasse = fieldPassword.getText();

            try {
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                Utilisateur utilisateur = utilisateurDAO.verifierUtilisateurParEmail(email, motDePasse);

                if (utilisateur != null) {
                    if (utilisateur instanceof Client){
                        utilisateur.seConnecter();
                        new DashboardClientScene(primaryStage);
                    }
                    else {
                        utilisateur.seConnecter();
                        new DashboardOrganisateurScene(primaryStage);
                    }
                } else {
                    System.out.println("Email ou mot de passe incorrect.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Redirige vers la création de compte
        btnCreerCompte.setOnAction(e -> {
            CreationCompteScene creationCompteScene = new CreationCompteScene(primaryStage);
        });

        VBox vbox = new VBox(10, labelEmail, fieldEmail, labelPassword, fieldPassword, btnConnexion, btnCreerCompte);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showClientDashboard(Stage stage) {
        // Impl�mentez cette m�thode pour afficher le tableau de bord du client
        showAlert(Alert.AlertType.INFORMATION, "Connexion reussie", "Bienvenue, Client !");
    }

    private void showOrganisateurDashboard(Stage stage) {
        // Impl�mentez cette m�thode pour afficher le tableau de bord de l'organisateur
        showAlert(Alert.AlertType.INFORMATION, "Connexion reussie", "Bienvenue, Organisateur !");
    }


}
