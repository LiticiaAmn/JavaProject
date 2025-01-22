import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Evenement {

    private int id;
    private String nom;
    private LocalDate date;
    private String lieu;
    private CategorieDePlace categorieDePlace;
    private int prix;
    private int nbrPlaces;

    public Evenement(int id, String nom, LocalDate date, String lieu, CategorieDePlace categorieDePlace, int prix, int nbrPlaces) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.categorieDePlace = categorieDePlace;
        this.prix = prix;
        this.nbrPlaces = nbrPlaces;
    }

    public Evenement(int id, String nom, String lieu, int nbrPlaces) {
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.nbrPlaces = nbrPlaces;

    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public CategorieDePlace getCategorieDePlace() {
        return categorieDePlace;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public int getPrix() {
        return prix;
    }

    public int getNbrPlaces() {
        return nbrPlaces;
    }
    /*public void reserver(String categorie, int nombrePlaces) {
        int placesDisponibles = categorieDePlace.getOrDefault(categorie, 0);
        if (placesDisponibles >= nombrePlaces) {
            placesParCategorie.put(categorie, placesDisponibles - nombrePlaces);
        } else {
            throw new IllegalArgumentException("Pas assez de places disponibles");
        }
    }*/
}
