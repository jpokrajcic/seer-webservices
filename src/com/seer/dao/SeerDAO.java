package com.seer.dao;

import com.seer.common.ErrorCodes;
import com.seer.config.Configuration;
import com.seer.Seer;
import com.seer.exception.DAOException;
import com.seer.mappers.SeerMapper;
import com.seer.utils.TypedProperties;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Collection;

public class SeerDAO {
    private TypedProperties sqlQueries = Configuration.get().getQueries();

    private SimpleJdbcTemplate simpleJdbcTemplate;
    private DriverManagerDataSource dataSource;

    private TypedProperties generalProperties = Configuration.get().getProps();
    private TypedProperties localeEN = Configuration.get().getLocaleEN();

    public SeerDAO(SimpleJdbcTemplate template) {
        this.simpleJdbcTemplate = template;
    }

    public SeerDAO(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Collection<Seer> getSeers() throws DAOException {
        String sql = this.sqlQueries.getString("Q_Select_Seers");

        Collection<Seer> seers = null;

        try {
            seers = this.simpleJdbcTemplate.query(sql, new SeerMapper());
        } catch (Exception ex) {
            throw new DAOException(ErrorCodes.DATABASE_READ_ERROR, "Unable to get Seers");
        }

        return Seers;
    }
}
