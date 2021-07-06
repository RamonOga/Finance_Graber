package model.store;

import db.DataBase;
import model.Company;
import model.Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PsqlStore implements AutoCloseable {
    private Connection connection;
    private DataBase dataBase;

    public PsqlStore(Properties prop) {
        dataBase = DataBase.getDataBase(prop);
        connection = dataBase.getConnection();
    }

    public List<Price> getAll(String name) {
        List<Price> rsl = new ArrayList<>();
        String query = "select * from price where name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            statement.setString(1, name);
            while (rs.next()) {
                rsl.add(new Price(rs.getDouble("highPrice"),
                        rs.getDouble("lowPrice"),
                        rs.getLong("timestamp")
                        ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rsl;
    }

    public boolean add(Company comp, Price price) {
        boolean rsl = false;
        String query = "insert into price (name, highprice, lowprice, timestamp) values (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comp.getTicker());
            statement.setDouble(2, price.getHighPrice());
            statement.setDouble(3, price.getLowPrice());
            statement.setLong(4, price.getTimeStamp());
            statement.executeUpdate();
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
        }
        return rsl;
    }


    public void addMap(Map<Company, Map<Long, Price>> map) {
        Set<Company> companySet = map.keySet();
        for (Company comp : companySet) {
            Map<Long, Price> tmpMap = map.get(comp);
            for (Long l : tmpMap.keySet()) {
                add(comp, tmpMap.get(l));
            }
        }
    }

    public boolean addCompany(Company comp) {
        boolean rsl = false;
        String query = "insert into company (ticker, name, sector, industry) values (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comp.getTicker());
            statement.setString(2, comp.getName());
            statement.setString(3, comp.getSector());
            statement.setString(4, comp.getIndustry());
            statement.executeUpdate();
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
        }
        return rsl;
    }

    public void addCompanyList(List<Company> companies) {
        for (Company comp : companies) {
            addCompany(comp);
        }

    }

    @Override
    public void close()  {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}