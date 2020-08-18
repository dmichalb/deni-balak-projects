/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.service;

import dmb.flooring.dao.FlooringPersistenceException;
import dmb.flooring.dto.Order;
import dmb.flooring.dto.OrderInput;
import dmb.flooring.dto.ProductType;
import dmb.flooring.dto.Taxes;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author dmich
 */
public interface FlooringServiceLayer {
    List<Order> getOrderByDate(LocalDate date) throws FlooringPersistenceException;
    
    void createOrder(OrderInput order, Properties prop) throws FlooringPersistenceException, DataValidationException;
    
    Order editOrder(int orderNum, OrderInput orderInput) throws FlooringPersistenceException;
    
    Order removeOrder(int orderNum) throws FlooringPersistenceException;
    
    Order getOrder(int orderNum) throws FlooringPersistenceException;
    
    ProductType getProduct(String productType);
    
    Taxes getTaxes(String state);
    
    void writeOrderNumProperty(Properties prop);
    
    void loadOrderNumProperty(Properties prop);
    
    List<String> findTaxMapKey();
    
    List<String> findProdMapKey();
    
    void loadData() throws FlooringPersistenceException;
    
    Properties findProperty();
    
    void writeData() throws FlooringPersistenceException;
}
