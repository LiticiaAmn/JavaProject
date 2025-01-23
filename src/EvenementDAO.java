import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvenementDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/reservation_ticket"; // Changez l'URL si nécessaire
    private static final String USER = "root"; // Nom d'utilisateur MySQL
    private static final String PASSWORD = ""; // Mot de passe MySQL
    private Connection connection;

    //Class.forName("com.mysql.cj.jdbc.Driver");

    // Constructeur : établir la connexion
    public EvenementDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Méthode pour ajouter un événement
    public void ajouterEvenement(Evenement evenement) throws SQLException {
        String query = "INSERT INTO evenements (nom, date, lieu, categorieId, prix, nbrPlace) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, evenement.getNom());
            stmt.setTimestamp(2, Timestamp.valueOf(evenement.getDate())); // Convertir LocalDateTime en Timestamp
            stmt.setString(3, evenement.getLieu());
            stmt.setInt(4, evenement.getCategorieId());
            stmt.setDouble(5, evenement.getPrix());
            stmt.setInt(6, evenement.getNbrPlace());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Événement ajouté avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de l'événement.");
            }
        }
    }

    // Méthode pour récupérer les places disponibles pour un événement donné
    public int getPlacesDisponibles(int evenementId) throws SQLException {
        String query = "SELECT nbrPlace FROM evenements WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, evenementId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("nbrPlace");
                }
            }
        }
        return 0; // Retourne 0 si aucun événement trouvé
    }

    // Méthode pour mettre à jour les places disponibles pour un événement donné
    public boolean updatePlacesDisponibles(int evenementId, int placesChange) throws SQLException {
        String query = "UPDATE evenements SET nbrPlace = nbrPlace + ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, placesChange);
            stmt.setInt(2, evenementId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si la mise à jour a été effectuée
        }
    }

    // Récupérer les catégories
    public List<String> getCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT nom FROM categories";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("nom"));
            }
        }
        return categories;
    }

    public int getCategorieIdByName(String nom) throws SQLException {
        String sql = "SELECT id FROM categories WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Catégorie non trouvée.");
            }
        }
    }

    public String getCategorieNameById(int categorieId) throws SQLException {
        String categorieName = null;
        String query = "SELECT nom FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categorieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categorieName = rs.getString("nom");
                }
            }
        }
        return categorieName;
    }

    public List<Evenement> getEvenements() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT e.id, e.nom, e.date, e.lieu, e.categorieId, e.prix, e.nbrPlace FROM evenements e";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                String lieu = rs.getString("lieu");
                int categorieId = rs.getInt("categorieId");
                double prix = rs.getDouble("prix");
                int nbrPlace = rs.getInt("nbrPlace");

                Evenement evenement = new Evenement(id, nom, date, lieu, categorieId, prix, nbrPlace);
                evenements.add(evenement);
            }
        }

        return evenements;
    }

    public void updateEvenement(Evenement evenement) throws SQLException {
        String query = "UPDATE evenements SET nom = ?, date = ?, lieu = ?, categorieId = ?, prix = ?, nbrPlace = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, evenement.getNom());
            stmt.setTimestamp(2, Timestamp.valueOf(evenement.getDate()));
            stmt.setString(3, evenement.getLieu());
            stmt.setInt(4, evenement.getCategorieId());
            stmt.setDouble(5, evenement.getPrix());
            stmt.setInt(6, evenement.getNbrPlace());
            stmt.setInt(7, evenement.getId());
            stmt.executeUpdate();
        }
    }

    public void supprimerEvenement(int evenementId) throws SQLException {
        String query = "DELETE FROM evenements WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, evenementId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement supprimé avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID.");
            }
        }
    }

    public int getTotalTicketsVendus() throws SQLException {
        String query = "SELECT SUM(nbrPlaceInitial - nbrPlace) AS totalVendus FROM evenements";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalVendus");
            }
        }
        return 0;
    }

    public double getChiffreAffairesTotal() throws SQLException {
        String query = "SELECT SUM((nbrPlaceInitial - nbrPlace) * prix) AS chiffreAffaires FROM evenements";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("chiffreAffaires");
            }
        }
        return 0.0;
    }

    public List<String> getTauxRemplissageParCategorie() throws SQLException {
        List<String> tauxParCategorie = new ArrayList<>();
        String query = "SELECT c.nom AS categorie, " +
                "SUM(nbrPlaceInitial - nbrPlace) / SUM(nbrPlaceInitial) * 100 AS tauxRemplissage " +
                "FROM evenements e " +
                "JOIN categories c ON e.categorieId = c.id " +
                "GROUP BY c.nom";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String categorie = rs.getString("categorie");
                double taux = rs.getDouble("tauxRemplissage");
                tauxParCategorie.add(categorie + ": " + String.format("%.2f", taux) + "%");
            }
        }
        return tauxParCategorie;
    }

    public Map<String, Object> getStats() throws SQLException {
        String query = """
        SELECT 
            COUNT(r.id) AS totalTicketsVendus,
            SUM(e.prix * (e.nbrPlace)) AS chiffreAffaires,
            AVG((e.nbrPlace - e.nbrPlace) / NULLIF(e.nbrPlace, 0)) * 100 AS tauxRemplissage
        FROM evenements e
        LEFT JOIN reservations r ON e.id = r.evenementId
    """;

        Map<String, Object> stats = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                stats.put("totalTicketsVendus", rs.getInt("totalTicketsVendus"));
                stats.put("chiffreAffaires", rs.getDouble("chiffreAffaires"));
                stats.put("tauxRemplissage", rs.getDouble("tauxRemplissage"));
            }
        }

        return stats;
    }


    // Méthode pour fermer la connexion
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
