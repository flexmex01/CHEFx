package com.example.project12;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChooserWindowController {

    @FXML
    private Main main;



    @FXML
    public void OnRestaurantRadio (ActionEvent e) throws Exception {
        main.showRestaurantLoginPage();
    }

    @FXML
    public void OnCustomerRadio (ActionEvent e) throws Exception {
        main.showCustomerLoginPage();
    }

    public void setMain(Main main) {
            this.main = main;
    }

    public void onAdminRadio(ActionEvent actionEvent) throws Exception {
        main.showAdminLoginPage();
    }
}