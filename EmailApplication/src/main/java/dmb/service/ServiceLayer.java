/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.service;

import dmb.dao.PersistenceException;
import dmb.dto.Email;
import java.util.List;

/**
 *
 * @author dmich
 */
public interface ServiceLayer {
    void createEmail(Email email) throws PersistenceException, DuplicateException;
    
    String createNewPassword(String password, String newPassword) throws DataValidationException;
    
    List<Email> getAllEmails();
    
    String getEmailByPassword(String password);
    
    List<String> getNameByEmailAddress(String emailAddress);
    
    String getEmailAddressByName(String firstName, String lastName);
    
    Email getEmail(String emailAddress);
    
    int getMailboxCapByEmail(String emailAddress);
        
    int settingMailboxCapacity(Email email, int newCapacity);
    
    String settingAltEmail(Email email, String altEmail) throws DataValidationException;
    
    void validateEmailAddress(String emailAddressInput);
    
    void loadData() throws PersistenceException;
    void writeData() throws PersistenceException;
   
}
