package com.company.GameStore.dao;

import com.company.GameStore.dto.Console;
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
public class ConsoleDaoTest {

    @Autowired
    private ConsoleDao consoleDao;

    private Console console1, console2;

    @Before
    public void setUp() throws Exception {
        List<Console> consoles = consoleDao.getAllConsoles();
        for (Console c: consoles) {
            consoleDao.deleteConsole(c.getConsoleId());
        }

        console1 = new Console();
        console1.setModel("PS4");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("500 GB");
        console1.setProcessor("AMD");
        console1.setPrice(new BigDecimal("499.99"));
        console1.setQuantity(25);

        console2 =  new Console();
        console2.setModel("Xbox One");
        console2.setManufacturer("Microsoft");
        console2.setMemoryAmount("500 GB");
        console2.setProcessor("AMD Jaguar");
        console2.setPrice(new BigDecimal("499.99"));
        console2.setQuantity(25);
    }

    @Test
    public void addGetDeleteConsole() {
        console1 = consoleDao.addConsole(console1);

        Console consoleTest = consoleDao.getConsole(console1.getConsoleId());

        assertEquals(consoleTest, console1);

        consoleDao.deleteConsole(console1.getConsoleId());

        consoleTest = consoleDao.getConsole(console1.getConsoleId());

        assertNull(consoleTest);

    }

    @Test
    public void getAllConsoles() {
        console1 = consoleDao.addConsole(console1);
        console2 = consoleDao.addConsole(console2);

        List<Console> consoleList = consoleDao.getAllConsoles();

        assertEquals(2, consoleList.size());
    }

    @Test
    public void getConsoleByManufacturer() {
        console1 = consoleDao.addConsole(console1);
        console2 = consoleDao.addConsole(console2);

        List<Console> consoleList = consoleDao.getConsoleByManufacturer("Sony");

        assertEquals(1, consoleList.size());
    }

    @Test
    public void updateConsole() {
        console1 = consoleDao.addConsole(console1);

        console1.setPrice(new BigDecimal("500.00"));

        consoleDao.updateConsole(console1);

        Console consoleTest = consoleDao.getConsole(console1.getConsoleId());

        assertEquals(consoleTest, console1);
    }
}