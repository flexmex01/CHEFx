package com.example.project12;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;


public class CustomerTimelineControllerF {
    public Button searchSubmit;
    public TextField searchBox2;
    public TextField searchBox;
    public TableView tableViewForFood;
    public Text taunt;
    public TextField searchBox3;
    public TableView tableViewForRestName;
    public AnchorPane DetailsPane;
    public Button showRestDetails;
    public Button viewCart;
    public Text nameOfRestaurant;
    public Text score;
    public Text price;
    public Text zipcode;
    public Text categories;
    private Main main;


    Boolean willShowDetails = false;
    Boolean searchByFoodName = false;
    Boolean searchByFoodCategory = false;
    Boolean searchByFoodPriceRange = false;

    Boolean searchByFoodNameInRest = false;
    Boolean searchByFoodCategoryInRest = false;
    Boolean searchByFoodPriceRangeInRest = false;

    @FXML
    ObservableList<Food> dataF;

    private boolean init1 = true;
    private boolean init2 = true;

    public void setMain(Main main){
        this.main = main;
        taunt.setText(taunt.getText() + " " + main.customer.getUsername());
        taunt.setTextAlignment(TextAlignment.CENTER);
        setVisibilityFalse();
    }


    public void setSearchActionFalse() {
        searchByFoodName = false;
        searchByFoodPriceRange = false;
        searchByFoodCategory = false;
        searchByFoodNameInRest = false;
        searchByFoodCategoryInRest = false;
        searchByFoodPriceRangeInRest = false;
    }
    public void setVisibilityFalse()
    {
        searchBox.setVisible(false);
        searchSubmit.setVisible(false);
        tableViewForFood.setVisible(false);
        tableViewForRestName.setVisible(false);
        searchBox2.setVisible(false);
        searchBox3.setVisible(false);
        showRestDetails.setVisible(false);
        DetailsPane.setVisible(false);
        viewCart.setVisible(false);
        searchBox.clear();
        searchBox2.clear();
        searchBox3.clear();
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

        TableColumn<Food, String> restName = new TableColumn<>("Available at");
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

        tableViewForFood.getColumns().addAll(foodName, category, price, restName);
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

        tableViewForRestName.getColumns().addAll(foodName, category, price,orderedAmount,IncreaseCol,DecreaseCol);
    }

    public void load() {
        setSearchActionFalse();
        setVisibilityFalse();
        if (init1) {
            initializeColumns();
            init1 = false;
        }

        // Add a listener to the tableViewForFood to detect row selection
        tableViewForFood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle the selected item (newValue) here
                // You can access its properties and perform actions as needed
                Food temp = (Food)newValue;
                try {
                    main.custWrapper.write("10," + temp.getRestaurantName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    main.CustomerOrderingRestaurant = ((ArrayList<Restaurant>)main.custWrapper.read()).get(0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                setDetails();
                loadForRestName();
                System.out.println("Selected Food name: " + temp.getName());
            }
        });
        tableViewForFood.setVisible(true);
        dataF = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.foodTemp;
        dataF.addAll(foodTemp);
        tableViewForFood.setEditable(true);
        tableViewForFood.setItems(dataF);
    }

    public void loadForRestName() {
        //System.out.println("Restaurant name: " + main.CustomerOrderingRestaurant.getName());
        setSearchActionFalse();
        setVisibilityFalse();
        showRestDetails.setVisible(true);
        viewCart.setVisible(true);
        if (init2) {
            initializeColumnsForRestName();
            init2 = false;
        }
        tableViewForRestName.setVisible(true);
        dataF = FXCollections.observableArrayList();
        ArrayList<Food> foodTemp = main.CustomerOrderingRestaurant.getFoodCollection();
        dataF.addAll(foodTemp);
        tableViewForRestName.setEditable(true);
        tableViewForRestName.setItems(dataF);
    }


    public void onSearchFoodUsingNameAction(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodName = true;
        searchBox.setVisible(true);
        searchBox.setPromptText("Enter the food name");
        searchSubmit.setVisible(true);
    }

    public void onSearchFoodUsingCategoryAction(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodCategory = true;
        searchBox.setVisible(true);
        searchBox.setPromptText("Enter the food category");
        searchSubmit.setVisible(true);
    }
    public void onSearchFoodUsingPriceRange(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodPriceRange = true;
        searchBox.setVisible(true);
        searchBox2.setVisible(true);
        searchBox2.setPromptText("Enter the upper bound of price");
        searchBox.setPromptText("Enter the lower bound of price");
        searchSubmit.setVisible(true);
    }
    public void onSearchFoodUsingNameInRestAction(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodNameInRest = true;
        searchBox.setVisible(true);
        searchBox2.setVisible(true);
        searchBox2.setPromptText("Enter the food name");
        searchBox.setPromptText("Enter the restaurant name");
        searchSubmit.setVisible(true);
    }

    public void onSearchFoodUsingCategoryInRestAction(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodCategoryInRest = true;
        searchBox.setVisible(true);
        searchBox2.setVisible(true);
        searchBox2.setPromptText("Enter the food category");
        searchBox.setPromptText("Enter the restaurant name");
        searchSubmit.setVisible(true);
    }
    public void onSearchFoodUsingPriceInRestAction(ActionEvent actionEvent) {
        setVisibilityFalse();
        setSearchActionFalse();
        searchBox.clear();
        searchByFoodPriceRangeInRest = true;
        searchBox.setVisible(true);
        searchBox2.setVisible(true);
        searchBox3.setVisible(true);
        searchBox3.setPromptText("Enter the upper bound of price");
        searchBox2.setPromptText("Enter the lower bound of price");
        searchBox.setPromptText("Enter the restaurant name");
        searchSubmit.setVisible(true);
    }
    public void defaultSearchAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        setVisibilityFalse();
        setSearchActionFalse();
        main.custWrapper.write("27,");
        ArrayList<Food>foodTemp = (ArrayList<Food>) main.custWrapper.read();
        main.foodTemp.clear();
        main.foodTemp.addAll(foodTemp);
        load();
    }
    public void finalSearch(ActionEvent actionEvent) throws IOException {

        if (searchBox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Search Field Alert");
            alert.setHeaderText("Empty Search Field alert");
            alert.setContentText("Search box cannot be empty.");
            alert.showAndWait();
        } else {
            if (searchByFoodName) {
                try {
                    main.foodTemp.clear();
                    main.custWrapper.write("21," + searchBox.getText());
                    ArrayList<ArrayList<Food>> foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                    if (!foodTemp.isEmpty()) {
                        for (ArrayList<Food> temp : foodTemp) {
                            main.foodTemp.addAll(temp);
                        }
                        load();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Food Name");
                        alert.setHeaderText("Incorrect Food Name");
                        alert.setContentText("The food name you provided is not correct.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (searchByFoodNameInRest) {
                try {
                    main.foodTemp.clear();
                    main.custWrapper.write("22," + searchBox2.getText() + "," + searchBox.getText());
                    ArrayList<ArrayList<Food> >foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                    if (!foodTemp.isEmpty()) {
                        for (ArrayList<Food> temp : foodTemp) {
                            main.foodTemp.addAll(temp);
                        }
                        load();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Food / Restaurant Name");
                        alert.setHeaderText("Incorrect Food / Restaurant Name");
                        alert.setContentText("The food / restaurant name you provided is not correct.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (searchByFoodCategory) {
                try {
                    main.foodTemp.clear();
                    main.custWrapper.write("23," + searchBox.getText());
                    ArrayList<ArrayList<Food>> foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                    if (!foodTemp.isEmpty()) {
                        for (ArrayList<Food> temp : foodTemp) {
                            main.foodTemp.addAll(temp);
                        }
                        load();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Food Category");
                        alert.setHeaderText("Incorrect Food Category");
                        alert.setContentText("The food category you provided is not correct.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (searchByFoodCategoryInRest) {
                try {
                    main.foodTemp.clear();
                    main.custWrapper.write("24," + searchBox2.getText() + "," + searchBox.getText());
                    ArrayList<ArrayList<Food>> foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                    if (!foodTemp.isEmpty()) {
                        for (ArrayList<Food> temp : foodTemp) {
                            main.foodTemp.addAll(temp);
                        }
                        load();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Incorrect Food Category / Restaurant Name");
                        alert.setHeaderText("Incorrect Food Category / Restaurant Name");
                        alert.setContentText("The food category / restaurant name you provided is not correct.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (searchByFoodPriceRange) {
                if (searchBox2.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty Search Field Alert");
                    alert.setHeaderText("Empty Search Field alert");
                    alert.setContentText("Search box cannot be empty.");
                    alert.showAndWait();
                } else {
                    boolean flag = true;
                    try {
                        try{
                            Double p1 = Double.parseDouble(searchBox.getText());
                            Double p2 = Double.parseDouble((searchBox2.getText()));
                        }catch (Exception e) {
                            flag = false;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Incompatible Input");
                            alert.setHeaderText("Incompatible Input");
                            alert.setContentText("Incompatible data type detected.");
                            alert.showAndWait();
                        }
                        if (flag) {

                            main.foodTemp.clear();
                            main.custWrapper.write("25," + searchBox.getText() + "," + searchBox2.getText());
                            ArrayList<ArrayList<Food>> foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                            if (!foodTemp.isEmpty()) {
                                for (ArrayList<Food> temp : foodTemp) {
                                    main.foodTemp.addAll(temp);
                                }
                                load();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Incorrect Food Price Range");
                                alert.setHeaderText("Incorrect Food Price Range");
                                alert.setContentText("The price range you provided is not correct.");
                                alert.showAndWait();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }else if (searchByFoodPriceRangeInRest) {
                if (searchBox2.getText().isEmpty() || searchBox3.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty Search Field Alert");
                    alert.setHeaderText("Empty Search Field alert");
                    alert.setContentText("Search box cannot be empty.");
                    alert.showAndWait();
                } else {
                    boolean flag = true;
                    try {
                        try{
                            Double p2 = Double.parseDouble((searchBox2.getText()));
                            Double p3 = Double.parseDouble((searchBox3.getText()));
                        }catch (Exception e) {
                            flag = false;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Incompatible Input");
                            alert.setHeaderText("Incompatible Input");
                            alert.setContentText("Incompatible data type detected.");
                            alert.showAndWait();
                        }
                        if (flag) {
                            main.foodTemp.clear();
                            main.custWrapper.write("26," + searchBox3.getText() + "," + searchBox2.getText() + "," + searchBox.getText());
                            ArrayList<ArrayList<Food>> foodTemp = (ArrayList<ArrayList<Food>>) main.custWrapper.read();
                            if (!foodTemp.isEmpty()) {
                                for (ArrayList<Food> temp : foodTemp) {
                                    main.foodTemp.addAll(temp);
                                }
                                load();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Incorrect Food Price Range / Restaurant Name");
                                alert.setHeaderText("Incorrect Food Price Range / Restaurant Name");
                                alert.setContentText("The price range / restaurant name you provided is not correct.");
                                alert.showAndWait();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public void onBackActionInCustomerTimelineF(ActionEvent actionEvent) throws Exception {
        main.showSearchBasisPage();
    }

    public void onBackActionInCustomerTimelineFRestDetails(ActionEvent actionEvent) throws Exception {
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

    public void OrderRefresh(MouseEvent mouseEvent) {
        tableViewForRestName.refresh();
    }

    public void OnRestDetailsAction(ActionEvent actionEvent) {
        willShowDetails = !willShowDetails;
        DetailsPane.setVisible(willShowDetails);
    }
}



