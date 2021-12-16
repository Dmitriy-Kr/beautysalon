package com.beautysalon.service;

import com.beautysalon.dao.DBException;
import com.beautysalon.dao.OrderingDao;
import com.beautysalon.entity.Ordering;

import java.util.List;

public class OrderingService implements Service<Ordering>{
    OrderingDao orderingDao = new OrderingDao();

    public boolean create(Ordering ordering) throws ServiceException {
        try {
            return orderingDao.create(ordering);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("Failed to save");
        }
    }
    @Override
    public Ordering findById(long id) throws ServiceException {
        throw new ServiceException("This method has no useful body");
//        return null;
    }

    @Override
    public Ordering findByValue(String value) throws ServiceException {
        throw new ServiceException("This method has no useful body");
//        return null;
    }

    @Override
    public List<Ordering> findAll() throws ServiceException {
        throw new ServiceException("This method has no useful body");
//        return null;
    }
}
