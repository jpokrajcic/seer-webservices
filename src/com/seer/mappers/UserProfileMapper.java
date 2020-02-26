package com.seer.mappers;

import com.seer.dto.UserProfile;
import com.seer.utils.DaoTools;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserProfileMapper implements RowMapper<UserProfile> {
	
	public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserProfile object = new UserProfile();
        object.id = DaoTools.getLong(rs, "id");
        object.username = rs.getString("username");
        object.password = rs.getString("password");
        object.email = rs.getString("email");

        object.firstName = rs.getString("first_name");
        object.lastName = rs.getString("last_name");

        object.enabled = rs.getBoolean("enabled");
        object.createdTime = rs.getTimestamp("created_time");
        object.lastUpdateTime = rs.getTimestamp("last_update_time");
        object.lastLoginTime = rs.getTimestamp("last_login_time");
        object.plainPassword = rs.getString("plain_password");
        object.role = rs.getInt("role");

        return object;
    }
}
