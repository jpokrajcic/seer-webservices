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
import com.seer.dto.Message;
import com.seer.exception.DAOException;
import com.seer.mappers.MessageMapper;
import com.seer.utils.TypedProperties;

public class MessageDAO {

    private SimpleJdbcTemplate  simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    public MessageDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Message create(Message object) throws DAOException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("message").
                usingGeneratedKeyColumns("id");

        try{
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("building_id", object.buildingId);
            parameters.addValue("apartment_id", object.apartmentId);
            parameters.addValue("title", object.title);
            parameters.addValue("description", object.description);
            parameters.addValue("date_created", object.dateCreated);

            Long rowId = 0L;

            rowId = (jdbcInsert.executeAndReturnKey(parameters)).longValue();

            if(rowId > 0){
                return getMessageById(rowId);
            }else{
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new message");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to create message");
        }
    }

    public Message update(Message object) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Update_Message");

        Integer updatedRows;

        try{
            updatedRows = this.simpleJdbcTemplate.update(sql,
                    object.title,
                    object.description,
                    object.dateCreated);

            if (updatedRows > 0) {
                return getMessageById(object.id.longValue());
            }
            else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "Message does not exist in database");
            }
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to update message");
        }
    }

    public Message getMessageById(Long id) throws DAOException {
        return getMessageById(id, seerId, null);
    }

    public Message getMessageById(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn!=null;

        String sql = this.sqlQueries.getString("Q_Select_Message_By_Id");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MessageMapper nm = new MessageMapper();
        Message message = null;

        try {
            if(existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setObject(1, id, Types.BIGINT);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            if (rs.next())
                message = nm.mapRow(rs, 0);
        }
        catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "Unable to get message");
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

        return message;
    }

    public Collection<Message> getMessagesByBuildingId(Long buildingId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Messages_By_Building_Id");

        Collection<Message> messages = null;

        try{
            messages = this.simpleJdbcTemplate.query(sql, new MessageMapper(), buildingId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get messages by building id");
        }
        return messages;
    }


    public Collection<Message> getMessagesByApartmentId(Long apartmentId) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Messages_By_Apartment_Id");

        Collection<Message> messages = null;

        try{
            messages = this.simpleJdbcTemplate.query(sql, new MessageMapper(), apartmentId);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get messages by apartment id");
        }
        return messages;
    }

    public Long delete(Long id) throws DAOException {
        String sql = this.sqlQueries.getString("Q_Delete_Message");

        Integer deletedRows;

        try{
            deletedRows = this.simpleJdbcTemplate.update(sql, id);
        }
        catch(DataAccessException e){
            throw new DAOException(ErrorCodes.DATABASE_DELETE_ERROR, "Unable to delete message");
        }

        if (deletedRows > 0) {
            return id;
        }else {
            throw new DAOException(ErrorCodes.DATABASE_DELETE_NOT_EXIST, "Message does not exist in database");
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