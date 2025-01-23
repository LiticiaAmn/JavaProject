import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evenement extends EvenementDAO {
    public int id;
    public String nom;
    public LocalDateTime date;
    public String lieu;
    public int categorieId;
    public String categorieNom; // Nom de la catégorie
    public double prix;
    public int nbrPlace;

    public Evenement(int id, String nom, LocalDateTime date, String lieu, int categorieId, double prix, int nbrPlace) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.categorieId = categorieId;
        this.prix = prix;
        this.nbrPlace = nbrPlace;
    }

    public Evenement(int id, String nom, LocalDateTime dateTime, String lieu, String categorieNom, double prix, int nbrPlaces) {
        this.id = id;
        this.nom = nom;
        this.date = dateTime;
        this.lieu = lieu;
        this.prix = prix;
        this.nbrPlace = nbrPlaces;
    }

    // Getters et setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public LocalDateTime getDate() { return date; }
    public String getLieu() { return lieu; }
    public int getCategorieId() { return categorieId; }
    public double getPrix() { return prix; }
    public int getNbrPlace() { return nbrPlace; }

    //public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom;}
    public void setDate(LocalDateTime date) { this.date = date;}
    public void setLieu(String lieu) { this.lieu = lieu;}
    //public void setCategorieId(int categorieId) { this.categorieId = categorieId; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setNbrPlace(int nbrPlace) { this.nbrPlace = nbrPlace;}

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", categorieId=" + categorieId +
                ", prix=" + prix +
                ", nbrPlace=" + nbrPlace +
                '}';
    }

    // Si vous voulez inclure le nom de la catégorie :
    public Evenement(int id, String nom, LocalDateTime date, String lieu, int categorieId, String categorieNom, double prix, int nbrPlace) {
        this(id, nom, date, lieu, categorieId, prix, nbrPlace);
        this.categorieNom = categorieNom;
    }

    // permet d'afficher la liste pour showEventTable dans main
    public String toFriendlyString(String categorieNom) {
        return String.format(
                "Nom : %s\nDate : %s\nLieu : %s\nCatégorie : %s\nPrix : %.2f€\nPlaces disponibles : %d",
                nom,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                lieu,
                categorieNom,
                prix,
                nbrPlace
        );
    }


}
