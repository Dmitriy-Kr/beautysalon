package com.beautysalon.dao;

import com.beautysalon.connectionpool.ConnectionPool;
import com.beautysalon.entity.Account;
import com.beautysalon.entity.Role;
import com.beautysalon.entity.RoleEnum;

import java.sql.*;

public class AccountDao extends AbstractDao implements BaseDao<Account> {
    static final String SQL_ADD_ACCOUNT
            = "INSERT account(login, password, role_id) VALUE(?, ?, (SELECT id FROM role WHERE name = ?))";
    static final String SQL_DELETE_ACCOUNT = "DELETE FROM account WHERE id = ?";
    static final String SQL_FIND_ACCOUNT_BY_ID =
            "SELECT login, password, name, create_time, account.id, role.id FROM account JOIN role ON role.id = account.role_id WHERE account.id = ?";
    static final String SQL_UPDATE_ACCOUNT
            = "UPDATE account SET login = ?, password = ?, role_id = (SELECT id FROM role WHERE name = ?) WHERE id = ?";
    private static final String SQL_FIND_ACCOUNT_BY_LOGIN =
            "SELECT login, password, name, create_time, account.id, role.id FROM account JOIN role ON role.id = account.role_id WHERE account.login = ?";

    @Override
    public boolean create(Account account) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreated = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, account.getLogin());
            preparedStatement.setString(i++, account.getPassword());
            preparedStatement.setString(i++, account.getRole().getName().toString());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    account.setId(resultSet.getLong(1));
                    isCreated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table account");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return isCreated;
    }

    public void update(Account account) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT);
            int i = 1;
            preparedStatement.setString(i++, account.getLogin());
            preparedStatement.setString(i++, account.getPassword());
            preparedStatement.setString(i++, account.getRole().getName().toString());
            preparedStatement.setLong(i++, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table account");
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
            preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public Account find(long id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long roleId = 0;
        Account resultAccount = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultAccount = new Account().setRole(new Role());
                resultAccount.setLogin(resultSet.getString("login"));
                resultAccount.setPassword(resultSet.getString("password"));
                resultAccount.getRole().setName(RoleEnum.valueOf(resultSet.getString("name").toUpperCase()));
                resultAccount.setCreateTime(Timestamp.valueOf(resultSet.getString("create_time")).toLocalDateTime());
                resultAccount.setId(resultSet.getLong("account.id"));
                resultAccount.getRole().setId(resultSet.getLong("role.id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table account");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultAccount;
    }

    public Account find(String login) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long roleId = 0;
        Account resultAccount = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultAccount = new Account().setRole(new Role());
                resultAccount.setLogin(resultSet.getString("login"));
                resultAccount.setPassword(resultSet.getString("password"));
                resultAccount.getRole().setName(RoleEnum.valueOf(resultSet.getString("name").toUpperCase()));
                resultAccount.setCreateTime(Timestamp.valueOf(resultSet.getString("create_time")).toLocalDateTime());
                resultAccount.setId(resultSet.getLong("account.id"));
                resultAccount.getRole().setId(resultSet.getLong("role.id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table account");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return resultAccount;
    }

//    public static Connection getConnection() throws SQLException {
//        Connection connection = ConnectionPool.INSTANCE.getConnection();
////        System.out.println("instance: " + ((ConnectionWrapper) connection).instance);
////        System.out.println("inst: " + ((ConnectionWrapper) connection).inst);
//        return connection;
//    }
}
