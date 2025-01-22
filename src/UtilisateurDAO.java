import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilisateurDAO {

    // Paramètres de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/reservation_ticket"; // Adresse de la base de données
    private static final String USER = "root"; // Nom d'utilisateur MySQL
    private static final String PASSWORD = ""; // Mot de passe MySQL

    private Connection connection; // Objet pour gérer la connexion à la base de données

    // Constructeur : initialise la connexion à la base
    public UtilisateurDAO() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour fermer la connexion à la base de données
    public void fermerConnexion() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close(); // Ferme la connexion
            System.out.println("Connexion fermée.");
        }
    }

    // Méthode pour lister tous les utilisateurs dans la table
    public ArrayList<Utilisateur> listerDAO() throws SQLException {
        ArrayList<Utilisateur> liste = new ArrayList<>(); // Liste pour stocker les objets Utilisateur

        String sql = "SELECT * FROM utilisateurs";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Utilisateur utilisateur;
                String typeDeCompte = resultSet.getString("typeDeCompte");

                if (typeDeCompte.equals("Client")) {
                    utilisateur = new Client(
                            resultSet.getString("nom"),
                            resultSet.getString("email"),
                            resultSet.getString("motDePasse")
                    );
                } else {
                    utilisateur = new Organisateur(
                            resultSet.getString("nom"),
                            resultSet.getString("email"),
                            resultSet.getString("motDePasse")
                    );
                }
                liste.add(utilisateur);
            }
        }

        return liste;
    }

    // Méthode pour vérifier un utilisateur par email et mot de passe
    public Utilisateur verifierUtilisateurParEmail(String email, String motDePasse) throws SQLException {
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND motDePasse = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String typeDeCompte = resultSet.getString("typeDeCompte");
                    if (typeDeCompte.equals("Client")) {
                        return new Client(
                                resultSet.getString("nom"),
                                resultSet.getString("email"),
                                resultSet.getString("motDePasse")
                        );
                    } else {
                        return new Organisateur(
                                resultSet.getString("nom"),
                                resultSet.getString("email"),
                                resultSet.getString("motDePasse")
                        );
                    }
                }
            }
        }

        return null; // Retourne null si aucun utilisateur n'est trouvé
    }

    // Méthode pour ajouter un utilisateur dans la base de données
    public void ajouterDAO(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom, email, motDePasse, typeDeCompte) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getMotDePasse());
            preparedStatement.setString(4, utilisateur instanceof Client ? "Client" : "Organisateur");

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Utilisateur ajouté avec succès : " + utilisateur);
            }
        }
    }

    // Méthode pour supprimer un utilisateur de la base de données
    public void supprimerDAO(Utilisateur utilisateur) throws SQLException {
        String sql = "DELETE FROM utilisateurs WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getEmail());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès : " + utilisateur);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'email : " + utilisateur.getEmail());
            }
        }
    }

    // Méthode pour mettre à jour les informations d'un utilisateur
    public void mettreAJourDAO(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE utilisateurs SET nom = ?, motDePasse = ?, typeDeCompte = ? WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getMotDePasse());
            preparedStatement.setString(3, utilisateur instanceof Client ? "Client" : "Organisateur");
            preparedStatement.setString(4, utilisateur.getEmail());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur mis à jour avec succès : " + utilisateur);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'email : " + utilisateur.getEmail());
            }
        }
    }
}
