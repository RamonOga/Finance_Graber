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

    public List<Price> getCompanyPriceList(String ticker) {
        List<Price> rsl = new ArrayList<>();
        String query = "select * from price where name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
             ) {
            statement.setString(1, ticker);
            statement.execute();
            try (ResultSet rs = statement.getResultSet()) {
                while (rs.next()) {
                    rsl.add(new Price(rs.getDouble("highPrice"),
                            rs.getDouble("lowPrice"),
                            rs.getLong("timestamp")
                    ));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rsl;
    }

    public List<Company> getCompanyList() {
        List<Company> rsl = new ArrayList<>();
        String query = "select * from company;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                rsl.add(new Company(rs.getString("ticker"),
                        rs.getString("name"),
                        rs.getString("sector"),
                        rs.getString("industry")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return rsl;
    }

    public void add(Company comp, Price price) {
        String query = "insert into price (name, highprice, lowprice, timestamp) values (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comp.getTicker());
            statement.setDouble(2, price.getHighPrice());
            statement.setDouble(3, price.getLowPrice());
            statement.setLong(4, price.getTimeStamp());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
        }
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

    public void addCompany(Company comp) {
        String query = "insert into company (ticker, name, sector, industry) values (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comp.getTicker());
            statement.setString(2, comp.getName());
            statement.setString(3, comp.getSector());
            statement.setString(4, comp.getIndustry());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.fillInStackTrace();
            System.out.println(sqle.getMessage());
        }
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