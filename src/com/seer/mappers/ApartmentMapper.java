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
        object.apartmentNumber = rs.getString("apartment_number");
        object.lastName = rs.getString("lastName");
        object.contact = rs.getString("contact");
        object.size = DaoTools.getInteger(rs, "size");
        object.adults = DaoTools.getInteger(rs, "adults");
        object.kids = DaoTools.getInteger(rs, "kids");
        object.email = rs.getString("email");
        object.phone = rs.getString("phone");
        object.mobile = rs.getString("mobile");
        object.rentEnds = rs.getDate("rent_ends");

        return object;
    }

}
