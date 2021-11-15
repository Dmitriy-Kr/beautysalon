package com.beautysalon.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> busyConnection;
    private String driver; //com.mysql.cj.jdbc.Driver
    private String URL;
    private Properties properties;
    private int min = 3;
    private int max = 10;
    private int current;

    ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(max);
    }

    public void init(String driver, String URL, Properties properties) {
        this.driver = driver;
        this.URL = URL;
        this.properties = properties;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            for (int i = 0; i < min; i++) {
                addNewConnectionToQueue();
            }

        busyConnection = new ArrayDeque<>();
    }

    public Connection getConnection() {
        Connection connection = null;

        if (freeConnections.isEmpty() && current < max) {
            addNewConnectionToQueue();
            current++;
        }

        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (!connection.isValid(0)) {
                connection = new ConnectionWrapper(DriverManager.getConnection(URL, properties));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        busyConnection.offer(connection);

        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            throw new NullPointerException("Connection is null");
        }
        if (connection instanceof ConnectionWrapper) {
            busyConnection.remove(connection);
            freeConnections.offer(connection);
        } else {
            throw new IllegalArgumentException("Wrong type of connection");
        }
        checkNumberOfCon();
    }

    private void checkNumberOfCon() {
        int realCurrent = (freeConnections.size() + busyConnection.size());
        if (realCurrent < current) {
            for (int i = realCurrent; i < current; i++) {
                addNewConnectionToQueue();
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < current; i++) {
            try {
                Connection connection = freeConnections.take();
                ((ConnectionWrapper) connection).realClose();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void addNewConnectionToQueue() {
        try {
            Connection connection = DriverManager.getConnection(URL, properties);
            freeConnections.put(new ConnectionWrapper(connection));
        } catch (Exception e) {
        }
    }
}
