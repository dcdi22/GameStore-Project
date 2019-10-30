package com.company.GameStore.service;

import com.company.GameStore.dao.*;
import com.company.GameStore.dto.Console;
import com.company.GameStore.dto.Game;
import com.company.GameStore.dto.Invoice;
import com.company.GameStore.dto.TShirt;
import com.company.GameStore.viewmodel.InvoiceViewModel;
import com.company.GameStore.viewmodel.OrderViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    //@Autowired doesn't actually create an instance of a class
    //@Bean is used to create class
    //@Qualifier is used to retrieve it??

    private ConsoleDao consoleDao;
    private GameDao gameDao;
    private TShirtDao tShirtDao;
    private InvoiceDao invoiceDao;
    private ProcessingFeeDao processingFeeDao;
    private SalesTaxRateDao salesTaxRateDao;

    // constructor mainly used for serviceLayer testing for injecting mocks
    // if did not use @injectMock and @Mock annotations
    // otherwise not needed???
    @Autowired
    public ServiceLayer(ConsoleDao consoleDao, GameDao gameDao, TShirtDao tShirtDao, InvoiceDao invoiceDao, ProcessingFeeDao processingFeeDao, SalesTaxRateDao salesTaxRateDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.tShirtDao = tShirtDao;
        this.invoiceDao = invoiceDao;
        this.processingFeeDao = processingFeeDao;
        this.salesTaxRateDao = salesTaxRateDao;
    }


    //
    // Invoice Api
    //

    @Transactional
    public Invoice addInvoice(OrderViewModel orderViewModel) {

        // Persist Invoice
        Invoice i = new Invoice();
//        InvoiceViewModel i = new InvoiceViewModel();
        i.setName(orderViewModel.getName());
        i.setStreet(orderViewModel.getStreet());
        i.setCity(orderViewModel.getCity());
        i.setState(orderViewModel.getState());
        i.setZipcode(orderViewModel.getZipcode());
        i.setItemType(orderViewModel.getItemType());
        i.setItemId(orderViewModel.getItemId());
        i.setQuantity(orderViewModel.getQuantity());

        // GET UNIT PRICE
        switch(orderViewModel.getItemType()) {
            case "Games":
                if (gameDao.getAllGames().contains(gameDao.getGame(orderViewModel.getItemId()))) {
                    i.setUnitPrice(gameDao.getGame(orderViewModel.getItemId()).getPrice());

                    // Grab Quantities
                    int dbQuantity = gameDao.getGame(orderViewModel.getItemId()).getQuantity();
                    int orderQuantity = orderViewModel.getQuantity();

                    if (orderQuantity <= dbQuantity) {
                        Game gameUpdated = gameDao.getGame(orderViewModel.getItemId());
                        int newQuantity = dbQuantity - orderQuantity;
                        gameUpdated.setQuantity(newQuantity);
                        gameDao.updateGame(gameUpdated);
                    } else {
                        throw new IllegalArgumentException("There's not enough stock in our inventory to match the quantity you asked for");
                    }
                }
                break;
            case "Consoles":
                if (consoleDao.getAllConsoles().contains(consoleDao.getConsole(orderViewModel.getItemId()))) {
                    i.setUnitPrice(consoleDao.getConsole(orderViewModel.getItemId()).getPrice());


                    // Grab Quantities
                    int dbQuantity = consoleDao.getConsole(orderViewModel.getItemId()).getQuantity();
                    int orderQuantity = orderViewModel.getQuantity();

                    if (orderQuantity <= dbQuantity) {
                        Console consoleUpdated = consoleDao.getConsole(orderViewModel.getItemId());
                        int newQuantity = dbQuantity - orderQuantity;
                        consoleUpdated.setQuantity(newQuantity);
                        consoleDao.updateConsole(consoleUpdated);
                    } else {
                        throw new IllegalArgumentException("There's not enough stock in our inventory to match the quantity you asked for");
                    }
                }
                break;
            case "T-Shirts":
                if (tShirtDao.getAllShirts().contains(tShirtDao.getShirt(orderViewModel.getItemId()))) {
                    i.setUnitPrice(tShirtDao.getShirt(orderViewModel.getItemId()).getPrice());


                    // Grab Quantities
                    int dbQuantity = tShirtDao.getShirt(orderViewModel.getItemId()).getQuantity();
                    int orderQuantity = orderViewModel.getQuantity();

                    if (orderQuantity <= dbQuantity) {
                        TShirt shirtUpdated = tShirtDao.getShirt(orderViewModel.getItemId());
                        int newQuantity = dbQuantity - orderQuantity;
                        shirtUpdated.setQuantity(newQuantity);
                        tShirtDao.updateShirt(shirtUpdated);
                    } else {
                        throw new IllegalArgumentException("There's not enough stock in our inventory to match the quantity you asked for");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Not a valid Item Type");
//                System.out.println("Not a valid Item Type");
        }

        // GET SUBTOTAL
        BigDecimal myPrice = i.getUnitPrice();
        BigDecimal myQuantity = new BigDecimal(i.getQuantity());
        BigDecimal mySubTotal = myPrice.multiply(myQuantity);

        i.setSubtotal(mySubTotal);

        // GET TAX
        if (salesTaxRateDao.getAllSTR().contains(salesTaxRateDao.getSTR(orderViewModel.getState()))) {
            BigDecimal myRate = salesTaxRateDao.getSTR(orderViewModel.getState()).getRate();
            BigDecimal myTax = mySubTotal.multiply(myRate);

            i.setTax(myTax);
        } else {
            throw new IllegalArgumentException("No state matching your input");
        }

        // GET PROCESSING FEE
        if (processingFeeDao.getAllProcessingFees().contains(processingFeeDao.getProcessingFee(orderViewModel.getItemType()))) {
            if (orderViewModel.getQuantity() > 10) {
                BigDecimal ogFee = processingFeeDao.getProcessingFee(orderViewModel.getItemType()).getFee();
                BigDecimal myFee = ogFee.add(new BigDecimal("15.49"));

                i.setProcessingFee(myFee);
            } else {
                BigDecimal ogFee = processingFeeDao.getProcessingFee(orderViewModel.getItemType()).getFee();

                i.setProcessingFee(ogFee);
            }
        }

        // GET TOTAL
        BigDecimal myTotal = mySubTotal.add(i.getTax()).add(i.getProcessingFee());

        i.setTotal(myTotal);

        i = invoiceDao.addInvoice(i);
        return i;


    }

    public InvoiceViewModel getInvoice(int invoiceId) {

        // Get the invoice object first
        Invoice invoice = invoiceDao.getInvoice(invoiceId);

        // Return Build
        return buildInvoiceViewModel(invoice);

    }

    public List<InvoiceViewModel> getAllInvoices() {

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for(Invoice invoice: invoiceList) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            ivmList.add(ivm);
        }

        return ivmList;

    }


    // Come back and add update and delete if you think you need it

    //
    // Console Api
    //

    public Console addConsole(Console console) {
        return consoleDao.addConsole(console);
    }

    public List<Console> getAllConsoles() {
        return consoleDao.getAllConsoles();
    }

    public List<Console> getConsoleByManufacturer(String manu) {
        return consoleDao.getConsoleByManufacturer(manu);
    }

    public Console getConsole(int consoleId) {
        return consoleDao.getConsole(consoleId);
    }

    public void updateConsole(Console console) {
        consoleDao.updateConsole(console);
    }

    public void deleteConsole(int consoleId) {
        consoleDao.deleteConsole(consoleId);
    }

    //
    // Game Api
    //

    public Game addGame(Game game) {
        return gameDao.addGame(game);
    }

    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    public List<Game> getGamesByStudio(String studio) {
        return gameDao.getGamesByStudio(studio);
    }

    public List<Game> getGamesByEsrb(String esrbRating) {
        return gameDao.getGamesByEsrb(esrbRating);
    }

    public List<Game> getGamesByTitle(String title) {
        return gameDao.getGamesByTitle(title);
    }

    public Game getGame(int gameId) {
        return gameDao.getGame(gameId);
    }

    public void updateGame(Game game) {
        gameDao.updateGame(game);
    }

    public void deleteGame(int gameId) {
        gameDao.deleteGame(gameId);
    }

    //
    // Tshirt Api
    //

    public TShirt addShirt(TShirt shirt) {
        return tShirtDao.addShirt(shirt);
    }

    public List<TShirt> getAllShirts() {
        return tShirtDao.getAllShirts();
    }

    public List<TShirt> getShirtByColor(String color) {
        return tShirtDao.getShirtByColor(color);
    }

    public List<TShirt> getShirtBySize(String size) {
        return tShirtDao.getShirtBySize(size);
    }

    public TShirt getShirt(int shirtId) {
        return tShirtDao.getShirt(shirtId);
    }

    public void updateShirt(TShirt shirt) {
        tShirtDao.updateShirt(shirt);
    }

    public void deleteShirt(int shirtId) {
        tShirtDao.deleteShirt(shirtId);
    }


    // HELPER METHODS
    // The build is like our rowMapper
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        // Assemble the InvoiceViewModel
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setName(invoice.getName());
        ivm.setStreet(invoice.getStreet());
        ivm.setCity(invoice.getCity());
        ivm.setState(invoice.getState());
        ivm.setZipcode(invoice.getZipcode());
        ivm.setItemType(invoice.getItemType());
        ivm.setItemId(invoice.getItemId());
        ivm.setUnitPrice(invoice.getUnitPrice());
        ivm.setQuantity(invoice.getQuantity());
        ivm.setSubtotal(invoice.getSubtotal());
        ivm.setTax(invoice.getTax());
        ivm.setProcessingFee(invoice.getProcessingFee());
        ivm.setTotal(invoice.getTotal());

        // return InvoiceViewModel
        return ivm;

    }


}
