package warehouse.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.Shake;
import warehouse.config.Customer;
import warehouse.config.Warehouse;
import warehouse.database.DatabaseHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reg {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField fullName;

    @FXML
    private Button exit;

    @FXML
    private Button regButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    Stage stage = new Stage();
    DatabaseHandler databaseHandler = new DatabaseHandler();


    public void exit(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) exit.getScene().getWindow();
        window("/warehouse/windows/auth.fxml");
        stage.close();
    }

    private void window(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    public void reg(javafx.event.ActionEvent actionEvent) {
        if (String.valueOf(fullName.getText())!=null & String.valueOf(loginField.getText())!=null & String.valueOf(passwordField.getText())!=null) {
            Customer customer = new Customer();
            customer.setFullName(fullName.getText());
            customer.setLogin(loginField.getText());
            customer.setPassword(passwordField.getText());

            ResultSet resultSet = databaseHandler.checkNameTable(customer.getFullName(), "customer", "fullName");
            int counter = 0;
            try {
                while (resultSet.next()) {
                    counter++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (counter < 1) {
                databaseHandler.addCustomer(customer);
            }
            Stage stage = (Stage) regButton.getScene().getWindow();
            window("/warehouse/windows/auth.fxml");
            stage.close();
        }else {
            errorLabel.setVisible(true);
            Shake loginShake = new Shake(loginField);
            Shake passwordShake = new Shake(passwordField);
            Shake fullname = new Shake(fullName);
            loginShake.playAnimation();
            passwordShake.playAnimation();
            fullname.playAnimation();
        }

    }
}

