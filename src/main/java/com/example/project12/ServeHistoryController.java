package com.example.project12;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ServeHistoryController {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public Text nameOfRestaurant;
    public Text score;
    public Text price;
    public Text zipcode;
    public Text categories;
    public AnchorPane servePlane;
    public Text servedCustomerNo;
    public Text totalFoodsServed;
    public Text totalSale;
    public Text tobeServed;
    public TableView tableView;
    public Text ifNoTable;
    private Main main;
    private boolean init = true;
    ObservableList<Food>data;
    public void onBackActionInServeHistory(ActionEvent actionEvent) throws Exception {
        main.showRestaurantTimeline();
    }

    public void setMain(Main main) {
        this.main = main;
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

        TableColumn<Food, Integer> servedAmount = new TableColumn<>("Served Amount");
        servedAmount.setMinWidth(180);
        servedAmount.setCellValueFactory(new PropertyValueFactory<>("orderedAmount"));
        servedAmount.setCellFactory(column -> {
            return new TableCell<Food, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
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


        tableView.getColumns().addAll(foodName, category, price,servedAmount);
    }

    public void load() {
        setDetails();
        setServePlane();
        if (init) {
            initializeColumns();
            init = false;
        }
        data = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.ClientR.getServedFoods();
        //System.out.println("Size: "+ main.ClientR.getServedFoods().size());
        data.addAll(foodTemp);
        tableView.setEditable(true);
        tableView.setItems(data);
        tableView.setVisible(!data.isEmpty());
        ifNoTable.setVisible(data.isEmpty());
        tableView.refresh();
    }
    private void setServePlane(){
         totalFoodsServed.setText(Integer.toString(main.ClientR.getTotalFoodsServed()));
         servedCustomerNo.setText(Integer.toString(main.ClientR.getCustomersServed()));
         totalSale.setText("$" + df.format(main.ClientR.getTotalSale()));
         tobeServed.setText(Integer.toString(main.ClientR.getCustomersInWaiting().size()));
    }
}


