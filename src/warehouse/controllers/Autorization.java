package warehouse.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import warehouse.Shake;
import warehouse.config.Basket;
import warehouse.config.Customer;
import warehouse.database.Constants;
import warehouse.database.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autorization {

    boolean isDisabled = true;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordFieldHidden;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button authButton;

    Stage stage = new Stage();

    public void authButton(ActionEvent actionEvent) {
        ResultSet resultSet = null;
        int counter = 0;
        String login = loginField.getText();
        String password = passwordFieldHidden.getText();
        if (login.equals("admin") && password.equals("admin")) {
            authButton.getScene().getWindow().hide();
            stage.close();
            window("/warehouse/windows/adminWindow.fxml");
        } else {
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Customer customer = new Customer();
            customer.setLogin(login);
            customer.setPassword(password);
            resultSet = databaseHandler.checkUser(customer);

            try {
                if (resultSet.next()) {
                    Constants.tempCustomer= resultSet.getInt(1);
                    authButton.getScene().getWindow().hide();
                    stage.close();
                    window("/warehouse/windows/MainWindow.fxml");
                    System.out.println(Constants.tempCustomer);
                }else {
                    errorLabel.setVisible(true);
                    Shake loginShake = new Shake(loginField);
                    Shake passwordShake = new Shake(passwordField);
                    Shake passwordHShake = new Shake(passwordFieldHidden);
                    loginShake.playAnimation();
                    passwordShake.playAnimation();
                    passwordHShake.playAnimation();
                    resultSet = null;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void togglePassword(ActionEvent actionEvent) {
        String password = passwordFieldHidden.getText();
        if (isDisabled) {
            passwordField.setText(password);
            passwordField.toFront();
            isDisabled = false;
        } else {
            password = passwordField.getText();
            passwordFieldHidden.setText(password);
            passwordFieldHidden.toFront();
            isDisabled = true;
        }
    }

    public void reg(MouseEvent mouseEvent) {
        passwordField.getScene().getWindow().hide();
        window("/warehouse/windows/reg.fxml");

    }

    public void window(String path) {
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
}
