package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.TaskCategory;
import com.seer.utils.DaoTools;

public class TaskCategoryMapper implements RowMapper<TaskCategory> {

    public TaskCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskCategory object = new TaskCategory();
        object.id = DaoTools.getLong(rs, "id");
        object.name = rs.getString("name");

        return object;
    }
}
