/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.bean;

import com.ag.beanI.UserBeanI;
import com.ag.dao.UserDao;
import com.ag.factory.DaoFactory;
import com.ag.factory.DaoType;
import com.ag.model.User;
import com.xag.util.BcryptPassword;
import static com.xag.util.BcryptPassword.checkPassword;
import com.xag.util.NoMatchFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * @author agunga
 */
//@Service
@Stateless
public class UserBean implements UserBeanI {

    Logger log = LoggerFactory.getLogger(UserBean.class);

    @PersistenceContext
    EntityManager em;

//    EntityManager em = Persistence.createEntityManagerFactory("eventaPU").createEntityManager();

    public UserDao getDao() {
        log.info(" em :::::::::;" + em);
        return (UserDao) new DaoFactory(DaoType.USER).getDao(em);
    }

    @Override
    public User add(User user) {

        System.out.println("In UserBean Before User Save :  "+ user.getEmail() + " " + user.getPhone() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getPassword() + " " + user.getGender() + " " + user.getDateCreated());
        String hashd_pwd = BcryptPassword.hashPassword(user.getPassword());
        log.info("hash = " + hashd_pwd);
        user.setPassword(hashd_pwd);

        try {
            return getDao().save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User update(User o) {
        try {
            return getDao().merge(o);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return getDao().findAll();
    }

    @Override
    public User findById(long id) {
        try {
            return getDao().findById(id);
        } catch (NoMatchFoundException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(User o) {
        return getDao().remove(o);
    }

    @Override
    public int delete(long id) {
        return getDao().removeById(id);
    }

    @Override
    public User authenticate(String username, String password, boolean phone) {

        User u1 = getDao().findByPhone(username);
        User u = getDao().findByUsername(username, phone);
        if (u != null) {
            log.info("pwd : " + password);
            log.info("hash pwd : " + u1.getPassword());
            if (checkPassword(password, u.getPassword())) {
                log.info("Login success!");
                return u;
            }
        }if (u1 != null) {
            log.info("pwd : " + password);
            log.info("hash pwd : " + u1.getPassword());
            if (checkPassword(password, u1.getPassword())) {
                log.info("1. Login success!");
                return u1;
            }
        }
        log.info("Login failed at nre exception");
        return null;
    }

    @Override
    public boolean changePassword(String username, String password, String newPassword, boolean phone) {
        User u = authenticate(username, password, phone);
        if (u == null) {
            return false;
        }
        String hashedNewPassword = BcryptPassword.hashPassword(newPassword);
        return (getDao().updatePassword(username, hashedNewPassword) > 0);
    }

}
