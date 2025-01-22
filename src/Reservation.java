public class Reservation {
    private int id;
    private Evenement evenement;
    private Utilisateur utilisateur;
    private String categorieDePlace;
    private int nombrePlaces;

    // Constructeur
    public Reservation(int id, Evenement evenement, Utilisateur utilisateur, String categorieDePlace, int nombrePlaces) {
        this.id = id;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.categorieDePlace = categorieDePlace;
        this.nombrePlaces = nombrePlaces;
    }

    public Reservation(Evenement evenement, Utilisateur utilisateur, String categorieDePlace, int nombrePlaces) {
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.categorieDePlace = categorieDePlace;
        this.nombrePlaces = nombrePlaces;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public String getCategorieDePlace() {
        return categorieDePlace;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setCategorieDePlace(String categorieDePlace) {
        this.categorieDePlace = categorieDePlace;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    @Override
    public String toString() {
        return "Réservation{" +
                "evenement=" + evenement.getNom() +
                ", utilisateur=" + utilisateur.getEmail() +
                ", categorie='" + categorieDePlace + '\'' +
                ", nombrePlaces=" + nombrePlaces +
                '}';
    }
}
