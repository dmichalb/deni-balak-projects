/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.service;

import dmb.dao.Dao;
import dmb.dao.PersistenceException;
import dmb.dto.Email;
import dmb.ui.View;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmich
 */
public class ServiceLayerImpl implements ServiceLayer{
    private Dao dao;
    
    public ServiceLayerImpl(Dao dao) {
        this.dao = dao;
    }
    
    @Override
    public void createEmail(Email email) throws PersistenceException, DuplicateException{
        int pwLength = email.getDefaultPWLength();
        String department = setDepartment(email);
        String company = setCompany(email);
        email.setPassword(generatePassword(pwLength, email));
            
        String firstName = email.getFirstName();
        String lastName = email.getLastName();
        String suffix = email.getEmailSuffix();
        
        
        String emailAddress;
        
        if(department.equals("None")){
            emailAddress = "" + firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + company.toLowerCase() + suffix;
        } else {
            emailAddress = "" + firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + department.toLowerCase() + "." + company.toLowerCase() + suffix;
        }
        
        email.setEmailAddress(emailAddress);
        dao.addEmail(emailAddress, email);
    }
    
    private String setDepartment(Email email){
      String deptChoiceString = email.getDepartment();
      int deptChoice = Integer.parseInt(deptChoiceString);
      String department;
      
      if (deptChoice == 1){
          department = "Sales";
      } else if (deptChoice == 2){
          department = "Development";
      } else if (deptChoice == 3) {
          department = "Accounting";
      } else {
          department = "None";
      }
      
      email.setDepartment(department);
      return department;
    }
    
    private String setCompany(Email email){
        String userInputCompany = email.getCompany();
         char ch = ' ';
        String companyName = userInputCompany.replace(ch, '-');
        
        email.setCompany(companyName);
        return companyName;
    }
    
    
    @Override
    public String createNewPassword(String password, String newPassword) throws DataValidationException{      
        String emailAddress = dao.getEmailByPassword(password);
        Email email = dao.getEmail(emailAddress);
        int characterCounter = 0;
        Boolean meetsCriteria;
        
        do {
                    
            for (int i = 0; i < newPassword.length(); i++){
                characterCounter++;
            }
            if (characterCounter < 8 || characterCounter > 20){
                validatePassword(newPassword);
                meetsCriteria = false;
            } else {
                email.setPassword(newPassword);
                meetsCriteria = true;
            }
        } while (meetsCriteria);
        return email.getPassword();
    }
    
    private void validatePassword(String newPassword) {
        int characterCount = 0;
        for (int i = 0; i < newPassword.length(); i++){
            characterCount++;
        }
        if (characterCount < 8 || characterCount > 20) {
            try {
                throw new PasswordValidationException(
                        "ERROR: Passwords must be between 8 and 20 characters long.");
            } catch (PasswordValidationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }    
    
    private String generatePassword(int length, Email email){
        String passwordSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "1234567890!@#$%^&*()";
        
        char[] password = new char[length];
        for (int i = 0; i < length; i ++){
            int random = (int)(Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(random);
        }
        
        email.setPassword(new String(password));
        return new String(password);
    }

    @Override
    public int settingMailboxCapacity(Email email, int newCapacity){
        email.setMailboxCapacity(newCapacity);
        return email.getMailboxCapacity();
    }

    @Override
    public String settingAltEmail(Email email, String altEmail) throws DataValidationException{
        email.setAltEmail(altEmail);
        return email.getAltEmail();
    }
    
    
    @Override
    public void validateEmailAddress(String emailAddressInput){
        List<Email> allEmails = dao.getAllEmails();
        
            for (Email email : allEmails){
                if(!email.getEmailAddress().equals(emailAddressInput)){
                    
                    try {
                        throw new DataValidationException(
                        "ERROR: That email address does not exist.");
                    } catch(DataValidationException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }

    }

    @Override
    public List<String> getNameByEmailAddress(String emailAddress) {
        return dao.getNameByEmailAddress(emailAddress);
    }

    @Override
    public String getEmailAddressByName(String firstName, String lastName) {
        return dao.getEmailByName(firstName, lastName);
    }

    @Override
    public int getMailboxCapByEmail(String emailAddress) {
        return dao.getMailboxCapByEmail(emailAddress);
    }

    @Override
    public List<Email> getAllEmails() {
        return dao.getAllEmails();
    }
    
    @Override
    public Email getEmail(String emailAddress){
        return dao.getEmail(emailAddress);
    }
    
    @Override
    public String getEmailByPassword(String password) {
        return dao.getEmailByPassword(password);
    }

    @Override
    public void loadData() throws PersistenceException {
        dao.loadData();
    }

    @Override
    public void writeData() throws PersistenceException {
        dao.writeData();
    }

}
