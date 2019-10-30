package com.company.GameStore.service;

import com.company.GameStore.dao.*;
import com.company.GameStore.dto.*;
import com.company.GameStore.viewmodel.InvoiceViewModel;
import com.company.GameStore.viewmodel.OrderViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTestEXAMPLE {

    // Mock is for unit tests from Mockito Library, same as doing the gameDao = mock(gameDao.class
    // MockBean is for integrated tests
        // Part of Spring, and mockbean creates a mock instance of the Application Context

    // Shove all mocks inside
    @InjectMocks
    ServiceLayer service;
    @Mock
    ConsoleDao consoleDao;
    @Mock
    GameDao gameDao;
    @Mock
    InvoiceDao invoiceDao;
    @Mock
    TShirtDao tShirtDao;
    @Mock
    ProcessingFeeDao processingFeeDao;
    @Mock
    SalesTaxRateDao salesTaxRateDao;

//    @Rule
//    public ExpectedException thrown= ExpectedException.none();


    @Before
    public void setUp() throws Exception {
//        doReturn(null).when(consoleDao).getConsole(0);
        MockitoAnnotations.initMocks(this);
        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpInvoiceDaoMock();
        setUpTShirtDaoMock();

        setUpProcessingFeeDaoMock();
        setUpSalesTaxRateDaoMock();

        // service = new ServiceLayer(consoleDao, gameDao, tShirtDao, invoiceDao, processingFeeDao, salesTaxRateDao);
        // This is filled by the InjectMocks annotation

    }

//    @Test
//    public void addGetConsole() {
//        Console console = new Console();
//        console.setModel("PS4");
//        console.setManufacturer("Sony");
//        console.setMemoryAmount("A lot");
//        console.setProcessor("AMD");
//        console.setPrice(new BigDecimal("50.00"));
//        console.setQuantity(1);
//        console = service.addConsole(console);
//        Console fromService = service.getConsole(console.getConsoleId());
//        assertEquals(console, fromService);
//
//        doReturn()
//
//        // Act
//
//
//        // Assert
//
////        verify(consoleDao, times(1)).addConsole(consoleInput);
//
//    }

    // ✷
    @Test
    public void addGetInvoice() {
        //example OrderViewModel that would come in
        OrderViewModel ovm = new OrderViewModel();
        ovm.setName("Delcie");
        ovm.setStreet("Street");
        ovm.setCity("City");
        ovm.setState("GA");
        ovm.setZipcode("30102");
        ovm.setItemType("Consoles");
        ovm.setItemId(1);
        ovm.setQuantity(1);

//        service.addInvoice(ovm);

        //example complete and calculated Invoice to be returned
        Invoice i = new Invoice();
        i.setName(ovm.getName());
        i.setStreet(ovm.getStreet());
        i.setCity(ovm.getCity());
        i.setState(ovm.getState());
        i.setZipcode(ovm.getZipcode());
        i.setItemType(ovm.getItemType());
        i.setItemId(ovm.getItemId());
        i.setUnitPrice(new BigDecimal("50.00"));
        i.setQuantity(ovm.getQuantity());
        i.setSubtotal(new BigDecimal("50.00"));
        i.setTax(new BigDecimal("3.50"));
        i.setProcessingFee(new BigDecimal("14.99"));
        i.setTotal(new BigDecimal("68.49"));

        //adding invoice
        i = invoiceDao.addInvoice(i);

        //retrieving invoice
        Invoice fromService = service.getInvoice(i.getInvoiceId());

        assertEquals(i, fromService);
//        assertEquals(ovm, fromService);

    }

    // ✷
    @Test
    public void getAllInvoices() {
        //example OrderViewModel that would come in
        OrderViewModel ovm = new OrderViewModel();
        ovm.setName("Delcie");
        ovm.setStreet("Street");
        ovm.setCity("City");
        ovm.setState("GA");
        ovm.setZipcode("30102");
        ovm.setItemType("Consoles");
        ovm.setItemId(1);
        ovm.setQuantity(1);

        service.addInvoice(ovm);

        //example complete and calculated Invoice to be returned
        Invoice i = new Invoice();
        i.setName(ovm.getName());
        i.setStreet(ovm.getStreet());
        i.setCity(ovm.getCity());
        i.setState(ovm.getState());
        i.setZipcode(ovm.getZipcode());
        i.setItemType(ovm.getItemType());
        i.setItemId(ovm.getItemId());
        i.setUnitPrice(new BigDecimal("50.00"));
        i.setQuantity(ovm.getQuantity());
        i.setSubtotal(new BigDecimal("50.00"));
        i.setTax(new BigDecimal("3.50"));
        i.setProcessingFee(new BigDecimal("14.99"));
        i.setTotal(new BigDecimal("68.49"));

        //adding invoice
        i = invoiceDao.addInvoice(i);

        List<InvoiceViewModel> iList = service.getAllInvoices();
        assertEquals(1, iList.size());
        assertEquals(i, iList.get(0));
    }

    // ✷ ##################################

    @Test
    public void addGetConsole() {
        Console console = new Console();
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("A lot");
        console.setProcessor("AMD");
        console.setPrice(new BigDecimal("50.00"));
        console.setQuantity(1);
        console = service.addConsole(console);
        Console fromService = service.getConsole(console.getConsoleId());
        assertEquals(console, fromService);

    }

    @Test
    public void getAllConsoles() {
        Console console = new Console();
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("A lot");
        console.setProcessor("AMD");
        console.setPrice(new BigDecimal("50.00"));
        console.setQuantity(1);
        console = service.addConsole(console);
        List<Console> cList = service.getAllConsoles();
        assertEquals(1, cList.size());
        assertEquals(console, cList.get(0));
    }

    @Test
    public void getConsoleByManufacturer() {
        Console console = new Console();
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("A lot");
        console.setProcessor("AMD");
        console.setPrice(new BigDecimal("50.00"));
        console.setQuantity(1);
        console = service.addConsole(console);
        List<Console> cList = service.getConsoleByManufacturer("Sony");
        assertEquals(1, cList.size());
        assertEquals(console, cList.get(0));
    }

    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("A lot");
        console.setProcessor("AMD");
        console.setPrice(new BigDecimal("50.00"));
        console.setQuantity(1);
        console = service.addConsole(console);

        ArgumentCaptor<Console> consoleCaptor = ArgumentCaptor.forClass(Console.class);
        doNothing().when(consoleDao).updateConsole(consoleCaptor.capture());
        service.updateConsole(console);
        verify(consoleDao, times(1)).updateConsole(consoleCaptor.getValue());
        Console console2 = consoleCaptor.getValue();
        assertEquals(console, console2);
    }

    @Test
    public void deleteConsole() {
        Console console = new Console();
        console.setConsoleId(1);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(consoleDao).deleteConsole(integerCaptor.capture());
        service.deleteConsole(1);
        verify(consoleDao, times(1)).deleteConsole(integerCaptor.getValue());
        assertEquals(1, integerCaptor.getValue().intValue());

    }

    // ✷ ##################################

    @Test
    public void addGetGame() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);
        Game fromService = service.getGame(game.getGameId());
        assertEquals(game, fromService);
    }

    @Test
    public void getAllGames() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);
        List<Game> gList = service.getAllGames();
        assertEquals(1, gList.size());
        assertEquals(game, gList.get(0));
    }

    @Test
    public void getGamesByStudio() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);
        List<Game> gList = service.getGamesByStudio("Guerrilla Games");
        assertEquals(1, gList.size());
        assertEquals(game, gList.get(0));
    }

    @Test
    public void getGamesByEsrb() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);
        List<Game> gList = service.getGamesByEsrb("T");
        assertEquals(1, gList.size());
        assertEquals(game, gList.get(0));
    }

    @Test
    public void getGamesByTitle() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);
        List<Game> gList = service.getGamesByTitle("Horizon Zero Dawn");
        assertEquals(1, gList.size());
        assertEquals(game, gList.get(0));
    }

    @Test
    public void updateGame() {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game = service.addGame(game);

        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        doNothing().when(gameDao).updateGame(gameCaptor.capture());
        service.updateGame(game);
        verify(gameDao, times(1)).updateGame(gameCaptor.getValue());
        Game game2 = gameCaptor.getValue();
        assertEquals(game, game2);
    }

    @Test
    public void deleteGame() {
        Game game = new Game();
        game.setGameId(1);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(gameDao).deleteGame(integerCaptor.capture());
        service.deleteGame(1);
        verify(gameDao, times(1)).deleteGame(integerCaptor.getValue());
        assertEquals(1, integerCaptor.getValue().intValue());
    }

    // ✷ ##################################

    @Test
    public void addGetShirt() {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt = service.addShirt(shirt);
        TShirt fromService = service.getShirt(shirt.getTshirtId());
        assertEquals(shirt, fromService);
    }

    @Test
    public void getAllShirts() {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt = service.addShirt(shirt);
        List<TShirt> sList = service.getAllShirts();
        assertEquals(1, sList.size());
        assertEquals(shirt, sList.get(0));
    }

    @Test
    public void getShirtByColor() {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt = service.addShirt(shirt);
        List<TShirt> sList = service.getShirtByColor("Blue");
        assertEquals(1, sList.size());
        assertEquals(shirt, sList.get(0));
    }

    @Test
    public void getShirtBySize() {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt = service.addShirt(shirt);
        List<TShirt> sList = service.getShirtBySize("S");
        assertEquals(1, sList.size());
        assertEquals(shirt, sList.get(0));
    }

    @Test
    public void updateShirt() {
        TShirt shirt = new TShirt();
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);
        shirt = service.addShirt(shirt);

        ArgumentCaptor<TShirt> shirtCaptor = ArgumentCaptor.forClass(TShirt.class);
        doNothing().when(tShirtDao).updateShirt(shirtCaptor.capture());
        service.updateShirt(shirt);
        verify(tShirtDao, times(1)).updateShirt(shirtCaptor.getValue());
        TShirt shirt2 = shirtCaptor.getValue();
        assertEquals(shirt, shirt2);
    }

    @Test
    public void deleteShirt() {
        TShirt shirt = new TShirt();
        shirt.setTshirtId(1);
        ArgumentCaptor<Integer> integerCaptor =  ArgumentCaptor.forClass(Integer.class);
        doNothing().when(tShirtDao).deleteShirt(integerCaptor.capture());
        service.deleteShirt(1);
        verify(tShirtDao, times(1)).deleteShirt(integerCaptor.getValue());
        assertEquals(1, integerCaptor.getValue().intValue());
    }

    // HELPER METHODS
    private void setUpInvoiceDaoMock() {
        // What's the point of this line?
//        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(1);
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

        Invoice invoice2 = new Invoice();
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

        List<Invoice> iList = new ArrayList<>();
        iList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice2);
        doReturn(invoice).when(invoiceDao).getInvoice(1);
        doReturn(iList).when(invoiceDao).getAllInvoices();

    }

    private void setUpConsoleDaoMock() {

//        consoleDao = mock(ConsoleDaoJdbcTemplateImpl.class);
        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("A lot");
        console.setProcessor("AMD");
        console.setPrice(new BigDecimal("50.00"));
        console.setQuantity(1);

        Console console2 = new Console();
        console2.setModel("PS4");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("A lot");
        console2.setProcessor("AMD");
        console2.setPrice(new BigDecimal("50.00"));
        console2.setQuantity(1);

        List<Console> cList = new ArrayList<>();
        cList.add(console);

        doReturn(console).when(consoleDao).addConsole(console2);
        doReturn(console).when(consoleDao).getConsole(1);
        doReturn(cList).when(consoleDao).getAllConsoles();
        doReturn(cList).when(consoleDao).getConsoleByManufacturer("Sony");

    }

    public void setUpGameDaoMock() {

//        gameDao = mock(GameDaoJdbcTemplateImpl.class);
        Game game = new Game();
        game.setGameId(1);
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);

        Game game2 = new Game();
        game2.setTitle("Horizon Zero Dawn");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse");
        game2.setPrice(new BigDecimal("20.65"));
        game2.setStudio("Guerrilla Games");
        game2.setQuantity(1);

        List<Game> gList = new ArrayList<>();
        gList.add(game);

        doReturn(game).when(gameDao).addGame(game2);
        doReturn(game).when(gameDao).getGame(1);
        doReturn(gList).when(gameDao).getAllGames();
        doReturn(gList).when(gameDao).getGamesByTitle("Horizon Zero Dawn");
        doReturn(gList).when(gameDao).getGamesByEsrb("T");
        doReturn(gList).when(gameDao).getGamesByStudio("Guerrilla Games");

    }

    public void setUpTShirtDaoMock() {

//        tShirtDao = mock(TShirtDaoJdbcTemplateImpl.class);
        TShirt shirt = new TShirt();
        shirt.setTshirtId(1);
        shirt.setSize("S");
        shirt.setColor("Blue");
        shirt.setDescription("Small Blue Shirt");
        shirt.setPrice(new BigDecimal("12.00"));
        shirt.setQuantity(1);

        TShirt shirt2 = new TShirt();
        shirt2.setSize("S");
        shirt2.setColor("Blue");
        shirt2.setDescription("Small Blue Shirt");
        shirt2.setPrice(new BigDecimal("12.00"));
        shirt2.setQuantity(1);

        List<TShirt> sList = new ArrayList<>();
        sList.add(shirt);

        doReturn(shirt).when(tShirtDao).addShirt(shirt2);
        doReturn(shirt).when(tShirtDao).getShirt(1);
        doReturn(sList).when(tShirtDao).getAllShirts();
        doReturn(sList).when(tShirtDao).getShirtBySize("S");
        doReturn(sList).when(tShirtDao).getShirtByColor("Blue");


    }

    public void setUpSalesTaxRateDaoMock() {
//        salesTaxRateDao = mock(SalesTaxRateDaoJdbcTemplateImpl.class);
        SalesTaxRate str = new SalesTaxRate();
        str.setState("GA");
        str.setRate(new BigDecimal("0.07"));

        SalesTaxRate str2 = new SalesTaxRate();
        str2.setState("GA");
        str2.setRate(new BigDecimal("0.07"));

        List<SalesTaxRate> strList = new ArrayList<>();
        strList.add(str);

//        doReturn(str).when(salesTaxRateDao).addSTR(str);
        doReturn(str).when(salesTaxRateDao).getSTR("GA");
        doReturn(strList).when(salesTaxRateDao).getAllSTR();

    }

    public void setUpProcessingFeeDaoMock() {
//        processingFeeDao = mock(ProcessingFeeDaoJdbcTemplateImpl.class);
        ProcessingFee pf = new ProcessingFee();
        pf.setProductType("Consoles");
        pf.setFee(new BigDecimal("14.99"));

        ProcessingFee pf2 = new ProcessingFee();
        pf2.setProductType("Consoles");
        pf2.setFee(new BigDecimal("14.99"));

        List<ProcessingFee> pfList = new ArrayList<>();
        pfList.add(pf);

//        doReturn(pf).when(processingFeeDao).addProcessingFee(pf2);
        doReturn(pf).when(processingFeeDao).getProcessingFee("Consoles");
        doReturn(pfList).when(processingFeeDao).getAllProcessingFees();
    }


}