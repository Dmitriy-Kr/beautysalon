package com.beautysalon.service;


import com.beautysalon.dao.DBException;
import com.beautysalon.dao.RegisterClientDao;
import com.beautysalon.entity.Client;

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
