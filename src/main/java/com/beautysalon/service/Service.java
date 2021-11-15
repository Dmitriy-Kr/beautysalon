package com.beautysalon.service;

import com.beautysalon.entity.BaseEntity;

import java.util.List;

public interface Service <T extends BaseEntity>{
    public T findById(long id) throws ServiceException;
    public T findByValue(String value) throws ServiceException;
    public List<T> findAll() throws ServiceException;
}
