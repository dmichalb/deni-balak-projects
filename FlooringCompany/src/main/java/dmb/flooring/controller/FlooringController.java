/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.controller;

import dmb.flooring.dao.FlooringPersistenceException;
import dmb.flooring.dto.Order;
import dmb.flooring.dto.OrderInput;
import dmb.flooring.service.DataValidationException;
import dmb.flooring.service.FlooringServiceLayer;
import dmb.flooring.ui.FlooringView;
import dmb.flooring.ui.UserIO;
import dmb.flooring.ui.UserIOImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author dmich
 */
public class FlooringController {
    private UserIO io = new UserIOImpl();
    private FlooringView view;
    private FlooringServiceLayer service;
    Properties prop;
    String mode;
    LocalDate date;
    OrderInput orderInput;
    Order order;
    
    public FlooringController(FlooringView view, FlooringServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() throws FlooringPersistenceException {
        
        boolean keepGoing = true;
        
        int menuSelection;
        
        loadData();
        
        try{
        
            while (keepGoing){
            menuSelection = prodMenu();
                
            switch (menuSelection) {
                case 1: io.print("DISPLAY BY DATE");
                getByDate(date);
                break;
                
                case 2: io.print("DISPLAY ORDER");
                getOrder();
                break;
                
                case 3: io.print("CREATE ORDER");
                createOrder();
                break;
                
                case 4: io.print("EDIT ORDER");
                editOrder();
                break;
                
                case 5: io.print("REMOVE ORDER");
                removeOrder();
                break;
                
                case 6: io.print("SAVE WORK");
                saveOrder();
                break;
                
                case 7: keepGoing = false;
                saveOrder();
                break;
                
                default: view.displayUnknownCommandBanner();
                }
            } view.displayExitBanner();

        } catch (FlooringPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    } 
    
    private void loadData() throws FlooringPersistenceException {
        service.loadData();
        prop = service.findProperty();
        mode = prop.getProperty("Mode");
    }

    private int prodMenu() {
        return view.getMenuSelectionProd();
    }
    
    private void getByDate(LocalDate date) throws FlooringPersistenceException {
        String stringDate = view.dateChoice();
        date = LocalDate.parse(stringDate);
        
        if (stringDate == null) {
            view.displayLoadingErrorBanner();
        } else {
        view.displayGetByDateBanner();
        List<Order> orderList = service.getOrderByDate(date);
        view.displayByDate(orderList);
        view.displayReturnToMenuBanner();
        }
    }
    
    
    private void createOrder() throws FlooringPersistenceException{
        view.displayCreateOrderBanner();
        boolean hasErrors = false;
        
        do { 
            OrderInput currentOrder = view.getNewOrderInfo(service.findTaxMapKey(), service.findProdMapKey());
            
            try { service.createOrder(currentOrder,prop);
            view.displayReturnToMenuBanner();
            hasErrors = false;
            } catch (DataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
    }
    
    private void editOrder() throws FlooringPersistenceException{
        List<String> taxes = new ArrayList<>(service.findTaxMapKey());
        List<String> product = new ArrayList<>(service.findProdMapKey());
        taxes.add("");
        product.add("");
    
        int orderNumChoice = view.orderNumChoice();
        view.displayEditOrderBanner();
        order = service.getOrder(orderNumChoice);
        view.displayOrder(order);
        int sureEdit = view.areYouSureEdit();
        
        if (sureEdit == 2) {
            view.displayReturnToMenuBanner();
        } else if (sureEdit == 1) {
            orderInput = view.editingOrderInfo(order, taxes, product);
            service.editOrder(orderNumChoice, orderInput);
            view.displayReturnToMenuBanner();
        }
    }
    
    
    private void getOrder() throws FlooringPersistenceException {
        view.displayGetOrderBanner();
        int orderNum = view.orderNumChoice();
        
        String stringOrderNum = String.valueOf(orderNum);
        
        if (stringOrderNum == null) {
            view.displayLoadingErrorBanner();
        } else {
        order = service.getOrder(orderNum);
        view.displayOrder(order);
        view.displayReturnToMenuBanner();
        }
    }
    
    private void removeOrder() throws FlooringPersistenceException {
        view.displayRemoveOrderBanner();
        int orderNum = view.orderNumChoice();
        
        String stringOrderNum = String.valueOf(orderNum);
        
        if (stringOrderNum == null) {
            view.displayLoadingErrorBanner();
        } else {
        order = service.getOrder(orderNum);
        view.displayOrder(order);
        int areYouSure = view.areYouSureRemove();
        
        if (areYouSure == 1) {
            service.removeOrder(orderNum);
            view.displayRemoveOrderSuccessBanner();
        } else if (areYouSure == 2) {
            view.displayReturnToMenuBanner();
        }
        }
    }
    private void saveOrder() throws FlooringPersistenceException {
        if (mode.equals("training")) {
            view.displayErrorTraining();
            return;
        } else {
        view.displaySaveBanner();
        service.writeData();
        service.writeOrderNumProperty(prop);
        view.displaySaveSuccessBanner();
        }
    }
}

    
