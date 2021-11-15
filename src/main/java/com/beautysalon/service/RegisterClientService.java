package com.beautysalon.service;


import com.beautysalon.dao.DBException;
import com.beautysalon.daodto.RegisterClientDao;
import com.beautysalon.daodto.ServicePageDtoDao;
import com.beautysalon.dto.ServicePageDto;
import com.beautysalon.entity.Client;

import java.util.List;

public class RegisterClientService {

    private RegisterClientDao registerClientDao = new RegisterClientDao();

    public boolean create(Client client) throws ServiceException {
        try {
            return registerClientDao.create(client);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from database");
        }
    }
}
