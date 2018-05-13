package model;

import java.time.LocalDate;

public class BloodProduct {
    private Integer idBP;
    private Integer idD;
    private ProductType productType;
    private LocalDate validUntil;
    private Double quantity;

    public BloodProduct(Integer idBP, Integer idD, ProductType productType, LocalDate validUntil, Double quantity) {
        this.idBP = idBP;
        this.idD = idD;
        this.productType = productType;
        this.validUntil = validUntil;
        this.quantity = quantity;
    }

    public Integer getIdBP() {
        return idBP;
    }

    public void setIdBP(Integer idBP) {
        this.idBP = idBP;
    }

    public Integer getIdD() {
        return idD;
    }

    public void setIdD(Integer idD) {
        this.idD = idD;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
