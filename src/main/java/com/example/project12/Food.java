package com.example.project12;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    private int restaurantId;
    private String restaurantName;
    private String category;
    private String name;
    private double price;
//
    private int orderedAmount;

    private transient Button increaseAmount;

    private transient Button decreaseAmount;

    private  String status;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Food(int restaurantId, String category, String name, double price){
        this.restaurantId = restaurantId;
        this.category = category;
        this.name = name;
        this.price = price;
        restaurantName = "";
        status = "Pending";
        orderedAmount = 0;
        increaseAmount = new Button("Increase");
        decreaseAmount = new Button("Decrease");
        increaseAmount.setOnAction(e -> {
            //System.out.println("Increase");
            orderedAmount++;
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Increased!");
            a.setHeaderText("Increased!");
            a.setContentText("Ordered Amount Increased: " + orderedAmount);
            a.showAndWait();
        });
        decreaseAmount.setOnAction( e -> {
            if (orderedAmount>0) {
                orderedAmount--;
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Decreased!");
                a.setHeaderText("Decreased!");
                a.setContentText("Ordered Amount Decreased: " + orderedAmount);
                a.showAndWait();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Invalid Action");
                a.setHeaderText("Invalid Action");
                a.setContentText("No order can be less than zero." + orderedAmount);
                a.showAndWait();
            }
        });
    }

    public int getRestaurantId(){
        return restaurantId;
    }
    public String getCategory(){
        return category;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }

    public void setOrderedAmount(int orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public int getOrderedAmount(){
        return orderedAmount;
    }


    public Button getIncreaseAmount() {
        if (increaseAmount == null) {
            increaseAmount = new Button("Increase");
            increaseAmount.setTextFill(Color.WHITE);
            BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(2,17,27), new CornerRadii(0), null);
            increaseAmount.setBackground(new Background(backgroundFill));
        }
            increaseAmount.setOnAction(e -> {
                //System.out.println("Increase");
                orderedAmount++;
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Increased!");
                a.setHeaderText("Increased!");
                a.setContentText("Ordered Amount Increased: " + orderedAmount);
                a.showAndWait();
            });

        return increaseAmount;
    }


    public Button getDecreaseAmount() {
        if (decreaseAmount == null) {
            decreaseAmount = new Button("Decrease");
            decreaseAmount.setTextFill(Color.WHITE);
            BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(2,17,27), new CornerRadii(0), null);
            decreaseAmount.setBackground(new Background(backgroundFill));
        }
        decreaseAmount.setOnAction( e -> {
            if (orderedAmount>0) {
                orderedAmount--;
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Decreased!");
                a.setHeaderText("Decreased!");
                a.setContentText("Ordered Amount Decreased: " + orderedAmount);
                a.showAndWait();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Invalid Action");
                a.setHeaderText("Invalid Action");
                a.setContentText("No order can be less than zero.");
                a.showAndWait();
            }
        });

        return decreaseAmount;
    }


}
