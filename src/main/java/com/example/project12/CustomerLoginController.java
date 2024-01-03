package com.example.project12;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CustomerLoginController {
    public Button backinCustomerLogin;
    @FXML
    private Main main;
    @FXML
    private TextField customerNameField;
    @FXML


    public void setMain(Main main) {
        this.main = main;
    }
    public void onCustomerLogin(ActionEvent actionEvent) throws Exception {
        if (customerNameField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field Alert");
            alert.setHeaderText("Empty Text Field Alert");
            alert.setContentText("You have to enter your username.");
            alert.showAndWait();
        }
        else {
            main.customer.setUsername(customerNameField.getText());
            main.customer.getOrderHistory().clear();
            main.customer.getOrderedFoods().clear();
            main.custWrapper = new SocketWrapper(main.serverAddress,44444);
            main.custWrapper.write("2," + main.customer.getUsername());
            System.out.println("Customer Socket opened");
            main.showSearchBasisPage();
        }
    }

    public void onBackActionInCustomerLogin(ActionEvent actionEvent) throws Exception {
        main.ChooserPage();
    }
}
