public class CategorieDePlace {

    int id;
    String nom;

    public CategorieDePlace(String nom, int id) {
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }
}