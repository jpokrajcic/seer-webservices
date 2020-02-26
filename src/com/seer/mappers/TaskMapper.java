package com.seer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.seer.dto.Task;
import com.seer.utils.DaoTools;

public class TaskMapper implements RowMapper<Task> {

    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task object = new Task();
        object.id = DaoTools.getLong(rs, "id");
        object.buildingId = DaoTools.getLong(rs, "building_id");
        object.apartmentId = DaoTools.getLong(rs, "apartment_id");
        object.taskCategoryId = DaoTools.getLong(rs, "task_category_id");
        object.name = rs.getString("name");
        object.description = rs.getString("description");
        object.completed = rs.getBoolean("completed");
        object.dueDate = rs.getDate("due_date");

        return object;
    }

}
