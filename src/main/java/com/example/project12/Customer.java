package com.example.project12;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable{

    private com.example.project12.Main Main;
    private com.example.project12.ShowOrderedFoodsController control;
    private transient Button deliver;
    private String username;
    private ArrayList<Food>OrderedFoods;
    private transient Button ClickHere;
    private  ArrayList<Food>orderHistory;
    private  boolean round = false;

    public boolean isRound() {
        return round;
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public ArrayList<Food> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Food> orderHistory) {
        this.orderHistory = orderHistory;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Food> getOrderedFoods() {
        return OrderedFoods;
    }

    public void setOrderedFoods(ArrayList<Food> orderedFoods) {
        OrderedFoods = orderedFoods;
    }

    public Button getClickHere() {
        if (ClickHere == null) {
            ClickHere = new Button("Click to view ordered foods");
            ClickHere.setTextFill(Color.WHITE);
            BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(0), null);
            ClickHere.setBackground(new Background(backgroundFill));
        }
            ClickHere.setOnAction(e -> {
            System.out.println("Chosen");
            Main.restaurantViewingCustomer = this;
            control.loadForFoodView();
        });
        return ClickHere;
    }

    public void setClickHere(Button clickHere) {
        ClickHere = clickHere;
    }
    public com.example.project12.Main getMain() {
        return Main;
    }

    public void setMain(com.example.project12.Main main) {
        Main = main;
    }

    public ShowOrderedFoodsController getControl() {
        return control;
    }

    public void setControl(ShowOrderedFoodsController control) {
        this.control = control;
    }
    public Button getDeliver() {
        if (deliver == null) {
            deliver = new Button("Deliver");
            deliver.setTextFill(Color.WHITE);
            BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(0), null);
            deliver.setBackground(new Background(backgroundFill));
            deliver.setOnAction(e -> {
                //System.out.println("Chosen");
                //Main.restaurantViewingCustomer = this;
                Main.ClientR.getCustomersInWaiting().remove(this);
                if (Main.ClientR.getServedFoods().isEmpty()){
                    Main.ClientR.getServedFoods().addAll(this.OrderedFoods);
                }
                else {
                    for (Food temp : OrderedFoods){
                        boolean flag = false;
                        for(Food temp2 : Main.ClientR.getServedFoods()){
                            if (temp2.getName().equals(temp.getName())){
                                temp2.setOrderedAmount(temp2.getOrderedAmount() + temp.getOrderedAmount());
                                flag = true;
                            }
                        }
                        if (!flag)
                        {
                            Main.ClientR.getServedFoods().add(temp);
                        }
                    }
                }

                Main.ClientR.setCustomersServed(Main.ClientR.getCustomersServed() + 1);
                Main.ClientR.setTotalSale(0);
                Main.ClientR.setTotalFoodsServed(0);
                for (Food temp : Main.ClientR.getServedFoods()){
                    Main.ClientR.setTotalSale(Main.ClientR.getTotalSale() + temp.getOrderedAmount()*temp.getPrice());
                    Main.ClientR.setTotalFoodsServed(Main.ClientR.getTotalFoodsServed() + temp.getOrderedAmount());
                }
                Customer servedCustomer = new Customer();
                servedCustomer.setUsername(this.username);
                servedCustomer.setOrderedFoods(this.OrderedFoods);
                servedCustomer.setOrderHistory(this.orderHistory);
                servedCustomer.setRound(true);
                //control.loadForFoodView();
                control.dataC.remove(this);
                if (!control.dataC.isEmpty())
                {
                    control.tableViewForShowingCustomers.setVisible(true);
                    control.ifNotTable.setVisible(false);
                }
                else {
                    control.tableViewForShowingCustomers.setVisible(false);
                    control.ifNotTable.setVisible(true);
                }
                control.tableViewForShowingCustomers.setItems(control.dataC);
                control.tableViewForShowingCustomers.refresh();
                try {
                    Main.restWrapper.write(servedCustomer);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



            });
        }


        return deliver;
    }

    public void setDeliver(Button deliver) {
        this.deliver = deliver;
    }
    Customer() {
        username = "";
        OrderedFoods = new ArrayList<Food>();
        orderHistory = new ArrayList<Food>();
        ClickHere = new Button("Click to view ordered foods");
        deliver = new Button("Deliver");
    }

}
