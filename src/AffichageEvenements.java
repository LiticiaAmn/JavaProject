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

public class AffichageEvenements {



    public AffichageEvenements(Stage primaryStage, boolean isOrganisateur) {
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

            // Nouvelle colonne : Nombre total de places
            TableColumn<Evenement, Integer> colNbrPlace = new TableColumn<>("Nombre total de Places");
            colNbrPlace.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNbrPlace()));

            // Nouvelle colonne : Places restantes
            TableColumn<Evenement, Integer> colNbrPlaceRestante = new TableColumn<>("Places Restantes");
            colNbrPlaceRestante.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNbrPlaceRestante()));

            // Nouvelle colonne : Nombre de places vendues
            TableColumn<Evenement, Integer> colNbrPlacesVendues = new TableColumn<>("Places Vendues");
            colNbrPlacesVendues.setCellValueFactory(data -> new SimpleObjectProperty<>(
                    data.getValue().getNbrPlace() - data.getValue().getNbrPlaceRestante()
            ));

            // Ajouter les colonnes au tableau
            eventTable.getColumns().addAll(colNom, colDate, colLieu, colCategorie, colPrix, colNbrPlace, colNbrPlaceRestante, colNbrPlacesVendues);

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

            // Actions selon le rôle de l'utilisateur
            HBox actionBox = new HBox(10);

            // Bouton Voir les statistiques
            Button btnStats = new Button("Voir les statistiques");
            btnStats.setDisable(true); // Désactivé par défaut

            // Active le bouton uniquement si un événement est sélectionné
            eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                btnStats.setDisable(newSelection == null);
            });

            btnStats.setOnAction(e -> {
                Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    new StatistiqueScene(primaryStage, selectedEvent); // Affiche les statistiques pour l'événement sélectionné
                }
            });


                Button btnModifier = new Button("Modifier");
                btnModifier.setOnAction(e -> {
                    Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                    if (selectedEvent != null) {
                        new FormulaireCreationEvenement(primaryStage);
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un événement à modifier.");
                    }
                });

                Button btnSupprimer = new Button("Supprimer");
                btnSupprimer.setOnAction(e -> {
                    Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                    if (selectedEvent != null) {
                        try {
                            EvenementDAO dao = new EvenementDAO();
                            dao.supprimerEvenement(selectedEvent.getId());
                            eventTable.getItems().remove(selectedEvent);
                            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement supprimé avec succès !");
                        } catch (SQLException ex) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression : " + ex.getMessage());
                        }
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un événement à supprimer.");
                    }
                });

                actionBox.getChildren().addAll(btnModifier, btnSupprimer);


            actionBox.getChildren().add(btnStats);

            // Bouton Retour
            Button btnRetour = new Button("Retour");
            btnRetour.setOnAction(e -> {

                    new DashboardOrganisateurScene(primaryStage);
            });

            layout.getChildren().addAll(title, filterBox, eventTable, actionBox, btnRetour);
            layout.setPadding(new Insets(20));
            primaryStage.setScene(new Scene(layout, 800, 600));
        }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    }

