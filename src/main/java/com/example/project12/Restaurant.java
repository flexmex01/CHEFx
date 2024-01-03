package com.example.project12;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Restaurant implements Serializable {
    private com.example.project12.Main Main;
    private com.example.project12.CustomerTimelineControllerR control;
    private int id;

    public com.example.project12.Main getMain() {
        return Main;
    }

    public void setMain(com.example.project12.Main main) {
        Main = main;
    }

    public CustomerTimelineControllerR getControl() {
        return control;
    }

    public void setControl(CustomerTimelineControllerR control) {
        this.control = control;
    }

    private String name;
    private double score;
    private String price;
    private String zipCode;
    private ArrayList<String>categories;
    private ArrayList<Food>foodCollection;
    private ArrayList<Customer>customersInWaiting;
    private ArrayList<Food>servedFoods;
    private transient Button Click;
    private int customersServed;

    private int totalFoodsServed;

    private double totalSale;

    private  int round;

    public Button getClick() {
        if (Click == null) {
            Click = new Button("Click here to view");
            Click.setTextFill(Color.WHITE);
            AtomicReference<BackgroundFill> backgroundFill = new AtomicReference<>(new BackgroundFill(Color.rgb(2,17,27), new CornerRadii(0), null));
            Click.setBackground(new Background(backgroundFill.get()));
        }
            Click.setOnAction(e -> {
                //System.out.println("Chosen");
               // System.out.println("Food number: " + foodCollection.size());
                Main.CustomerOrderingRestaurant = this;
                control.loadForRestName();
            });

        return Click;
    }

    public Restaurant(int id, String name, double score, String price, String zipCode){
        this.id = id;
        this.name = new String(name);
        this.score = score;
        this.price = new String(price);
        this.zipCode = new String(zipCode);
        categories = new ArrayList<String>();
        foodCollection = new ArrayList<Food>();
        Click = new Button("Click here to view");
        customersInWaiting = new ArrayList<Customer>();
        servedFoods = new ArrayList<>();
        customersServed = 0;
        round = 1;
    }

    public ArrayList<Customer> getCustomersInWaiting() {
        return customersInWaiting;
    }

    public void setCustomersInWaiting(ArrayList<Customer> customersInWaiting) {
        this.customersInWaiting = customersInWaiting;
    }

    //getter functions
    public int getId(){
        return id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getName(){
        return name;
    }

    public int getTotalFoodsServed() {
        return totalFoodsServed;
    }

    public void setTotalFoodsServed(int totalFoodsServed) {
        this.totalFoodsServed = totalFoodsServed;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    public double getScore(){
        return score;
    }
    public String getPrice(){
        return price;
    }
    public String getZipCode(){
        return zipCode;
    }
    public ArrayList<String> getCategories(){
        return categories;
    }
    public ArrayList<Food> getFoodCollection(){
        return foodCollection;
    }

    public int getCustomersServed() {
        return customersServed;
    }

    public void setCustomersServed(int customersServed) {
        this.customersServed = customersServed;
    }

    public ArrayList<Food> getServedFoods() {
        return servedFoods;
    }

    public void setServedFoods(ArrayList<Food> servedFoods) {
        this.servedFoods = servedFoods;
    }

    public void addCategory(String temp){
        if (!containsCategoryV2(temp))
        {
            categories.add(temp);
        }
    }

    public boolean isFoodPresent(Food tempF){
        for ( Food temp : foodCollection)
        {
            if (temp.getName().equalsIgnoreCase(tempF.getName()) && temp.getCategory().equalsIgnoreCase(tempF.getCategory()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean addFood(Food temp){
        if (!isFoodPresent(temp))
        {
            foodCollection.add(temp);
            return true;
        }
        return false;
    }

    public ArrayList<Food> searchFood(String foodName){
        ArrayList<Food>searchFood = new ArrayList<Food>();
        for (Food temp : foodCollection)
        {
            if (temp.getName().toLowerCase().contains(foodName.toLowerCase()))
            {
                searchFood.add(temp);
            }
        }

        return searchFood;
    }


    public ArrayList<Food> searchFoodCategory(String foodCategory){
        ArrayList<Food>searchFood = new ArrayList<Food>();
        for (Food temp : foodCollection)
        {
            if (temp.getCategory().toLowerCase().contains(foodCategory.toLowerCase()))
            {
                searchFood.add(temp);
            }
        }

        return searchFood;
    }

    public ArrayList<Food> searchFoodWithPrice(double priceHigher, double priceLower){
        ArrayList<Food>searchFood = new ArrayList<Food>();
        for (Food temp : foodCollection)
        {
            if((priceHigher>=temp.getPrice()) && (priceLower<=temp.getPrice()))
            {
                searchFood.add(temp);
            }
        }

        return searchFood;
    }

    public boolean containsCategory(String category){
        boolean flag = false;
        for (String temp : categories)
        {
            if (temp.toLowerCase().contains(category.toLowerCase()))
            {
                flag = true;
            }
        }
        return flag;
    }
    public boolean containsCategoryV2(String category){
        boolean flag = false;
        for (String temp : categories)
        {
            if (temp.equalsIgnoreCase(category))
            {
                flag = true;
            }
        }
        return flag;
    }


}
