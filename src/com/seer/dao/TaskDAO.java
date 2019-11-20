package com.seer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.seer.common.ErrorCodes;
import com.seer.config.Configuration;
import com.seer.dto.Task;
import com.seer.exception.DAOException;
import com.seer.mappers.TaskMapper;
import com.seer.utils.TypedProperties;

public class TaskDAO {

    private SimpleJdbcTemplate  simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    public TaskDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Task create(Task object, Long seerId) throws DAOException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("task").
                usingGeneratedKeyColumns("id");

        try{
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("building_id", object.buildingId);
            parameters.addValue("apartment_id", object.apartmentId);
            parameters.addValue("task_category_id", object.taskCategoryId);
            parameters.addValue("name", object.name);
            parameters.addValue("description", object.description);
            parameters.addValue("done", object.done);
            parameters.addValue("due_date", object.dueDate);

            Long rowId = 0L;

            rowId = (jdbcInsert.executeAndReturnKey(parameters)).longValue();

            if(rowId > 0){
                return getTaskById(rowId);
            }else{
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new task");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to create task");
        }
    }

    public Task update(Task object) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Update_Task");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.apartmentId,
                    object.taskCategoryId,
                    object.name,
                    object.description,
                    object.done,
                    object.dueDate
            );

            if (updatedRows > 0) {
                return getTaskById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "Task does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update task");
        }
    }

    public Task changeTaskStatus(Task object, Long seerId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Change_Task_Status");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.done,
                    object.id);

            if (updatedRows > 0) {
                return getTaskById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "Task does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update task");
        }
    }

    public Task getTaskById(Long id) throws DAOException {
        return getTaskById(id, null);
    }

    public Task getTaskById(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn!=null;

        String sql = this.sqlQueries.getString("Q_Select_Task_By_Id");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TaskMapper nm = new TaskMapper();
        Task task = null;

        try {
            if(existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setObject(1, id, Types.BIGINT);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            if (rs.next())
                task = nm.mapRow(rs, 0);
        }
        catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to get task");
        }
        finally {
            try {
                if(rs != null)
                    rs.close();
                if(pstmt != null)
                    pstmt.close();
                if(existingConnection == false && conn != null){
                    conn.close();
                }
            }
            catch (SQLException e) {
            }
        }

        return task;
    }

    public Collection<Task> getTasksByBuildingId(Long buildingId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Tasks_By_Building_Id");

        Collection<Task> tasks = null;

        try{
            tasks = this.simpleJdbcTemplate.query(sql, new TaskMapper(), buildingId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get tasks");
        }
        return tasks;
    }

    public Long delete(Long id) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Delete_Task");

        Integer deletedRows;

        try{
            deletedRows = this.simpleJdbcTemplate.update(sql, id);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_DELETE_ERROR, "Unable to delete task");
        }

        if (deletedRows > 0) {
            return id;
        }else {
            throw new DAOException(ErrorCodes.DATABASE_DELETE_NOT_EXIST, "Task does not exist in database");
        }
    }

    /**
     * Closes connection and performs commit or rollback
     *
     * @param conn
     * @param commit
     * @throws DAOException
     */
    private void closeConnection(Connection conn, Boolean commit) throws DAOException {
        if(conn == null)
            throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Database connection problem");

        if(commit){
            try {
                conn.commit();
            }
            catch (SQLException ex){
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to commit data to database");
            }
        }
        else{
            try{
                conn.rollback();
            }
            catch (Exception ex){
            }
        }

        try{
            conn.close();
        }
        catch (SQLException ex){
        }
    }

}