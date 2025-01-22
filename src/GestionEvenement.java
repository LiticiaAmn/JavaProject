import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionEvenement {

    private List<Evenement> evenements;

    public GestionEvenement() {
        evenements = new ArrayList<>();
    }

    // Ajouter un événement à la liste
    public void ajouterEvenement(Evenement evenement) {
        evenements.add(evenement);
    }

    // Obtenir tous les événements
    public List<Evenement> getEvenements() {
        return evenements;
    }

    // Obtenir les événements filtrés par type ou lieu
    public List<Evenement> getEvenementsFiltres(String type, String lieu) {
        List<Evenement> result = new ArrayList<>();
        for (Evenement evenement : evenements) {
            if (evenement.getLieu().equals(lieu) || type.equals(evenement.getClass().getSimpleName())) {
                result.add(evenement);
            }
        }
        return result;
    }

    // Afficher les statistiques pour un événement donné
    public void afficherStatistiques(Evenement evenement) {
        System.out.println("Statistiques pour l'événement : " + evenement.getNom());
        System.out.println("Total des tickets vendus : " + evenement.getTotalTicketsVendus());

        for (Map.Entry<String, Integer> entry : evenement.getCategoriesPlaces().entrySet()) {
            String categorie = entry.getKey();
            System.out.println("Taux de remplissage de la catégorie " + categorie + ": " +
                    (evenement.getTauxRemplissage(categorie) * 100) + "%");
        }

        System.out.println("Chiffre d'affaires généré : " + evenement.getChiffreAffaires());
    }
}
