import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class TableauEvenementScene {
    private Stage primaryStage;
    private boolean isOrganisateur;

    public TableauEvenementScene(Stage primaryStage, boolean isOrganisateur) {
        this.primaryStage = primaryStage;
        this.isOrganisateur = isOrganisateur;
    }

    public Scene getScene() {
        Label title = new Label(isOrganisateur ? "Mes Événements" : "Liste des Événements");

        TableView<String> eventTable = new TableView<>();
        eventTable.setPlaceholder(new Label("Aucun événement disponible."));
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Colonnes d'exemple
        TableColumn<String, String> colName = new TableColumn<>("Nom");
        TableColumn<String, String> colDate = new TableColumn<>("Date");
        TableColumn<String, String> colLocation = new TableColumn<>("Lieu");
        eventTable.getColumns().addAll(colName, colDate, colLocation);

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> {
            if (isOrganisateur) {
                DashboardOrganisateurScene dashboardOrganisateur = new DashboardOrganisateurScene(primaryStage);
               // primaryStage.setScene(dashboardOrganisateur.getScene());
            } else {
                DashboardClientScene dashboard = new DashboardClientScene(primaryStage);
               // primaryStage.setScene(dashboard.getScene());
            }
        });

        VBox layout = new VBox(10, title, eventTable, btnRetour);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 500, 400);
    }
}
