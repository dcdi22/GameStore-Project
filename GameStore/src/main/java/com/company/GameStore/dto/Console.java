package com.company.GameStore.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class Console {

    /*
    console_id int(11) not null auto_increment primary key,
    model varchar(50) not null,
    manufacturer varchar(50) not null,
    memory_amount varchar(20),
    processor varchar(20),
    price decimal(5, 2) not null,
    quantity int(11) not null
     */

//    @Size(max = 11)
    private int consoleId;
    @Size(min = 1, max = 50)
    private String model;
    @Size(min = 1, max = 50)
    private String manufacturer;
    @Size(max = 20)
    private String memoryAmount;
    @Size(max = 20)
    private String processor;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
//    @Size(max = 11)
    private int quantity;

    public Console() { }

    public Console(int consoleId, String model, String manufacturer, String memoryAmount, String processor, BigDecimal price, int quantity) {
        this.consoleId = consoleId;
        this.model = model;
        this.manufacturer = manufacturer;
        this.memoryAmount = memoryAmount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemoryAmount() {
        return memoryAmount;
    }

    public void setMemoryAmount(String memoryAmount) {
        this.memoryAmount = memoryAmount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return consoleId == console.consoleId &&
                quantity == console.quantity &&
                model.equals(console.model) &&
                manufacturer.equals(console.manufacturer) &&
                Objects.equals(memoryAmount, console.memoryAmount) &&
                Objects.equals(processor, console.processor) &&
                price.equals(console.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consoleId, model, manufacturer, memoryAmount, processor, price, quantity);
    }
}
