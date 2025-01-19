package com.projet.appAsso;

import com.projet.appMembres.InitialisationAppMembre;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAssoView {

    @FXML
    private Label nom_asso;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

    };
}
