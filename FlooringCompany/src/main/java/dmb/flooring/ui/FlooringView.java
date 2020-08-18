/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.ui;

import dmb.flooring.dao.FlooringDao;
import dmb.flooring.dao.FlooringDaoFileImpl;
import dmb.flooring.dto.OrderInput;
import dmb.flooring.dto.Order;
import dmb.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dmich
 */
public class FlooringView {
    UserIO io = new UserIOImpl();
    FlooringDao dao = new FlooringDaoFileImpl();
    
    private Map<String, Taxes> taxes = new HashMap<>();
    
    public FlooringView(UserIO io) {
        this.io = io;
    } 
    
    public int getMenuSelectionProd () {
        io.print("\n** Menu **");
        io.print("1. Display Orders by Date");
        io.print("2. Display Order by Order Number");
        io.print("3. Create an Order");
        io.print("4. Edit an Order");
        io.print("5. Remove an Order");
        io.print("6. Save Current Work");
        io.print("7. Quit");
        
        return io.readInt("Please choose from the above options. 1, 7");
    }
    
    public OrderInput getNewOrderInfo(List<String> taxes, List<String> product) {
        String customerName = io.readString("Please input customer's name.");
        String state = io.readString("Please input the initials of the customer's state of residence. (TX)", taxes);
        String productType = io.readString ("Please input the type of product you are purchasing.", product);
        BigDecimal area = io.readBigDecimal("Please input the area of the room in square feet. (0.00)");
        String stringDate = io.readString("Please input today's date. (YYYY-MM-DD)");
        
        OrderInput orderInput = new OrderInput();
        orderInput.setCustomerName(customerName);
        orderInput.setState(state);
        orderInput.setProductType(productType);
        orderInput.setArea(area);
        
        LocalDate date = LocalDate.parse(stringDate);
        orderInput.setDate(date);
        
        io.print("Customer Name: " + customerName);
        io.print("State: " + state);
        io.print("Product Type: " + productType);
        
        String currentArea = String.valueOf(area);
        io.print("Area: " + currentArea);
       
        io.print("Date: " + stringDate);
        
        return orderInput;
    }
    public OrderInput editingOrderInfo(Order order, List<String> taxes, List<String> product) {
        String customerName = io.readString("Please input customer's name.");
        String state = io.readString("Please input the initials of the customer's state of residence. (TX)", taxes);
        String productType = io.readString ("Please input the type of product you are purchasing.", product);
        String stringArea = io.readString("Please input the area of the room in square feet.");
        
        OrderInput orderInput = new OrderInput();
        
        if (customerName.length() == 0) {
            orderInput.setCustomerName(order.getCustomerName());
        } else {
        orderInput.setCustomerName(customerName);
        }
        
        if (state.length() == 0) {
            orderInput.setState(order.getState());
        } else {
        orderInput.setState(state);
        }
        
        if (productType.length() == 0) {
            orderInput.setProductType(order.getProductType());
        } else {
        orderInput.setProductType(productType);
        }
        
        if (stringArea.length() == 0) {
            orderInput.setArea(order.getArea());
        } else {
            BigDecimal area = new BigDecimal(stringArea);
            orderInput.setArea(area);
        }
        orderInput.setDate(order.getDate());
        
        io.print(customerName);
        io.print(state);
        io.print(productType);
        io.print(stringArea);
        
        return orderInput;
    }
    
    public int areYouSureAdd() {
        io.print("\nAre you sure you want to add this order?");
        io.print("1. Yes");
        io.print("2. No");
        
        return io.readInt("Please choose from above (1, 2)");
    }
    
    public void displayGetByDateBanner() {
        io.print("\n** Orders By Date **");
    }
    
    public String displayReturnToMenuBanner() {
        return io.readString("\nPlease hit enter to return to menu.");
    }
    
    public void displayGetOrderBanner() {
        io.print("\n** Get Order **");
    }
    /*
    public void displayOrderElements(Order order) {
        io.print("Order Number: " + order.getOrderNum());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State of Residence: " + order.getState());
        io.print("State's Tax Rate: " + order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area of Room: " + order.getArea());
        io.print("Labor Cost per Sq Ft: " + order.getLaborCostSqFt());
        io.print("Material Cost per Sq Ft: " + order.getCostSqFt());
        io.print("Total Tax: " + order.getTax());
        io.print("Total Cost: " + order.getTotal());
    }*/
    
    public void displayCreateOrderBanner() {
        io.print("\n** Create New Order **");
    }
    public String displayCreateOrderSuccessBanner() {
        return io.readString("Order Created. Please hit enter to return to menu.");
    }
    
    public void displayEditOrderBanner() {
        io.print("\n** Edit an Order **");
    }
    public String displayEditOrderSuccessBanner() {
        return io.readString("Order Edited. Please hit enter to return to menu.");
    }
    public int areYouSureEdit() {
        io.print("\nYou are only able to edit the following variables: \nCustomer Name \nState of Residence \nProduct Type \nArea of Room");
        io.print("Are you sure you want to edit this order?");
        io.print("1. Yes");
        io.print("2. No");
        
        return io.readInt("Please choose from above (1, 2)");
    }
    
    public void displayOrder(Order order) {
        if (order != null) {
            io.print("Order Number: " + order.getOrderNum() + "\nCustomer Name: " + order.getCustomerName() +
                    "\nState of Residence: " + order.getState() + "\nProduct Type: " + order.getProductType() + 
                    "\nArea of Room: " + order.getArea() + "\nTotal Material Cost:" + order.getTotalMaterialCost() + 
                    "\nTotal Labor Cost: " + order.getTotalLaborCost() + "\nOrder Total: " + order.getTotal());
        } else {
            io.print("The order you're looking for does not exist.");
        }
    }
    
    public void displayByDate(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            io.print(currentOrder.getOrderNum() + "\n" + currentOrder.getCustomerName() +
                    "\n" + currentOrder.getState() + "\n" + currentOrder.getProductType() + 
                    "\n" + currentOrder.getArea() + "\n" + currentOrder.getTotalMaterialCost() + 
                    "\n" + currentOrder.getTotalLaborCost() + "\n" + currentOrder.getTotal());
        }
    }
    
    public int areYouSureRemove() {
        io.print("Are you sure you want to remove this order?");
        io.print("1. Yes");
        io.print("2. No");
        
        return io.readInt("Please choose from above (1, 2)");
    }
    
    public void displayRemoveOrderBanner() {
        io.print("** Remove an Order **");
    }
    public String displayRemoveOrderSuccessBanner() {
        return io.readString("Order Removed. Please hit enter to return to menu.");
    }
    
    public String dateChoice() {
        return io.readString("Please enter the date you are searching for (YYYY-MM-DD)");
    }
    
    public int orderNumChoice() {
        return io.readInt("Please enter the order number (1, 2, 3...)");
    }
    
    public void displaySaveBanner() {
        io.print("** Saving Order **");
    }
    
    public String displaySaveSuccessBanner() {
        return io.readString("You have successfully saved your order. Please hit enter to continue");
    }
    
    public String displayLoadingErrorBanner() {
        io.print("This item does not exist.");
        return io.readString("Please hit enter to return to the menu.");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayErrorTraining() {
        io.print("You cannot save during training mode.");
    }
}
