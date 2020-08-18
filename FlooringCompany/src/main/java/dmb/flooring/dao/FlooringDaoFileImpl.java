/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Order;
import dmb.flooring.dto.Taxes;
import dmb.flooring.dto.ProductType;
import dmb.flooring.ui.FlooringView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author dmich
 */
public class FlooringDaoFileImpl implements FlooringDao {
    FlooringView view;
    FlooringDao dao;
    FlooringDaoStubImpl stubImpl;
    
    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Taxes> taxes = new HashMap<>();
    private Map<String, ProductType> product = new HashMap<>();
    
    
    public static final String ORDER_FILE = "order.txt";
    public static final String DELIMITER = "::";
    
    public FlooringDaoFileImpl() {
    }
    public FlooringDaoFileImpl(FlooringDao dao, FlooringDaoStubImpl stubImpl) {
        this.dao = dao;
        this.stubImpl = stubImpl;
    }
    
    public Map<Integer, Order> getOrderList() {
        return orders;
    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) throws FlooringPersistenceException{
        return new ArrayList<Order>(orders.values()
                .stream()
                .filter(o -> o.getDate().isEqual(date))
                .collect(Collectors.toList()));
    }

    @Override
    public Order addOrder(int orderNum, Order order) throws FlooringPersistenceException{
        Order newOrder = orders.put(orderNum, order);
        return newOrder;
    }

    

    @Override
    public Order removeOrder(int orderNum)throws FlooringPersistenceException{
        Order removedOrder = orders.remove(orderNum);
        return removedOrder;
    }

    @Override
    public Order getOrder(int orderNum) throws FlooringPersistenceException{
        Order order = orders.get(orderNum);
        return order;
    }
    
    private Order unmarshallOrder(String orderAsText){
        String[] orderTokens = orderAsText.split(DELIMITER);
        int currentOrderNum = Integer.parseInt(orderTokens[0]);
        
        Order orderFromFile = new Order(currentOrderNum);
        
        orderFromFile.setCustomerName(orderTokens[1]);
        
        orderFromFile.setState(orderTokens[2]);
        
        BigDecimal currentTaxRate = new BigDecimal(orderTokens[3]);
        orderFromFile.setTaxRate(currentTaxRate);
        
        orderFromFile.setProductType(orderTokens[4]);
        
        BigDecimal currentArea = new BigDecimal(orderTokens[5]);
        orderFromFile.setArea(currentArea);
        
        BigDecimal currentCostSqFt = new BigDecimal(orderTokens[6]);
        orderFromFile.setCostSqFt(currentCostSqFt);
        
        BigDecimal currentLaborCostSqFt = new BigDecimal(orderTokens[7]);
        orderFromFile.setLaborCostSqFt(currentLaborCostSqFt);
        
        BigDecimal currentTotalMaterialCost = new BigDecimal(orderTokens[8]);
        orderFromFile.setTotalMaterialCost(currentTotalMaterialCost);
        
        BigDecimal currentTotalLaborCost = new BigDecimal(orderTokens[9]);
        orderFromFile.setTotalLaborCost(currentTotalLaborCost);
        
        BigDecimal currentTax = new BigDecimal(orderTokens[10]);
        orderFromFile.setTax(currentTax);
        
        BigDecimal currentTotal = new BigDecimal(orderTokens[11]);
        orderFromFile.setTotal(currentTotal);
        
        LocalDate currentDate = LocalDate.parse(orderTokens[12]);
        orderFromFile.setDate(currentDate);
        
        
                
        return orderFromFile;
    }
    private void loadOrder() throws FlooringPersistenceException {
        Scanner sc;
        
        try {
            sc = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load inventory data into memory.", e);
        }
            String currentLine;
            Order currentOrder;
            
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                
                orders.put(currentOrder.getOrderNum(), currentOrder);
        }
            sc.close();
    }
    private String marshallOrder(Order anOrder) {
        String orderAsText = anOrder.getOrderNum() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostSqFt() + DELIMITER;
        orderAsText += anOrder.getLaborCostSqFt() + DELIMITER;
        orderAsText += anOrder.getTotalMaterialCost() + DELIMITER;
        orderAsText += anOrder.getTotalLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal() + DELIMITER;
        orderAsText += anOrder.getDate();
        
        return orderAsText;
    }
    
    private void writeOrder() throws FlooringPersistenceException {
        PrintWriter out;
        
        try{ 
            out = new PrintWriter(new FileWriter(ORDER_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save order data.", e);
        }
        String orderAsText;
        List<Order> orderList = this.getAllOrders();
        for(Order currentOrder : orderList) {
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<Order>(orders.values());
    }
    
    @Override
    public void loadOrderData() throws FlooringPersistenceException {
        loadOrder();
    }
    
    @Override
    public void writeOrderData() throws FlooringPersistenceException {
        writeOrder();
    }
    /*
    public Properties findProperty() {
        Properties prop = new Properties();
        
        try (FileInputStream input = new FileInputStream("flooring.properties")) {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }*/
    
}
