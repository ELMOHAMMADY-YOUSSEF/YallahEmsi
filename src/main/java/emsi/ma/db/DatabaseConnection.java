package emsi.ma.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Informations dyal base de données
    private static final String URL = "jdbc:mysql://localhost:3306/Yallah_Emsi";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456789"; 

    // L'objet Connection unique
    private static Connection connection = null;
    private DatabaseConnection() {}

    //récupérer la connexion
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Charger le driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Établir la connexion
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion à la base Yallah_Emsi réussie !");
                
            } catch (ClassNotFoundException e) {
                System.out.println("❌ Erreur : Le Driver MySQL est introuvable.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ Erreur : Impossible de se connecter. Assure-toi que XAMPP/WAMP est allumé.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}