package com.company.GameStore.dao;

import com.company.GameStore.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    // Prepared Statements
    private static final String SELECT_INVOICE_SQL=
            "select * from invoice where invoice_id = ?";

    private static final String SELECT_ALL_INVOICES_SQL=
            "select * from invoice";

    private static final String INSERT_INVOICE_SQL=
            "insert into invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal, tax, processing_fee, total) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_INVOICE_SQL=
            "update invoice set name = ?, street = ?, city = ?, state = ?, zipcode = ?, item_type = ?, item_id = ?, unit_price = ?, quantity = ?, subtotal = ?, tax = ?, processing_fee = ?, total = ? where invoice_id = ?";

    private static final String DELETE_INVOICE_SQL=
            "delete from invoice where invoice_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice getInvoice(int invoiceId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_SQL, this::mapRowToInvoice, invoiceId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES_SQL, this::mapRowToInvoice);
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(UPDATE_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal(),
                invoice.getInvoiceId());
    }

    @Override
    public void deleteInvoice(int invoiceId) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, invoiceId);
    }

    private Invoice mapRowToInvoice(ResultSet set, int rowNum) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        //loop through the metaData and get all the column names in the table
        for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
            columnNames.add(set.getMetaData().getColumnName(i));
        }
        //sort the column names alphabetically
        columnNames = columnNames.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        //loop through all the methods in the class, since they may come back in any order, sort them alphabetically
        List<Method> setters = Arrays.stream(Invoice.class.getMethods()).filter(method -> method.getName().contains("set")).
                sorted(Comparator.comparing(Method::getName)).collect(Collectors.toList());
        //now that the methods and column names are both sorted, they should correspond to each other
        Invoice invoice = new Invoice();
        //call the setter methods using reflection instead of the dot operator on the item instance
        //notice the syntax, call invoke on the method directly and pass in the instance of the object we created
        for (int i = 0; i < setters.size(); i++) {
            try {
                if (setters.get(i).getParameterTypes()[0].getSimpleName().equalsIgnoreCase("localdate")) {
                    //if the setter expects a date convert it to a date object
                    setters.get(i).invoke(invoice, set.getDate(columnNames.get(i)).toLocalDate());
                } else {
                    //else set the property directly, no extra methods are need
                    setters.get(i).invoke(invoice, set.getObject(columnNames.get(i)));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.err.println("Error in reflective method call");
            }
        }

        return invoice;
    }
}
