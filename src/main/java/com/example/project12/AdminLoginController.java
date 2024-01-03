package com.example.project12;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AdminLoginController {
    public Text adminCredentials;
    public TextField adminNameField;
    public PasswordField adminPassword;
    private Main main;
    public void setMain(Main main){
        this.main = main;
        adminCredentials.setTextAlignment(TextAlignment.CENTER);
    }
    public void onBackActionInAdminLogin(ActionEvent actionEvent) throws Exception {
        main.ChooserPage();
    }
    public void onAdminLogin(ActionEvent actionEvent) throws Exception {
        String userName = adminNameField.getText();
        String password = adminPassword.getText();
        if (userName.isEmpty() || password.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field Alert");
            alert.setTitle("Empty Text Field Alert");
            alert.setContentText("Admin name and password must be entered.");
            alert.showAndWait();
        }
        else {
            Admin admin = new Admin();
            admin.setUserName(userName);
            admin.setPassword(password);
            main.adminWrapper = new SocketWrapper(main.serverAddress, 44444);
            main.adminWrapper.write(admin);
            boolean flag = (Boolean) main.adminWrapper.read();
            if (!flag) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText("Incorrect restaurant credentials provided.");
                alert.showAndWait();
            }
            else {
                main.showAddRestaurantPage();
            }

        }
    }
}
