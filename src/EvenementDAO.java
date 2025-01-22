import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO {
    private Connection connection;

    public EvenementDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour rechercher des événements par mot-clé
    public List<Evenement> rechercherEvenements(String recherche) throws SQLException {
        String sql = "SELECT * FROM evenements WHERE nom LIKE ?";
        List<Evenement> evenements = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + recherche + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Créer des objets Evenement à partir des résultats
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String lieu = rs.getString("lieu");
                    int nbrPlaces = rs.getInt("nbrPlaces");

                    Evenement evenement = new Evenement(id, nom, lieu, nbrPlaces);
                    evenements.add(evenement);
                }
            }
        }
        return evenements;
    }
}
