import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une réservation
    public void ajouterReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (evenement_id, utilisateur_email, categorie, nombre_tickets) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservation.getEvenement().getId());
            stmt.setString(2, reservation.getUtilisateur().getEmail());
            stmt.setString(3, reservation.getCategorieDePlace());
            stmt.setInt(4, reservation.getNombrePlaces());
            stmt.executeUpdate();
        }
    }

    // Récupérer l'historique des réservations d'un utilisateur
    public List<Reservation> getHistoriqueReservations(String email) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE utilisateur_email = ?";
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int evenementId = rs.getInt("evenement_id");
                    String categorie = rs.getString("categorie");
                    int nombreTickets = rs.getInt("nombre_tickets");

                    Evenement evenement = getEvenementById(evenementId);
                    Utilisateur utilisateur = new Utilisateur(email) {
                        @Override
                        public void seConnecter() {

                        }

                        @Override
                        public void seDeconnecter() {

                        }
                    };

                    Reservation reservation = new Reservation(evenement, utilisateur, categorie, nombreTickets);
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    // Annuler une réservation
    public void annulerReservation(int idReservation) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
        }
    }

    // Récupérer un événement par ID
    public Evenement getEvenementById(int id) throws SQLException {
        String sql = "SELECT * FROM evenements WHERE id = ?";
        Evenement evenement = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String lieu = rs.getString("lieu");
                    int nbrPlaces = rs.getInt("nbrPlaces");

                    evenement = new Evenement(id, nom, lieu, nbrPlaces);
                }
            }
        }
        return evenement;
    }
}
