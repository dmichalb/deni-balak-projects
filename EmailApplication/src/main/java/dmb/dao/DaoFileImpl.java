/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.dao;

import dmb.dto.Email;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author dmich
 */
public class DaoFileImpl implements Dao{
    private Map<String, Email> emails = new HashMap<>();
    
    public static final String EMAIL_FILE = "email.txt";
    public static final String DELIMITER = "::";
    
    @Override
    public Email addEmail(String emailAddress, Email email) throws PersistenceException{
        Email newEmail = emails.put(emailAddress, email);
        return newEmail;
    }

    @Override
    public Email getEmail(String emailAddress) {
        return emails.get(emailAddress);
    }
    
    @Override
    public List<String> getNameByEmailAddress(String emailAddress) {
        List<String> fullName = new ArrayList<>();
        Email thisEmail = emails.get(emailAddress);
        String firstName = thisEmail.getFirstName();
        String lastName = thisEmail.getLastName();
        fullName.add(firstName);
        fullName.add(lastName);
        return fullName;
    }
    
    @Override
    public String getEmailByName(String firstName, String lastName){
        List<Email> allEmails = getAllEmails();
        String emailAddress = "";
        for (Email email : allEmails){
            if (email.getFirstName().equals(firstName) && email.getLastName().equals(lastName)){
                emailAddress = email.getEmailAddress();
            } else {
                emailAddress = "Email address does not exist for " + firstName + " " + lastName + ".";
            }
        }
        return emailAddress;
    }
    
    @Override
    public String getEmailByPassword(String password) {
        List<Email> allEmails = getAllEmails();
        String thisEmailAddress = "";
        for (Email email : allEmails){
            if (email.getPassword().equals(password))
                thisEmailAddress = email.getEmailAddress();
        }
        return thisEmailAddress;
    }
    
    @Override
    public int getMailboxCapByEmail(String emailAddress){
        Email thisEmail = emails.get(emailAddress);
        int mailboxCap = thisEmail.getMailboxCapacity();
        
        return mailboxCap;
    }
    
    @Override
    public List<Email> getAllEmails(){
        return new ArrayList<Email>(emails.values());
    }

    
    public void loadEmail() throws PersistenceException{
        Scanner sc;
        
        try{
            sc = new Scanner(new BufferedReader(new FileReader(EMAIL_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load roster data into memory.", e);
        }
        String currentLine;
            Email currentEmail;
            
            while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentEmail = unmarshallEmail(currentLine);
            emails.put(currentEmail.getEmailAddress(), currentEmail);
        }
            sc.close();    
    }
    
    private Email unmarshallEmail(String emailAsText) {
        String[] emailTokens = emailAsText.split(DELIMITER);
        String emailAddress = emailTokens[0];
        Email emailFromFile = new Email(emailAddress);
        
        emailFromFile.setFirstName(emailTokens[1]);
        emailFromFile.setLastName(emailTokens[2]);
        emailFromFile.setPassword(emailTokens[3]);
        emailFromFile.setDepartment(emailTokens[4]);
        emailFromFile.setCompany(emailTokens[5]);
        emailFromFile.setMailboxCapacity(Integer.parseInt(emailTokens[6]));
        emailFromFile.setAltEmail(emailTokens[7]);
        
        return emailFromFile;
    }
    
    private String marshallEmail(Email anEmail)
    {
        String emailAsText = anEmail.getEmailAddress() + DELIMITER;
        
        emailAsText += anEmail.getFirstName() + DELIMITER;
        emailAsText += anEmail.getLastName() + DELIMITER;
        emailAsText += anEmail.getPassword() + DELIMITER;
        emailAsText += anEmail.getDepartment() + DELIMITER;
        emailAsText += anEmail.getCompany() + DELIMITER;
        emailAsText += anEmail.getMailboxCapacity() + DELIMITER;
        emailAsText += anEmail.getAltEmail();
        
        return emailAsText;
    }    
    
    public void writeEmail()throws PersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(EMAIL_FILE));
        } catch (IOException e) {
            throw new PersistenceException("Could not save Email data.", e);
        }
            String dvdAsText;
            
            List<Email> emailList = this.getAllEmails();
            
            for(Email currentEmail : emailList) {
                dvdAsText = marshallEmail(currentEmail);
                out.println(dvdAsText);
                out.flush();
            }
            out.close();
        }
    
    
    @Override
    public void loadData() throws PersistenceException{
        loadEmail();
    }

    @Override
    public void writeData() throws PersistenceException{
        writeEmail();
    }
}
