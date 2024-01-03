package com.example.project12;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

import java.util.ArrayList;

public class RestaurantTimelineController {
    public Button backinRestaurantTimeline;
    @FXML
    private TableView tableView;

    @FXML
    private Text nameOfRestaurant;
    @FXML
    private Text score;
    @FXML
    private Text price;
    @FXML
    private Text zipcode;
    @FXML
    private Text categories;
    ObservableList<Food> data;
    private Main main;

    private boolean init = true;

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
    private void initializeColumns() {
        TableColumn<Food, String> foodName = new TableColumn<>("Food Name");
        foodName.setMinWidth(200);
        foodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodName.setCellFactory(column -> {
            return new TableCell<Food, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setAlignment(Pos.CENTER); // Center alignment for text
                    } else {
                        setText(null);
                    }
                }
            };
        });

        TableColumn<Food, String> category = new TableColumn<>("Category");
        category.setMinWidth(200);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(column -> {
            return new TableCell<Food, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setAlignment(Pos.CENTER); // Center alignment for text
                    } else {
                        setText(null);
                    }
                }
            };
        });

        TableColumn<Food, Double> price = new TableColumn<>("Price");
        price.setMinWidth(180);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(column -> {
            return new TableCell<Food, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(String.valueOf(item));
                        setAlignment(Pos.CENTER); // Center alignment for text
                    } else {
                        setText(null);
                    }
                }
            };
        });


        tableView.getColumns().addAll(foodName, category, price);
    }

    public void load() {
        setDetails();
        if (init) {
            initializeColumns();
            init = false;
        }
        data = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.ClientR.getFoodCollection();
        data.addAll(foodTemp);
        tableView.setEditable(true);
        tableView.setItems(data);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void onBackActionInRestaurantTimeline(ActionEvent actionEvent) throws Exception {
        System.out.println("Restaurant Socket Closed");
        main.ClientR.setRound(1);
        main.restWrapper.write(main.ClientR);
        main.restWrapper.write("103," + main.ClientR.getId());
        main.restWrapper.closeConnection();
        main.showRestaurantLoginPage();
    }

    public void onShowOrdersAction(ActionEvent actionEvent) throws Exception {
        main.showOrderedFoodPage();
    }

    public void onAddFoodAction(ActionEvent actionEvent) throws Exception {
        main.showAddFoodPage();
    }

    public void onShowServeHistory(ActionEvent actionEvent) throws Exception {
        main.showServeHistory();
    }
}
