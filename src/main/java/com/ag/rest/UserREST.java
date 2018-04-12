/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.rest;

import com.ag.beanI.UserBeanI;
import com.ag.model.*;
import com.xag.util.RestUtil;
import com.xag.util.ResponseObject;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author agunga
 */
@Path("/user")
public class UserREST {

    @EJB
    UserBeanI beanI;

    private ResponseObject responseObject = null;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Admin entity) {
        responseObject = (beanI.add(entity) != null) ? RestUtil.setResponseObject(true, entity) : RestUtil.setResponseObject(false, entity);
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Admin entity) {
        responseObject = (beanI.update(entity) != null) ? RestUtil.setResponseObject(true, entity) : RestUtil.setResponseObject(false, entity);
        return Response.status(Response.Status.OK).entity(responseObject).build();

    }

    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(Admin entity) {
        responseObject = (beanI.delete(entity)) ? RestUtil.setResponseObject(true, null) : RestUtil.setResponseObject(false, null);
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @DELETE
    @Path("/remove/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") long id) {
        responseObject = (beanI.delete(id) > 0) ? RestUtil.setResponseObject(true, null) : RestUtil.setResponseObject(false, null);
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @GET
    @Path("/find/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") long id) {
        responseObject = (beanI.findById(id) != null) ? RestUtil.setResponseObject(true, beanI.findById(id)) : RestUtil.setResponseObject(false, null);
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @GET
    @Path("/findAll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        responseObject = RestUtil.setResponseObject(true, beanI.findAll());
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @POST
    @Path("/authenticate/{phone}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@PathParam("phone") String phone, @PathParam("password") String password) {
        User user = beanI.authenticate(phone, password, true);
        responseObject = (user != null) ? RestUtil.setResponseObject(true, user) : RestUtil.setResponseObject(false, null);
        return Response.status(Response.Status.OK).entity(responseObject).build();

    }

    @POST
    @Path("/changePassword/{username}/{password}/{newPassword}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(@PathParam("username") String username, @PathParam("password") String password, @PathParam("newPassword") String newPassword) {
        responseObject = beanI.changePassword(username, password, newPassword, true) ? RestUtil.setResponseObject(true, true) : RestUtil.setResponseObject(false, false);
        return Response.status(Response.Status.OK).entity(responseObject).build();

    }

}
