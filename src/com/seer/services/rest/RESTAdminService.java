package com.seer.services.rest;

import com.seer.config.Configuration;
import com.seer.exception.RESTSeerException;
import com.seer.exception.SeerException;
import com.seer.services.AdminService;
import com.seer.services.SessionService;
import com.seer.services.rest.ro.AdminService.GetForCustomerRO;
import com.seer.services.rest.ro.AdminService.GetForLocationRO;
import com.seer.services.rest.ro.EmptyRO;
import com.seer.utils.TypedProperties;
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

@Path("/v1/admin/")
@Component
public class RESTAdminService {

    protected AdminService adminService;
    protected SessionService sessionService;

    private TypedProperties generalProperties = Configuration.get().getProps();

    @Context
    ServletContext sc;

    @PostConstruct
    protected void setupServices() {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.sc);
        adminService = (AdminService) ctx.getBean("adminService");
        sessionService = (SessionService) ctx.getBean("sessionService");
    }

    /////////////////////////////////////////
    ////// CREATE ADMIN REST API HERE ///////
    /////////////////////////////////////////
}