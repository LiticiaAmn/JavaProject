import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Cette classe permet le démarrage de l'application.
 *
 * @author Mady
 * @version 1.0
 * @since Java 17
 */

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Execute().executeAtStart();
        new ConnexionScene(primaryStage);

    }
    
}
