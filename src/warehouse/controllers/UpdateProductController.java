package warehouse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.config.Warehouse;
import warehouse.database.Configs;
import warehouse.database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import static warehouse.database.Configs.warehouseTemp;

public class UpdateProductController {

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
    private Button updateButton;

    @FXML
    private Button cancelButton;

    DatabaseHandler databaseHandler = new DatabaseHandler();
    int counter = 0;

    @FXML
    void initialize() {
        Warehouse warehouse = warehouseTemp;

        nameField.setText(warehouse.getName());
        categoryField.setText(warehouse.getProductCategory());
        typeField.setText(warehouse.getProductType());
        vendorField.setText(warehouse.getVendor());
        quantityField.setText(String.valueOf(warehouse.getQuantity()));
        sellPriceField.setText(String.valueOf(warehouse.getSellPrice()));
    }

    @FXML
    void update(ActionEvent event) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseTemp.getId());
        warehouse.setName(nameField.getText());
        warehouse.setProductCategory(categoryField.getText());
        warehouse.setProductType(typeField.getText());
        warehouse.setVendor(vendorField.getText());
        warehouse.setQuantity(Integer.parseInt(quantityField.getText()));
        warehouse.setSellPrice(Double.parseDouble(sellPriceField.getText()));

        databaseHandler.updateProduct(warehouse);

        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
