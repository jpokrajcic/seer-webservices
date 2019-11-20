package com.seer.security.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BCryptRealm extends JdbcRealm {

    private Logger logger;

    public BCryptRealm() {
        logger = Logger.getLogger(BCryptRealm.class);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String username = userPassToken.getUsername();

        if (username == null) {
            logger.info("Username is null#" + username);
            return null;
        }

        String bcryptSaltNHash = getPasswordForUser(username);

        if (bcryptSaltNHash == null) {
            logger.info("No account found for user#" + username);
            return null;
        }

        //return salted credentials
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, bcryptSaltNHash, getName());

        return info;
    }

    private String getPasswordForUser(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(authenticationQuery);
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            boolean hasAccount = resultSet.next();
            if (!hasAccount) {
                return null;
            }

            // bcryptSaltNHash contains concatenated salt and hashed password
            String bcryptSaltNHash = resultSet.getString(1);
            if (resultSet.next()) {
                throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
            }

            return bcryptSaltNHash;

        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            logger.error(message, e);
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(conn);
        }
    }
}
