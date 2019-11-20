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
import com.seer.dto.Apartment;
import com.seer.exception.DAOException;
import com.seer.mappers.ApartmentMapper;
import com.seer.utils.TypedProperties;

public class ApartmentDAO {

    private SimpleJdbcTemplate  simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    public ApartmentDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Apartment create(Apartment object, Long seerId) throws DAOException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("apartment").
                usingGeneratedKeyColumns("id");

        try{
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("building_id", object.buildingId);
            parameters.addValue("apartment_number", object.apartmentNumber);
            parameters.addValue("last_name", object.lastName);
            parameters.addValue("contact", object.contact);
            parameters.addValue("size", object.size);
            parameters.addValue("adults", object.adults);
            parameters.addValue("kids", object.kids);
            parameters.addValue("email", object.email);
            parameters.addValue("phone", object.phone);
            parameters.addValue("mobile", object.mobile);
            parameters.addValue("rent_ends", object.rentEnds);
            );

            Long rowId = 0L;

            rowId = (jdbcInsert.executeAndReturnKey(parameters)).longValue();

            if(rowId > 0){
                return getApartmentById(rowId);
            }else{
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new apartment");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to create apartment");
        }
    }

    public Apartment update(Apartment object) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Update_Apartment");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.apartmentNumber,
                    object.lastName,
                    object.contact,
                    object.size,
                    object.adults,
                    object.kids,
                    object.email,
                    object.phone,
                    object.mobile,
                    object.rentEnds);

            if (updatedRows > 0) {
                return getApartmentById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "Apartment does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update apartment");
        }
    }

    public Apartment getApartmentById(Long id) throws DAOException {
        return getApartmentById(id, null);
    }

    public Apartment getApartmentById(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn!=null;

        String sql = this.sqlQueries.getString("Q_Select_Apartment_By_Id");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ApartmentMapper nm = new ApartmentMapper();
        Apartment apartment = null;

        try {
            if(existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setObject(1, id, Types.BIGINT);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            if (rs.next())
                apartment = nm.mapRow(rs, 0);
        }
        catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to get apartment");
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

        return apartment;
    }

    public Collection<Apartment> getApartmentsByBuildingId(Long buildingId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Apartments_By_Building_Id");

        Collection<Apartment> apartments = null;

        try{
            apartments = this.simpleJdbcTemplate.query(sql, new ApartmentMapper(), buildingId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get apartments by building id");
        }
        return apartments;
    }

    public Long delete(Long id) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Delete_Apartment");

        Integer deletedRows;

        try{
            deletedRows = this.simpleJdbcTemplate.update(sql, id);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_DELETE_ERROR, "Unable to delete apartment");
        }

        if (deletedRows > 0) {
            return id;
        }else {
            throw new DAOException(ErrorCodes.DATABASE_DELETE_NOT_EXIST, "Apartment does not exist in database");
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