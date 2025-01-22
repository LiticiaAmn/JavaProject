import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class Evenement {
    private String nom;
    private LocalDate date;
    private String lieu;
    private Map<String, Integer> categoriesPlaces; // Catégorie -> Nombre de places disponibles
    private Map<String, Double> prixPlaces; // Catégorie -> Prix
    private Map<String, Integer> placesVendu; // Catégorie -> Nombre de places vendues

    public Evenement(String nom, LocalDate date, String lieu, Map<String, Integer> categoriesPlaces, Map<String, Double> prixPlaces) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.categoriesPlaces = new HashMap<>(categoriesPlaces);
        this.prixPlaces = new HashMap<>(prixPlaces);
        this.placesVendu = new HashMap<>();
        for (String categorie : categoriesPlaces.keySet()) {
            placesVendu.put(categorie, 0);
        }
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public Map<String, Integer> getCategoriesPlaces() {
        return categoriesPlaces;
    }

    public Map<String, Double> getPrixPlaces() {
        return prixPlaces;
    }

    public void reserverPlaces(String categorie, int nombre) throws PlacesInsuffisantesException {
        if (categoriesPlaces.containsKey(categorie)) {
            int disponibles = categoriesPlaces.get(categorie);
            if (disponibles >= nombre) {
                categoriesPlaces.put(categorie, disponibles - nombre);
                placesVendu.put(categorie, placesVendu.get(categorie) + nombre);
            } else {
                throw new PlacesInsuffisantesException("Places insuffisantes pour cette catégorie : " + categorie);
            }
        } else {
            throw new IllegalArgumentException("Catégorie invalide : " + categorie);
        }
    }

    public int getTotalTicketsVendus() {
        int total = 0;
        for (int vendu : placesVendu.values()) {
            total += vendu;
        }
        return total;
    }

    public double getChiffreAffaires() {
        double total = 0.0;
        for (String categorie : placesVendu.keySet()) {
            total += placesVendu.get(categorie) * prixPlaces.get(categorie);
        }
        return total;
    }

    public double getTauxRemplissage(String categorie) {
        if (categoriesPlaces.containsKey(categorie)) {
            int totalInitial = categoriesPlaces.get(categorie) + placesVendu.get(categorie);
            return (double) placesVendu.get(categorie) / totalInitial;
        } else {
            throw new IllegalArgumentException("Catégorie invalide : " + categorie);
        }
    }
}
