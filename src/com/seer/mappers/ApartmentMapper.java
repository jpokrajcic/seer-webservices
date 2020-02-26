package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.Apartment;
import com.seer.utils.DaoTools;

public class ApartmentMapper implements RowMapper<Apartment> {

    public Apartment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Apartment object = new Apartment();
        object.id = DaoTools.getLong(rs, "id");
        object.buildingId = DaoTools.getLong(rs, "building_id");
        object.number = rs.getString("number");
        object.lastName = rs.getString("last_name");
        object.contact = rs.getString("contact");
        object.size = DaoTools.getInteger(rs, "size");
        object.adultsCount = DaoTools.getInteger(rs, "adults_count");
        object.childrenCount = DaoTools.getInteger(rs, "children_count");
        object.email = rs.getString("email");
        object.phone = rs.getString("phone");
        object.mobile = rs.getString("mobile");
        object.rentEnds = rs.getDate("rent_ends");
        object.isOccupied = rs.getBoolean("is_occupied");

        return object;
    }

}
