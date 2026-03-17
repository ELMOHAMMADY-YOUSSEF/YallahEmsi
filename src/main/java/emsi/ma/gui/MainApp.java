package emsi.ma.gui;

import emsi.ma.dao.UtilisateurDAO;
import emsi.ma.model.Utilisateur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        // 1. LES COMPOSANTS (Textes, Cases, Bouton)
        Label titre = new Label("Connexion - Yallah Emsi");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TextField champEmail = new TextField();
        champEmail.setPromptText("Email (ex: youssef.mohammady@emsi.ma)");
        champEmail.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField champMotDePasse = new PasswordField();
        champMotDePasse.setPromptText("Mot de passe");
        champMotDePasse.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button boutonLogin = new Button("Se connecter");
        boutonLogin.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-cursor: hand;");
        boutonLogin.setMaxWidth(Double.MAX_VALUE); 
        
        Label messageErreur = new Label(""); 
        messageErreur.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // 2. L'ACTION DU BOUTON (Vérification dans MySQL)
        boutonLogin.setOnAction(event -> {
            String emailTape = champEmail.getText();
            String mdpTape = champMotDePasse.getText();
            
            UtilisateurDAO dao = new UtilisateurDAO();
            Utilisateur userConnecte = dao.seConnecter(emailTape, mdpTape);
            
            if (userConnecte != null) {
                messageErreur.setStyle("-fx-text-fill: #27ae60;"); // Vert pour le succès
                messageErreur.setText("✅ Bienvenue " + userConnecte.getPrenom() + " !");
            } else {
                messageErreur.setStyle("-fx-text-fill: #c0392b;"); // Rouge pour l'erreur
                messageErreur.setText("❌ Erreur : Email ou mot de passe incorrect.");
            }
        });

        // 3. ORGANISATION (De haut en bas)
        VBox racine = new VBox(20); 
        racine.setPadding(new Insets(50)); 
        racine.setAlignment(Pos.CENTER); 
        racine.getChildren().addAll(titre, champEmail, champMotDePasse, boutonLogin, messageErreur);

        // 4. AFFICHAGE DE LA FENÊTRE
        Scene scene = new Scene(racine, 400, 450);
        primaryStage.setTitle("Yallah Emsi - Authentification");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}