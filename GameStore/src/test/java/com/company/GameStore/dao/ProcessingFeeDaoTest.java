package com.company.GameStore.dao;

import com.company.GameStore.dto.ProcessingFee;
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
public class ProcessingFeeDaoTest {

    @Autowired
    private ProcessingFeeDao processingFeeDao;

    private ProcessingFee pf1, pf2;

    @Before
    public void setUp() throws Exception {
        List<ProcessingFee> pfList = processingFeeDao.getAllProcessingFees();
        for (ProcessingFee pf: pfList) {
            processingFeeDao.deleteProcessingFee(pf.getProductType());
        }

        pf1 = new ProcessingFee();
        pf1.setProductType("Games");
        pf1.setFee(new BigDecimal("1.49"));

        pf2 = new ProcessingFee();
        pf2.setProductType("Consoles");
        pf2.setFee(new BigDecimal("14.99"));
    }

    @Test
    public void addGetDeleteProcessingFee() {
        pf1 = processingFeeDao.addProcessingFee(pf1);

        ProcessingFee pfTest = processingFeeDao.getProcessingFee(pf1.getProductType());

        assertEquals(pf1, pfTest);

        processingFeeDao.deleteProcessingFee(pf1.getProductType());

        pfTest = processingFeeDao.getProcessingFee(pf1.getProductType());

        assertNull(pfTest);
    }

    @Test
    public void getAllProcessingFees() {
        pf1 = processingFeeDao.addProcessingFee(pf1);
        pf2 = processingFeeDao.addProcessingFee(pf2);

        List<ProcessingFee> processingFees = processingFeeDao.getAllProcessingFees();

        assertEquals(2, processingFees.size());
    }

    @Test
    public void updateProcessingFee() {
        pf1 = processingFeeDao.addProcessingFee(pf1);
        pf1.setFee(new BigDecimal("20.01"));
        processingFeeDao.updateProcessingFee(pf1);

        ProcessingFee pfTest = processingFeeDao.getProcessingFee(pf1.getProductType());

        assertEquals(pf1, pfTest);
    }
}