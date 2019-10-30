package com.company.GameStore.controller;

import com.company.GameStore.SecurityConfig;
import com.company.GameStore.service.ServiceLayer;
import com.company.GameStore.viewmodel.InvoiceViewModel;
import com.company.GameStore.viewmodel.OrderViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import javax.sql.DataSource;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
@ContextConfiguration
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private SecurityConfig securityConfig;


    @MockBean
    DataSource dataSource;

    @Before
    public void setUp() throws Exception {
    }

    // ==================================================

    @Test
    public void getInvoiceReturnJson() throws Exception {
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setName("Delcie");
        invoice.setStreet("Street");
        invoice.setCity("City");
        invoice.setState("GA");
        invoice.setZipcode("30102");
        invoice.setItemType("Consoles");
        invoice.setItemId(1);
        invoice.setUnitPrice(new BigDecimal("50.00"));
        invoice.setQuantity(1);
        invoice.setSubtotal(new BigDecimal("50.00"));
        invoice.setTax(new BigDecimal("3.50"));
        invoice.setProcessingFee(new BigDecimal("14.99"));
        invoice.setTotal(new BigDecimal("68.49"));
        invoice.setItemId(8);

        String outputJson = mapper.writeValueAsString(invoice);

        when(service.getInvoice(8)).thenReturn(invoice);

        this.mockMvc.perform(get("/purchase/8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getInvoiceReturn404() throws Exception {

        when(service.getInvoice(100)).thenThrow(new IllegalArgumentException("Could not find an invoice with matching Id"));

        this.mockMvc.perform(get("/purchase/100"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void createInvoice() throws Exception {
        OrderViewModel invoice = new OrderViewModel();
        invoice.setName("Delcie");
        invoice.setStreet("Street");
        invoice.setCity("City");
        invoice.setState("GA");
        invoice.setZipcode("30102");
        invoice.setItemType("Consoles");
        invoice.setItemId(1);
//        invoice.setUnitPrice(new BigDecimal("50.00"));
        invoice.setQuantity(1);
//        invoice.setSubtotal(new BigDecimal("50.00"));
//        invoice.setTax(new BigDecimal("3.50"));
//        invoice.setProcessingFee(new BigDecimal("14.99"));
//        invoice.setTotal(new BigDecimal("68.49"));

        String inputJson = mapper.writeValueAsString(invoice);

        InvoiceViewModel outVoice = new InvoiceViewModel();
        outVoice.setName("Delcie");
        outVoice.setStreet("Street");
        outVoice.setCity("City");
        outVoice.setState("GA");
        outVoice.setZipcode("30102");
        outVoice.setItemType("Consoles");
        outVoice.setItemId(1);
        outVoice.setUnitPrice(new BigDecimal("50.00"));
        outVoice.setQuantity(1);
        outVoice.setSubtotal(new BigDecimal("50.00"));
        outVoice.setTax(new BigDecimal("3.50"));
        outVoice.setProcessingFee(new BigDecimal("14.99"));
        outVoice.setTotal(new BigDecimal("68.49"));
        outVoice.setItemId(8);

        String outputJson = mapper.writeValueAsString(outVoice);

        when(service.addInvoice(invoice)).thenReturn(outVoice);

        this.mockMvc.perform(post("/purchase")
                .with(csrf().asHeader())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getAllInvoices() throws Exception {
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setName("Delcie");
        invoice.setStreet("Street");
        invoice.setCity("City");
        invoice.setState("GA");
        invoice.setZipcode("30102");
        invoice.setItemType("Consoles");
        invoice.setItemId(1);
        invoice.setUnitPrice(new BigDecimal("50.00"));
        invoice.setQuantity(1);
        invoice.setSubtotal(new BigDecimal("50.00"));
        invoice.setTax(new BigDecimal("3.50"));
        invoice.setProcessingFee(new BigDecimal("14.99"));
        invoice.setTotal(new BigDecimal("68.49"));
        invoice.setItemId(8);

        InvoiceViewModel invoice2 = new InvoiceViewModel();
        invoice2.setName("Delcie");
        invoice2.setStreet("Street");
        invoice2.setCity("City");
        invoice2.setState("GA");
        invoice2.setZipcode("30102");
        invoice2.setItemType("Consoles");
        invoice2.setItemId(1);
        invoice2.setUnitPrice(new BigDecimal("50.00"));
        invoice2.setQuantity(1);
        invoice2.setSubtotal(new BigDecimal("50.00"));
        invoice2.setTax(new BigDecimal("3.50"));
        invoice2.setProcessingFee(new BigDecimal("14.99"));
        invoice2.setTotal(new BigDecimal("68.49"));
        invoice2.setItemId(8);

        List<InvoiceViewModel> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        invoiceList.add(invoice2);

        when(service.getAllInvoices()).thenReturn(invoiceList);

        List<InvoiceViewModel> listChecker = new ArrayList<>();
        listChecker.addAll(invoiceList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/purchase"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));


    }
}