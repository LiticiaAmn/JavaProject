import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe gère l'affichage des événements pour un client dans une interface JavaFX.
 * Elle permet de visualiser une liste d'événements, de les filtrer et de réserver.
 *
 * @author Liticia
 * @version 1.0
 * @since Java 17
 */
public class AffichageEvenementClient {

    /**
     * Constructeur de la classe AffichageEvenementClient.
     * Initialise l'interface utilisateur pour afficher les événements.
     *
     * @param primaryStage Le stage principal de l'application JavaFX.
     * @param isOrganisateur Un booléen indiquant si l'utilisateur est un organisateur.
     */
    public AffichageEvenementClient(Stage primaryStage, boolean isOrganisateur) {
        VBox layout = new VBox(10);
        Label title = new Label("Liste des Événements");

        // TableView pour afficher les événements
        TableView<Evenement> eventTable = new TableView<>();

        // Colonnes pour le tableau
        TableColumn<Evenement, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));

        TableColumn<Evenement, LocalDateTime> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));

        TableColumn<Evenement, String> colLieu = new TableColumn<>("Lieu");
        colLieu.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLieu()));

        TableColumn<Evenement, String> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(data -> {
            try {
                EvenementDAO dao = new EvenementDAO();
                String categorieName = dao.getCategorieNameById(data.getValue().getCategorieId());
                return new SimpleStringProperty(categorieName);
            } catch (SQLException e) {
                return new SimpleStringProperty("Erreur");
            }
        });

        TableColumn<Evenement, Double> colPrix = new TableColumn<>("Prix");
        colPrix.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrix()));

        TableColumn<Evenement, Integer> colPlaces = new TableColumn<>("Places Disponibles");
        colPlaces.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNbrPlace()));

        // Nouvelle colonne pour le bouton "Réserver"
        TableColumn<Evenement, Void> colReserver = new TableColumn<>("Réserver");
        colReserver.setCellFactory(col -> {
            TableCell<Evenement, Void> cell = new TableCell<Evenement, Void>() {
                private final Button btnReserver = new Button("Réserver");

                {
                    btnReserver.setOnAction(e -> {
                        Evenement selectedEvent = getTableView().getItems().get(getIndex());
                        reserverEvent(selectedEvent);  // Appeler la méthode de réservation
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnReserver);
                    }
                }
            };
            return cell;
        });

        // Ajouter les colonnes au tableau
        eventTable.getColumns().addAll(colNom, colDate, colLieu, colCategorie, colPrix, colPlaces, colReserver);

        // Charger les événements
        List<Evenement> evenements;
        try {
            EvenementDAO dao = new EvenementDAO();
            evenements = dao.getEvenements(); // Initialise `evenements`
            eventTable.getItems().addAll(evenements); // Ajout direct des événements au tableau
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les événements : " + e.getMessage());
            return;
        }

        // Tri par date
        colDate.setSortType(TableColumn.SortType.ASCENDING);
        eventTable.getSortOrder().add(colDate);

        // Champs de filtrage
        HBox filterBox = new HBox(10);
        Label filterLabel = new Label("Filtres :");
        TextField filterLieu = new TextField();
        filterLieu.setPromptText("Lieu");
        TextField filterArtiste = new TextField();
        filterArtiste.setPromptText("Artiste/Intervenant");
        ComboBox<String> filterCategorie = new ComboBox<>();
        filterCategorie.setPromptText("Type d'Événement");

        // Charger les catégories dans le filtre
        try {
            EvenementDAO dao = new EvenementDAO();
            filterCategorie.getItems().add("Tous les événements"); // Ajoute l'option "Tous les événements"
            filterCategorie.getItems().addAll(dao.getCategories());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les catégories : " + e.getMessage());
        }

        Button btnFiltrer = new Button("Filtrer");
        btnFiltrer.setOnAction(e -> {
            final String lieu = filterLieu.getText();
            final String artiste = filterArtiste.getText();
            final String categorie = filterCategorie.getValue();

            eventTable.getItems().clear(); // Vider le tableau avant d'appliquer les filtres
            try {
                List<Evenement> filtres = evenements.stream() // Utilisation de la variable evenements existante
                        .filter(ev -> (lieu.isEmpty() || ev.getLieu().toLowerCase().contains(lieu.toLowerCase())))
                        .filter(ev -> {
                            try {
                                // Vérifie la catégorie. Si "Tous les événements" est sélectionné, ne pas filtrer par catégorie
                                return "Tous les événements".equals(categorie) ||
                                        categorie == null ||
                                        new EvenementDAO().getCategorieNameById(ev.getCategorieId()).equals(categorie);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                return false;
                            }
                        })
                        .filter(ev -> (artiste.isEmpty() || ev.getNom().toLowerCase().contains(artiste.toLowerCase())))
                        .toList();

                eventTable.getItems().addAll(filtres);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'application des filtres : " + ex.getMessage());
            }
        });

        filterBox.getChildren().addAll(filterLabel, filterLieu, filterArtiste, filterCategorie, btnFiltrer);

        // Bouton Retour
        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> {
            new DashboardClientScene(primaryStage);
        });

        // Ajouter tous les éléments à la mise en page
        layout.getChildren().addAll(title, filterBox, eventTable, btnRetour);
        layout.setPadding(new Insets(20));
        primaryStage.setScene(new Scene(layout, 800, 600));
    }

    /**
     * Gère la réservation d'un événement.
     * Actuellement, affiche simplement une alerte de confirmation.
     *
     * @param evenement L'événement à réserver.
     */
    private void reserverEvent(Evenement evenement) {
        // Code pour gérer la réservation (par exemple ouvrir un formulaire de réservation ou traiter la logique de réservation)
        showAlert(Alert.AlertType.INFORMATION, "Réservation", "Réservation pour l'événement : " + evenement.getNom() + " effectuée !");
    }

    /**
     * Affiche une alerte avec le message spécifié.
     *
     * @param alertType Le type d'alerte (INFO, WARNING, ERROR, etc.).
     * @param title Le titre de l'alerte.
     * @param message Le message à afficher dans l'alerte.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
