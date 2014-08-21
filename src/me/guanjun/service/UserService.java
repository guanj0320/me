package me.guanjun.service;

import me.guanjun.persistent.model.User;

import java.util.List;

/**
 * Created by guanj on 2014/4/24.
 */
public interface UserService {
    List getUsers() throws RuntimeException, Exception;
    User getUser(String username) throws RuntimeException,Exception;
    boolean removeUser(String username) throws RuntimeException,Exception;
    boolean addUser(User user) throws RuntimeException,Exception;
    boolean modifyUser(User user) throws RuntimeException,Exception;
}
