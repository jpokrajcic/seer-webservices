package com.seer.services;

import com.seer.dao.*;
import com.seer.dto.*;
import com.seer.enums.UserTypeEnum;
import org.apache.shiro.session.Session;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.seer.common.ErrorCodes;
import com.seer.common.StatusCodes;

import com.seer.exception.DAOException;
import com.seer.exception.SeerException;
import com.seer.services.flex.ResponseData;

import java.util.Collection;

public class SeerService extends AbstractSeerService {

    /**
     * This constructor gets fired when DataSource configured by Spring
     * @param dataSource
     */
    public SeerService(DriverManagerDataSource dataSource) {
        super(dataSource);
    }

    ///////////////////////////////
    ////////// BUILDING ///////////
    ///////////////////////////////

    public ResponseData getBuildingById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Building res = dao.getBuildingForId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getBuildings(String sessionToken) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Collection<Building> res = dao.getBuildings();
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getBuildingsBySeerId(String sessionToken) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Collection<Building> res = dao.getBuildingsBySeerId(userProfile.id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createBuilding(String sessionToken, Building object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Building createdBuilding = dao.create(object, userProfile.id);
                if (createdBuilding != null) {
                    return new ResponseData(StatusCodes.OK, "", createdBuilding);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdBuilding);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new building.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateBuilding(String sessionToken, Building object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Building objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteBuilding(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            BuildingDAO dao = new BuildingDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete building.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    ////////// APARTMENT //////////
    ///////////////////////////////

    public ResponseData getApartmentById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            ApartmentDAO dao = new ApartmentDAO(dataSource);
            try {
                Apartment res = dao.getApartmentById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getApartmentsByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            ApartmentDAO dao = new ApartmentDAO(dataSource);
            try {
                Collection<Apartment> res = dao.getApartmentsByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createApartment(String sessionToken, Apartment object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            ApartmentDAO dao = new ApartmentDAO(dataSource);
            try {
                Apartment createdApartment = dao.create(object);
                if (createdApartment != null) {
                    return new ResponseData(StatusCodes.OK, "", createdApartment);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdApartment);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new apartment.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateApartment(String sessionToken, Apartment object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            ApartmentDAO dao = new ApartmentDAO(dataSource);
            try {
                Apartment objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteApartment(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            ApartmentDAO dao = new ApartmentDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete apartment.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    ////////// TASK ///////////////
    ///////////////////////////////

    public ResponseData getTaskById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Task res = dao.getTaskById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getTasksByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Collection<Task> res = dao.getTasksByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createTask(String sessionToken, Task object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Task createdTask = dao.create(object);
                if (createdTask != null) {
                    return new ResponseData(StatusCodes.OK, "", createdTask);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdTask);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new task.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateTask(String sessionToken, Task object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Task objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData changeTaskStatus(String sessionToken, Boolean completed, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Task objUpdated = dao.changeTaskStatus(completed, id);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update status error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteTask(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskDAO dao = new TaskDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete task.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    /////// TASK CATEGORY /////////
    ///////////////////////////////

    public ResponseData getTaskCategoryById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                TaskCategory res = dao.getTaskCategoryById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getTaskCategories(String sessionToken) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                Collection<TaskCategory> res = dao.getTaskCategories();
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getTaskCategoriesByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                Collection<TaskCategory> res = dao.getTaskCategoriesByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createTaskCategory(String sessionToken, TaskCategory object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                TaskCategory createdTaskCategory = dao.create(object);
                if (createdTaskCategory != null) {
                    return new ResponseData(StatusCodes.OK, "", createdTaskCategory);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdTaskCategory);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new task category.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateTaskCategory(String sessionToken, TaskCategory object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                TaskCategory objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteTaskCategory(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            TaskCategoryDAO dao = new TaskCategoryDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete task category.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    ////////// MESSAGE ////////////
    ///////////////////////////////

    public ResponseData getMessageById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Message res = dao.getMessageById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMessagesByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Collection<Message> res = dao.getMessagesByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMessagesByApartmentId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Collection<Message> res = dao.getMessagesByApartmentId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createMessage(String sessionToken, Message object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Message createdMessage = dao.create(object, userProfile.role);
                if (createdMessage != null) {
                    return new ResponseData(StatusCodes.OK, "", createdMessage);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdMessage);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new message.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateMessage(String sessionToken, Message object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Message objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteMessage(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete message.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData markMessageAsRead(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MessageDAO dao = new MessageDAO(dataSource);
            try {
                Long markedObjectId = dao.markAsRead(id);
                if (markedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", markedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", markedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to mark message as read.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    ////////// MONITORING /////////
    ///////////////////////////////

    public ResponseData getMonitoringById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Monitoring res = dao.getMonitoringById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMonitoringsByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Collection<Monitoring> res = dao.getMonitoringsByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMonitoringsByBuildingIdAndCategoryId(String sessionToken, Long buildingId, Long categoryId) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Collection<Monitoring> res = dao.getMonitoringsByBuildingIdAndCategoryId(buildingId, categoryId);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createMonitoring(String sessionToken, Monitoring object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Monitoring createdMonitoring = dao.create(object);
                if (createdMonitoring != null) {
                    return new ResponseData(StatusCodes.OK, "", createdMonitoring);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdMonitoring);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new monitoring.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateMonitoring(String sessionToken, Monitoring object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Monitoring objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteMonitoring(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringDAO dao = new MonitoringDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete monitoring.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }


    ///////////////////////////////
    //// MONITORING CATEGORY //////
    ///////////////////////////////

    public ResponseData getMonitoringCategoryById(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                MonitoringCategory res = dao.getMonitoringCategoryById(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMonitoringCategories(String sessionToken) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                Collection<MonitoringCategory> res = dao.getMonitoringCategories();
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData getMonitoringCategoriesByBuildingId(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                Collection<MonitoringCategory> res = dao.getMonitoringCategoriesByBuildingId(id);
                if (res != null) {
                    return new ResponseData(StatusCodes.OK, "", res);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData createMonitoringCategory(String sessionToken, MonitoringCategory object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                MonitoringCategory createdMonitoringCategory = dao.create(object);
                if (createdMonitoringCategory != null) {
                    return new ResponseData(StatusCodes.OK, "", createdMonitoringCategory);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", createdMonitoringCategory);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new monitoring category.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData updateMonitoringCategory(String sessionToken, MonitoringCategory object) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                MonitoringCategory objUpdated = dao.update(object);
                if (objUpdated != null) {
                    return new ResponseData(StatusCodes.OK, "", objUpdated);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", objUpdated);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Update error");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }

    public ResponseData deleteMonitoringCategory(String sessionToken, Long id) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        if (userProfile != null && userProfile.role == UserTypeEnum.SEER){
            MonitoringCategoryDAO dao = new MonitoringCategoryDAO(dataSource);
            try {
                Long deletedObjectId = dao.delete(id);
                if (deletedObjectId > 0) {
                    return new ResponseData(StatusCodes.OK, "", deletedObjectId);
                } else {
                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", deletedObjectId);
                }
            } catch (DAOException ex) {
                throw new SeerException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to delete monitoring category.");
            }
        }else{
            return new ResponseData(1,"",null);
        }
    }
}