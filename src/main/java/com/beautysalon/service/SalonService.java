package com.beautysalon.service;


import com.beautysalon.dao.DBException;
import com.beautysalon.dao.ServicePageDtoDao;
import com.beautysalon.dto.ServicePageDto;

import java.util.List;

public class SalonService{
    private ServicePageDtoDao serviceDao = new ServicePageDtoDao();

    public List<ServicePageDto> findAll() throws ServiceException {
        try {
            return serviceDao.findAll();
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("Failed to read from database");
        }
    }
}
