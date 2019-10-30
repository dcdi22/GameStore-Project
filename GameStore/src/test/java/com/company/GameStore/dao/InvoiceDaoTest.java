package com.company.GameStore.dao;

import com.company.GameStore.dto.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    private InvoiceDao invoiceDao;

    private Invoice invoice1, invoice2;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        for(Invoice i: invoices) {
            invoiceDao.deleteInvoice(i.getInvoiceId());
        }

        invoice1 = new Invoice();
        invoice1.setName("Delcie");
        invoice1.setStreet("Street");
        invoice1.setCity("City");
        invoice1.setState("Ga");
        invoice1.setZipcode("30102");
        invoice1.setItemType("Game");
        invoice1.setItemId(22);
        invoice1.setUnitPrice(new BigDecimal("25.50"));
        invoice1.setQuantity(1);
        invoice1.setSubtotal(new BigDecimal("25.50"));
        invoice1.setTax(new BigDecimal(".07"));
        invoice1.setProcessingFee(new BigDecimal("1.00"));
        invoice1.setTotal(new BigDecimal("28.26"));

        invoice2 = new Invoice();
        invoice2.setName("Michelle");
        invoice2.setStreet("Road");
        invoice2.setCity("City");
        invoice2.setState("Ga");
        invoice2.setZipcode("30102");
        invoice2.setItemType("console");
        invoice2.setItemId(22);
        invoice2.setUnitPrice(new BigDecimal("499.99"));
        invoice2.setQuantity(1);
        invoice2.setSubtotal(new BigDecimal("499.99"));
        invoice2.setTax(new BigDecimal(".07"));
        invoice2.setProcessingFee(new BigDecimal("2.00"));
        invoice2.setTotal(new BigDecimal("536.99"));

    }

    @Test
    public void addGetDeleteInvoice() {
        invoice1 = invoiceDao.addInvoice(invoice1);

        Invoice invoiceTest = invoiceDao.getInvoice(invoice1.getInvoiceId());

        assertEquals(invoice1, invoiceTest);

        invoiceDao.deleteInvoice(invoice1.getInvoiceId());

        invoiceTest = invoiceDao.getInvoice(invoice1.getInvoiceId());

        assertNull(invoiceTest);
    }

    @Test
    public void getAllInvoices() {
        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        assertEquals(2, invoiceList.size());
    }

    @Test
    public void updateInvoice() {
        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice1.setName("Richard");
        invoiceDao.updateInvoice(invoice1);

        Invoice invoiceTest = invoiceDao.getInvoice(invoice1.getInvoiceId());

        assertEquals(invoice1, invoiceTest);
    }
}