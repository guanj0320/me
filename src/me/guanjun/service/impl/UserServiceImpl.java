package me.guanjun.service.impl;

import me.guanjun.persistent.dao.UserDAO;
import me.guanjun.persistent.model.User;
import me.guanjun.service.UserService;

import java.util.List;

/**
 * Created by guanj on 2014/4/24.
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List getUsers() throws RuntimeException, Exception {
        return userDAO.queryObjs();
    }

    @Override
    public User getUser(String username) throws RuntimeException, Exception {
        return userDAO.queryObj(username);
    }

    @Override
    public boolean removeUser(String username) throws RuntimeException, Exception {
        userDAO.execDelete(username);
        return true;
    }

    @Override
    public boolean addUser(User user) throws RuntimeException, Exception {
        userDAO.execInsert(user);
        return true;
    }

    @Override
    public boolean modifyUser(User user) throws RuntimeException, Exception {
        userDAO.execUpdate(user);
        return true;
    }
}
