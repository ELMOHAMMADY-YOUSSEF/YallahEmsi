package emsi.ma.gui;

import emsi.ma.dao.UtilisateurDAO;
import emsi.ma.model.Utilisateur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        // 1. GESTION DES LOGOS (En haut de la page)
        // On charge les images depuis le dossier src/main/resources/images/
        ImageView logoEmsi = null;
        ImageView logoYallah = null;
        
        try {
            logoEmsi = new ImageView(new Image(getClass().getResourceAsStream("/images/emsi_logo.jpg")));
            logoEmsi.setFitHeight(60); // Hauteur du logo
            logoEmsi.setPreserveRatio(true);

            logoYallah = new ImageView(new Image(getClass().getResourceAsStream("/images/yallah_emsi_logo.jpg")));
            logoYallah.setFitHeight(80);
            logoYallah.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("⚠️ Attention : Les images n'ont pas été trouvées dans src/main/resources/images/");
        }

        // On met les deux logos côte à côte dans une boîte horizontale (HBox)
        HBox boxLogos = new HBox(20);
        boxLogos.setAlignment(Pos.CENTER);
        if(logoEmsi != null && logoYallah != null) {
            boxLogos.getChildren().addAll(logoEmsi, logoYallah);
        }

        // 2. LES COMPOSANTS DU FORMULAIRE
        Label titre = new Label("Espace de Connexion");
        titre.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #34495e; -fx-padding: 10px 0px;");

        TextField champEmail = new TextField();
        champEmail.setPromptText("Email (ex: youssef@emsi.ma)");
        // Design moderne pour la case texte (Bordures arrondies)
        champEmail.setStyle("-fx-font-size: 14px; -fx-padding: 12px; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #bdc3c7; -fx-background-color: #f9fbfc;");

        PasswordField champMotDePasse = new PasswordField();
        champMotDePasse.setPromptText("Mot de passe");
        champMotDePasse.setStyle("-fx-font-size: 14px; -fx-padding: 12px; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-color: #bdc3c7; -fx-background-color: #f9fbfc;");

        Button boutonLogin = new Button("Se connecter");
        // Bouton stylé en vert EMSI avec des bords arrondis
        boutonLogin.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        boutonLogin.setMaxWidth(Double.MAX_VALUE); 

        Label messageErreur = new Label(""); 
        messageErreur.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        // Action du bouton
        boutonLogin.setOnAction(event -> {
            UtilisateurDAO dao = new UtilisateurDAO();
            Utilisateur userConnecte = dao.seConnecter(champEmail.getText(), champMotDePasse.getText());
            
            if (userConnecte != null) {
                messageErreur.setStyle("-fx-text-fill: #27ae60;");
                messageErreur.setText("✅ Bienvenue " + userConnecte.getPrenom() + " !");
            } else {
                messageErreur.setStyle("-fx-text-fill: #e74c3c;");
                messageErreur.setText("❌ Identifiants incorrects.");
            }
        });

        // 3. LA CARTE BLANCHE (Card UI)
        VBox carteLogin = new VBox(15); 
        carteLogin.setPadding(new Insets(40, 40, 40, 40)); 
        carteLogin.setAlignment(Pos.CENTER); 
        carteLogin.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        carteLogin.getChildren().addAll(boxLogos, titre, champEmail, champMotDePasse, boutonLogin, messageErreur);

        // Ajout d'une belle ombre sous la carte blanche
        DropShadow ombre = new DropShadow();
        ombre.setColor(Color.rgb(0, 0, 0, 0.15)); // Ombre légère et douce
        ombre.setRadius(20);
        ombre.setOffsetY(5);
        carteLogin.setEffect(ombre);

        // 4. LE FOND DE L'APPLICATION (Gris très clair pour faire ressortir la carte)
        StackPane fondEcran = new StackPane(carteLogin);
        fondEcran.setStyle("-fx-background-color: #ecf0f1;"); // Gris clair
        // On limite la largeur de la carte blanche pour qu'elle ne prenne pas tout l'écran
        carteLogin.setMaxWidth(400);
        carteLogin.setMaxHeight(500);

        // 5. AFFICHAGE
        Scene scene = new Scene(fondEcran, 600, 600); // Fenêtre un peu plus grande
        primaryStage.setTitle("Yallah Emsi - Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}