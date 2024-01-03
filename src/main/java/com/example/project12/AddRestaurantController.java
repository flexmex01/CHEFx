package com.example.project12;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class AddRestaurantController {
    public TextField name;
    public TextField score;
    public TextField price;
    public Button add;
    public TextField category1;
    public TextField category2;
    public TextField category3;
    public TextField categoryCount;
    public Button next;
    public TextField zipcode;
    private Main main;
    private int COUNT;

    public void setMain(Main main){
        setVisibilityFalse();
        this.main = main;
    }

    private void setVisibilityFalse(){
        add.setVisible(false);
        category1.setVisible(false);
        category2.setVisible(false);
        category3.setVisible(false);
    }
    public void onBackActionInAddRestaurant(ActionEvent actionEvent) throws Exception {
        main.adminWrapper.write("113,");
        main.adminWrapper.closeConnection();
        main.showAdminLoginPage();
    }

    public void onNextAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String restName = "";
        Double restScore = 0.0;
        String restPrice = "";
        String restZip = "";
        int categoryNum = 0;
        try {
            restName = name.getText();
            restScore = Double.parseDouble(score.getText());
            restPrice = price.getText();
            categoryNum = Integer.parseInt(categoryCount.getText());
            COUNT = categoryNum;
            restZip = zipcode.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception caught.");
            alert.setHeaderText("Exception caught.");
            alert.setContentText("Provide the details in their accepted format.");
            alert.showAndWait();
        }
        if (restPrice.isEmpty() || restName.isEmpty() || restZip.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field Alert");
            alert.setHeaderText("Empty Text Field alert");
            alert.setContentText("All information has to be provided.");
            alert.showAndWait();
        } else if (!restPrice.equalsIgnoreCase("$") && !restPrice.equalsIgnoreCase("$%") && !restPrice.equalsIgnoreCase("$$$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect Price Format");
            alert.setHeaderText("Incorrect Price Format");
            alert.setContentText("Price must be in $/$$/$$$ format");
            alert.showAndWait();

        } else if (categoryNum <= 0 || categoryNum > 3) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect Category Amount");
            alert.setHeaderText("Incorrect Category Amount");
            alert.setContentText("Category count must be in between 1 and 3.");
            alert.showAndWait();

        } else {
            name.setVisible(false);
            price.setVisible(false);
            score.setVisible(false);
            next.setVisible(false);
            add.setVisible(true);
            zipcode.setVisible(false);
            main.adminWrapper.write("10," + restName);
            ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.adminWrapper.read();
            if (!restTemp.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Existing Restaurant Alert");
                alert.setHeaderText("Existing Restaurant Alert");
                alert.setContentText("Restaurant with the provided name exists already.");
                alert.showAndWait();

            } else {
                main.adminWrapper.write("17,");
                int id = (int)main.adminWrapper.read();
                main.newRest = new Restaurant(id,restName,restScore,restPrice,restZip);
                categoryCount.setVisible(false);
                if (categoryNum == 1) {
                    category1.setVisible(true);
                } else if (categoryNum == 2) {
                    category1.setVisible(true);
                    category2.setVisible(true);
                } else {
                    category1.setVisible(true);
                    category2.setVisible(true);
                    category3.setVisible(true);
                }
            }

        }
    }

    public void onAddAction(ActionEvent actionEvent) throws IOException {
        String cat1 = "";
        String cat2 = "";
        String cat3 = "";
        if (COUNT == 1)
        {
         cat1 = category1.getText();
         main.newRest.addCategory(cat1);
        }
        else if (COUNT == 2)
        {
            cat1 = category1.getText();
            main.newRest.addCategory(cat1);
            cat2 = category2.getText();
            main.newRest.addCategory(cat2);
        }
        else if (COUNT == 3)
        {
            cat1 = category1.getText();
            main.newRest.addCategory(cat1);
            cat2 = category2.getText();
            main.newRest.addCategory(cat2);
            cat3 = category3.getText();
            main.newRest.addCategory(cat3);
        }
        System.out.println("New ID:  " + main.newRest.getId());
        main.newRest.setRound(0);
        main.adminWrapper.write(main.newRest);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successful Restaurant Addition");
        alert.setHeaderText("Successful Restaurant Addition");
        alert.setContentText("Restaurant has been added successfully.");
        alert.showAndWait();

    }
}
