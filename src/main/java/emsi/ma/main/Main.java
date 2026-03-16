package emsi.ma.main;

import java.sql.Connection;
import emsi.ma.db.DatabaseConnection; // On importe notre fameux fichier de connexion
import emsi.ma.dao.*;
import emsi.ma.model.*;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("⏳ Lancement du test de connexion...");
                
        UtilisateurDAO dao = new UtilisateurDAO();
        
        // On simule Youssef qui essaie de se connecter avec les bons identifiants
        System.out.println("👉 Tentative 1 : Avec les bons identifiants...");
        Utilisateur user1 = dao.seConnecter("ahmed.elfassi@emsi.ma", "motdepasse123");

    }
}