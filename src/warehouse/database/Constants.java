package warehouse.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import warehouse.config.Basket;
import warehouse.config.Customer;

/**
 * Класс констант для полей базы данных
 */

public class Constants {

    public static final String WAREHOUSE_DB = "warehouse_main";
    public static final String WAREHOUSE_BASKET = "basket";
    public static final String WAREHOUSE_CUSTOMER = "customer";

    public static final String ID = "id_warehouse";
    public static final String ID_BASKET = "idBasket";
    public static final String NAME = "Наименование";
    public static final String CATEGORY_PRODUCT = "Категория_товара";
    public static final String TYPE_PRODUCT = "Тип_товара";
    public static final String VENDOR = "Производитель";
    public static final String QUANTITY = "Количество";
    public static final String QUANTITYWAREHOUSE = "Количество_склад";
    public static final String RETAIL_PRICE = "Цена_розницы";
    public static final String PURCHASE_PRICE = "Цена_закупки";
    public static final String SELL_PRICE = "Цена_продаж";
    public static final String IDPRODUCT = "idТовара";
    public static final String IDCUSTOMER = "idПокупателя";
    public static final String PRICE = "Цена_фикс";

    public static final String ID_BD_CUSTOMER = "idCustomer";
    public static final String FULLNAME_BD_CUSTOMER = "fullname";
    public static final String LOGIN_BD_CUSTOMER = "login";
    public static final String PASSWORD_BD_CUSTOMER = "password";

    public static int tempCustomer = 0;
    public static int tempBasketInt = 1;
    public static double tempBasketPrice = 0;
    public static double tempBasketSum = 0;
    public static ObservableList<Basket> basketCustomer = FXCollections.observableArrayList();

}