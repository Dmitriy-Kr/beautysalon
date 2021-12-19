package com.beautysalon.dao;

import com.beautysalon.entity.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ClientDao extends AbstractDao implements BaseDao<Client>{
    static final String SQL_ADD_CLIENT
            = "INSERT client(name, surname, account_id) VALUE(?, ?, (SELECT id FROM account WHERE login = ?))";
    static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE id = ?";
    static final String SQL_FIND_CLIENT_BY_ID =
            "SELECT client.name, surname, login, password, role.name, create_time, client.id, account.id, role.id FROM client" +
                    " JOIN account ON account.id = client.account_id " +
                    "JOIN role ON role.id = account.role_id WHERE client.id = ?";
    static final String SQL_UPDATE_CLIENT = "UPDATE client SET name = ?, surname = ? WHERE id = ?";
    static final String SQL_FIND_CLIENT_ID_BY_ACCOUNT_LOGIN =
            "SELECT client.id FROM client WHERE account_id = (SELECT account.id FROM account WHERE account.login = ?)";
    private static final String SQL_ADD_ACCOUNT =
            "INSERT account(login, password, role_id) VALUE(?, ?, (SELECT `id` FROM `role` WHERE `name` = ?))";
    private static final String SQL_ADD_CLIENT_AFTER_ACCOUNT =
            "INSERT client(account_id, name, surname) VALUE(?, ?, ?);";

    public boolean create(Client client) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreated = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_CLIENT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, client.getName());
            preparedStatement.setString(i++, client.getSurname());
            preparedStatement.setString(i++, client.getAccount().getLogin());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    client.setId(resultSet.getLong(1));
                    isCreated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table client");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return isCreated;
    }

    public boolean createClientAndAccount(Client client) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            preparedStatement.setString(i++, client.getAccount().getLogin());
            preparedStatement.setString(i++, client.getAccount().getPassword());
            preparedStatement.setString(i++, client.getAccount().getRole().getName().toString());

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    client.getAccount().setId(resultSet.getLong(1));
                }
            }

            preparedStatement = connection.prepareStatement(SQL_ADD_CLIENT_AFTER_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

            i = 1;
            preparedStatement.setLong(i++, client.getAccount().getId());
            preparedStatement.setString(i++, client.getName());
            preparedStatement.setString(i++, client.getSurname());

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    client.setId(resultSet.getLong(1));
                    flag = true;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            throw new DBException("Failed to connect table account");
        } finally {
            close(resultSet);
            close(preparedStatement);
            closeConnectionWithCommitTrue(connection);
        }
        return flag;
    }

    public void update(Client client) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_CLIENT);
            int i = 1;
            preparedStatement.setString(i++, client.getName());
            preparedStatement.setString(i++, client.getSurname());
            preparedStatement.setLong(i++, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table client");
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
            preparedStatement = connection.prepareStatement(SQL_DELETE_CLIENT);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table client");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public Client find(long id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client resultClient = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_CLIENT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultClient = new Client()
                        .setAccount(new Account()
                                .setRole(new Role()));
                resultClient.setName(resultSet.getString("name"));
                resultClient.setSurname(resultSet.getString("surname"));
                resultClient.getAccount().setLogin(resultSet.getString("login"));
                resultClient.getAccount().setPassword(resultSet.getString("password"));
                resultClient.getAccount().getRole().setName(RoleEnum.valueOf(resultSet.getString("role.name").toUpperCase()));
                resultClient.getAccount().setCreateTime(Timestamp.valueOf(resultSet.getString("create_time")).toLocalDateTime());
                resultClient.setId(resultSet.getLong("client.id"));
                resultClient.getAccount().setId(resultSet.getLong("account.id"));
                resultClient.getAccount().getRole().setId(resultSet.getLong("role.id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table client");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultClient;
    }

    public long findClientIdByAccountLogin(String login) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long clientId = 0;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_CLIENT_ID_BY_ACCOUNT_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clientId = resultSet.getLong("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table client");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return clientId;
    }
}
