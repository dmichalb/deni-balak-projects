/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.dto;

import java.util.Scanner;

/**
 *
 * @author dmich
 */
    
    //Generate random pw
    
    //Set capacity
    
    //Set altEmail
    
    //Change pw
public class Email {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private String department;
    private String company;
    private int mailboxCapacity = 500;
    private String altEmail;
    
    private final int defaultPWLength = 10;
    private final String emailSuffix = ".com";
    


    public Email(String emailAddress) {
    }
    public Email(){
    }
    
    
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getDefaultPWLength() {
        return defaultPWLength;
    }

    public String getEmailSuffix() {
        return emailSuffix;
    }
    

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getMailboxCapacity() {
        return mailboxCapacity;
    }

    public void setMailboxCapacity(int mailboxCapacity) {
        this.mailboxCapacity = mailboxCapacity;
    }

    public String getAltEmail() {
        return altEmail;
    }

    public void setAltEmail(String altEmail) {
        this.altEmail = altEmail;
    }
    
}
