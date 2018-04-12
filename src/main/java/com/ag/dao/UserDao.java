/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.dao;

import com.ag.model.Admin;
import com.ag.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author agunga
 */
public class UserDao extends GenericDao<User, Long> {

    EntityManager em;

    public UserDao(EntityManager entityManager) {
        super(User.class, entityManager);
        this.em = entityManager;
    }

    public User findByUsername(String username, boolean phone) {
        String qString = "SELECT u FROM User u WHERE u.username =:username";
        System.out.println(qString);
        try {
            return (User) em.createQuery(qString)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public User findByPhone(String phone) {
        String qString = "SELECT u FROM User u WHERE u.phone =:phone ";
        System.out.println(qString);
        try {
            return (User) em.createQuery(qString)
                    .setParameter("phone", phone).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int updatePassword(String username, String hashedNewPassword) {
        return em.createQuery("UPDATE User u SET u.password = " + hashedNewPassword + " WHERE u.username =:username ")
                .setParameter("username", username).executeUpdate();
    }
}
