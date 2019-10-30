package com.company.GameStore.dao;

import com.company.GameStore.dto.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleDaoJdbcTemplateImpl implements ConsoleDao {

    // Prepared Statements
    private  static final String SELECT_CONSOLE_SQL=
            "select * from console where console_id = ?";

    private static final String SELECT_ALL_CONSOLES=
            "select * from console";

    private static final String SELECT_CONSOLES_BY_MANU=
            "select * from console where manufacturer = ?";

    private static final String INSERT_CONSOLE_SQL=
            "insert into console (model, manufacturer, memory_amount, processor, price, quantity) values (?,?,?,?,?,?)";

    private static final String UPDATE_CONSOLE_SQL=
            "update console set model = ?, manufacturer = ?, memory_amount = ?, processor = ?, price = ?, quantity = ? where console_id = ?";

    private static final String DELETE_CONSOLE_SQL=
            "delete from console where console_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ConsoleDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Console getConsole(int consoleId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CONSOLE_SQL, this::mapRowToConsole, consoleId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Console> getAllConsoles() {
        return jdbcTemplate.query(SELECT_ALL_CONSOLES, this::mapRowToConsole);
    }

    @Override
    public List<Console> getConsoleByManufacturer(String manu) {
        return jdbcTemplate.query(SELECT_CONSOLES_BY_MANU, this::mapRowToConsole, manu);
    }

    @Override
    public Console addConsole(Console console) {
        jdbcTemplate.update(INSERT_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        console.setConsoleId(id);
        return console;
    }

    @Override
    public void updateConsole(Console console) {
        jdbcTemplate.update(UPDATE_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsoleId());
    }

    @Override
    public void deleteConsole(int consoleId) {
        jdbcTemplate.update(DELETE_CONSOLE_SQL, consoleId);
    }

    private Console mapRowToConsole(ResultSet rs, int rowNum) throws SQLException {
        Console console = new Console();
        console.setConsoleId(rs.getInt("console_id"));
        console.setModel(rs.getString("model"));
        console.setManufacturer(rs.getString("manufacturer"));
        console.setMemoryAmount(rs.getString("memory_amount"));
        console.setProcessor(rs.getString("processor"));
        console.setPrice(rs.getBigDecimal("price"));
        console.setQuantity(rs.getInt("quantity"));
        return console;
    }
}
