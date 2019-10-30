package com.company.GameStore.dao;

import com.company.GameStore.dto.TShirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TShirtDaoJdbcTemplateImpl implements TShirtDao {

    // Prepared Statements
    private static final String SELECT_TSHIRT_SQL=
            "select * from t_shirt where t_shirt_id = ?";

    private static final String SELECT_ALL_TSHIRTS_SQL=
            "select * from t_shirt";

    private static final String SELECT_TSHIRT_BY_COLOR=
            "select * from t_shirt where color = ?";

    private static final String SELECT_TSHIRT_BY_SIZE=
            "select * from t_shirt where size = ?";

    private static final String INSERT_TSHIRT_SQL=
            "insert into t_shirt (size, color, description, price, quantity) values (?,?,?,?,?)";

    private static final String UPDATE_TSHIRT_SQL=
            "update t_shirt set size = ?, color = ?, description = ?, price = ?, quantity = ? where t_shirt_id = ?";

    private static final String DELETE_TSHIRT_SQL=
            "delete from t_shirt where t_shirt_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TShirtDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TShirt getShirt(int tshirtId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_TSHIRT_SQL, this::mapRowToTShirt, tshirtId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<TShirt> getAllShirts() {
        return jdbcTemplate.query(SELECT_ALL_TSHIRTS_SQL, this::mapRowToTShirt);
    }

    @Override
    public List<TShirt> getShirtByColor(String color) {
        return jdbcTemplate.query(SELECT_TSHIRT_BY_COLOR, this::mapRowToTShirt, color);
    }

    @Override
    public List<TShirt> getShirtBySize(String size) {
        return jdbcTemplate.query(SELECT_TSHIRT_BY_SIZE, this::mapRowToTShirt, size);
    }

    @Override
    public TShirt addShirt(TShirt tShirt) {
        jdbcTemplate.update(INSERT_TSHIRT_SQL,
                tShirt.getSize(),
                tShirt.getColor(),
                tShirt.getDescription(),
                tShirt.getPrice(),
                tShirt.getQuantity());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        tShirt.setTshirtId(id);
        return tShirt;
    }

    @Override
    public void updateShirt(TShirt tShirt) {
        jdbcTemplate.update(UPDATE_TSHIRT_SQL,
                tShirt.getSize(),
                tShirt.getColor(),
                tShirt.getDescription(),
                tShirt.getPrice(),
                tShirt.getQuantity(),
                tShirt.getTshirtId());
    }

    @Override
    public void deleteShirt(int tshirtId) {
        jdbcTemplate.update(DELETE_TSHIRT_SQL, tshirtId);
    }

    private TShirt mapRowToTShirt(ResultSet rs, int rowNum) throws SQLException {
        TShirt tShirt = new TShirt();
        tShirt.setTshirtId(rs.getInt("t_shirt_id"));
        tShirt.setSize(rs.getString("size"));
        tShirt.setColor(rs.getString("color"));
        tShirt.setDescription(rs.getString("description"));
        tShirt.setPrice(rs.getBigDecimal("price"));
        tShirt.setQuantity(rs.getInt("quantity"));
        return tShirt;
    }
 }
