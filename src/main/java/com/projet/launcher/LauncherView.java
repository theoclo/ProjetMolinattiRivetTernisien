package com.projet.launcher;

import com.projet.appAsso.AppAsso;
import com.projet.appAsso.HomeAssoView;
import com.projet.appEV.AppEV;
import com.projet.appEV.BaseEVView;
import com.projet.appEV.InitialisationAppEV;
import com.projet.appMembres.AppMembre;
import com.projet.appMembres.ConnexionMembreView;
import com.projet.appMembres.InitialisationAppMembre;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class LauncherView {
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: rgba(237, 237, 237, 0.6)";

    @FXML
    private Button appMembres;
    @FXML
    private Button appEV;
    @FXML
    private Button appAsso;
    @FXML
    private MenuButton eteindre;
    @FXML
    private MenuItem eteindre2;

    @FXML
    public void initialize() {
        eteindre2.setOnAction(event -> {
            System.out.println("Bouton 'Eteindre' cliqué");
            System.exit(0);
        });

        appMembres.setOnMouseEntered(e -> appMembres.setStyle(HOVERED_BUTTON_STYLE));
        appMembres.setOnMouseExited(e -> appMembres.setStyle(IDLE_BUTTON_STYLE));

        appAsso.setOnMouseEntered(e -> appAsso.setStyle(HOVERED_BUTTON_STYLE));
        appAsso.setOnMouseExited(e -> appAsso.setStyle(IDLE_BUTTON_STYLE));

        appEV.setOnMouseEntered(e -> appEV.setStyle(HOVERED_BUTTON_STYLE));
        appEV.setOnMouseExited(e -> appEV.setStyle(IDLE_BUTTON_STYLE));

        appMembres.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            InitialisationAppMembre.main(new String[]{});
                            FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
                            Font.loadFont(inputstream,12);
                            FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
                            fxmlLoader.setController(new ConnexionMembreView());
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Application Membre");
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException o) {
                            o.printStackTrace();
                        }
                    }
                }
            }
        });

        appEV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            InitialisationAppEV.main(new String[]{});
                            FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
                            Font.loadFont(inputstream,12);
                            FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_base.fxml"));
                            fxmlLoader.setController(new BaseEVView());
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Application Espaces Verts");
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException o) {
                            o.printStackTrace();
                        }
                    }
                }
            }
        });

        appAsso.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            InitialisationAppMembre.main(new String[]{});
                            FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
                            Font.loadFont(inputstream,12);
                            FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_connexion.fxml"));
                            fxmlLoader.setController(new HomeAssoView());
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Application Association");
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException o) {
                            o.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
