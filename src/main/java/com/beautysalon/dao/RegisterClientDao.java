package com.beautysalon.dao;

import com.beautysalon.entity.Client;

import java.sql.*;

public class RegisterClientDao extends AbstractDao {
    private static final String SQL_ADD_ACCOUNT =
            "INSERT account(login, password, role_id) VALUE(?, ?, (SELECT `id` FROM `role` WHERE `name` = ?))";
    private static final String SQL_ADD_CLIENT =
            "INSERT client(account_id, name, surname) VALUE(?, ?, ?);";

    public boolean create(Client client) throws DBException {
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

            preparedStatement = connection.prepareStatement(SQL_ADD_CLIENT, Statement.RETURN_GENERATED_KEYS);

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
}
