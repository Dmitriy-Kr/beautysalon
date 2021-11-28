package com.beautysalon.dao;

import com.beautysalon.connectionpool.ConnectionPool;
import com.beautysalon.entity.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao <T extends BaseEntity>{
//    List<T> findAll();
//    T findById(long id);
    boolean create(T t) throws DBException;
//    boolean delete(T t);
//    boolean delete(long id);
//    T update(T t);
}
