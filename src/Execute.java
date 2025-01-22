import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute {

    public void executeAtStart() throws IOException {
        try {
            String strClassName = "com.mysql.cj.jdbc.Driver";
            String dbName= "reservation_ticket";
            String login= " root";
            String motdepasse= "";
            String strUrl = "jdbc:mysql://localhost:3306/" + dbName;
            Class.forName(strClassName);
            Connection conn = DriverManager.getConnection(strUrl, login, motdepasse);
            conn.close();
        }
        catch(ClassNotFoundException e) {
            System.err.println("Driver non chargé!"); e.printStackTrace();
        } catch(SQLException e) {
            System.out.println("La requête n'a pas été exécutée");
        }
    }
}