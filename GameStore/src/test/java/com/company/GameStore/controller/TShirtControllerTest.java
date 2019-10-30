package com.company.GameStore.controller;

import com.company.GameStore.SecurityConfig;
import com.company.GameStore.dto.TShirt;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TShirtController.class)
@ContextConfiguration
public class TShirtControllerTest {

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
    public void getShirtReturnWithJson() throws Exception {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt.setTshirtId(8);

        String outputJson = mapper.writeValueAsString(shirt);

        when(service.getShirt(8)).thenReturn(shirt);

        this.mockMvc.perform(get("/tshirt/8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getShirtReturn404() throws Exception {
        TShirt shirt = new TShirt();

        int idForShirtThatDoesNotExist = 100;

        when(service.getShirt(idForShirtThatDoesNotExist)).thenThrow(new IllegalArgumentException("Could not find shirt with matching Id"));

        this.mockMvc.perform(get("/tshirt/" + idForShirtThatDoesNotExist))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void addShirt() throws Exception {
        TShirt inShirt = new TShirt();
        inShirt.setSize("S");
        inShirt.setColor("Blue");
        inShirt.setDescription("Small Blue Shirt");
        inShirt.setPrice(new BigDecimal("12.00"));
        inShirt.setQuantity(1);

        String inputJson = mapper.writeValueAsString(inShirt);

        TShirt outShirt = new TShirt();
        outShirt.setSize("S");
        outShirt.setColor("Blue");
        outShirt.setDescription("Small Blue Shirt");
        outShirt.setPrice(new BigDecimal("12.00"));
        outShirt.setQuantity(1);
        outShirt.setTshirtId(8);

        String outputJson = mapper.writeValueAsString(outShirt);

        when(service.addShirt(inShirt)).thenReturn(outShirt);

        this.mockMvc.perform(post("/tshirt")
                .with(csrf().asHeader())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getAllShirts() throws Exception {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt.setTshirtId(8);

        TShirt shirt2 = new TShirt();
        shirt2.setSize("S");
        shirt2.setColor("Blue");
        shirt2.setDescription("Small Blue Shirt");
        shirt2.setPrice(new BigDecimal("12.00"));
        shirt2.setQuantity(1);
        shirt2.setTshirtId(9);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(shirt);
        shirtList.add(shirt2);

        when(service.getAllShirts()).thenReturn(shirtList);

        List<TShirt> listChecker = new ArrayList<>();
        listChecker.addAll(shirtList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/tshirt"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getShirtByColor() throws Exception {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt.setTshirtId(8);

        TShirt shirt2 = new TShirt();
        shirt2.setSize("S");
        shirt2.setColor("Blue");
        shirt2.setDescription("Small Blue Shirt");
        shirt2.setPrice(new BigDecimal("12.00"));
        shirt2.setQuantity(1);
        shirt2.setTshirtId(9);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(shirt);
        shirtList.add(shirt2);

        when(service.getShirtByColor("Blue")).thenReturn(shirtList);

        List<TShirt> listChecker = new ArrayList<>();
        listChecker.addAll(shirtList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/tshirt/GetColor/Blue"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getShirtBySize() throws Exception {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt.setTshirtId(8);

        TShirt shirt2 = new TShirt();
        shirt2.setSize("S");
        shirt2.setColor("Blue");
        shirt2.setDescription("Small Blue Shirt");
        shirt2.setPrice(new BigDecimal("12.00"));
        shirt2.setQuantity(1);
        shirt2.setTshirtId(9);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(shirt);
        shirtList.add(shirt2);

        when(service.getShirtBySize("S")).thenReturn(shirtList);

        List<TShirt> listChecker = new ArrayList<>();
        listChecker.addAll(shirtList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/tshirt/GetSize/S"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void updateShirt() throws Exception {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt.setTshirtId(8);

        String inputJson = mapper.writeValueAsString(shirt);
//        String outputJson = mapper.writeValueAsString(shirt);

        this.mockMvc.perform(put("/tshirt/8")
                .with(csrf().asHeader())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void deleteShirt() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/tshirt/8")
                .with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}