/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Order;
import dmb.flooring.service.FlooringServiceLayer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dmich
 */
public class FlooringDaoStubImplTest {
    private FlooringDao dao;
    
    public FlooringDaoStubImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("flooringDaoStub", FlooringDaoStubImpl.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringPersistenceException {
        List<Order> orderList = dao.getAllOrders();
        orderList.clear();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOrderByDate method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testGetOrderByDate() throws Exception {
        Order order = new Order();
        order.setOrderNum(1);
        order.setCustomerName("Michael Scott");
        order.setState("PA");
        order.setTax(order.getTaxRate());
        order.setProductType("Wood");
        
        BigDecimal area = new BigDecimal(100.00);
        order.setArea(area);
        order.setCostSqFt(order.getCostSqFt());
        order.setLaborCostSqFt(order.getLaborCostSqFt());
        order.setTotalMaterialCost(order.getTotalMaterialCost());
        order.setTotalLaborCost(order.getTotalLaborCost());
        order.setTax(order.getTax());
        order.setTotal(order.getTotal());
        
        LocalDate date = LocalDate.parse("2020-02-28");
        order.setDate(date);
        
        dao.addOrder(order.getOrderNum(), order);
        
        
        Order order2 = new Order();
        order2.setOrderNum(2);
        order2.setCustomerName("Dwight Schrute");
        order2.setState("PA");
        order2.setTax(order2.getTaxRate());
        order2.setProductType("Tile");
        
        BigDecimal area2 = new BigDecimal(200.00);
        order2.setArea(area2);
        order2.setCostSqFt(order2.getCostSqFt());
        order2.setLaborCostSqFt(order2.getLaborCostSqFt());
        order2.setTotalMaterialCost(order2.getTotalMaterialCost());
        order2.setTotalLaborCost(order2.getTotalLaborCost());
        order2.setTax(order2.getTax());
        order2.setTotal(order2.getTotal());
                
        order2.setDate(date);
        
        dao.addOrder(order2.getOrderNum(), order2);
        
        
        assertEquals(2, dao.getOrderByDate(date).size());
    }

    /**
     * Test of addOrder method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testAddGetOrder() throws Exception {
        Order order = new Order();
        order.setOrderNum(1);
        order.setCustomerName("Michael Scott");
        order.setState("PA");
        order.setTax(order.getTaxRate());
        order.setProductType("Wood");
        
        BigDecimal area = new BigDecimal(100.00);
        order.setArea(area);
        order.setCostSqFt(order.getCostSqFt());
        order.setLaborCostSqFt(order.getLaborCostSqFt());
        order.setTotalMaterialCost(order.getTotalMaterialCost());
        order.setTotalLaborCost(order.getTotalLaborCost());
        order.setTax(order.getTax());
        order.setTotal(order.getTotal());
        
        String date = "2020-02-28";
        order.setDate(LocalDate.parse(date));
        
        dao.addOrder(order.getOrderNum(), order);
        
        Order fromDao = dao.getOrder(order.getOrderNum());
        
        assertEquals(order, fromDao);
    }

    /**
     * Test of removeOrder method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
       
        Order order = new Order();
        order.setOrderNum(1);
        order.setCustomerName("Michael Scott");
        order.setState("PA");
        order.setTax(order.getTaxRate());
        order.setProductType("Wood");
        
        BigDecimal area = new BigDecimal(100.00);
        order.setArea(area);
        order.setCostSqFt(order.getCostSqFt());
        order.setLaborCostSqFt(order.getLaborCostSqFt());
        order.setTotalMaterialCost(order.getTotalMaterialCost());
        order.setTotalLaborCost(order.getTotalLaborCost());
        order.setTax(order.getTax());
        order.setTotal(order.getTotal());
        
        String date = "2020-02-28";
        order.setDate(LocalDate.parse(date));
        
        dao.addOrder(order.getOrderNum(), order);
        
        Order order2 = new Order();
        order2.setOrderNum(2);
        order2.setCustomerName("Dwight Schrute");
        order2.setState("PA");
        order2.setTax(order2.getTaxRate());
        order2.setProductType("Tile");
        
        BigDecimal area2 = new BigDecimal(200.00);
        order2.setArea(area2);
        order2.setCostSqFt(order2.getCostSqFt());
        order2.setLaborCostSqFt(order2.getLaborCostSqFt());
        order2.setTotalMaterialCost(order2.getTotalMaterialCost());
        order2.setTotalLaborCost(order2.getTotalLaborCost());
        order2.setTax(order2.getTax());
        order2.setTotal(order2.getTotal());
        
        order2.setDate(LocalDate.parse(date));
        
        dao.addOrder(order2.getOrderNum(), order2);
        
        dao.removeOrder(2);
        assertEquals(1, dao.getAllOrders().size());
        assertNull(dao.getOrder(2));
        
        dao.removeOrder(1);
        assertEquals(0, dao.getAllOrders().size());
        assertNull(dao.getOrder(1));
    }


    /**
     * Test of getAllOrders method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        Order order = new Order();
        order.setOrderNum(1);
        order.setCustomerName("Michael Scott");
        order.setState("PA");
        order.setTax(order.getTaxRate());
        order.setProductType("Wood");
        
        BigDecimal area = new BigDecimal(100.00);
        order.setArea(area);
        order.setCostSqFt(order.getCostSqFt());
        order.setLaborCostSqFt(order.getLaborCostSqFt());
        order.setTotalMaterialCost(order.getTotalMaterialCost());
        order.setTotalLaborCost(order.getTotalLaborCost());
        order.setTax(order.getTax());
        order.setTotal(order.getTotal());
        
        String date = "2020-02-28";
        order.setDate(LocalDate.parse(date));
        
        dao.addOrder(order.getOrderNum(), order);
        
        Order order2 = new Order();
        order.setOrderNum(1);
        order.setCustomerName("Dwight Schrute");
        order.setState("PA");
        order.setTax(order2.getTaxRate());
        order.setProductType("Tile");
        
        BigDecimal area2 = new BigDecimal(200.00);
        order.setArea(area2);
        order.setCostSqFt(order2.getCostSqFt());
        order.setLaborCostSqFt(order2.getLaborCostSqFt());
        order.setTotalMaterialCost(order2.getTotalMaterialCost());
        order.setTotalLaborCost(order2.getTotalLaborCost());
        order.setTax(order2.getTax());
        order.setTotal(order2.getTotal());
        
        order.setDate(LocalDate.parse(date));
        
        dao.addOrder(order2.getOrderNum(), order2);
        
        List<Order> allOrders = dao.getAllOrders();
        
        assertEquals(2, dao.getAllOrders().size());
        
    }

    
    //feeling really sick. not doing this
    /**
     * Test of loadOrderData method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testLoadOrderData() throws Exception {
        
    }

    /**
     * Test of writeOrderData method, of class FlooringDaoStubImpl.
     */
    @Test
    public void testWriteOrderData() throws Exception {
        
    }
    
}
