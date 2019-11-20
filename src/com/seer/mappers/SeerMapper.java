package com.seer.mappers;

import com.seer.dto.Seer;
import com.seer.utils.DaoTools;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SeerMapper implements RowMapper<Seer> {

    public Seer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seer object = new Seer();
        object.id = DaoTools.getLong(rs, "id");
        object.name = rs.getString("name");

        return object;
    }
}
