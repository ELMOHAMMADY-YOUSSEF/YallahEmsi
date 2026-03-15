package emsi.ma.main;

import java.sql.Connection;
import emsi.ma.db.DatabaseConnection; // On importe notre fameux fichier de connexion

public class Main {

    public static void main(String[] args) {
        
        System.out.println("⏳ Lancement du test de connexion...");
        
        // Hna kantswlo l'fichier dyalna : "Wa chof wach t9der t7el l'bab m3a MySQL"
        Connection cnx = DatabaseConnection.getConnection();
        
        // Kan-vérifiw wach l'bab t7el wla b9a msdoud (null)
        if (cnx != null) {
            System.out.println("🎉 Nadi ! La connexion mchat mzyan m3a la base de données Yallah_Emsi.");
        } else {
            System.out.println("❌ Aïe, kayn mochkil f la connexion. Chouf l'erreur lfou9.");
        }

    }
}