package com.company.GameStore.dao;

import com.company.GameStore.dto.SalesTaxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SalesTaxRateDaoJdbcTemplateImpl implements SalesTaxRateDao{

    /*
    Currently I don't see a point in adding the other crud methods.
    After the first import of States there's not going to be a new State
    maybe an additional territory, so I thought just to have a Get and
    an Update, an update in case you need to update the rate for the state though
     */

    // Prepared Statements
    private static final String SELECT_STR_SQL=
            "select * from sales_tax_rate where state = ?";

    private static final String SELECT_ALL_STR_SQL=
            "select * from sales_tax_rate";

    private static final String INSERT_STR_SQL=
            "insert into sales_tax_rate (state, rate) values (?,?)";

    private static final String UPDATE_STR_SQL=
            "update sales_tax_rate set state = ?, rate = ? where state = ?";

    private static final String DELETE_STR_SQL=
            "delete from sales_tax_rate where state = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SalesTaxRateDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SalesTaxRate getSTR(String state) {
        try {
            return jdbcTemplate.queryForObject(SELECT_STR_SQL, this::mapRowToSTR, state);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SalesTaxRate> getAllSTR() {
        return jdbcTemplate.query(SELECT_ALL_STR_SQL, this::mapRowToSTR);
    }

    @Override
    public SalesTaxRate addSTR(SalesTaxRate str) {
        jdbcTemplate.update(INSERT_STR_SQL,
                str.getState(),
                str.getRate());
        return str;
    }

    @Override
    public void updateSTR(SalesTaxRate str) {
        jdbcTemplate.update(UPDATE_STR_SQL,
                str.getState(),
                str.getRate(),
                str.getState());
    }

    @Override
    public void deleteSTR(String state) {
        jdbcTemplate.update(DELETE_STR_SQL, state);
    }

    private SalesTaxRate mapRowToSTR(ResultSet rs, int rowNum) throws SQLException {
        SalesTaxRate str = new SalesTaxRate();
        str.setState(rs.getString("state"));
        str.setRate(rs.getBigDecimal("rate"));
        return str;
    }
}
