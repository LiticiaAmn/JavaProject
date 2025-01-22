import java.time.LocalDate;
import java.util.Map;

public class Conference extends Evenement {

    /**
     * Constructeur pour la classe Conference
     *
     * @param nom               Nom de la conférence
     * @param date              Date de la conférence
     * @param lieu              Lieu de la conférence
     * @param categoriesPlaces  Map associant chaque catégorie à son nombre de places disponibles
     * @param prixPlaces        Map associant chaque catégorie à son prix
     */
    public Conference(String nom, LocalDate date, String lieu,
                      Map<String, Integer> categoriesPlaces, Map<String, Double> prixPlaces) {
        super(nom, date, lieu, categoriesPlaces, prixPlaces);
    }
}
