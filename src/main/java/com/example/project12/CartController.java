package com.example.project12;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartController {
    private static final DecimalFormat df = new DecimalFormat("0.00");
        @FXML
        public Button backinCustomerTimeline;

        @FXML
        public TableView tableViewForName;
        @FXML
        public Button submitOrder;
        public Text Price;
        public Text ifNotTable;
        @FXML
        private Text taunt;
        private Main main;
        @FXML
        private boolean init1 = true;
    @FXML
    ObservableList<Food> dataF;


        public void setMain(Main main) {

            this.main = main;
            taunt.setText( taunt.getText() + " " + main.customer.getUsername());
            taunt.setTextAlignment(TextAlignment.CENTER);
            loadForRestName();
        }


        //On choosing various search options

        private void initializeColumnsForRestName() {

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


            TableColumn<Food, Integer> orderedAmount = new TableColumn<>("Ordered Amount");
            orderedAmount.setMinWidth(100);
            orderedAmount.setCellValueFactory(new PropertyValueFactory<>("orderedAmount"));
            orderedAmount.setCellFactory(column -> {
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

            TableColumn<Food, Button> IncreaseCol = new TableColumn<>("Increase");
            IncreaseCol.setCellValueFactory(new PropertyValueFactory<>("increaseAmount"));
            IncreaseCol.setMinWidth(80);
            IncreaseCol.setCellFactory(column -> {
                return new TableCell<Food, Button>() {
                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setGraphic(item); // Use setGraphic to display buttons
                            setAlignment(Pos.CENTER); // Center alignment for buttons
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            });

            TableColumn<Food, Button> DecreaseCol = new TableColumn<>("Decrease");
            DecreaseCol.setCellValueFactory(new PropertyValueFactory<>("decreaseAmount"));
            DecreaseCol.setMinWidth(80);
            DecreaseCol.setCellFactory(column -> {
                return new TableCell<Food, Button>() {
                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setGraphic(item); // Use setGraphic to display buttons
                            setAlignment(Pos.CENTER); // Center alignment for buttons
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            });

            TableColumn<Food, String> restName = new TableColumn<>("To be placed at");
            restName.setMinWidth(180);
            restName.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
            restName.setCellFactory(column -> {
                return new TableCell<Food, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
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

            tableViewForName.getColumns().addAll(foodName, category, price,orderedAmount,IncreaseCol,DecreaseCol,restName);
        }


        public void loadForRestName() {
            double price = 0;
            for (Food temp : main.customer.getOrderedFoods()){
                if (temp.getOrderedAmount()>0)
                {
                    price = price + temp.getPrice()*temp.getOrderedAmount();
                }
            }
            Price.setText("$" + df.format(price));
            Price.setTextAlignment(TextAlignment.CENTER);
           // System.out.println("Restaurant name: " + main.CustomerOrderingRestaurant.getName());
            submitOrder.setVisible(true);
            if (init1) {
                initializeColumnsForRestName();
                init1 = false;
            }
            dataF = FXCollections.observableArrayList();
            ArrayList<Food> foodTemp = main.customer.getOrderedFoods();
            dataF.addAll(foodTemp);
            tableViewForName.setEditable(true);
            tableViewForName.setItems(dataF);
            if (!dataF.isEmpty())
            {
                tableViewForName.setVisible(true);
                ifNotTable.setVisible(false);
            }
            else {
                tableViewForName.setVisible(false);
                ifNotTable.setVisible(true);
            }
        }

        public void onBackActionInCart(ActionEvent actionEvent) throws Exception {
            main.showCustomerTimelineR();
        }

        public void onSubmitOrderAction(ActionEvent actionEvent) throws IOException {
            boolean hasOrdered = false;
            ArrayList<Food>foodTemp = new ArrayList<>(main.customer.getOrderedFoods());
            for (Food temp : foodTemp){
                if (temp.getOrderedAmount()>0){
                    hasOrdered = true;
                    break;
                }
            }
            System.out.println("Ordered food size"+main.customer.getOrderedFoods().size());
            if (!hasOrdered){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning: No ordered foods found");
                alert.setHeaderText("No ordered foods found");
                alert.setContentText("You must increase/decrease ordered amounts in menu to order.");
                alert.showAndWait();
            }
            else{
                //System.out.println(main.customer.getOrderedFoods().size());
                Customer carrier = new Customer();
                carrier = main.customer;
                main.custWrapper.write(carrier);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successful order placement");
                alert.setHeaderText("Successful order placement");
                alert.setContentText("Your order has been placed successfully. Now sit tight and wait for your food to be delivered!");
                alert.showAndWait();
                main.customer.getOrderHistory().addAll(main.customer.getOrderedFoods());
                main.customer.setOrderedFoods( new ArrayList<Food>());
                tableViewForName.refresh();
                tableViewForName.setVisible(false);
                double price = 0.0;
                Price.setText("$" + df.format(price));
                ifNotTable.setVisible(true);
            }
        }

    public void OrderRefresh(MouseEvent mouseEvent) {
        double price = 0;
        for (Food temp : main.customer.getOrderedFoods()){
            if (temp.getOrderedAmount()>0)
            {
                price = price + temp.getPrice()*temp.getOrderedAmount();
            }
        }
        Price.setText("$" + df.format(price));
        tableViewForName.refresh();
        ArrayList<Food>foodTemp = new ArrayList<>(main.customer.getOrderedFoods());
        for (Food temp : foodTemp){
            if (temp.getOrderedAmount()==0){
                main.customer.getOrderedFoods().remove(temp);
            }
        }
        dataF.clear();
        dataF.addAll(main.customer.getOrderedFoods());
        tableViewForName.setItems(dataF);
        tableViewForName.refresh();
        if (!dataF.isEmpty())
        {
            tableViewForName.setVisible(true);
            ifNotTable.setVisible(false);
        }
        else {
            tableViewForName.setVisible(false);
            ifNotTable.setVisible(true);
        }
    }

    public void onResetCartAction(ActionEvent actionEvent) {
            main.customer.getOrderedFoods().clear();
            dataF.clear();
            tableViewForName.refresh();
            tableViewForName.setVisible(false);
            ifNotTable.setVisible(true);
            double price = 0.0;
            Price.setText("$" + df.format(price));
    }

    public void onShowOrderHistory(ActionEvent actionEvent) throws Exception {
            main.showOrderHistory();
    }
}
