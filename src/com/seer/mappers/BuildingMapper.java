package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.Building;
import com.seer.utils.DaoTools;

public class BuildingMapper implements RowMapper<Building> {

    public Building mapRow(ResultSet rs, int rowNum) throws SQLException {
        Building object = new Building();
        object.id = DaoTools.getLong(rs, "id");
        object.name = rs.getString("name");
        object.street = rs.getString("street");
        object.city = rs.getString("city");
        object.countryId = DaoTools.getLong(rs, "country_id");
        object.isSmart = rs.getBoolean("is_smart");

        return object;
    }

}
