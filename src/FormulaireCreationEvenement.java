import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Cette classe est une scène permettant à l'organisateur
 * de créer un événement via un formulaire.
 *
 * @author Julie
 * @version 1.0
 * @since Java 17
 */



public class FormulaireCreationEvenement {

    public FormulaireCreationEvenement(Stage primaryStage) {
        Label labelNom = new Label("Nom:");
        TextField fieldNom = new TextField();
        Label labelDate = new Label("Date (dd/MM/yyyy HH:mm):");
        TextField fieldDate = new TextField();
        Label labelLieu = new Label("Lieu:");
        TextField fieldLieu = new TextField();
        Label labelCategorie = new Label("Catégorie:");
        ComboBox<String> comboBoxCategorie = new ComboBox<>();

        // Charger les catégories depuis la base de données
        try {
            EvenementDAO dao = new EvenementDAO();
            comboBoxCategorie.getItems().addAll(dao.getCategories());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les catégories : " + e.getMessage());
        }

        Label labelPrix = new Label("Prix:");
        TextField fieldPrix = new TextField();
        Label labelPlaces = new Label("Nombre de Places:");
        TextField fieldPlaces = new TextField();

        Button btnCreer = new Button("Créer");
        btnCreer.setOnAction(e -> {
            String nom = fieldNom.getText();
            String dateStr = fieldDate.getText();
            String lieu = fieldLieu.getText();
            String categorie = comboBoxCategorie.getValue(); // Nom de la catégorie sélectionnée
            double prix;
            int nbrPlaces;

            try {
                prix = Double.parseDouble(fieldPrix.getText());
                nbrPlaces = Integer.parseInt(fieldPlaces.getText());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un prix et un nombre de places valides.");
                return;
            }

            if (categorie == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une catégorie.");
                return;
            }

            try {
                // Parse la date au format français
                DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime date = LocalDateTime.parse(dateStr, frenchFormatter);

                EvenementDAO dao = new EvenementDAO();

                // Trouver l'ID de la catégorie depuis son nom
                int categorieId = dao.getCategorieIdByName(categorie);

                dao.ajouterEvenement(new Evenement(0, nom, date, lieu, categorieId, prix, nbrPlaces, nbrPlaces));
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté !");

                // Retourner au tableau de bord organisateur
                new DashboardOrganisateurScene(primaryStage);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Format de date incorrect. Utilisez le format dd/MM/yyyy HH:mm.");
            }
        });

        Button btnAnnuler = new Button("Retour");
        btnAnnuler.setOnAction(e -> new DashboardOrganisateurScene (primaryStage));

        VBox layout = new VBox(10, labelNom, fieldNom, labelDate, fieldDate, labelLieu, fieldLieu,
                labelCategorie, comboBoxCategorie, labelPrix, fieldPrix, labelPlaces, fieldPlaces, btnCreer, btnAnnuler);
        layout.setPadding(new Insets(20));
        primaryStage.setScene(new Scene(layout, 400, 400));
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}