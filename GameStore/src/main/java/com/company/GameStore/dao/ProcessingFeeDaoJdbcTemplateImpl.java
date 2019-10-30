package com.company.GameStore.dao;

import com.company.GameStore.dto.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProcessingFeeDaoJdbcTemplateImpl implements ProcessingFeeDao {

    // Prepared Statements
    private static final String SELECT_PF=
            "select * from processing_fee where product_type = ?";

    private static final String SELECT_ALL_PF=
            "select * from processing_fee";

    private static final String INSERT_PF=
            "insert into processing_fee (product_type, fee) values (?,?)";

    private static final String UPDATE_PF=
            "update processing_fee set product_type = ?, fee = ? where product_type =?";

    private static final String DELETE_PF=
            "delete from processing_fee where product_type = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProcessingFeeDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProcessingFee getProcessingFee(String productType) {
        try {
            return jdbcTemplate.queryForObject(SELECT_PF, this::mapRowToProcessingFee, productType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ProcessingFee> getAllProcessingFees() {
        return jdbcTemplate.query(SELECT_ALL_PF, this::mapRowToProcessingFee);
    }

    @Override
    public ProcessingFee addProcessingFee(ProcessingFee processingFee) {
        jdbcTemplate.update(INSERT_PF,
                processingFee.getProductType(),
                processingFee.getFee());
        return processingFee;
    }

    @Override
    public void updateProcessingFee(ProcessingFee processingFee) {
        jdbcTemplate.update(UPDATE_PF,
                processingFee.getProductType(),
                processingFee.getFee(),
                processingFee.getProductType());
    }

    @Override
    public void deleteProcessingFee(String productType) {
        jdbcTemplate.update(DELETE_PF, productType);
    }

    private ProcessingFee mapRowToProcessingFee(ResultSet rs, int rowNum) throws SQLException {
        ProcessingFee pf = new ProcessingFee();
        pf.setProductType(rs.getString("product_type"));
        pf.setFee(rs.getBigDecimal("fee"));
        return pf;
    }
}
