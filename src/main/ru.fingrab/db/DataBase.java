package db;

import java.sql.*;
import java.util.Properties;

public class DataBase {
    private static DataBase dataBase;
    private Properties properties;
    private Connection connection;

    private DataBase(Properties properties) {
        initProperties(properties);
        initConnection();
    }

    public static DataBase getDataBase(Properties propertiesPath) {
        if (dataBase == null) {
            dataBase = new DataBase(propertiesPath);
        }
        return dataBase;
    }

    private void initConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(properties.getProperty("urlSQL"),
                    properties.getProperty("loginSQL"),
                    properties.getProperty("passwordSQL"));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void initProperties(Properties prop) {
        properties = prop;
    }

    public Connection getConnection() {
        return connection;
    }

}