import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe est une scène permettant d'afficher
 * le formulaire de réservation pour le client.
 *
 * @author Sabrina
 * @version 1.0
 * @since Java 17
 */


public class ReservationScene {
    private ReservationDAO reservationDAO;

    public ReservationScene(Stage primaryStage) {
        // Connexion à la base de données
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votre_base", "username", "password");
            reservationDAO = new ReservationDAO(connection);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de se connecter à la base de données.", Alert.AlertType.ERROR);
            return;
        }

        // Création de l'interface utilisateur
        Label titleLabel = new Label("Réservation d'Événements");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField evenementField = new TextField();
        evenementField.setPromptText("ID de l'événement");

        TextField utilisateurField = new TextField();
        utilisateurField.setPromptText("Email de l'utilisateur");

        ComboBox<String> categorieBox = new ComboBox<>();
        categorieBox.setPromptText("Choisissez une catégorie");
        categorieBox.getItems().addAll("VIP", "Standard", "Economique");

        TextField placesField = new TextField();
        placesField.setPromptText("Nombre de places");

        Button reserverButton = new Button("Réserver");
        reserverButton.setOnAction(event -> {
            try {
                // Vérifications des champs utilisateur
                if (evenementField.getText().isEmpty() || utilisateurField.getText().isEmpty() ||
                        categorieBox.getValue() == null || placesField.getText().isEmpty()) {
                    showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
                    return;
                }

                int evenementId = Integer.parseInt(evenementField.getText());
                String email = utilisateurField.getText();
                String categorie = categorieBox.getValue();
                int placesDemandees = Integer.parseInt(placesField.getText());

                if (placesDemandees <= 0) {
                    showAlert("Erreur", "Le nombre de places doit être supérieur à 0.", Alert.AlertType.ERROR);
                    return;
                }

                // Récupérer l'événement depuis le DAO
                Evenement evenement = reservationDAO.getEvenementById(evenementId);

                if (evenement == null) {
                    showAlert("Erreur", "L'événement spécifié est introuvable.", Alert.AlertType.ERROR);
                    return;
                }

                // Créer et enregistrer la réservation
                Reservation reservation = new Reservation(
                        evenement,
                        new Utilisateur(email) {
                            @Override
                            public void seConnecter() {

                            }

                            @Override
                            public void seDeconnecter() {

                            }
                        },
                        categorie,
                        placesDemandees
                );

                reservationDAO.ajouterReservation(reservation);
                showAlert("Succès", "Réservation effectuée avec succès !", Alert.AlertType.INFORMATION);

                // Réinitialiser les champs après succès
                evenementField.clear();
                utilisateurField.clear();
                categorieBox.setValue(null);
                placesField.clear();

            } catch (NumberFormatException e) {
                showAlert("Erreur", "Veuillez entrer des valeurs valides pour l'ID de l'événement et le nombre de places.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                showAlert("Erreur", "Une erreur s'est produite lors de la réservation : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        VBox layout = new VBox(10, titleLabel, evenementField, utilisateurField, categorieBox, placesField, reserverButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Gestion des Réservations");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
