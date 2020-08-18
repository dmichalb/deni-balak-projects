/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dto;

import dmb.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dmich
 */
public class Order {
    
    private int orderNum;
    
    private String customerName;
    private String state;
    private String productType;
    private BigDecimal area;
    private LocalDate date;
    
    private BigDecimal taxRate;
    private BigDecimal costSqFt;
    private BigDecimal laborCostSqFt;
    
    private BigDecimal totalMaterialCost;
    private BigDecimal totalLaborCost;
    private BigDecimal tax;
    private BigDecimal total;
        
    

    public Order(int orderNum) {
        this.orderNum = orderNum;
    }
    
    public Order(LocalDate date) {
        this.date = date;
    }
    
    public Order() {
        
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    
    public LocalDate getDate() {

        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getCostSqFt() {
        return costSqFt;
    }

    public void setCostSqFt(BigDecimal costSqFt) {
        this.costSqFt = costSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
       this.totalLaborCost = totalLaborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

   
}
