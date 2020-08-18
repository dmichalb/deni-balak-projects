/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dmich
 */
public interface FlooringDao {
    
    List<Order> getOrderByDate(LocalDate date) throws FlooringPersistenceException;
    
    Order addOrder(int orderNum, Order order) throws FlooringPersistenceException;
    
    
    Order removeOrder(int orderNum) throws FlooringPersistenceException;
    
    Order getOrder(int orderNum) throws FlooringPersistenceException;
    
    List<Order> getAllOrders() throws FlooringPersistenceException;
    
    //Properties findProperty();
    
    void loadOrderData() throws FlooringPersistenceException;
    
    void writeOrderData() throws FlooringPersistenceException;
   
    
}
