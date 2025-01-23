import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Spectacle extends Evenement {

    // Constructeur adapté à la base de données
    public Spectacle(int id, String nom, LocalDateTime date, String lieu, int categorieId, double prix, int nbrPlace, int nbrPlaceRestante) {
        super(id, nom, date, lieu, categorieId, prix, nbrPlace, nbrPlaceRestante);
    }

    // Exemple pour convertir une chaîne en LocalDateTime si nécessaire
    public static LocalDateTime parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter).atStartOfDay(); // Définit l'heure par défaut à 00:00:00
    }

    // Méthode pour effectuer une réservation
    public boolean reserver(int nombreDeTickets) throws PlacesInsuffisantesException {
        if (nombreDeTickets > getNbrPlace()) {
            throw new PlacesInsuffisantesException("Pas assez de places disponibles !");
        }

        // Met à jour le nombre de places et le chiffre d'affaires
        setNbrPlace(getNbrPlace() - nombreDeTickets);
        return true;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", categorieId=" + categorieId +
                ", nbrPlace=" + nbrPlace +
                ", prix=" + prix +
                '}';
    }
}
