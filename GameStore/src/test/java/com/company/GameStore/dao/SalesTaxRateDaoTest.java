package com.company.GameStore.dao;

import com.company.GameStore.dto.SalesTaxRate;
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
public class SalesTaxRateDaoTest {

    @Autowired
    private SalesTaxRateDao strDao;

    private SalesTaxRate str1, str2;

    @Before
    public void setUp() throws Exception {
        List<SalesTaxRate> strList = strDao.getAllSTR();
        for (SalesTaxRate s: strList) {
            strDao.deleteSTR(s.getState());
        }

        str1 = new SalesTaxRate();
        str1.setState("GA");
        str1.setRate(new BigDecimal(".07"));

        str2 = new SalesTaxRate();
        str2.setState("AL");
        str2.setRate(new BigDecimal(".05"));
    }

    @Test
    public void addGetDeleteSTR() {
        str1 = strDao.addSTR(str1);

        SalesTaxRate strTest = strDao.getSTR(str1.getState());

        assertEquals(strTest, str1);

        strDao.deleteSTR(str1.getState());

        strTest = strDao.getSTR(str1.getState());

        assertNull(strTest);
    }

    @Test
    public void getAllSTR() {
        str1 = strDao.addSTR(str1);
        str2 = strDao.addSTR(str2);

        List<SalesTaxRate> strList = strDao.getAllSTR();

        assertEquals(2, strList.size());
    }

    @Test
    public void updateSTR() {
        str1 = strDao.addSTR(str1);
        str1.setRate(new BigDecimal(".25"));
        strDao.updateSTR(str1);

        SalesTaxRate test = strDao.getSTR(str1.getState());

        assertEquals(str1, test);
    }
}