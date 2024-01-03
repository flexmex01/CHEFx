package com.example.project12;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddFoodController {
    public Text nameOfRestaurant;
    public Text score;
    public Text price;
    public Text zipcode;
    public Text categories;
    public TextField foodCategory;
    public TextField foodName;
    public TextField foodPrice;
    private  Main main;
    public void setMain(Main main) {
        this.main = main;
        setDetails();
    }

    private void setDetails(){
        nameOfRestaurant.setText(main.ClientR.getName());
        score.setText(main.ClientR.getScore() + "");
        price.setText(main.ClientR.getPrice());
        zipcode.setText(main.ClientR.getZipCode());
        if (main.ClientR.getCategories().size() == 3){

            categories.setText(main.ClientR.getCategories().get(0) + ", " + main.ClientR.getCategories().get(1) + ", " + main.ClientR.getCategories().get(2));
        }
        else if (main.ClientR.getCategories().size() == 2){
            categories.setText(main.ClientR.getCategories().get(0) + ", " + main.ClientR.getCategories().get(1));
        }
        else{
            categories.setText(main.ClientR.getCategories().get(0));
        }
    }
    public void onBackActionInAddFood(ActionEvent actionEvent) throws Exception {
        main.showRestaurantTimeline();
    }

    public void onAddAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String FoodCategory = foodCategory.getText();
        String FoodName = foodName.getText();
        Double price = 0.0;
        try {
            price = Double.parseDouble(foodPrice.getText());
        if (FoodCategory.isEmpty() || FoodName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field Alert");
            alert.setHeaderText("Empty Text Field Alert");
            alert.setContentText("Food cannot be added without filling the text-fields.");
            alert.showAndWait();
        }
        else {
            Food temp = new Food(main.ClientR.getId(), FoodCategory, FoodName, price);
            boolean check = main.ClientR.addFood(temp);
            if (check) {
                main.restWrapper.write(temp);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successful Food Addition");
                alert.setHeaderText("Successful Food Addition");
                alert.setContentText("Food added successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unsuccessful Food Addition");
                alert.setHeaderText("Unsuccessful Food Addition");
                alert.setContentText("The food meant to be added already exists.");
                alert.showAndWait();
            }
        }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Caught!");
            alert.setHeaderText("Exception Caught!");
            alert.setContentText("All information must be provided in the appropriate format.");
            alert.showAndWait();
        }

    }
}
