import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static final String URL = "jdbc:sqlite:evenements.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
