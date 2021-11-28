package com.beautysalon.dao;

import com.beautysalon.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class AstractDao contains simple basic methods for working with DB
 */
public abstract class AbstractDao {
    void close(AutoCloseable autoCloseable){
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Cannot close " + autoCloseable);
            }
        }
    }

    void closeConnectionWithCommitTrue(Connection connection){
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Cannot close " + connection);
            }
        }
    }

    Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = ConnectionPool.INSTANCE.getConnection();
        return connection;
    }
}
