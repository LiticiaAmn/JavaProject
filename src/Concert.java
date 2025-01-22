import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Concert extends Evenement {

    public Concert(int id, String nom, LocalDate date, String lieu, CategorieDePlace categorieDePlace, int prix, int nbrPlaces) {
        super(id, nom, date, lieu, categorieDePlace, prix, nbrPlaces);
    }

    public Concert(int id, String nom, String lieu, int nbrPlaces) {
        super(id, nom, lieu, nbrPlaces);
    }


}
