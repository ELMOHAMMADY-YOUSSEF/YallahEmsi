package emsi.ma.gui;

import emsi.ma.dao.UtilisateurDAO;
import emsi.ma.model.Utilisateur;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        // --- 1. FOND DE L'APPLICATION (Gris très clair et épuré) ---
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f0f4f3;"); // Un gris avec une toute petite pointe de vert

        // --- 2. GESTION DES LOGOS (Optionnels mais stylés) ---
        ImageView logoYallah = null;
        try {
            logoYallah = new ImageView(new Image(getClass().getResourceAsStream("/images/Yallah_Emsi_logo.png")));
            logoYallah.setFitHeight(60);
            logoYallah.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("ℹ️ Logo Yallah non trouvé, on continue sans.");
        }

        // --- 3. CARTE CENTRALE (Card UI) ---
        VBox card = new VBox(20);
        card.setMaxSize(480, 600); // Carte plus grande
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 50px;");

        // Ombre douce et moderne
        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(0, 0, 0, 0.08)); 
        ds.setRadius(30);
        ds.setOffsetY(10);
        card.setEffect(ds);

        // --- 4. EN-TÊTE (Titre Yallah EMSI) ---
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Yallah EMSI");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #2ecc71;"); // Vert nature
        
        Label subtitleLabel = new Label("Espace de Connexion");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitleLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        if (logoYallah != null) {
            headerBox.getChildren().add(logoYallah);
        }
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        VBox.setMargin(headerBox, new Insets(0, 0, 20, 0));

        // --- 5. CHAMPS DE SAISIE AVEC ICÔNES ---
        TextField emailField = new TextField();
        emailField.setPromptText("Adresse email");
        HBox emailBox = createIconInput("✉", emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        HBox passwordBox = createIconInput("🔒", passwordField);

        // Message d'erreur/succès
        Label messageErreur = new Label("");
        messageErreur.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // --- 6. BOUTON DE CONNEXION ---
        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        
        // Effet de Hover (Survol de la souris)
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));

        // L'action MySQL
        loginButton.setOnAction(event -> {
            UtilisateurDAO dao = new UtilisateurDAO();
            Utilisateur userConnecte = dao.seConnecter(emailField.getText(), passwordField.getText());
            
            if (userConnecte != null) {
                messageErreur.setStyle("-fx-text-fill: #2ecc71;"); 
                messageErreur.setText("✅ Connexion réussie, bienvenue " + userConnecte.getPrenom() + " !");
            } else {
                messageErreur.setStyle("-fx-text-fill: #e74c3c;"); 
                messageErreur.setText("❌ Identifiants incorrects.");
            }
        });

        // --- 7. LIENS DU BAS ---
        HBox footerBox = new HBox(5);
        footerBox.setAlignment(Pos.CENTER);
        Label noAccountLabel = new Label("Nouveau sur Yallah EMSI ?");
        noAccountLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 14px;");
        Hyperlink registerLink = new Hyperlink("Créer un compte");
        registerLink.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        registerLink.setBorder(null);
        footerBox.getChildren().addAll(noAccountLabel, registerLink);

        // --- ASSEMBLAGE ---
        card.getChildren().addAll(headerBox, emailBox, passwordBox, loginButton, messageErreur, footerBox);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 900, 750);
        stage.setTitle("Yallah EMSI - Portail");
        stage.setScene(scene);
        stage.show();

        // --- ANIMATIONS ---
        FadeTransition ft = new FadeTransition(Duration.millis(1000), card);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        ScaleTransition st = new ScaleTransition(Duration.millis(1000), card);
        st.setFromX(0.95);
        st.setFromY(0.95);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    // 🛠️ LA FONCTION MAGIQUE POUR METTRE L'ICÔNE DANS LE CHAMP
    private HBox createIconInput(String iconText, TextInputControl inputField) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        // Design du faux champ de texte (contour gris)
        box.setStyle("-fx-background-color: #f9fbfc; -fx-border-color: #bdc3c7; -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5 15 5 15;");

        Label icon = new Label(iconText);
        icon.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 20px;"); // Icône grise par défaut

        // Le vrai champ de texte devient transparent
        inputField.setStyle("-fx-background-color: transparent; -fx-prompt-text-fill: #a6b0b3; -fx-font-size: 16px;");
        inputField.setPrefHeight(45);
        HBox.setHgrow(inputField, Priority.ALWAYS); // Il prend toute la place restante

        box.getChildren().addAll(icon, inputField);

        // Effet quand on clique dedans : ça devient Vert !
        inputField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                box.setStyle("-fx-background-color: #ffffff; -fx-border-color: #2ecc71; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 4 14 4 14;");
                icon.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 20px;");
            } else {
                box.setStyle("-fx-background-color: #f9fbfc; -fx-border-color: #bdc3c7; -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 5 15 5 15;");
                icon.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 20px;");
            }
        });

        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}