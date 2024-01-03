package com.example.project12;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class CustomerTimelineControllerR implements Serializable {

    public Button showRestDetails;
    public AnchorPane DetailsPane;
    public Text nameOfRestaurant;
    public Text score;
    public Text price;
    public Text zipcode;
    public Text categories;
    public Button backinCustomerTimelineRestDetails;
    public Button searchRestaurantUsingName;
    public Button SearchRestaurantUsingPrice;
    public Button searchRestaurantUsingScore;
    public Button searchRestaurantUsingZipcode;
    @FXML
    public TableView tableViewForName;
    @FXML
    public TableView tableViewForRestNamePriceZipcodeCategories;
    public Button searchRestaurantUsingCategory;

    public Button defaultSearchButton;
    public TextField searchBoxForScoreH;

    public TextField searchBox3;
    public Button backinCart;
    public Button viewCart;

    @FXML
    private Text taunt;
    private Main main;
    @FXML
    private TextField searchBox;
    @FXML
    private Button searchSubmit;

    @FXML
    private boolean restDetailsVew = false;
    private boolean searchByRestName = false;
    private boolean searchByRestPrice = false;
    private boolean searchByZipcode = false;
    private  boolean searchByCategory = false;
    private  boolean searchByScore = false;



    @FXML
    ObservableList<Food> dataF;
    @FXML
    ObservableList<Restaurant> dataR;

    @FXML


    private boolean init1 = true;
    @FXML
    private boolean init2 = true;


    public void setMain(Main main) {

        this.main = main;
        taunt.setText( taunt.getText() + " " + main.customer.getUsername());
        taunt.setTextAlignment(TextAlignment.CENTER);
        setVisibilityFalse();

    }

    public void setSearchActionFalse() {
        searchByRestName = false;
        searchByRestPrice = false;
        searchByZipcode = false;
        searchByCategory = false;
        searchByScore = false;
    }
    public void setVisibilityFalse()
    {
        viewCart.setVisible(false);
        searchBox.setVisible(false);
        searchSubmit.setVisible(false);
        tableViewForName.setVisible(false);
        DetailsPane.setVisible(false);
        showRestDetails.setVisible(false);
        searchBoxForScoreH.setVisible(false);
        tableViewForRestNamePriceZipcodeCategories.setVisible(false);
        searchBox3.setVisible(false);
    }


    private void setDetails(){
        nameOfRestaurant.setText(main.CustomerOrderingRestaurant.getName());
        score.setText(main.CustomerOrderingRestaurant.getScore() + "");
        price.setText(main.CustomerOrderingRestaurant.getPrice());
        zipcode.setText(main.CustomerOrderingRestaurant.getZipCode());
        if (main.CustomerOrderingRestaurant.getCategories().size() == 3){

            categories.setText(main.CustomerOrderingRestaurant.getCategories().get(0) + ", " + main.CustomerOrderingRestaurant.getCategories().get(1) + ", " + main.CustomerOrderingRestaurant.getCategories().get(2));
        }
        else if (main.CustomerOrderingRestaurant.getCategories().size() == 2){
            categories.setText(main.CustomerOrderingRestaurant.getCategories().get(0) + ", " + main.CustomerOrderingRestaurant.getCategories().get(1));
        }
        else{
            categories.setText(main.CustomerOrderingRestaurant.getCategories().get(0));
        }
        categories.setTextAlignment(TextAlignment.CENTER);

    }

    //On choosing various search options
    public void onSearchActionByRestName(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchBox.setPromptText("Enter the name");
        searchBox.setVisible(true);
        searchSubmit.setVisible(true);
        searchByRestName = true;

    }
    public void onSearchActionByScore(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchBoxForScoreH.clear();
        searchBoxForScoreH.setVisible(true);
        searchBoxForScoreH.setPromptText("Enter the upper bound");
        searchBox.setPromptText("Enter the lower bound");
        searchBox.setVisible(true);
        searchSubmit.setVisible(true);
        searchByScore = true;
    }

    public void onSearchActionByRestPrice(ActionEvent actionEvent) {
        setSearchActionFalse();
        setVisibilityFalse();
        searchBox.clear();
        searchBox.setPromptText("Enter the price (in $)");
        searchBox.setVisible(true);
        searchSubmit.setVisible(true);
        searchByRestPrice = true;
    }

    public void onSearchActionByZipcode(ActionEvent actionEvent) {
        setSearchActionFalse();
        setVisibilityFalse();
        searchBox.clear();
        searchBox.setPromptText("Enter the zipcode");
        searchBox.setVisible(true);
        searchSubmit.setVisible(true);
        searchByZipcode = true;
    }

    public void onSearchActionByCategory(ActionEvent actionEvent) {
        setSearchActionFalse();
        setVisibilityFalse();
        searchBox.clear();
        searchBox.setPromptText("Enter the category");
        searchBox.setVisible(true);
        searchSubmit.setVisible(true);
        searchByCategory = true;
    }
    public void onDefaultSearchAction(ActionEvent actionEvent) {
        setSearchActionFalse();
        setVisibilityFalse();
        try {
            main.custWrapper.write("16," + "Hello");
            Object o = main.custWrapper.read();
            ArrayList<Restaurant> restTemp = null;
            if (o instanceof ArrayList<?>) {
                restTemp = (ArrayList<Restaurant>) o;
            }
            main.restTemp = restTemp;
            for (Restaurant temp : main.restTemp) {
                temp.setMain(main);
                temp.setControl(this);
            }
            loadForRestNamePriceZipcodeCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //On submitting the search for every options except Score
    public void finalSearch(ActionEvent actionEvent) throws IOException {

        if (searchBox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Search Field Alert");
            alert.setHeaderText("Empty Search Field alert");
            alert.setContentText("Search box cannot be empty.");
            alert.showAndWait();
        } else {
            if (searchByRestName) {
                try {
                    main.custWrapper.write("11," + searchBox.getText());
                    ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.custWrapper.read();
                    if (!restTemp.isEmpty()) {
                        main.restTemp = restTemp;
                        for (Restaurant temp : main.restTemp) {
                            temp.setMain(main);
                            temp.setControl(this);
                        }
                        loadForRestNamePriceZipcodeCategories();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Restaurant Name");
                        alert.setHeaderText("Incorrect Restaurant Name");
                        alert.setContentText("The restaurant name you provided is not correct.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (searchByScore) {
                if (searchBoxForScoreH.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty Search Field Alert");
                    alert.setHeaderText("Empty Search Field alert");
                    alert.setContentText("Search box cannot be empty.");
                    alert.showAndWait();
                }
                else {
                    boolean flag = true;
                    try {
                        try{
                            Double p1 = Double.parseDouble(searchBox.getText());
                            Double p2 = Double.parseDouble((searchBoxForScoreH.getText()));
                        }catch (Exception e) {
                            flag = false;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Incompatible Input");
                            alert.setHeaderText("Incompatible Input");
                            alert.setContentText("Incompatible data type detected.");
                            alert.showAndWait();
                        }
                        if (flag) {
                            main.custWrapper.write("12," + searchBox.getText() + "," + searchBoxForScoreH.getText());
                            ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.custWrapper.read();
                            if (!restTemp.isEmpty()) {
                                main.restTemp = restTemp;
                                for (Restaurant temp : main.restTemp) {
                                    temp.setMain(main);
                                    temp.setControl(this);
                                }
                                init2 = true;
                                loadForRestNamePriceZipcodeCategories();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Incorrect Score Range");
                                alert.setHeaderText("Incorrect Score Range");
                                alert.setContentText("The score range you provided is not correct or does not exist.");
                                alert.showAndWait();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (searchByRestPrice) {
                try {
                    main.custWrapper.write("13," + searchBox.getText());
                    ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.custWrapper.read();
                    if (!restTemp.isEmpty()) {
                        main.restTemp = restTemp;
                        for (Restaurant temp : main.restTemp) {
                            temp.setMain(main);
                            temp.setControl(this);
                        }
                        init2 = true;
                        loadForRestNamePriceZipcodeCategories();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Price String");
                        alert.setHeaderText("Incorrect Price String");
                        alert.setContentText("The price you provided is not correct or does not exist.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (searchByZipcode) {
                try {
                    main.custWrapper.write("14," + searchBox.getText());
                    ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.custWrapper.read();
                    if (!restTemp.isEmpty()) {
                        main.restTemp = restTemp;
                        for (Restaurant temp : main.restTemp) {
                            temp.setMain(main);
                            temp.setControl(this);
                        }
                        init2 = true;
                        loadForRestNamePriceZipcodeCategories();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Zipcode");
                        alert.setHeaderText("Incorrect Zipcode");
                        alert.setContentText("The zipcode you provided is not correct or does not exist.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (searchByCategory) {
                try {
                    main.custWrapper.write("15," + searchBox.getText());
                    ArrayList<Restaurant> restTemp = (ArrayList<Restaurant>) main.custWrapper.read();
                    if (!restTemp.isEmpty()) {
                        main.restTemp = restTemp;
                        for (Restaurant temp : main.restTemp) {
                            temp.setMain(main);
                            temp.setControl(this);
                        }
                        init2 = true;
                        loadForRestNamePriceZipcodeCategories();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Category");
                        alert.setHeaderText("Incorrect Category");
                        alert.setContentText("The category you provided is not correct or does not exist.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

        tableViewForName.getColumns().addAll(foodName, category, price,orderedAmount,IncreaseCol,DecreaseCol);
    }

    private void initializeColumnsForRestNamePriceZipcodeCategories() {
        TableColumn<Restaurant, String> restName = new TableColumn<>("Restaurant Name");
        restName.setMinWidth(300);
        restName.setCellValueFactory(new PropertyValueFactory<>("name"));
        restName.setCellFactory(column -> {
            return new TableCell<Restaurant, String>() {
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

        TableColumn<Food, Button> ClickHereToOpenCol = new TableColumn<>("");
        ClickHereToOpenCol.setCellValueFactory(new PropertyValueFactory<>("Click"));
        ClickHereToOpenCol.setMinWidth(255);
        ClickHereToOpenCol.setCellFactory(column -> {
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

        if (!tableViewForRestNamePriceZipcodeCategories.getColumns().isEmpty()){
          tableViewForRestNamePriceZipcodeCategories.getColumns().clear();
      }
        tableViewForRestNamePriceZipcodeCategories.getColumns().addAll(restName,ClickHereToOpenCol);
    }

    public void loadForRestName() {
        //System.out.println("Restaurant name: " + main.CustomerOrderingRestaurant.getName());
        setSearchActionFalse();
        setVisibilityFalse();
        searchBox3.setVisible(true);
        viewCart.setVisible(true);
        showRestDetails.setVisible(true);
        if (init1) {
            initializeColumnsForRestName();
            init1 = false;
        }
        tableViewForName.setVisible(true);
        dataF = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.CustomerOrderingRestaurant.getFoodCollection();
        dataF.addAll(foodTemp);
        tableViewForName.setEditable(true);
        tableViewForName.setItems(dataF);
    }

    public void loadForRestNamePriceZipcodeCategories() {
       // showRestDetails.setVisible(false);
        if (init2) {
            initializeColumnsForRestNamePriceZipcodeCategories();
            init2 = false;
        }
        tableViewForRestNamePriceZipcodeCategories.setVisible(true);
        dataR = FXCollections.observableArrayList();
        //ArrayList<Food> foodTemp = main.CustomerOrderingRestaurant.getFoodCollection();
        dataR.addAll(main.restTemp);
        System.out.println(dataR.size());
        tableViewForRestNamePriceZipcodeCategories.setEditable(true);
        tableViewForRestNamePriceZipcodeCategories.setItems(dataR);
    }

    public void onBackActionInCustomerTimeline(ActionEvent actionEvent) throws Exception {
        main.showSearchBasisPage();
    }

    public void OnRestDetailsAction(ActionEvent actionEvent) {
            restDetailsVew = !restDetailsVew;
            if (restDetailsVew){
                setDetails();
            }
            DetailsPane.setVisible(restDetailsVew);
    }

    public void onBackActionInCustomerTimelineRestDetails(ActionEvent actionEvent) throws Exception {
        main.showSearchBasisPage();
    }


    public void onViewCartAction(ActionEvent actionEvent) throws Exception {
        ArrayList<Food>foodTemp = new ArrayList<>(main.CustomerOrderingRestaurant.getFoodCollection());
        for (Food temp : foodTemp){
            if (temp.getOrderedAmount()>0){
                if (!main.customer.getOrderedFoods().isEmpty() && (temp.getRestaurantId()!= main.customer.getOrderedFoods().get(0).getRestaurantId())) {
                    main.customer.getOrderedFoods().clear();
                }
                main.customer.getOrderedFoods().add(temp);
            }
        }
        main.showCart();
    }

    public void dynamicSearch(KeyEvent keyEvent) {

        String foodName = searchBox3.getText();
        ArrayList<Food>foodTemp = main.CustomerOrderingRestaurant.searchFood(foodName);
        dataF.clear();
        dataF.addAll(foodTemp);
        tableViewForName.setEditable(true);
        tableViewForName.setItems(dataF);
        tableViewForName.refresh();

    }

    public void OrderRefresh(MouseEvent mouseEvent) {
        tableViewForName.refresh();
    }
}



