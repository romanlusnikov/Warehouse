package warehouse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import warehouse.config.Basket;
import warehouse.config.Customer;
import warehouse.config.Warehouse;

import static warehouse.database.Configs.warehouseTemp;

import warehouse.database.Constants;
import warehouse.database.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

//@SuppressWarnings("All")

public class MainController {

    @FXML
    private Button exit;

    @FXML
    private TableView<Warehouse> tableView;

    @FXML
    private TableColumn<Warehouse, String> purchasePriceColumn;

    @FXML
    private TableColumn<Warehouse, String> idColumn;

    @FXML
    private TableColumn<Warehouse, String> categoryColumn;

    @FXML
    private TableColumn<Warehouse, String> nameColumn;

    @FXML
    private TableColumn<Warehouse, String> quantityColumn;

    @FXML
    private TableColumn<Warehouse, String> retailPriceColumn;

    @FXML
    private TableColumn<Warehouse, String> sellPriceColumn;

    @FXML
    private TableColumn<Warehouse, String> typeColumn;

    @FXML
    private TableColumn<Warehouse, String> vendorColumn;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox choiceboxSearch;

    @FXML
    private TableView<Basket> basketView;

    @FXML
    private TableColumn<Warehouse, String> nameBasketColumn;

    @FXML
    private TableColumn<Warehouse, String> quantityBasketColumn;

    @FXML
    private TableColumn<Warehouse, String> sellBasketColumn;

    @FXML
    private TextField summ;


    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private ObservableList<Warehouse> dbTableList = FXCollections.observableArrayList();
    private ObservableList<Basket> basketList = FXCollections.observableArrayList();
    private ObservableList<Basket> intBasketList = FXCollections.observableArrayList();


    Stage stage = new Stage();

    /**
     * Метод инициализации приложения
     */
    @FXML
    void initialize() {
        updateTable();
        updateTableBasket();
        sum();
    }


    @FXML
    void camera() {
        filterOnCategory("Видеонаблюдение");
    }

    @FXML
    void skud() {
        filterOnCategory("СКУД");
    }

    @FXML
    void power() {
        filterOnCategory("Источники питания");
    }

    @FXML
    void intercom() {
        filterOnCategory("Домофоны");
    }

    @FXML
    void network() {
        filterOnCategory("Сетевое оборудование");
    }

    /**
     * Метод обновления таблицы
     */
    @FXML
    void updateTable(MouseEvent mouseEvent) {
        updateTable();
        updateTableBasket();
        sum();
    }

    /**
     * Метод отображения окна добавления значений в базу данных
     */
    @FXML
    void addProduct(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/windows/AddProductWindow.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    /**
     * Метод отображения окна редактирования значения в базе данных
     */
    @FXML
    void editProduct(MouseEvent mouseEvent) {
        Warehouse warehouse = new Warehouse();
        warehouse = tableView.getSelectionModel().getSelectedItem();
        if (warehouse != null) {
            warehouseTemp = warehouse;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/warehouse/windows/UpdateProductWindow.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        }
        updateTable();
    }

    /**
     * Метод удаления значения строки в таблице
     */
    @FXML
    void deleteProduct(MouseEvent mouseEvent) {
        Warehouse warehouse = new Warehouse();
        warehouse = tableView.getSelectionModel().getSelectedItem();
        try {
            if (warehouse != null) {
                int id = warehouse.getId();
                databaseHandler.deleteRow(id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        updateTable();
    }


    /**
     * Метод сортировки и отображения базы данных по категории товара
     */


    void filterOnCategory(String choice) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        dbTableList = databaseHandler.sortByCategory(choice);
        tableView.setItems(dbTableList);

    }

//    void filterOnCategory(String choice) {
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
//        vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        retailPriceColumn.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
//        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
//        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
//
//        dbTableList = databaseHandler.sortByCategory(choice);
//        tableView.setItems(dbTableList);
//
//    }

    /**
     * Метод поиска по выбранному типу
     */
    @FXML
    void searchOnType() throws IllegalArgumentException {
        String search = "%" + searchField.getText() + "%";
        String choice = (String) choiceboxSearch.getValue();
        System.out.println(search);

        if (search.equals("")) {
            updateTable();
        } else {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
            vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

            dbTableList = databaseHandler.findProduct(search, choice);
            tableView.setItems(dbTableList);
        }
    }

 /*     @FXML
   void searchOnType(ActionEvent event) throws IllegalArgumentException {
        String search = "%" + searchField.getText() + "%";
        String choice = (String) choiceboxSearch.getValue();
        System.out.println(search);
        if (search.equals("")) {
            updateTable();
        } else {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
            vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            retailPriceColumn.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
            purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
            sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

            dbTableList = databaseHandler.findProduct(search, choice);
            tableView.setItems(dbTableList);
        }
    }
*/

    /**
     * Метод для обновления содержимого главной таблицы
     */
    @FXML
    void updateTable() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        dbTableList = databaseHandler.getProduct();
        tableView.setItems(dbTableList);

    }

    private void updateTableBasket() {
        nameBasketColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityBasketColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellBasketColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        basketList = databaseHandler.getProductBasket();
        basketView.setItems(basketList);
    }

    public void addBasket(MouseEvent mouseEvent) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse = tableView.getSelectionModel().getSelectedItem();

        Basket basket = new Basket();

        basket.setName(warehouse.getName());
        basket.setQuantity("1");
        basket.setQuantityWarehouse(String.valueOf(warehouse.getQuantity()));
        basket.setSellPrice(String.valueOf(warehouse.getSellPrice()));
        basket.setIdProduct(String.valueOf(warehouse.getId()));
        basket.setIdCustomer(String.valueOf(Constants.tempCustomer));
        basket.setPrice(String.valueOf(warehouse.getSellPrice()));


        Basket basket2 = new Basket();

        int counter = 0;
        int id = Integer.parseInt(basket.getIdProduct());
        double sum=0;

        ResultSet resultSet = databaseHandler.checkNameBasketTable(basket.getName(), "basket", "Наименование");

        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter < 1) {
            databaseHandler.addProductBasket(basket);
            updateTableBasket();
            sum();
            return;
        }
        intBasketList = databaseHandler.searchQuantity(id);
        basket2 = intBasketList.get(0);
        Constants.tempBasketInt = Integer.parseInt(basket2.getQuantity());
        sum= Double.parseDouble(basket2.getPrice())+Double.parseDouble(basket2.getSellPrice());

        if (Constants.tempBasketInt < Integer.parseInt(basket.getQuantityWarehouse())) {
            databaseHandler.updateQuantityADD(id, Integer.parseInt(basket2.getQuantity()),sum);
        }

        updateTableBasket();
        sum();
    }

    public void deleteBasket(MouseEvent mouseEvent) {
        Basket basket = new Basket();
        basket = basketView.getSelectionModel().getSelectedItem();
        double sellPrice = Double.parseDouble(basket.getSellPrice());
        double price = Double.parseDouble(basket.getPrice());
        double sum= sellPrice-price;
        System.out.println(sum);
        int minus = Integer.parseInt(basket.getQuantity()) - 1;
        int id = Integer.parseInt(String.valueOf(basket.getId()));
        if (basket != null) {

            try {
                if (minus == 0) {
                    databaseHandler.deleteRowBasket(id);
                    updateTableBasket();
                    sum();
                    return;
                }
                databaseHandler.deleteProductBasket(id, minus,sum);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            updateTableBasket();
            sum();
        }
    }

    public void allDelete(MouseEvent mouseEvent) {
        databaseHandler.deleteAllRowBasket();
        updateTableBasket();
        sum();
    }

    public void sum() {
        ResultSet resultSet = null;
        try {
            resultSet = databaseHandler.sum();
            if (resultSet.next()) {
                summ.setText(String.valueOf(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void order(ActionEvent event) {
        window("/warehouse/windows/order.fxml");
    }

    public void exit(ActionEvent actionEvent) {
        exit.getScene().getWindow().hide();
        stage.close();
        window("/warehouse/windows/auth.fxml");
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
