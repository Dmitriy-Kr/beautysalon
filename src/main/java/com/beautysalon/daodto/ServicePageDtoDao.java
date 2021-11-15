package com.beautysalon.daodto;

import com.beautysalon.dao.DBException;
import com.beautysalon.dto.ServicePageDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePageDtoDao implements BaseDtoDao {
    private static final String SQL_FIND_SERVICE_EMPLOYEE_ALL =
            "SELECT service.name, service.price, employee.name, employee.surname, employee.rating " +
                    "FROM service JOIN profession ON profession.id = service.profession_id " +
                    "JOIN employee ON employee.profession_id = profession.id";

    public List<ServicePageDto> findAll() throws DBException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ServicePageDto> resultList = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_SERVICE_EMPLOYEE_ALL);

            while (resultSet.next()){
                ServicePageDto resultService = new ServicePageDto();
                resultService.setServiceName(resultSet.getString("service.name"));
                resultService.setPrice(resultSet.getDouble("service.price"));
                resultService.setEmployeeName(resultSet.getString("employee.name"));
                resultService.setEmployeeSurname(resultSet.getString("employee.surname"));
                resultService.setEmployeeRating(resultSet.getDouble("employee.rating"));
                resultList.add(resultService);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Failed to connect table service");
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        return resultList;
    }
}
