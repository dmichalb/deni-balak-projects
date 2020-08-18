/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.dao;

import dmb.dto.Email;
import java.util.List;

/**
 *
 * @author dmich
 */
public interface Dao {
    Email addEmail(String emailAddress, Email email) throws PersistenceException;
    
    List<Email> getAllEmails();
    
    Email getEmail(String emailAddress);
    
    List<String> getNameByEmailAddress(String emailAddress);
    
    String getEmailByName(String firstName, String lastName);
    
    String getEmailByPassword(String password);
    
    int getMailboxCapByEmail(String emailAddress);
    
    void loadData() throws PersistenceException;
    void writeData() throws PersistenceException;
}
