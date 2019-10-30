package com.company.GameStore.viewmodel;

import com.company.GameStore.dto.Invoice;

import java.math.BigDecimal;

public class InvoiceViewModel extends Invoice {

    public InvoiceViewModel() {
        super();
    }

    public InvoiceViewModel(int invoiceId, String name, String street, String city, String state, String zipcode, String itemType, int itemId, BigDecimal unitPrice, int quantity, BigDecimal subtotal, BigDecimal tax, BigDecimal processingFee, BigDecimal total) {
        super(invoiceId, name, street, city, state, zipcode, itemType, itemId, unitPrice, quantity, subtotal, tax, processingFee, total);
    }

    @Override
    public int getInvoiceId() {
        return super.getInvoiceId();
    }

    @Override
    public void setInvoiceId(int invoiceId) {
        super.setInvoiceId(invoiceId);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getStreet() {
        return super.getStreet();
    }

    @Override
    public void setStreet(String street) {
        super.setStreet(street);
    }

    @Override
    public String getCity() {
        return super.getCity();
    }

    @Override
    public void setCity(String city) {
        super.setCity(city);
    }

    @Override
    public String getState() {
        return super.getState();
    }

    @Override
    public void setState(String state) {
        super.setState(state);
    }

    @Override
    public String getZipcode() {
        return super.getZipcode();
    }

    @Override
    public void setZipcode(String zipcode) {
        super.setZipcode(zipcode);
    }

    @Override
    public String getItemType() {
        return super.getItemType();
    }

    @Override
    public void setItemType(String itemType) {
        super.setItemType(itemType);
    }

    @Override
    public int getItemId() {
        return super.getItemId();
    }

    @Override
    public void setItemId(int itemId) {
        super.setItemId(itemId);
    }

    @Override
    public BigDecimal getUnitPrice() {
        return super.getUnitPrice();
    }

    @Override
    public void setUnitPrice(BigDecimal unitPrice) {
        super.setUnitPrice(unitPrice);
    }

    @Override
    public int getQuantity() {
        return super.getQuantity();
    }

    @Override
    public void setQuantity(int quantity) {
        super.setQuantity(quantity);
    }

    @Override
    public BigDecimal getSubtotal() {
        return super.getSubtotal();
    }

    @Override
    public void setSubtotal(BigDecimal subtotal) {
        super.setSubtotal(subtotal);
    }

    @Override
    public BigDecimal getTax() {
        return super.getTax();
    }

    @Override
    public void setTax(BigDecimal tax) {
        super.setTax(tax);
    }

    @Override
    public BigDecimal getProcessingFee() {
        return super.getProcessingFee();
    }

    @Override
    public void setProcessingFee(BigDecimal processingFee) {
        super.setProcessingFee(processingFee);
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal();
    }

    @Override
    public void setTotal(BigDecimal total) {
        super.setTotal(total);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
