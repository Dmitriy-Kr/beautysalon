package com.beautysalon.service;

import com.beautysalon.dao.ClientDao;
import com.beautysalon.dao.DBException;
import com.beautysalon.entity.Client;

import java.util.List;

public class ClientService implements Service<Client>{
    private ClientDao clientDao = new ClientDao();

    public boolean createClientAndAccount(Client client) throws ServiceException {
        try {
            return clientDao.createClientAndAccount(client);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from database");
        }
    }

    @Override
    public Client findById(long id) throws ServiceException {
        return null;
    }

    @Override
    public Client findByValue(String value) throws ServiceException {
        return null;
    }

    @Override
    public List<Client> findAll() throws ServiceException {
        return null;
    }

    public long findClientIdByAccountLogin(String accountLogin) throws ServiceException {
        try {
            return clientDao.findClientIdByAccountLogin(accountLogin);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("Find client id by account login method: failed to read from database");
        }
    }
}
