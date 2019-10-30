package com.company.GameStore.dao;

import com.company.GameStore.dto.TShirt;
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
public class TShirtDaoTest {

    @Autowired
    private TShirtDao tShirtDao;

    private TShirt shirt1, shirt2;

    @Before
    public void setUp() throws Exception {
        List<TShirt> sList = tShirtDao.getAllShirts();
        for (TShirt s: sList) {
            tShirtDao.deleteShirt(s.getTshirtId());
        }

        shirt1 = new TShirt();
        shirt1.setSize("Small");
        shirt1.setColor("Blue");
        shirt1.setDescription("Small blue shirt.");
        shirt1.setPrice(new BigDecimal("15.45"));
        shirt1.setQuantity(12);

        shirt2 = new TShirt();
        shirt2.setSize("Medium");
        shirt2.setColor("Blue");
        shirt2.setDescription("Medium blue shirt.");
        shirt2.setPrice(new BigDecimal("17.60"));
        shirt2.setQuantity(24);

    }

    @Test
    public void addGetDeleteShirts() {
        shirt1 = tShirtDao.addShirt(shirt1);

        TShirt shirtTest = tShirtDao.getShirt(shirt1.getTshirtId());

        assertEquals(shirt1, shirtTest);

        tShirtDao.deleteShirt(shirt1.getTshirtId());

        shirtTest = tShirtDao.getShirt(shirt1.getTshirtId());

        assertNull(shirtTest);
    }

    @Test
    public void getAllShirts() {
        shirt1 = tShirtDao.addShirt(shirt1);
        shirt2 = tShirtDao.addShirt(shirt2);

        List<TShirt> sList = tShirtDao.getAllShirts();

        assertEquals(2, sList.size());
    }

    @Test
    public void getShirtByColor() {
        shirt1 = tShirtDao.addShirt(shirt1);
        shirt2 = tShirtDao.addShirt(shirt2);

        List<TShirt> sList = tShirtDao.getShirtByColor("Blue");

        assertEquals(2, sList.size());
    }

    @Test
    public void getShirtBySize() {
        shirt1 = tShirtDao.addShirt(shirt1);
        shirt2 = tShirtDao.addShirt(shirt2);

        List<TShirt> sList = tShirtDao.getShirtBySize("Small");

        assertEquals(1, sList.size());
    }

    @Test
    public void updateShirt() {
        shirt1 = tShirtDao.addShirt(shirt1);
        shirt1.setColor("Red");
        tShirtDao.updateShirt(shirt1);

        TShirt shirtTest = tShirtDao.getShirt(shirt1.getTshirtId());

        assertEquals(shirt1, shirtTest);
    }
}