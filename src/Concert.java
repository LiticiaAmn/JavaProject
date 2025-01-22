import java.time.LocalDate;
import java.util.Map;

public class Concert extends Evenement {

    public Concert(String nom, LocalDate date, String lieu, Map<String, Integer> categoriesPlaces, Map<String, Double> prixPlaces) {
        super(nom, date, lieu, categoriesPlaces, prixPlaces);
    }
}
