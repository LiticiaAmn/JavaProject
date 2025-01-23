import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Cette classe est une scène permettant au client
 * de consulter toutes les réservations effectuées.
 *
 * @author Liticia
 * @version 1.0
 * @since Java 17
 */

public class HistoriqueReservationsScene {
    private ReservationDAO reservationDAO;

    public HistoriqueReservationsScene(Stage primaryStage, String utilisateurEmail) {
        // Connexion à la base de données
        try {
            // Adapter cette ligne selon votre configuration
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votre_base", "username", "password");
            reservationDAO = new ReservationDAO(connection);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de se connecter à la base de données.", Alert.AlertType.ERROR);
            return;
        }

        // Création des composants UI
        Label titleLabel = new Label("Historique des Réservations");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ListView pour afficher les réservations
        ListView<String> reservationListView = new ListView<>();
        try {
            // Récupérer l'historique des réservations pour l'utilisateur
            List<Reservation> reservations = reservationDAO.getHistoriqueReservations(utilisateurEmail);
            for (Reservation reservation : reservations) {
                reservationListView.getItems().add(reservation.toString()); // Utilisation du toString pour afficher les réservations
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer l'historique des réservations.", Alert.AlertType.ERROR);
            return;
        }

        // Bouton pour annuler une réservation
        Button cancelButton = new Button("Annuler la réservation");
        cancelButton.setOnAction(event -> {
            String selectedReservation = reservationListView.getSelectionModel().getSelectedItem();
            if (selectedReservation == null) {
                showAlert("Erreur", "Veuillez sélectionner une réservation à annuler.", Alert.AlertType.ERROR);
                return;
            }

            // Extraire l'ID de la réservation depuis le texte affiché (c'est une méthode simplifiée, vous pourriez l'améliorer)
            String[] reservationParts = selectedReservation.split(",");
            int reservationId = Integer.parseInt(reservationParts[0].split("=")[1].trim());

            try {
                reservationDAO.annulerReservation(reservationId);
                showAlert("Succès", "Réservation annulée avec succès.", Alert.AlertType.INFORMATION);
                reservationListView.getItems().remove(selectedReservation); // Supprimer la réservation annulée de la liste
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de l'annulation de la réservation.", Alert.AlertType.ERROR);
            }
        });

        // Disposition des éléments
        VBox layout = new VBox(10, titleLabel, reservationListView, cancelButton);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Historique des Réservations");
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
