import java.time.LocalDate;
import java.util.Map;

public class Conference extends Evenement {


    public Conference(int id, String nom, LocalDate date, String lieu, CategorieDePlace categorieDePlace, int prix, int nbrPlaces) {
        super(id, nom, date, lieu, categorieDePlace, prix, nbrPlaces);
    }

    public Conference(int id, String nom, String lieu, int nbrPlaces) {
        super(id, nom, lieu, nbrPlaces);
    }
}
