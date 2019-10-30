package com.company.GameStore.dao;

import com.company.GameStore.dto.SalesTaxRate;

import java.util.List;

public interface SalesTaxRateDao {

    /*
    get
    get all
    add
    update
    delete
     */

    SalesTaxRate getSTR(String state);

    List<SalesTaxRate> getAllSTR();

    SalesTaxRate addSTR(SalesTaxRate str);

    void updateSTR(SalesTaxRate str);

    void deleteSTR(String state);

}
