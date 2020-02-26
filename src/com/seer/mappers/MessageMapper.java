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
        object.apartmentNumber = rs.getString("apartment_number");
        object.apartmentLastName = rs.getString("apartment_last_name");
        object.title = rs.getString("title");
        object.body = rs.getString("body");
        object.dateCreated = rs.getDate("date_created");
        object.read = rs.getBoolean("read");
        object.sentBySeer = rs.getBoolean("sent_by_seer");

        return object;
    }

}
