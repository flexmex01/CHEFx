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
import java.util.ArrayList;

public class OrderHistoryController {

    public Text ifNotTable;
    private Thread t;
    private volatile boolean stopThread = false;
    private class ReadThread implements Runnable {

        private SocketWrapper tempSocket;

        ReadThread(SocketWrapper tempSocket) throws IOException {
            this.tempSocket = tempSocket;

        }

        @Override
        public void run() {
            try {
                if (!dataF.isEmpty())
                {
                    tableViewForName.setVisible(true);
                    ifNotTable.setVisible(false);
                }
                else {
                    tableViewForName.setVisible(false);
                    ifNotTable.setVisible(true);
                }
                while (!stopThread && !Thread.currentThread().isInterrupted()) {
                    //System.out.println("Hello");
                    Object o = tempSocket.read();
                    if (o instanceof Customer) {
                        Customer obj = (Customer) o;
                            for (Food temp : obj.getOrderedFoods())
                            {
                                for (Food temp2 : main.customer.getOrderHistory()){
                                    if (temp2.getName().equals(temp.getName())){
                                        temp2.setStatus("Delivered");
                                    }
                                }
                            }
                            dataF = FXCollections.observableArrayList();
                            dataF.clear();
                            dataF.addAll(main.customer.getOrderHistory());
                            tableViewForName.setEditable(true);
                            tableViewForName.setItems(dataF);
                            tableViewForName.setVisible(true);
                            tableViewForName.refresh();

                    }
                    else if (o instanceof String){
                        break;
                    }
                }
            }catch (IOException e) {
                // Handle IOException, e.g., log the error or take appropriate action
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // Handle ClassNotFoundException, e.g., log the error or take appropriate action
                e.printStackTrace();
            }
        }
    }


    @FXML
    public TableView tableViewForName;
    @FXML
    private Text taunt;
    private Main main;
    @FXML
    private boolean init1 = true;
    @FXML
    ObservableList<Food> dataF;


    public void setMain(Main main) throws IOException {

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



        TableColumn<Food, String> restName = new TableColumn<>("Order placed at");
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


        TableColumn<Food, String> Status = new TableColumn<>("Status");
        Status.setMinWidth(180);
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Status.setCellFactory(column -> {
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

        tableViewForName.getColumns().addAll(foodName, category, price,orderedAmount,restName,Status);
    }


    public void loadForRestName() throws IOException {
        //System.out.println("Restaurant name: " + main.CustomerOrderingRestaurant.getName());
        if (init1) {
            initializeColumnsForRestName();
            init1 = false;
        }
        tableViewForName.setVisible(true);
        dataF = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.customer.getOrderHistory();
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
        t = new Thread(new ReadThread(main.custWrapper));
        t.start();
    }


    public void onClearHistoryAction(ActionEvent actionEvent) {
        main.customer.getOrderHistory().clear();
        dataF.clear();
        tableViewForName.setEditable(true);
        tableViewForName.setItems(dataF);
        tableViewForName.refresh();
        tableViewForName.setVisible(false);
        ifNotTable.setVisible(true);
    }

    public void onBackActionInHistory(ActionEvent actionEvent) throws Exception {
        stopThread = true;
        if (t.isAlive()){
            t.interrupt();
            System.out.println(t.isInterrupted());
        }
        main.custWrapper.write("101,");
        main.custWrapper.reset();
        main.showCart();
    }
}
