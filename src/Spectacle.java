import java.time.LocalDate;
import java.util.Map;

public class Spectacle extends Evenement {

    public Spectacle(int id, String nom, LocalDate date, String lieu, CategorieDePlace categorieDePlace, int prix, int nbrPlaces) {
        super(id, nom, date, lieu, categorieDePlace, prix, nbrPlaces);
    }

    public Spectacle(int id, String nom, String lieu, int nbrPlaces) {
        super(id, nom, lieu, nbrPlaces);
    }
}
