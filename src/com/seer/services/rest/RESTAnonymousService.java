package com.seer.services.rest;

import com.seer.common.ErrorCodes;
import com.seer.exception.RESTSeerException;
import com.seer.exception.SeerException;
import com.seer.services.SeerService;
import com.seer.services.SessionService;
import com.seer.services.flex.ResponseData;
import com.seer.services.rest.ro.AnonymusService.LoginRO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//imports here
@Path("/v1/anon/")
@Component
public class RESTAnonymousService {

    protected SeerService seerService;
    protected SessionService sessionService;

    protected DriverManagerDataSource dataSource;

    @Context
    ServletContext sc;

    @PostConstruct
    protected void setupServices() {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.sc);
        seerService = (SeerService) ctx.getBean("seerService");
        sessionService = (SessionService) ctx.getBean("sessionService");

        dataSource = (DriverManagerDataSource) ctx.getBean("dataSource");
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RESTResponseData login(LoginRO ro) throws RESTSeerException {

        ResponseData responseData = null;
        try {
            responseData = sessionService.login(ro.username, ro.password);
        } catch (Exception ex) {
            throw new RESTSeerException(ErrorCodes.AUTHENTICATION_FAILED);
        }

        return new RESTResponseData(responseData);
    }

    @POST
    @Path("/adminLogin")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RESTResponseData adminLogin(LoginRO ro) throws RESTSeerException {

        ResponseData responseData = null;
        try {
            responseData = sessionService.adminLogin(ro.username, ro.password);
        } catch (Exception ex) {
            throw new RESTSeerException(ErrorCodes.AUTHENTICATION_FAILED);
        }

        return new RESTResponseData(responseData);
    }

    @POST
    @Path("/logout")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RESTResponseData logout() throws RESTSeerException {
        ResponseData responseData = null;
        try {
            responseData = sessionService.logout();
        } catch (Exception ex) {
            throw new RESTSeerException(ErrorCodes.LOGOUT_FAILED);
        }
        return new RESTResponseData(responseData);
    }

    @POST
    @Path("/keepAlive")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RESTResponseData keepAlive(String sessionToken) throws RESTSeerException {
        ResponseData responseData = null;
        try {
            responseData = sessionService.keepAlive(sessionToken);
        }
        catch (Exception ex) {
            throw new RESTSeerException(0,ex.getMessage());
        }
        return new RESTResponseData(responseData);
    }

//    @POST
//    @Path("/getUserProfile")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @RequiresPermissions("user:read")
//    public RESTResponseData getUserProfile(String sessionToken) throws RESTSeerException {
//        ResponseData responseData = null;
//        try {
//            responseData = sessionService.getUserProfile(sessionToken);
//        }
//        catch (SeerException ex) {
//            throw new RESTSeerException(ex.errorCode,ex.getMessage());
//        }
//        // must convert java.sql.Timestamp!
//        responseData.data = new com.seer.dto.UserProfile().fromCleanDto((com.seer.dto.UserProfile) responseData.data);
//        return new RESTResponseData(responseData);
//    }
}