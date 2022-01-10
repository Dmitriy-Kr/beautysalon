package com.beautysalon.dao;

import com.beautysalon.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderingDao extends AbstractDao implements BaseDao<Ordering>{
    static final String SQL_ADD_ORDERING
            = "INSERT ordering(ordering_date_time, status, service_id, employee_id, client_id) " +
            "VALUE(?, ?, ?, ?, ?)";
    static final String SQL_DELETE_ORDERING = "DELETE FROM ordering WHERE id = ?";
    static final String SQL_FIND_ORDERING_ALL =
            "SELECT ordering.id, ordering_date_time, status, service_id, employee_id, client_id, create_time, " +
            "update_time, service.name, service.price, service.spend_time," +
            "employee.name, employee.surname, employee.rating, employee.profession_id, " +
            "profession.name, " +
            "client.name, client.surname " +
            "FROM ordering " +
            "JOIN service ON service.id = ordering.service_id " +
            "JOIN employee ON employee.id = ordering.employee_id " +
            "JOIN client ON client.id = ordering.client_id " +
            "JOIN profession ON profession.id = employee.profession_id";
    static final String SQL_FIND_ORDERING_BY_ID =
            "SELECT ordering_date_time, status, service_id, employee_id, client_id, create_time, update_time," +
                    "service.name, service.price, service.spend_time," +
                    "employee.name, employee.surname, employee.rating, employee.profession_id, " +
                    "profession.name, " +
                    "client.name, client.surname " +
                    "FROM ordering " +
                    "JOIN service ON service.id = ordering.service_id " +
                    "JOIN employee ON employee.id = ordering.employee_id " +
                    "JOIN client ON client.id = ordering.client_id " +
                    "JOIN profession ON profession.id = employee.profession_id " +
                    "WHERE ordering.id = ?";
    static final String SQL_UPDATE_ORDERING
            = "UPDATE ordering SET ordering_date_time = ?, status = ? WHERE id = ?";

    public boolean create(Ordering ordering) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreated = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_ORDERING, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(ordering.getOrderDateTime()));
            preparedStatement.setString(i++, ordering.getStatus().toString());
            preparedStatement.setLong(i++, ordering.getService().getId());
            preparedStatement.setLong(i++, ordering.getEmployee().getId());
            preparedStatement.setLong(i++, ordering.getClient().getId());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    ordering.setId(resultSet.getLong(1));
                    isCreated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table ordering");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return isCreated;
    }

    public void update(Ordering ordering) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDERING);
            int i = 1;
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(ordering.getOrderDateTime()));
            preparedStatement.setString(i++, ordering.getStatus().toString());
            preparedStatement.setLong(i++, ordering.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table ordering");
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
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDERING);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table ordering");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public Ordering find(long id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Ordering resultOrdering = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ORDERING_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                resultOrdering = new Ordering();
                resultOrdering.setService(new Service());
                resultOrdering.setEmployee(new Employee());
                resultOrdering.setClient(new Client());
                resultOrdering.getEmployee().setProfession(new Profession());

                resultOrdering.setId(id);
                resultOrdering.setOrderDateTime(resultSet.getTimestamp("ordering_date_time").toLocalDateTime());
                resultOrdering.setStatus(StatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
                resultOrdering.getService().setId(resultSet.getLong("service_id"));
                resultOrdering.getEmployee().setId(resultSet.getLong("employee_id"));
                resultOrdering.getClient().setId(resultSet.getLong("client_id"));
                resultOrdering.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
                resultOrdering.setUpdateTime(resultSet.getTimestamp("update_time").toLocalDateTime());
                resultOrdering.getService().setName(resultSet.getString("service.name"));
                resultOrdering.getService().setPrice(resultSet.getDouble("service.price"));
                resultOrdering.getService().setSpendTime(resultSet.getTime("service.spend_time"));
                resultOrdering.getEmployee().setName(resultSet.getString("employee.name"));
                resultOrdering.getEmployee().setSurname(resultSet.getString("employee.surname"));
                resultOrdering.getEmployee().setRating(resultSet.getDouble("employee.rating"));
                resultOrdering.getEmployee().getProfession().setId(resultSet.getLong("employee.profession_id"));
                resultOrdering.getEmployee().getProfession().setName(resultSet.getString("profession.name"));
                resultOrdering.getClient().setName(resultSet.getString("client.name"));
                resultOrdering.getClient().setSurname(resultSet.getString("client.surname"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table ordering");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultOrdering;
    }

    public List<Ordering> findAll() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ordering> resultOrderings = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ORDERING_ALL);

            while (resultSet.next()){
                Ordering ordering = new Ordering();
                ordering.setService(new Service());
                ordering.setEmployee(new Employee());
                ordering.setClient(new Client());
                ordering.getEmployee().setProfession(new Profession());

                ordering.setId(resultSet.getLong("ordering.id"));
                ordering.setOrderDateTime(resultSet.getTimestamp("ordering_date_time").toLocalDateTime());
                ordering.setStatus(StatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
                ordering.getService().setId(resultSet.getLong("service_id"));
                ordering.getEmployee().setId(resultSet.getLong("employee_id"));
                ordering.getClient().setId(resultSet.getLong("client_id"));
                ordering.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
                ordering.setUpdateTime(resultSet.getTimestamp("update_time").toLocalDateTime());
                ordering.getService().setName(resultSet.getString("service.name"));
                ordering.getService().setPrice(resultSet.getDouble("service.price"));
                ordering.getService().setSpendTime(resultSet.getTime("service.spend_time"));
                ordering.getEmployee().setName(resultSet.getString("employee.name"));
                ordering.getEmployee().setSurname(resultSet.getString("employee.surname"));
                ordering.getEmployee().setRating(resultSet.getDouble("employee.rating"));
                ordering.getEmployee().getProfession().setId(resultSet.getLong("employee.profession_id"));
                ordering.getEmployee().getProfession().setName(resultSet.getString("profession.name"));
                ordering.getClient().setName(resultSet.getString("client.name"));
                ordering.getClient().setSurname(resultSet.getString("client.surname"));
                resultOrderings.add(ordering);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table ordering");
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return resultOrderings;
    }
}
