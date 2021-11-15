package com.beautysalon.dao;

import com.beautysalon.connectionpool.ConnectionPool;
import com.beautysalon.connectionpool.ConnectionWrapper;
import com.beautysalon.entity.*;
import com.beautysalon.servlet.BeautySalonServlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceDao implements BaseDao<Service>{
    static final String URL = "jdbc:mysql://localhost:3306/beautysalon";
    static final String SQL_ADD_SERVICE
            = "INSERT service(name, price, spend_time, profession_id) VALUE(?, ?, ?, (SELECT id FROM profession WHERE name = ?))";
    static final String SQL_DELETE_SERVICE = "DELETE FROM service WHERE id = ?";
    static final String SQL_FIND_SERVICE_BY_ID =
            "SELECT service.name, price, spend_time, profession.name, service.id, profession.id " +
                    "FROM service JOIN profession ON profession.id = service.profession_id WHERE service.id = ?";
    static final String SQL_UPDATE_SERVICE
            = "UPDATE service SET name = ?, price = ?, spend_time = ? WHERE id = ?";
    private static final String SQL_FIND_SERVICE_ALL =
            "SELECT service.name, price, spend_time, profession.name, service.id, profession.id " +
            "FROM service JOIN profession ON profession.id = service.profession_id";

    @Override
    public boolean create(Service service) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_SERVICE, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, service.getName());
            preparedStatement.setDouble(i++, service.getPrice());
            preparedStatement.setTime(i++, service.getSpendTime());
            preparedStatement.setString(i++, service.getProfession().getName());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    service.setId(resultSet.getLong(1));
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return flag;
    }

    public void update(Service service) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_SERVICE);
            int i = 1;
            preparedStatement.setString(i++, service.getName());
            preparedStatement.setDouble(i++, service.getPrice());
            preparedStatement.setTime(i++, service.getSpendTime());
            preparedStatement.setLong(i++, service.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public void delete(long id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_SERVICE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public Service find(long id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Service resultService = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_SERVICE_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                resultService = new Service().setProfession(new Profession());
                resultService.setName(resultSet.getString("service.name"));
                resultService.setPrice(resultSet.getDouble("price"));
                resultService.setSpendTime(resultSet.getTime("spend_time"));
                resultService.getProfession().setName(resultSet.getString("profession.name"));
                resultService.setId(resultSet.getLong("service.id"));
                resultService.getProfession().setId(resultSet.getLong("profession.id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultService;
    }

    public List<Service> findAll() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Service> resultList = new ArrayList<>();

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_SERVICE_ALL);

            while (resultSet.next()){
                Service resultService = new Service().setProfession(new Profession());
                resultService.setName(resultSet.getString("service.name"));
                resultService.setPrice(resultSet.getDouble("price"));
                resultService.setSpendTime(resultSet.getTime("spend_time"));
                resultService.getProfession().setName(resultSet.getString("profession.name"));
                resultService.setId(resultSet.getLong("service.id"));
                resultService.getProfession().setId(resultSet.getLong("profession.id"));
                resultList.add(resultService);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultList;
    }

//    public static Connection getConnection() throws SQLException {
//        Connection connection = null;
//        connection = ConnectionPool.INSTANCE.getConnection();
////        System.out.println("instance: " + ((ConnectionWrapper)connection).instance);
////        System.out.println("inst: " + ((ConnectionWrapper)connection).inst);
//        return connection;
//    }

}
