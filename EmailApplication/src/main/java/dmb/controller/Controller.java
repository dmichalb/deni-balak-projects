/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.controller;

import dmb.dao.Dao;
import dmb.dao.PersistenceException;
import dmb.dto.Email;
import dmb.service.DataValidationException;
import dmb.service.PasswordValidationException;
import dmb.service.DuplicateException;
import dmb.service.ServiceLayer;
import dmb.ui.UserIO;
import dmb.ui.UserIOImpl;
import dmb.ui.View;
import java.util.List;

/**
 *
 * @author dmich
 */
public class Controller {
    private ServiceLayer service;
    private Dao dao;
    private View view; 
    private UserIO io = new UserIOImpl();
    
    public Controller(ServiceLayer service, Dao dao, View view){
        this.service = service;
        this.dao = dao;
        this.view = view;
    }
    
    public void run() throws PersistenceException, DuplicateException, DataValidationException{
        boolean keepGoing = true;
        int menuSelection;
        loadData();
        
        try{
            while(keepGoing){
                menuSelection = getMenuSelection();
            
                switch(menuSelection){
                    case 1: io.print("1. Create Email Address");
                    createEmail();
                    break;
                
                    case 2: io.print("2. Change Password");
                    changePassword();
                    break;
                 
                    case 3: io.print("3. Set Alternate Email Address");
                    setAltEmail();
                    break;
                
                    case 4: io.print("4. Set Mailbox Capacity");
                    setMailboxCapacity();
                    break;
                
                    case 5: io.print("5. Access Email Information");
                    accessEmailInformation();
                    break;
                
                    case 6: io.print("6. Search for Email Address by Name");
                    viewEmail();
                    break;
                
                    case 7: keepGoing = false;
                    saveAndExit();
                    break;
                
                    default: unknownCommand();
                }
            } view.exitBanner();
            
        } catch(PersistenceException ex){
            view.displayErrorMessage(ex.getMessage());
        }
    }
    
    private int getMenuSelection(){
        return view.menuSelection();
    }
    private void unknownCommand() {
        view.unknownCommandBanner();
    }
    
    private void createEmail() throws PersistenceException{
        view.createEmailBanner();
        boolean hasErrors = false;
        Email currentEmail = view.getEmailInfo();
        do{
           try {
               service.createEmail(currentEmail);
               view.emailCreatedSuccess();
               view.displayEmailAddress(currentEmail);
               view.displayPassword(currentEmail);
               hasErrors = false;
           } catch (DuplicateException e) {
               hasErrors = true;
               view.displayErrorMessage(e.getMessage());
           }
        } while (hasErrors);
    }
    
  
    private void changePassword() throws DataValidationException{
        String password = view.validatePassword();
        String newPassword = view.getNewPassword();
        String emailaddress = service.getEmailByPassword(password);
        Email email = service.getEmail(emailaddress);
        service.createNewPassword(password, newPassword);
        view.displayPassword(email);
           
    }
    
    private void setAltEmail() throws DataValidationException{
        String emailAddress = view.searchEmail();
        service.validateEmailAddress(emailAddress);
        Email email = service.getEmail(emailAddress);
        
        String altEmail = view.inputAltEmailAddress();
        service.settingAltEmail(email, altEmail);
        
        view.displayAltEmail(email);
    }
    
    
    private void setMailboxCapacity(){
        String emailAddress = view.searchEmail();
        Email email = service.getEmail(emailAddress);
        int newCapacity = view.inputNewCapacity();
        service.settingMailboxCapacity(email, newCapacity);
        view.displayNewCapacity(email);
    }
    
    private void accessEmailInformation(){
        String emailAddress = view.searchEmail();
        service.validateEmailAddress(emailAddress);
        Email email = service.getEmail(emailAddress);
        view.displayEmail(email);
    }
    
    private void viewEmail(){
        String firstName = view.searchFirstName();
        String lastName = view.searchLastName();
        String emailAddress = service.getEmailAddressByName(firstName, lastName);
        view.displayEmailAddress(emailAddress);
    }
    
    
    private void saveAndExit() throws PersistenceException{
        view.displaySaveBanner();
        service.writeData();
        view.displaySaveSuccessBanner();
        //view.displayExitBanner();
    }
    private void loadData() throws PersistenceException {
        service.loadData();
    }
    
}
