import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    // Méthode statique pour obtenir la connexion
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                String url = "jdbc:mysql://localhost:3306/reservation_ticket"; // Remplacez par votre URL de BD
                String user = "root"; // Remplacez par votre nom d'utilisateur
                String password = ""; // Remplacez par votre mot de passe

                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Impossible de se connecter à la base de données.");
            }
        }
        return connection;
    }
}
