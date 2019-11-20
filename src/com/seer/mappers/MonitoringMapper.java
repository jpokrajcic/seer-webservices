package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.Monitoring;
import com.seer.utils.DaoTools;

public class MonitoringMapper implements RowMapper<Monitoring> {

    public Monitoring mapRow(ResultSet rs, int rowNum) throws SQLException {
        Monitoring object = new Monitoring();
        object.id = DaoTools.getLong(rs, "id");
        object.buildingId = DaoTools.getLong(rs, "building_id");
        object.monitoringCategoryId = DaoTools.getLong(rs, "monitoring_category_id");
        object.value = DaoTools.getInteger(rs, "value");
        object.entryDate = rs.getDate("entry_date");

        return object;
    }
}
