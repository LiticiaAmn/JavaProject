import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreationCompteScene {
    public CreationCompteScene(Stage primaryStage) {
        Label labelNom = new Label("Nom:");
        TextField fieldNom = new TextField();
        Label labelEmail = new Label("Email:");
        TextField fieldEmail = new TextField();
        Label labelMotDePasse = new Label("Mot de passe:");
        PasswordField fieldMotDePasse = new PasswordField();
        Label labelTypeCompte = new Label("Type de compte:");
        ComboBox<String> comboBoxTypeCompte = new ComboBox<>();
        comboBoxTypeCompte.getItems().addAll("Client", "Organisateur");

        Button btnCreerCompte = new Button("Créer le compte");
        Button btnAnnuler = new Button("Annuler");

        btnCreerCompte.setOnAction(e -> {
            String nom = fieldNom.getText();
            String email = fieldEmail.getText();
            String motDePasse = fieldMotDePasse.getText();
            String typeCompte = comboBoxTypeCompte.getValue();

            if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || typeCompte == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            try {
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                if (typeCompte.equals("Client")) {
                    utilisateurDAO.ajouterDAO(new Client(nom, email, motDePasse));
                } else if (typeCompte.equals("Organisateur")) {
                    utilisateurDAO.ajouterDAO(new Organisateur(nom, email, motDePasse));
                }

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte créé avec succès !");
                new ConnexionScene(primaryStage); // Retour à la page de connexion
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de créer le compte.");
            }
        });

        btnAnnuler.setOnAction(e -> new ConnexionScene(primaryStage));

        VBox vbox = new VBox(10, labelNom, fieldNom, labelEmail, fieldEmail, labelMotDePasse, fieldMotDePasse,
                labelTypeCompte, comboBoxTypeCompte, btnCreerCompte, btnAnnuler);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Création de compte");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
