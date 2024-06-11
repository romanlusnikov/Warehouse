package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.config.Warehouse;
import warehouse.database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductController {

    @FXML
    private TextField categoryField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField vendorField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField sellPriceField;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField retailPriceField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    DatabaseHandler databaseHandler = new DatabaseHandler();
    int counter = 0;

    /**
     * Метод добавления значений полей в базу данных
     */
    @FXML
    public void add(ActionEvent event) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(nameField.getText());
        warehouse.setProductCategory(categoryField.getText());
        warehouse.setProductType(categoryField.getText());
        warehouse.setVendor(vendorField.getText());
        warehouse.setQuantity(Integer.parseInt(quantityField.getText()));
        warehouse.setRetailPrice(Double.parseDouble("0"));
        warehouse.setPurchasePrice(Double.parseDouble("0"));
        warehouse.setSellPrice(Double.parseDouble(sellPriceField.getText()));

        ResultSet resultSet = databaseHandler.checkNameTable(warehouse.getName(),"warehouse_main","Наименование");
        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (counter < 1) {
            databaseHandler.addProduct(warehouse);
        }

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Метод возврата/закрытия окна добавления
     */
    @FXML
    public void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}