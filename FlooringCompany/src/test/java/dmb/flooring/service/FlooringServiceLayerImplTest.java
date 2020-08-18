/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.service;

import dmb.flooring.dao.ProductTypeDao;
import dmb.flooring.dao.TaxesDao;
import dmb.flooring.dto.Order;
import dmb.flooring.dto.ProductType;
import dmb.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dmich
 */
public class FlooringServiceLayerImplTest {
    
    FlooringServiceLayer service;
    ProductTypeDao prodDao;
    TaxesDao taxDao;
    Map<String, BigDecimal> taxes = new HashMap<>();
    Map<String, ProductType> products = new HashMap<>();
    Map<Integer, Order> orders = new HashMap<>();
    
    
    
    public FlooringServiceLayerImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringServiceLayerImpl.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    
    

    @Test
    public void testFindTaxMapKey() {
        
        String state = "TX";
        BigDecimal taxRate = BigDecimal.valueOf(.0625);
        Taxes tax = new Taxes(state);
        tax.setState(state);
        tax.setTaxRate(taxRate);
        
        taxes.put(state, taxRate);
        
        Set<String> keys = taxes.keySet();
        List<String> keyList = new ArrayList<>();
        keys.forEach((k) -> {
            keyList.add(k);
        });
                
        
        service.findTaxMapKey();
        
        assertEquals(1, keyList.size());
    }

    /**
     * Test of findProdMapKey method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testFindProdMapKey() {
        String productType = "Wood";
        BigDecimal costSqFt = BigDecimal.valueOf(5.15);
        BigDecimal laborSqFt = BigDecimal.valueOf(4.75);
        ProductType prod = new ProductType(productType);
        prod.setProductType(productType);
        prod.setCostSqFt(costSqFt);
        prod.setLaborCostSqFt(laborSqFt);
        
        
        products.put(productType, prod);
        
        Set<String> keys = products.keySet();
        List<String> keyList = new ArrayList<>();
        keys.forEach((k) -> {
            keyList.add(k);
        });
                
        
        service.findProdMapKey();
        
        assertEquals(1, keyList.size());
        
    }

    /**
     * Test of setOrderInfo method, of class FlooringServiceLayerImpl.
     */
    @Test
    public void testSetOrderInfo() {
        Order order = new Order();

        int orderNum = 1;
        order.setOrderNum(orderNum);
        order.setCustomerName("Michael Scott");
        order.setState("PA");
        order.setTaxRate(BigDecimal.valueOf(.0675));
        order.setProductType("Wood");
        order.setArea(BigDecimal.valueOf(100.00));
        
        LocalDate date = LocalDate.parse("2020-02-29");
        order.setDate(date);
        
        order.setCostSqFt(BigDecimal.valueOf(5.15));
        order.setLaborCostSqFt(BigDecimal.valueOf(4.75));
        order.setTotalMaterialCost(BigDecimal.valueOf(515.00));
        order.setTotalLaborCost(BigDecimal.valueOf(475.00));
        order.setTax(BigDecimal.valueOf(66.83));
        order.setTotal(BigDecimal.valueOf(1056.83));
        
        orders.put(order.getOrderNum(), order);
        
        Set<Integer> keys = orders.keySet();
                List<Integer> keyList = new ArrayList<>();
                keys.forEach((k) -> {
                    keyList.add(k);
                });       
                
        assertEquals(1,keyList.size());
 
    }
    
    @Test
    public void calcTotalLaborCostWood() {
        BigDecimal laborSqFt = BigDecimal.valueOf(4.75);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(475.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalLaborCost = laborSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalLaborCost);
        
        
    }
    
    @Test
    public void calcTotalLaborCostCarpet() {
        BigDecimal laborSqFt = BigDecimal.valueOf(2.10);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(210.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalLaborCost = laborSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalLaborCost);
    }
    
    @Test
    public void calcTotalLaborCostLaminate() {
        BigDecimal laborSqFt = BigDecimal.valueOf(2.10);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(210.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalLaborCost = laborSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalLaborCost);
    }
    
    @Test
    public void calcTotalLaborCostTile() {
        BigDecimal laborSqFt = BigDecimal.valueOf(4.15);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(415.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalLaborCost = laborSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalLaborCost);
    }
    
    @Test
    public void calcTotalMaterialCostWood() {
        BigDecimal costSqFt = BigDecimal.valueOf(4.75);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(475.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalMaterialCost = costSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalMaterialCost);
    }
    
    @Test
    public void calcTotalMaterialCostCarpet() {
        BigDecimal costSqFt = BigDecimal.valueOf(2.10);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(210.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalMaterialCost = costSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalMaterialCost);
    }
    
    @Test
    public void calcTotalMaterialCostLaminate() {
        BigDecimal costSqFt = BigDecimal.valueOf(2.10);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(210.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalMaterialCost = costSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalMaterialCost);
    }
    
    @Test
    public void calcTotalMaterialCostTile() {
        BigDecimal costSqFt = BigDecimal.valueOf(4.15);
        BigDecimal area = BigDecimal.valueOf(100.00);
        
        BigDecimal expected = BigDecimal.valueOf(415.00).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal totalMaterialCost = costSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
        
        assertEquals(expected, totalMaterialCost);
    }
 

   
}
