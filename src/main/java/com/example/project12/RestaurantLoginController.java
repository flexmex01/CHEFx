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

public class RestaurantLoginController {
    public Button backinRestaurantLogin;
    @FXML
    private Main main;
    @FXML
    private TextField restNameField;
    @FXML
    private PasswordField restPassword;
    @FXML
    public void setMain(Main main) {
        this.main = main;
    }

    public void onRestLogin(ActionEvent actionEvent) throws Exception {
        String userName = restNameField.getText();
        String password = restPassword.getText();
        if (userName.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field Alert");
            alert.setHeaderText("Empty Text Field alert");
            alert.setContentText("Restaurant name and password must be entered.");
            alert.showAndWait();
        } else {
            RestaurantManager restaurantManager = new RestaurantManager();
            restaurantManager.setUserName(userName);
            restaurantManager.setPassword(password);
            main.restWrapper = new SocketWrapper(main.serverAddress, 44444);
            try {
                main.restWrapper.write(restaurantManager);
                Object o = main.restWrapper.read();
                if (o instanceof ArrayList<?>) {
                    ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>)o;
                    if (!restTemp.isEmpty()) {
                        main.ClientR = restTemp.get(0);
                        main.showRestaurantTimeline();
                        main.restWrapper.write("1," + main.ClientR.getId());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Credentials");
                        alert.setHeaderText("Incorrect Credentials");
                        alert.setContentText("Incorrect restaurant credentials provided.");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Already Logged In!");
                    alert.setHeaderText("Already Logged In!");
                    alert.setContentText("Multiple login instances found");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public void onBackActionInRestaurantLogin(ActionEvent actionEvent) throws Exception {
        main.ChooserPage();
    }
}
