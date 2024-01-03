package com.example.project12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application{
    private Stage stage1;
    public Restaurant ClientR;      // if the client is restaurant
    public Customer customer = new Customer();  // if the client is customer
    public Restaurant CustomerOrderingRestaurant;  // if the client is customer, this is the restaurant from where he will order
    public Customer restaurantViewingCustomer;

    public String serverAddress = "127.0.0.1";

    //for restaurant connection
    public SocketWrapper restWrapper;

    //for customer connection
    public SocketWrapper custWrapper;


    public Restaurant newRest;
    //for admin connection
    public SocketWrapper adminWrapper;

    public ArrayList<Restaurant> restTemp;
    public ArrayList<Food> foodTemp = new ArrayList<Food>();

    @Override
    public void start(Stage stage) throws Exception {

        stage1 = stage;
        ChooserPage();

    }
    public void ChooserPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooserWindow.fxml"));
        Parent root = loader.load();

        // Loading the controller
        ChooserWindowController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage1.setTitle("CHEFx");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showRestaurantLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantLoginPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        RestaurantLoginController controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Restaurant Login");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showAdminLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminLoginPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        AdminLoginController controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Admin Login");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showCustomerLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerLoginPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        CustomerLoginController controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Customer Login");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showSearchBasisPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SearchBasisPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        SearchBasisController controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Choosing search basis");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }



    public void showAddRestaurantPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddRestaurant.fxml"));
        Parent root = loader.load();

        // Loading the controller
        AddRestaurantController controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Adding new restaurant");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showRestaurantTimeline() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantTimeline.fxml"));
        Parent root = loader.load();

        // Loading the controller
        RestaurantTimelineController controller = loader.getController();
        controller.setMain(this);
        controller.load();

        // Set the primary stage
        stage1.setTitle("Restaurant Timeline");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showAddFoodPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddFood.fxml"));
        Parent root = loader.load();

        // Loading the controller
        AddFoodController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage1.setTitle("Adding new food");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showOrderedFoodPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowOrderedFoods.fxml"));
        Parent root = loader.load();
        ShowOrderedFoodsController controller = loader.getController();
        // Loading the controller
        controller.setMain(this);
        controller.loadForCustView();

        // Set the primary stage
        stage1.setTitle("Ordered Foods");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showCart() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Cart.fxml"));
        Parent root = loader.load();
        CartController controller = loader.getController();
        // Loading the controller
        controller.setMain(this);

        // Set the primary stage
        stage1.setTitle("Cart");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showOrderHistory() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderHistory.fxml"));
        Parent root = loader.load();
        OrderHistoryController controller = loader.getController();
        // Loading the controller
        controller.setMain(this);

        // Set the primary stage
        stage1.setTitle("Order History");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showServeHistory() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ServeHistory.fxml"));
        Parent root = loader.load();
        ServeHistoryController controller = loader.getController();
        // Loading the controller
        controller.setMain(this);
        controller.load();
        // Set the primary stage
        stage1.setTitle("Serve History");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }
    public void showCustomerTimelineR() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerTimelineR.fxml"));
        Parent root = loader.load();
        // Loading the controller
        CustomerTimelineControllerR controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Customer Timeline");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public void showCustomerTimelineF() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerTimelineF.fxml"));
        Parent root = loader.load();
        // Loading the controller
        CustomerTimelineControllerF controller = loader.getController();
        controller.setMain(this);
        // Set the primary stage
        stage1.setTitle("Customer Timeline");
        stage1.setScene(new Scene(root, 600, 680));
        stage1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
