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
import com.seer.dto.TaskCategory;
import com.seer.exception.DAOException;
import com.seer.mappers.TaskCategoryMapper;
import com.seer.utils.TypedProperties;

public class TaskCategoryDAO {

    private SimpleJdbcTemplate  simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    public TaskCategoryDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public TaskCategory create(TaskCategory object) throws DAOException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("taskCategory").
                usingGeneratedKeyColumns("id");

        try{
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", object.buildingId);

            Long rowId = 0L;

            rowId = (jdbcInsert.executeAndReturnKey(parameters)).longValue();

            if(rowId > 0){
                return getTaskCategoryById(rowId);
            }else{
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new taskCategory");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to create taskCategory");
        }
    }

    public TaskCategory update(TaskCategory object) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Update_TaskCategory");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.name,
                    object.id);

            if (updatedRows > 0) {
                return getTaskCategoryById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "TaskCategory does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update taskCategory");
        }
    }

    public TaskCategory getTaskCategoryById(Long id) throws DAOException {
        return getTaskCategoryById(id, seerId, null);
    }

    public TaskCategory getTaskCategoryById(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn!=null;

        String sql = this.sqlQueries.getString("Q_Select_Task_Category_By_Id");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TaskCategoryMapper nm = new TaskCategoryMapper();
        TaskCategory taskCategory = null;

        try {
            if(existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setObject(1, id, Types.BIGINT);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            if (rs.next())
                taskCategory = nm.mapRow(rs, 0);
        }
        catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to get taskCategory");
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

        return taskCategory;
    }

    public Collection<TaskCategory> getTaskCategoriesByBuildingId(Long buildingId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Task_Categories_By_Building_Id");

        Collection<TaskCategory> taskCategorys = null;

        try{
            taskCategorys = this.simpleJdbcTemplate.query(sql, new TaskCategoryMapper(), buildingId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get task categories by building id");
        }
        return taskCategorys;
    }

    public Collection<TaskCategory> getTaskCategories() throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Task_Categories");

        Collection<TaskCategory> taskCategorys = null;

        try{
            taskCategorys = this.simpleJdbcTemplate.query(sql, new TaskCategoryMapper());
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get task categories");
        }
        return taskCategorys;
    }

    public Long delete(Long id) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Delete_Task_Category");

        Integer deletedRows;

        try{
            deletedRows = this.simpleJdbcTemplate.update(sql, id);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_DELETE_ERROR, "Unable to delete taskCategory");
        }

        if (deletedRows > 0) {
            return id;
        }else {
            throw new DAOException(ErrorCodes.DATABASE_DELETE_NOT_EXIST, "TaskCategory does not exist in database");
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