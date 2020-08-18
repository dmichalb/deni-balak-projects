/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author dmich
 */
public class FlooringDaoStubImpl implements FlooringDao{
    
    Order onlyOrder = new Order();
    List<Order> orderList = new ArrayList<>();
    
    public FlooringDaoStubImpl() {
        onlyOrder.setOrderNum(1);
        onlyOrder.setCustomerName("Michael Scott");
        onlyOrder.setState("PA");
        onlyOrder.setTax(onlyOrder.getTaxRate());
        onlyOrder.setProductType("Wood");
        
        BigDecimal area = new BigDecimal(100.00);
        onlyOrder.setArea(area);
        onlyOrder.setCostSqFt(onlyOrder.getCostSqFt());
        onlyOrder.setLaborCostSqFt(onlyOrder.getLaborCostSqFt());
        onlyOrder.setTotalMaterialCost(onlyOrder.getTotalMaterialCost());
        onlyOrder.setTotalLaborCost(onlyOrder.getTotalLaborCost());
        onlyOrder.setTax(onlyOrder.getTax());
        onlyOrder.setTotal(onlyOrder.getTotal());
        
        String date = "2020-02-28";
        onlyOrder.setDate(LocalDate.parse(date));
        
        orderList.add(onlyOrder);
    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) throws FlooringPersistenceException {
        return new ArrayList<Order>(orderList
                .stream()
                .filter(o -> o.getDate().isEqual(date))
                .collect(Collectors.toList()));
    }

    @Override
    public Order addOrder(int orderNum, Order order) throws FlooringPersistenceException {
         orderList.add(order);
            return order;
    }

    @Override
    public Order removeOrder(int orderNum) throws FlooringPersistenceException {
        for (Order order : orderList) {
            if (orderNum == order.getOrderNum()) {
                orderList.remove(orderList.indexOf(order));
                return order;
            }
        } 
        return null;
    }

    @Override
    public Order getOrder(int orderNum) throws FlooringPersistenceException {
        for (Order order : orderList) {
            if (orderNum == order.getOrderNum()) {
                return order;
            }
        } 
        return null;
    }

    @Override
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        return orderList;
    }
    
    //Not testing these
    @Override
    public void loadOrderData() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeOrderData() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
