package com.example.project12;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SearchBasisController {
    public Text taunt;
    private Main main;


    public void onBackActionInSearchBasis(ActionEvent actionEvent) throws Exception {
        main.custWrapper.write("108," + main.customer.getUsername());
        main.custWrapper.closeConnection();
        System.out.println("Customer Socket Closed");
        main.showCustomerLoginPage();
    }

    public void onSearchActionByRestaurant(ActionEvent actionEvent) throws Exception {
        main.showCustomerTimelineR();
    }

    public void onSearchActionByFood(ActionEvent actionEvent) throws Exception {
        main.showCustomerTimelineF();

    }

    public void setMain(Main main) {
        this.main = main;
        taunt.setText(taunt.getText() + " " + main.customer.getUsername());
        taunt.setTextAlignment(TextAlignment.CENTER);
    }
}
