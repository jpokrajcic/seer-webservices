package com.seer.services.rest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.seer.services.flex.ResponseData;
import com.seer.services.rest.ro.SeerService.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.seer.config.Configuration;
import com.seer.exception.RESTSeerException;
import com.seer.exception.SeerException;
import com.seer.services.SessionService;
import com.seer.services.SeerService;
import com.seer.services.rest.ro.GetDataRO;
import com.seer.services.rest.ro.DeleteItemRO;
import com.seer.utils.TypedProperties;

@Path("/v1/user/")
@Component
public class RESTUserService {

	protected SeerService seerService;
	protected SessionService sessionService;

	@Context
	ServletContext sc;

	@PostConstruct
	protected void setupServices() {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.sc);
		seerService = (SeerService) ctx.getBean("seerService");
		sessionService = (SessionService) ctx.getBean("sessionService");
	}


	/**
	 *
	 * Building
	 */
	@POST
	@Path("/createBuilding")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createBuilding(CreateBuildingRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createBuilding(ro.sessionToken, ro.building);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildings")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getBuildings(GetDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getBuildings(ro.sessionToken);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateBuilding")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateBuilding(UpdateBuildingRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateBuilding(ro.sessionToken, ro.building);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteBuilding")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteBuilding(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteBuilding(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}


	/**
	 *
	 * Apartment
	 */
	@POST
	@Path("/createApartment")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createApartment(CreateApartmentRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createApartment(ro.sessionToken, ro.apartment);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getApartments")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getApartments(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getApartmentsByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateApartment")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateApartment(UpdateApartmentRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateApartment(ro.sessionToken, ro.apartment);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteApartment")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteApartment(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteApartment(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}


	/**
	 *
	 * Task
	 */
	@POST
	@Path("/createTask")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createTask(CreateTaskRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createTask(ro.sessionToken, ro.task);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getTasksByBuildingId")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getTasksByBuildingId(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getTasksByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateTask")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateTask(UpdateTaskRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateTask(ro.sessionToken, ro.task);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/changeTaskStatus")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData changeTaskStatus(UpdateTaskStatusRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.changeTaskStatus(ro.sessionToken, ro.completed, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteTask")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteTask(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteTask(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	/**
	 *
	 * Message
	 */
	@POST
	@Path("/createMessage")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createMessage(CreateMessageRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createMessage(ro.sessionToken, ro.message);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildingMessages")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getBuildingMessages(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMessagesByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getApartmentMessages")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getApartmentMessages(GetApartmentDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMessagesByApartmentId(ro.sessionToken, ro.apartmentId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateMessage")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateMessage(UpdateMessageRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateMessage(ro.sessionToken, ro.message);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteMessage")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteMessage(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteMessage(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/markMessageAsRead")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData markMessageAsRead(MarkMessageRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.markMessageAsRead(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}


	/**
	 *
	 * Monitoring
	 */
	@POST
	@Path("/createMonitoring")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createMonitoring(CreateMonitoringRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createMonitoring(ro.sessionToken, ro.monitoring);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildingMonitorings")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getBuildingMonitorings(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMonitoringsByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildingMonitoringsByCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData GetBuildingMonitoringsByCategoryRO(GetBuildingMonitoringsByCategoryRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMonitoringsByBuildingIdAndCategoryId(ro.sessionToken, ro.buildingId, ro.categoryId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateMonitoring")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateMonitoring(UpdateMonitoringRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateMonitoring(ro.sessionToken, ro.monitoring);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteMonitoring")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteMonitoring(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteMonitoring(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}


	/**
	 *
	 * Task category
	 */
	@POST
	@Path("/createTaskCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createTaskCategory(CreateTaskCategoryRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createTaskCategory(ro.sessionToken, ro.taskCategory);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getTaskCategories")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getTaskCategories(GetDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getTaskCategories(ro.sessionToken);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildingTaskCategories")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getBuildingTaskCategories(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getTaskCategoriesByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateTaskCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateTaskCategory(UpdateTaskCategoryRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateTaskCategory(ro.sessionToken, ro.taskCategory);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteTaskCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteTaskCategory(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteTaskCategory(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}


	/**
	 *
	 * Monitoring category
	 */
	@POST
	@Path("/createMonitoringCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData createMonitoringCategory(CreateMonitoringCategoryRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.createMonitoringCategory(ro.sessionToken, ro.monitoringCategory);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getMonitoringCategories")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getMonitoringCategories(GetDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMonitoringCategories(ro.sessionToken);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/getBuildingMonitoringCategories")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData getBuildingMonitoringCategories(GetBuildingDataRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.getMonitoringCategoriesByBuildingId(ro.sessionToken, ro.buildingId);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/updateMonitoringCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData updateMonitoringCategory(UpdateMonitoringCategoryRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.updateMonitoringCategory(ro.sessionToken, ro.monitoringCategory);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}

	@POST
	@Path("/deleteMonitoringCategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public RESTResponseData deleteMonitoringCategory(DeleteItemRO ro) throws RESTSeerException {
		ResponseData responseData = null;
		try {
			responseData = seerService.deleteMonitoringCategory(ro.sessionToken, ro.id);
		}
		catch (SeerException ex){
			throw new RESTSeerException(ex.errorCode,ex.getMessage());
		}
		return new RESTResponseData(responseData);
	}
}