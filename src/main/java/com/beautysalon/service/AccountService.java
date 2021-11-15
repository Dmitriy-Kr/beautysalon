package com.beautysalon.service;

import com.beautysalon.dao.AccountDao;
import com.beautysalon.dao.DBException;
import com.beautysalon.entity.Account;

import java.util.List;

public class AccountService implements Service<Account> {

    private AccountDao accountDao = new AccountDao();

    @Override
    public Account findById(long id) throws ServiceException {
        try {
            return accountDao.find(id);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from database");
        }
    }

    @Override
    public Account findByValue(String value) throws ServiceException {
        try {
            return accountDao.find(value);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from database");
        }
    }

    @Override
    public List<Account> findAll() throws ServiceException {
        return null;
    }
    //    public Note create(Note note) throws ServiceException {
//        try {
//            return noteDao.create(note);
//        } catch (DaoException e) {
//            e.printStackTrace();
//            throw new ServiceException("failed to save");
//        }
//    }
//
//    public void delete(Integer id) throws ServiceException {
//        try {
//            noteDao.delete(id);
//        } catch (DaoException e) {
//            e.printStackTrace();
//            throw new ServiceException("failed to delete from Database");
//        }
//    }
//
//    public List<Note> findAll() throws  ServiceException {
//        try {
//            return noteDao.findAllNote();
//        } catch (DaoException e) {
//            e.printStackTrace();
//            throw new ServiceException("failed to read from Database");
//        }
//    }

}
