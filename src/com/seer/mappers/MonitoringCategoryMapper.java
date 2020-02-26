package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.MonitoringCategory;
import com.seer.utils.DaoTools;

public class MonitoringCategoryMapper implements RowMapper<MonitoringCategory> {

    public MonitoringCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        MonitoringCategory object = new MonitoringCategory();
        object.id = DaoTools.getLong(rs, "id");
        object.name = rs.getString("name");
        object.buildingId = DaoTools.getLong(rs, "building_id");

        return object;
    }
}
