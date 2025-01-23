import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Map;

public class StatistiqueScene {

    public StatistiqueScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        Label title = new Label("Statistiques des Événements");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        try {
            EvenementDAO dao = new EvenementDAO();
            Map<String, Object> stats = dao.getStats(); // Récupération des statistiques

            // Extraction des données depuis la Map
            int totalTicketsVendus = stats.get("totalTicketsVendus") != null ? (int) stats.get("totalTicketsVendus") : 0;
            double chiffreAffaires = stats.get("chiffreAffaires") != null ? (double) stats.get("chiffreAffaires") : 0.0;
            double tauxRemplissage = stats.get("tauxRemplissage") != null ? (double) stats.get("tauxRemplissage") : 0.0;

            // Création des labels pour afficher les statistiques
            Label totalTicketsLabel = new Label("Total des tickets vendus : " + totalTicketsVendus);
            Label chiffreAffairesLabel = new Label("Chiffre d'affaires total : " + chiffreAffaires + " €");
            Label tauxRemplissageLabel = new Label("Taux de remplissage moyen : " + String.format("%.2f", tauxRemplissage) + " %");

            layout.getChildren().addAll(title, totalTicketsLabel, chiffreAffairesLabel, tauxRemplissageLabel);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les statistiques : " + e.getMessage());
        }

        // Bouton Retour
        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> new DashboardOrganisateurScene(primaryStage));
        layout.getChildren().add(btnRetour);

        primaryStage.setScene(new Scene(layout, 400, 300));
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
