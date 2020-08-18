/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.service;

import dmb.flooring.dao.FlooringDao;
import dmb.flooring.dao.FlooringDaoFileImpl;
import dmb.flooring.dao.FlooringPersistenceException;
import dmb.flooring.dao.ProductTypeDao;
import dmb.flooring.dao.TaxesDao;
import dmb.flooring.dto.Order;
import dmb.flooring.dto.OrderInput;
import dmb.flooring.dto.ProductType;
import dmb.flooring.dto.Taxes;
import dmb.flooring.ui.FlooringView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author dmich
 */
public class FlooringServiceLayerImpl implements FlooringServiceLayer{
    FlooringView view;
    FlooringDao dao = new FlooringDaoFileImpl();
    ProductTypeDao prodDao;
    TaxesDao taxDao;
    
    int orderNumProp;
    Map<String, Taxes> taxRates = new HashMap<>();
    
    public FlooringServiceLayerImpl(FlooringDao dao, ProductTypeDao prodDao, TaxesDao taxDao, FlooringView view) {
        this.dao = dao;
        this.prodDao = prodDao;
        this.taxDao = taxDao;
        this.view = view;
    }
    public FlooringServiceLayerImpl() {
    }
    
    @Override
    public List<Order> getOrderByDate(LocalDate date) throws FlooringPersistenceException {
        return dao.getOrderByDate(date);
    }

    @Override
    public void createOrder(OrderInput orderInput, Properties prop) throws FlooringPersistenceException, DataValidationException{
        int sureAdd = view.areYouSureAdd();
        Order order = new Order();
        order.setOrderNum(useThisOrderNum(prop));
        setOrderInfo(order, orderInput);
            
        if (sureAdd == 1) {
            dao.addOrder(order.getOrderNum(), order);
        } else {
            System.out.print("Your information will be discarded.");
        } 
    }

    @Override
    public Order editOrder(int orderNum, OrderInput orderInput) throws FlooringPersistenceException {
        Order editedOrder = setOrderInfo(dao.getOrder(orderNum), orderInput);
        return editedOrder;
        
    }

    @Override
    public Order removeOrder(int orderNum) throws FlooringPersistenceException {
        Order removedOrder = dao.removeOrder(orderNum);
        return removedOrder;
    }

    @Override
    public Order getOrder(int orderNum) throws FlooringPersistenceException {
        return dao.getOrder(orderNum);
    }

    @Override
    public ProductType getProduct(String productType) {
        return prodDao.getProduct(productType);
    }

    @Override
    public Taxes getTaxes(String state) {
        return taxDao.getTaxes(state);
    }
    
    private void validateOrderData(Order order) throws DataValidationException {
        if (order.getCustomerName() == null || order.getCustomerName().trim().length() == 0
            || order.getState() == null || order.getState().trim().length() == 0
            || order.getProductType() == null || order.getProductType().trim().length() == 0
            || order.getArea() == null || order.getArea().toString().trim().length() == 0
            || order.getDate() == null || order.getDate().toString().trim().length() == 0) {
         throw new DataValidationException(
                 "ERROR: All fields [Customer Name, State, Product Type, Area, Date] are required.");
        }
    }
    @Override
    public void loadOrderNumProperty(Properties prop) {
        
        try (InputStream input = new FileInputStream("flooring.properties")) {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeOrderNumProperty(Properties prop) {
        Order order = new Order(orderNumProp);
        
        String stringOrderNum = String.valueOf(orderNumProp);
        
        try (OutputStream output = new FileOutputStream("flooring.properties")) {
            prop.setProperty("OrderNum", stringOrderNum);
            
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    private int useThisOrderNum(Properties prop) {
        
        String stringOrderNumProp = prop.getProperty("OrderNum");
        orderNumProp = Integer.parseInt(stringOrderNumProp);
        int nextOrderNum = ++orderNumProp;
            return nextOrderNum;
        }
    
    private BigDecimal calcTotalLaborCost(Order order) {
        return order.getLaborCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
    }
    
    private BigDecimal calcTotalMaterialCost(Order order) {
        return order.getCostSqFt().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
    }
 
    private BigDecimal findTaxRate(String key) {
        return taxDao.getTaxRates()
                .get(key)
                .getTaxRate();
    }
    
    private BigDecimal findLaborPerSqFt(String key) {
        return prodDao.getProductTypeInfo()
                .get(key)
                .getLaborCostSqFt();
    }
    
    private BigDecimal findMaterialPerSqFt(String key) {
        return prodDao.getProductTypeInfo()
                .get(key)
                .getCostSqFt();
    }
    @Override
    public List<String> findTaxMapKey() {
        Set<String> keys = taxDao.getTaxRates().keySet();
        return helper(keys);
    }
    @Override
    public List<String> findProdMapKey() {
        Set<String> keys = prodDao.getProductTypeInfo().keySet();
        return helper(keys);
    }
 
    private List<String> helper(Set<String> keys) {
        List<String> keyList = new ArrayList<>();
        keys.forEach((k) -> {
            keyList.add(k);
        });
        return keyList;
    }
    
    private BigDecimal calcTax(Order order) {
        return (order.getTotalLaborCost().add(order.getTotalMaterialCost())).multiply(order.getTaxRate()).setScale(2, RoundingMode.HALF_UP);
    }
    
    private BigDecimal calcTotal(Order order) {
        return (order.getTotalLaborCost().add(order.getTotalMaterialCost())).subtract(order.getTax());
    }
    
    public Order setOrderInfo(Order order, OrderInput input) {
        
        order.setCustomerName(input.getCustomerName());
        order.setState(input.getState());
        order.setTaxRate(findTaxRate(input.getState()));
        order.setProductType(input.getProductType());
        order.setArea(input.getArea());
        order.setDate(input.getDate());
        order.setCostSqFt(findMaterialPerSqFt(input.getProductType()));
        order.setLaborCostSqFt(findLaborPerSqFt(input.getProductType()));
        order.setTotalMaterialCost(calcTotalMaterialCost(order));
        order.setTotalLaborCost(calcTotalLaborCost(order));
        order.setTax(calcTax(order));
        order.setTotal(calcTotal(order));
        
        return order;
    }
    
    public void loadData() throws FlooringPersistenceException {
        taxDao.loadTaxData();
        prodDao.loadProductData();
        dao.loadOrderData();
        
    }
    
    public void writeData() throws FlooringPersistenceException {
        dao.writeOrderData();
    }
    
    public Properties findProperty() {
        Properties prop = new Properties();
        
        try (FileInputStream input = new FileInputStream("flooring.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
