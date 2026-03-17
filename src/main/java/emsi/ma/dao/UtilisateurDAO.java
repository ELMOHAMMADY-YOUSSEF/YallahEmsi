package emsi.ma.dao;
import java.util.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import emsi.ma.db.*;
import emsi.ma.model.*;

public class UtilisateurDAO {

	//methode dyal ajouter etudiant
	public void inscrireEtudiant(Utilisateur u) {
		String sql="INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, telephone, cne, filiere, niveau, role) VALUES(?,?,?,?,?,?,?,?,?)";
		Connection cnx=DatabaseConnection.getConnection();
		
		try {
			PreparedStatement ps=cnx.prepareStatement(sql);
			ps.setString(1,u.getNom());
			ps.setString(2,u.getPrenom());
			ps.setString(3,u.getEmail());
			ps.setString(4,u.getMotDePasse());
			ps.setString(5,u.getTelephone());
			ps.setString(6,u.getCne());
			ps.setString(7,u.getFiliere());
			ps.setString(8,u.getNiveau());
			ps.setString(9,u.getRole());
			
			int testinscrire=ps.executeUpdate();
			if(testinscrire >0) {
				System.out.println("Succès : L'étudiant " + u.getNom() + " a été inscrit dans la base de données Yallah_Emsi !");
			}
			}catch (SQLException e) {
	            System.out.println("Erreur lors de l'inscription de l'étudiant.");
	            e.printStackTrace();
	        }	
	}
	
	public Utilisateur seConnecter(String email,String motDePasse) {
		Utilisateur utilisateurtouver=null;
		String sql="SELECT * FROM Utilisateur WHERE email=? AND mot_de_passe=?";
		Connection cnx = DatabaseConnection.getConnection();
		try {
			PreparedStatement ps=cnx.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, motDePasse);
			//had rs ghadi ikon fiha resultat dyal requet sql yaeni tableau fih gae hadok li eadhm email w modpass shah de type Result
			ResultSet rs = ps.executeQuery(); 
			if (rs.next()) {
				//had next() bhala ka tsawal wach rs fiha les donne kant true ka dir had chi ltaht non mkdir walo 
				utilisateurtouver = new Utilisateur(
                    rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("email"), rs.getString("mot_de_passe"), rs.getString("telephone"),
                    rs.getString("cne"), rs.getString("filiere"), rs.getString("niveau"), rs.getString("role")
                );
                System.out.println("✅ Bienvenue " + utilisateurtouver.getPrenom() + " ! Connexion réussie.");
            } else {
                System.out.println("❌ Erreur : Email ou mot de passe incorrect.");
            }
			
		}catch(SQLException e) {
			System.out.println("❌ Erreur lors de la tentative de connexion.");
            e.printStackTrace();
		}
		return utilisateurtouver;
		
	}

}
