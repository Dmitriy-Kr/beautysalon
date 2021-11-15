package com.beautysalon.service;


import com.beautysalon.dao.AccountDao;
import com.beautysalon.dao.DBException;
import com.beautysalon.dao.ServiceDao;
import com.beautysalon.daodto.ServicePageDtoDao;
import com.beautysalon.dto.ServicePageDto;

import java.util.List;

public class SalonService{
    private ServicePageDtoDao serviceDao = new ServicePageDtoDao();

    public List<ServicePageDto> findAll() throws ServiceException {
        try {
            return serviceDao.findAll();
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from database");
        }
    }
}