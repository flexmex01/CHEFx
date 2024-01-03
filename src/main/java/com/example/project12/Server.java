package com.example.project12;


import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;









public class Server extends Application {
    private static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    private static ArrayList<Food> foods = new ArrayList<Food>();

    private static int maxID = 0;
    private static ServerSocket serverSocket;

    private  boolean value = false;

    public HashMap<Integer, SocketWrapper> RestMap = new HashMap<>();
    public HashMap<String, SocketWrapper> custMap = new HashMap<>();


    //getter functions

    public int getRestaurantNumber() {
        return restaurants.size();
    }

    public int getFoodNumber() {
        return foods.size();
    }

    public static Restaurant getRestaurantAtIndex(int index) {
        return restaurants.get(index - 1);
    }

    public Food getFoodAtIndex(int index) {
        return foods.get(index - 1);
    }

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static ArrayList<Food> getFoods() {
        return foods;
    }

    private class ServeThread implements Runnable {
        private SocketWrapper clientWrapper;

        public ServeThread(SocketWrapper clientWrapper) throws IOException {
        this.clientWrapper = clientWrapper;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object received = null;
                    received = clientWrapper.read();
                    if (received instanceof String) {
                        String receivedObject = (String) received;
                        String[] s = receivedObject.split(",");
                        if (s[0].equals("1")) {
                            System.out.println("Rest Socket added");
                            RestMap.put(Integer.parseInt(s[1]), clientWrapper);
                        } else if (s[0].equals("2")) {
                            System.out.println("Customer Socket added");
                            custMap.put(s[1],  clientWrapper);
                        } else if (s[0].equals("101")) {
                            clientWrapper.reset();
                            clientWrapper.write("102");
                        }else if (s[0].equals("103")) {
                            clientWrapper.closeConnection();
                            RestMap.remove(Integer.parseInt(s[1]));
                            System.out.println("Removed Restaurant");
                            break;
                        }else if (s[0].equals("108")) {
                            clientWrapper.closeConnection();
                            custMap.remove((s[1]));
                            System.out.println("Removed Customer");
                            break;
                        }
                        else if (s[0].equals("113")) {
                            clientWrapper.closeConnection();
                            System.out.println("Removed Admin");
                            break;
                        }else if (s[0].equals("10")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithNameWhileAdding(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("11")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithName(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("12")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithScore(Double.parseDouble(s[2]), Double.parseDouble(s[1]));
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("13")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithPrice(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("14")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithZipCode(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("15")) {
                            ArrayList<Restaurant> restTemp = searchRestaurantWithCategory(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("16")) {
                            ArrayList<Restaurant> restTemp = getRestaurants();
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("3")) {
                            System.out.println("Finally " + getRestaurantAtIndex(Integer.parseInt(s[1])).getFoodCollection().size());
                            Restaurant restTemp = getRestaurantAtIndex(Integer.parseInt(s[1]));
                            clientWrapper.reset();
                            clientWrapper.write(restTemp);
                        } else if (s[0].equals("17")) {
                            int carrierID = getMaxId();
                            clientWrapper.reset();
                            clientWrapper.write(carrierID);
                        } else if (s[0].equals("18")) {
                            ArrayList<Customer> custTemp = new ArrayList<>(getRestaurantAtIndex(Integer.parseInt(s[1])).getCustomersInWaiting());
                            //System.out.println("No of customers when refreshed: " + custTemp.size());
                            clientWrapper.reset();
                            clientWrapper.write(custTemp);
                        } else if (s[0].equals("21")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodWithName(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("22")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodinRestaurant(s[1], s[2]);
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("23")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodWithCategory(s[1]);
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("24")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodWithCategoryInRestaurant(s[1], s[2]);
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("25")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodWithPrice(Double.parseDouble(s[2]), Double.parseDouble(s[1]));
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("26")) {
                            ArrayList<ArrayList<Food>> foodTemp = searchFoodInRestaurantWithPrice(Double.parseDouble(s[1]), Double.parseDouble(s[2]), s[3]);
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        } else if (s[0].equals("27")) {
                            ArrayList<Food> foodTemp = getFoods();
                            clientWrapper.reset();
                            clientWrapper.write(foodTemp);
                        }

                    } else if (received instanceof Customer) {
                        //System.out.println("Customer food id " + ((Customer) received).getOrderedFoods().get(0).getRestaurantId());
                        Customer carrier = new Customer();
                        carrier = (Customer) received;
                        //System.out.println("Heyyy");
                        if (!carrier.isRound()) {
                            addCustomersInRestaurant(carrier);

                            if (RestMap.get(carrier.getOrderedFoods().get(0).getRestaurantId()) != null && RestMap.get(carrier.getOrderedFoods().get(0).getRestaurantId()).isConnected()) {
                                System.out.println("Connected Restaurant");
                                SocketWrapper tempWrapper = RestMap.get((((Customer) received).getOrderedFoods().get(0).getRestaurantId()));
                                int restId = (((Customer) received).getOrderedFoods().get(0).getRestaurantId());
                                tempWrapper.write(getRestaurantAtIndex(restId).getCustomersInWaiting());
                            }
                        }
                        else{
                            if (custMap.get(carrier.getUsername()) != null && custMap.get(carrier.getUsername()).isConnected()) {
                                SocketWrapper tempWrapper = custMap.get(carrier.getUsername());
                                System.out.println("Connected Customer");
                                removeCustomersInRestaurant(carrier);
                                tempWrapper.write(carrier);
                            }
                        }
                    } else if (received instanceof Food) {
                        Food temp = (Food) received;
                        boolean flag = addFoodinRestaurant(temp);
                    } else if (received instanceof RestaurantManager) {
                        RestaurantManager restaurantManager = (RestaurantManager) received;
                        ArrayList<Restaurant> restTemp = new ArrayList<>();
                        int restID = -1;
                        clientWrapper.reset();
                        if (restaurantManager.getUserName().equalsIgnoreCase(restaurantManager.getPassword())) {
                            restTemp = searchRestaurantWithNameWhileAdding(restaurantManager.getUserName());
                            if (!restTemp.isEmpty()) {
                                restID = restTemp.get(0).getId();
                            }
                        }
                        if (restID ==-1) {
                            clientWrapper.write(restTemp);
                        }
                        else {
                            if (RestMap.get(restID)!= null){
                                clientWrapper.write(false);
                            }
                            else{
                                //System.out.println("Lgg: " + restTemp.get(0).getServedFoods().size() + " " + restTemp.size());
                                clientWrapper.write(restTemp);
                            }
                        }
                    } else if (received instanceof Admin) {
                        Admin admin = (Admin) received;
                        boolean flag;
                        if (admin.getUserName().equalsIgnoreCase(admin.getPassword()) && admin.getUserName().equalsIgnoreCase("Mesbah")) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                        clientWrapper.reset();
                        clientWrapper.write(flag);
                    } else if (received instanceof Restaurant) {
                        Restaurant newRest = (Restaurant) received;
                        if (newRest.getRound()==1)
                        {
                            //System.out.println("Replaced");
                            replaceRestaurant(newRest);
                        }
                        else {
                            addRestaurant(newRest);
                            //System.out.println("Hello");
                        }
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);


            }
        }
    }


    private class ClientHandler implements Runnable {
        private SocketWrapper clientWrapper;

        public ClientHandler(Socket clientSocket) throws IOException {
            clientWrapper = new SocketWrapper(clientSocket);
        }

        @Override
        public void run() {
            try {
                Thread serveThread = new Thread(new ServeThread(clientWrapper));
                serveThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
public int getRestIdFromName(String restName){
        int id = -1;
        for (Restaurant temp : restaurants)
        {
            if (restName.equalsIgnoreCase(temp.getName()))
            {
                id = temp.getId();
                break;
            }
        }
        return id;
    }
    public String getRestNameFromId(int id){
        String restName = new String();
        for (Restaurant temp : restaurants)
        {
            if (id == temp.getId())
            {
                restName = temp.getName();
                break;
            }
        }
        return restName;
    }
    public static int getMaxId(){
        return maxID+1;
    }


    //for restaurant menu

    public static void addRestaurant(Restaurant temp){
        if (temp.getId()>maxID)
        {
            maxID = temp.getId();
        }
        restaurants.add(temp);
    }

    public static void replaceRestaurant(Restaurant temp){
        for (Restaurant temp1 : restaurants)
        {
            if (temp1.getId() == temp.getId())
            {
                int index = temp1.getId()-1;
                restaurants.remove(index);
                restaurants.add(index,temp);
                //System.out.println("Size of replaced : " + temp1.getServedFoods().size());
                break;
            }

        }
    }

    public static ArrayList<Restaurant> searchRestaurantWithName(String restName){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        for (Restaurant temp : restaurants)
        {
            if (temp.getName().toLowerCase().contains(restName.toLowerCase()))
            {
                searchResult.add(temp);
            }
        }
        return searchResult;
    }

    public static ArrayList<Restaurant> searchRestaurantWithScore(double scoreHigher, double scoreLower){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        for (Restaurant temp : restaurants)
        {
            if ((scoreHigher >= temp.getScore()) && (scoreLower <= temp.getScore()))
            {

                searchResult.add(temp);
            }
        }
        return searchResult;
    }

    public static ArrayList<Restaurant> searchRestaurantWithCategory(String category){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        for(Restaurant temp : restaurants)
        {
            if (temp.containsCategory(category))
            {
                searchResult.add(temp);
            }
        }
        return searchResult;
    }

    public static ArrayList<Restaurant> searchRestaurantWithPrice(String price){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        for (Restaurant temp : restaurants)
        {
            if (price.equals(temp.getPrice()))
            {
                searchResult.add(temp);
            }
        }
        return searchResult;
    }

    public static ArrayList<Restaurant> searchRestaurantWithZipCode(String zipCode){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        for (Restaurant temp : restaurants)
        {
            if (temp.getZipCode().equals(zipCode))
            {

                searchResult.add(temp);
            }
        }
        return searchResult;
    }


    public static boolean addFoodinRestaurant(Food tempF){
        boolean flag = false;
        for (Restaurant temp : restaurants)
        {
            if (temp.getId() == tempF.getRestaurantId())
            {
                flag = temp.addFood(tempF);
                //System.out.println("Size in server: " + temp.getFoodCollection().size());
                break;
            }
        }
        if (flag)
        {
            foods.add(tempF);
        }
        return flag;
    }



    //for food menu
    public static ArrayList<ArrayList<Food> >searchFoodWithName(String foodName){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {
            Carrier = temp.searchFood(foodName);
            if (!Carrier.isEmpty())
            {
                searchFood.add(Carrier);
            }
        }
        return searchFood;
    }

    public static ArrayList <ArrayList<Food>> searchFoodinRestaurant(String foodName, String restName){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {
            if (temp.getName().toLowerCase().contains(restName.toLowerCase())) //
            {
                Carrier = temp.searchFood(foodName);
                if (!Carrier.isEmpty())
                {
                    searchFood.add(Carrier);
                }

            }
        }

        return searchFood;

    }


    public static ArrayList<ArrayList<Food> >searchFoodWithCategory(String foodCategory){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {
            Carrier = temp.searchFoodCategory(foodCategory);
            if (!Carrier.isEmpty())
            {
                searchFood.add(Carrier);
            }
        }
        return searchFood;
    }

    public static ArrayList <ArrayList<Food>> searchFoodWithCategoryInRestaurant(String foodCategory, String restName){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {

            if (temp.getName().toLowerCase().contains(restName.toLowerCase())) //
            {
                Carrier = temp.searchFoodCategory(foodCategory);
                if (!Carrier.isEmpty())
                {
                    searchFood.add(Carrier);
                }
            }
        }
        return searchFood;

    }

    public static ArrayList<ArrayList<Food> >searchFoodWithPrice(double priceHigher, double priceLower){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {
            Carrier = temp.searchFoodWithPrice(priceHigher,priceLower);
            if (!Carrier.isEmpty())
            {
                searchFood.add(Carrier);
            }
        }
        return searchFood;
    }

    public static ArrayList <ArrayList<Food>> searchFoodInRestaurantWithPrice(double priceHigher, double priceLower, String restName){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>Carrier = new ArrayList<Food>();
        for (Restaurant temp : restaurants)
        {
            if (temp.getName().toLowerCase().contains(restName.toLowerCase()))
            {
                Carrier = temp.searchFoodWithPrice(priceHigher, priceLower);
                if (!Carrier.isEmpty())
                {
                    searchFood.add(Carrier);
                }
            }
        }
        return searchFood;
    }
    public ArrayList <ArrayList<Food>> displayCostliestFoodsInRestaurant(String restName){
        ArrayList <ArrayList<Food>>searchFood = new ArrayList<ArrayList<Food>>();
        ArrayList<Food>getCollection = new ArrayList<Food>();

        for (Restaurant tempR : restaurants)
        {
            ArrayList<Food>Carrier= new ArrayList<Food>();
            if (tempR.getName().toLowerCase().contains(restName.toLowerCase()))
            {
                getCollection = tempR.getFoodCollection();
                if (!getCollection.isEmpty())
                {
                    double maxPrice = getCollection.get(0).getPrice();

                    for (Food temp : getCollection)
                    {
                        if(temp.getPrice()>maxPrice)
                        {
                            maxPrice = temp.getPrice();
                        }
                    }
                    for (Food temp : getCollection)
                    {
                        if(temp.getPrice()==maxPrice)
                        {
                            Carrier.add(temp);
                        }
                    }
                }
                if (!Carrier.isEmpty())
                {
                    searchFood.add(Carrier);
                }
            }
        }
        return searchFood;
    }
    public synchronized static ArrayList<Restaurant> searchRestaurantWithNameWhileAdding(String restName){
        ArrayList<Restaurant>searchResult = new ArrayList<Restaurant>();
        System.out.println(restaurants.size() + " " + restName);

        for (Restaurant temp : restaurants)
        {
            if (restName.toLowerCase().equals(temp.getName().toLowerCase()))
            {
                searchResult.add(temp);
                break;
            }
        }
        return searchResult;
    }

    public synchronized static void setFoodCountToZero(Restaurant restaurant){
        ArrayList<Food>foodTemp = restaurant.getFoodCollection();
        for (Food temp : foodTemp)
        {
            temp.setOrderedAmount(0);
        }
    }

    public static void addCustomersInRestaurant(Customer custTemp){
        int id = custTemp.getOrderedFoods().get(0).getRestaurantId();
        for (Restaurant temp : restaurants){
            if (temp.getId() == id){
                temp.getCustomersInWaiting().add(custTemp);
                System.out.println("In waiting after adding: " + temp.getCustomersInWaiting().size());
                setFoodCountToZero(temp);
                break;
            }
        }
    }

    public static void removeCustomersInRestaurant(Customer custTemp){
        int id = custTemp.getOrderedFoods().get(0).getRestaurantId();
        for (Restaurant temp : restaurants){
            if (temp.getId() == id){
                for(Customer tempC : temp.getCustomersInWaiting())
                {
                    if (tempC.getUsername().equals(custTemp.getUsername()))
                    {
                        temp.getCustomersInWaiting().remove(tempC);
                        break;
                    }
                }
                //System.out.println("In waiting after adding: " + temp.getCustomersInWaiting().size());
                //setFoodCountToZero(temp);
                break;
            }
        }
    }

    private static final String RESTAURANT_FILE_NAME = "/Users/mesbah/IdeaProjects/Project1-2/src/main/java/com/example/project12/restaurant.txt";
    private static final String MENU_FILE_NAME = "/Users/mesbah/IdeaProjects/Project1-2/src/main/java/com/example/project12/menu.txt";
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {

        String line;
        boolean flag;
        ArrayList<String> Rcategories;
        Rcategories = new ArrayList<String>();

        //File operations
        BufferedReader fp = new BufferedReader(new FileReader(RESTAURANT_FILE_NAME));
        while (true) {
            line = fp.readLine();
            if (line == null) break;
            //System.out.println(line);
            String[] array = line.split(",", -1);
            Restaurant tempR = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4]);
            for (int i = 5; i < array.length && !array[i].isEmpty(); i++) {
                flag = false;
                if (Rcategories.isEmpty()) {
                    Rcategories.add(array[i]);
                } else {
                    for (String temp : Rcategories) {
                        if (temp.equalsIgnoreCase(array[i])) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        Rcategories.add(array[i]);
                    }
                    tempR.addCategory(array[i]);
                }
            }
            addRestaurant(tempR);
            //System.out.println(tempR.getName());
        }
        BufferedReader tp = new BufferedReader(new FileReader(MENU_FILE_NAME));
        while (true) {
            line = tp.readLine();
            if (line == null) break;
            //System.out.println(line);
            String[] array = line.split(",(?!\\s)", -1);
            Food tempF = new Food(Integer.parseInt(array[0]), array[1], array[2], Double.parseDouble(array[3]));
            //
            tempF.setRestaurantName(getRestaurantAtIndex(Integer.parseInt(array[0])).getName());
            addFoodinRestaurant(tempF);
        }
        System.out.println(restaurants.size());
        fp.close();
        tp.close();
        try {
            serverSocket = new ServerSocket(44444);
            System.out.println("Server is waiting ... ");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepts a client ... ");
                // Create a new thread for each client
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }
}
