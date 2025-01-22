import java.time.LocalDate;
import java.util.Map;

public class Spectacle extends Evenement {

    /**
     * Constructeur pour la classe Spectacle
     *
     * @param nom               Nom du spectacle
     * @param date              Date du spectacle
     * @param lieu              Lieu du spectacle
     * @param categoriesPlaces  Map associant chaque catégorie à son nombre de places disponibles
     * @param prixPlaces        Map associant chaque catégorie à son prix
     */
    public Spectacle(String nom, LocalDate date, String lieu,
                     Map<String, Integer> categoriesPlaces, Map<String, Double> prixPlaces) {
        super(nom, date, lieu, categoriesPlaces, prixPlaces);
    }
}
