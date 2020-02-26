package com.seer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.seer.common.ErrorCodes;
import com.seer.config.Configuration;
import com.seer.dto.MonitoringCategory;
import com.seer.exception.DAOException;
import com.seer.mappers.MonitoringCategoryMapper;
import com.seer.utils.TypedProperties;

public class MonitoringCategoryDAO {

    private SimpleJdbcTemplate  simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    public MonitoringCategoryDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public MonitoringCategory create(MonitoringCategory object) throws DAOException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("monitoringCategory").
                usingGeneratedKeyColumns("id");

        try{
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", object.name);
            parameters.addValue("building_id", object.buildingId);

            Long rowId = 0L;

            rowId = (jdbcInsert.executeAndReturnKey(parameters)).longValue();

            if(rowId > 0){
                return getMonitoringCategoryById(rowId);
            }else{
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new monitoringCategory");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to create monitoringCategory");
        }
    }

    public MonitoringCategory update(MonitoringCategory object) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Update_Monitoring_Category");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.name,
                    object.id);

            if (updatedRows > 0) {
                return getMonitoringCategoryById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "MonitoringCategory does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update monitoringCategory");
        }
    }

    public MonitoringCategory getMonitoringCategoryById(Long id) throws DAOException {
        return getMonitoringCategoryById(id, null);
    }

    public MonitoringCategory getMonitoringCategoryById(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn!=null;

        String sql = this.sqlQueries.getString("Q_Select_Monitoring_Category_By_Id");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MonitoringCategoryMapper nm = new MonitoringCategoryMapper();
        MonitoringCategory monitoringCategory = null;

        try {
            if(existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql);

            pstmt.setObject(1, id, Types.BIGINT);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            if (rs.next())
                monitoringCategory = nm.mapRow(rs, 0);
        }
        catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to get monitoringCategory");
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

        return monitoringCategory;
    }

    public Collection<MonitoringCategory> getMonitoringCategoriesByBuildingId(Long buildingId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Monitoring_Categories_By_Building_Id");

        Collection<MonitoringCategory> monitoringCategorys = null;

        try{
            monitoringCategorys = this.simpleJdbcTemplate.query(sql, new MonitoringCategoryMapper(), buildingId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get tack categories by building id");
        }
        return monitoringCategorys;
    }

    public Collection<MonitoringCategory> getMonitoringCategories() throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Monitoring_Categories");

        Collection<MonitoringCategory> monitoringCategorys = null;

        try{
            monitoringCategorys = this.simpleJdbcTemplate.query(sql, new MonitoringCategoryMapper());
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get monitoring categories");
        }
        return monitoringCategorys;
    }

    public Long delete(Long id) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Delete_Monitoring_Category");

        Integer deletedRows;

        try{
            deletedRows = this.simpleJdbcTemplate.update(sql, id);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_DELETE_ERROR, "Unable to delete monitoringCategory");
        }

        if (deletedRows > 0) {
            return id;
        }else {
            throw new DAOException(ErrorCodes.DATABASE_DELETE_NOT_EXIST, "MonitoringCategory does not exist in database");
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