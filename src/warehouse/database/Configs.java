package warehouse.database;

import warehouse.config.Warehouse;

public class Configs {

    protected String dbHost = "localhost";
    protected String dbPort = "3306";
    protected String dbUser = "root";
    protected String dbPass = "root";

    protected String dbName= "warehouse_db";

    public static Warehouse warehouseTemp = new Warehouse();

}