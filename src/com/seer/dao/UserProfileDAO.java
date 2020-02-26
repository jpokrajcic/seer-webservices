package com.seer.dao;

import com.seer.common.ErrorCodes;
import com.seer.config.Configuration;
import com.seer.dto.UserProfile;
import com.seer.dto.UserProfileInfo;
import com.seer.exception.DAOException;
import com.seer.mappers.UserProfileInfoMapper;
import com.seer.mappers.UserProfileMapper;
import com.seer.utils.TypedProperties;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.List;

public class UserProfileDAO {

    private TypedProperties sqlQueries = Configuration.get().getQueries();

    private SimpleJdbcTemplate simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties generalProperties = Configuration.get().getProps();

    public UserProfileDAO(SimpleJdbcTemplate template) {
        this.simpleJdbcTemplate = template;
    }

    public UserProfileDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public UserProfileDAO() {
        String test = "ddd";
    }

    /**
     * Create UserProfile and return it (opens new connection)
     *
     * @param object
     * @return UserProfile
     * @throws DAOException
     */
    public UserProfile create(UserProfile object) throws DAOException {
        return create(object, null);
    }

    /**
     * Create UserProfile and return it (uses existing connection or opens a new one)
     *
     * @param object
     * @param conn   (if null, open new connection)
     * @return UserProfile
     * @throws DAOException
     */
    public UserProfile create(UserProfile object, Connection conn) throws DAOException {

        Boolean existingConnection = conn != null;
        String sql = this.sqlQueries.getString("Q_Insert_User");

        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        UserProfile createdUserProfile = null;

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            if (existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            pstmt.setObject(1, object.customerId, Types.BIGINT);
            pstmt.setObject(2, object.username, Types.VARCHAR);
            pstmt.setObject(3, object.password, Types.VARCHAR);
            pstmt.setObject(4, object.email, Types.VARCHAR);
            pstmt.setObject(5, object.firstName, Types.VARCHAR);
            pstmt.setObject(6, object.lastName, Types.VARCHAR);
            pstmt.setObject(7, object.enabled, Types.BOOLEAN);
            pstmt.setObject(8, currentTime, Types.TIMESTAMP);
            pstmt.setObject(9, currentTime, Types.TIMESTAMP);
            pstmt.setObject(10,  object.lastLoginTime, Types.TIMESTAMP);
            pstmt.setObject(11, object.plainPassword, Types.VARCHAR);
            pstmt.setObject(12, object.role, Types.INTEGER);

            pstmt.execute();

            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdUserProfile = getUserProfileForId(generatedKeys.getLong(1), conn);
            } else {
                throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "Unable to insert new user");
            }
        } catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_INSERT_ERROR, "UserProfile insert failed");
        } finally {
            try {
                if (generatedKeys != null)
                    generatedKeys.close();
                if (pstmt != null)
                    pstmt.close();
                if (existingConnection == false && conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }

        return createdUserProfile;
    }

    public UserProfile update(UserProfile object) throws DAOException {
        return this.update(object, null);
    }

    public UserProfile update(UserProfile object, Connection conn) throws DAOException {

        Boolean existingConnection = conn != null;

        String sql = this.sqlQueries.getString("Q_Update_User_Profile");

        PreparedStatement pstmt = null;

        UserProfile updatedUserProfile = null;
        int numUpdated = 0;

        try {
            if (existingConnection == false)
                conn = dataSource.getConnection();

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            pstmt = conn.prepareStatement(sql);

            pstmt.setObject(1, object.username, Types.VARCHAR);
            pstmt.setObject(2, object.password, Types.VARCHAR);
            pstmt.setObject(3, object.email, Types.VARCHAR);
            pstmt.setObject(4, object.firstName, Types.VARCHAR);
            pstmt.setObject(5, object.lastName, Types.VARCHAR);
            pstmt.setObject(6, object.enabled, Types.BOOLEAN);
            pstmt.setObject(7, object.createdTime, Types.TIMESTAMP);
            pstmt.setObject(8, currentTime, Types.TIMESTAMP);
            pstmt.setObject(9, object.lastLoginTime, Types.TIMESTAMP);
            pstmt.setObject(11, object.plainPassword, Types.VARCHAR);
            pstmt.setObject(12, object.id, Types.BIGINT);

            numUpdated = pstmt.executeUpdate();

            if (numUpdated > 0) {
                updatedUserProfile = getUserProfileForId(object.id, conn);
            } else {
                throw new DAOException(ErrorCodes.DATABASE_UPDATE_NOT_EXIST, "UserProfile does not exist in database");
            }
        } catch (SQLException ex) {
            throw new DAOException(ErrorCodes.DATABASE_UPDATE_ERROR, "UserProfile update failed");
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (existingConnection == false && conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }

        return updatedUserProfile;
    }

    public UserProfileInfo getUserProfileInfoForId(Long id) throws DAOException {
        Logger logger = Logger.getLogger(UserProfileDAO.class);
        logger.debug("getUserProfileInfoForId");

        String sql = this.sqlQueries.getString("Q_Select_User_Profile_Info_By_Id");

        UserProfileDAO userProfileDAO = new UserProfileDAO(dataSource);

        UserProfileInfo userProfileInfo = null;
        List<UserProfileInfo> userProfilesInfo;

        try {
            userProfilesInfo = this.simpleJdbcTemplate.query(sql, new UserProfileInfoMapper(), id);
        } catch (Exception ex) {
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get user profile info");
        }
        if (userProfilesInfo != null && userProfilesInfo.size() == 1) {
            userProfileInfo = userProfilesInfo.get(0);
        } else if (userProfilesInfo.size() > 1) {
            throw new DAOException(ErrorCodes.ITEM_NOT_UNIQUE, "User profile not unique!!!");
        }

        return userProfileInfo;
    }

    public UserProfile getUserProfileForId(Long id) throws DAOException {
        return getUserProfileForId(id, null);
    }

    public UserProfile getUserProfileForId(Long id, Connection conn) throws DAOException {

        Boolean existingConnection = conn != null;

        String sql = this.sqlQueries.getString("Q_Select_User_Profile_By_Id");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserProfileMapper mapper = new UserProfileMapper();
        UserProfile userProfile = null;

        try {

            if (existingConnection == false)
                conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id, Types.BIGINT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userProfile = mapper.mapRow(rs, 0);
            }
        } catch (Exception e) {
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get userProfile");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (existingConnection == false) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }

        if (userProfile == null)
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get userProfile");

        return userProfile;
    }

    public UserProfile getUserProfileForUsername(String username) throws DAOException {
        Logger logger = Logger.getLogger(UserProfileDAO.class);
        logger.debug("getUserProfileForUsername");

        String sql = this.sqlQueries.getString("Q_Select_User_Profile_By_Username");

        UserProfile userProfile = null;
        List<UserProfile> userProfiles;

        try {
            userProfiles = this.simpleJdbcTemplate.query(sql, new UserProfileMapper(), username);
        } catch (Exception ex) {
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get userProfile");
        }
        if (userProfiles != null && userProfiles.size() == 1) {
            userProfile = userProfiles.get(0);
        } else if (userProfiles.size() > 1) {
            throw new DAOException(ErrorCodes.ITEM_NOT_UNIQUE, "Username not unique.");
        }

        return userProfile;
    }

    public String getPasswordForUsername(String username) {
        String sql = this.sqlQueries.getString("Q_Select_User_Password");

        String password = this.simpleJdbcTemplate.queryForObject(
                sql, String.class, username);

        return password;
    }
}