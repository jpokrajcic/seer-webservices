package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.Message;
import com.seer.utils.DaoTools;

public class MessageMapper implements RowMapper<Message> {

    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message object = new Message();
        object.id = DaoTools.getLong(rs, "id");
        object.buildingId = DaoTools.getLong(rs, "building_id");
        object.apartmentId = DaoTools.getLong(rs, "apartment_id");
        object.title = rs.getString("title");
        object.description = rs.getString("description");
        object.dateCreated = rs.getDate("date_created");

        return object;
    }

}
