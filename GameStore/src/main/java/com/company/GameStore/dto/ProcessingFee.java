package com.company.GameStore.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class ProcessingFee {

    /*
    product_type varchar(20) not null,
    fee decimal (4,2)
     */

    @Size(min = 1, max = 20)
    private String productType;
    @Digits(integer = 4, fraction = 2)
    private BigDecimal fee;

    public ProcessingFee() {
    }

    public ProcessingFee(String productType, BigDecimal fee) {
        this.productType = productType;
        this.fee = fee;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingFee that = (ProcessingFee) o;
        return productType.equals(that.productType) &&
                Objects.equals(fee, that.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, fee);
    }
}
