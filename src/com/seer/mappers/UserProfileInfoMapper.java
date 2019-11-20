package com.seer.mappers;

import com.seer.dto.UserProfileInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserProfileInfoMapper implements RowMapper<UserProfileInfo> {
	
	public UserProfileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserProfileInfo object = new UserProfileInfo();
        object.firstName = rs.getString("first_name");
        object.lastName = rs.getString("last_name");
		object.email = rs.getString("email");
		object.username = rs.getString("username");
        return object;
    }
}
