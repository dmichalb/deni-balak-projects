/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.ui;

import dmb.dao.DaoFileImpl;
import dmb.dto.Email;

/**
 *
 * @author dmich
 */
public class View {
    DaoFileImpl dao;
    private UserIO io;
    public View(UserIO io){
        this.io = io;
    }
    
    public int menuSelection(){
        io.print("\nMenu Selection");
        io.print("1. Create Email Address");
        io.print("2. Change Password");
        io.print("3. Set Alternate Email Address");
        io.print("4. Set Mailbox Capacity");
        io.print("5. Access Email Information");
        io.print("6. Search for Email Address by Name");
        io.print("7. Exit");
        
        return io.readInt("Select from the above choices", 1, 7);
    }
    
    public Email getEmailInfo(){
        String firstName = io.readString("Enter your first name.");
        String lastName = io.readString("Enter your last name");
        String company = io.readString("Enter your company's name.");
        io.print("Department Codes: \n1. Sales"
                + "\n2. Development"
                + "\n3. Accounting"
                + "\n0. None");
        int deptChoice = io.readInt("Enter your department's code.");

        
        
        Email newEmail = new Email();
        newEmail.setFirstName(firstName);
        newEmail.setLastName(lastName);
        newEmail.setCompany(company);
        newEmail.setDepartment(String.valueOf(deptChoice));
        
        return newEmail;
    }
    
    public void displayEmail(Email email){
        io.print("Email Address: " + email.getEmailAddress());
        io.print("Name: " + email.getFirstName() + " " + email.getLastName());
        io.print("Password: " + email.getPassword());
        io.print("Alternate Email Address: " + email.getAltEmail());
        io.print("Mailbox Capacity: " + email.getMailboxCapacity());
    }    
    
    public void displayEmailAddress(String emailAddress){
        if (emailAddress != null){
            io.print(emailAddress);
        } else {
            io.print("Email address does not exist");
        }
        io.readString("Please hit enter to continue");
    }
    
    public void displayAltEmail(Email email){
        io.print("Your Alternate Email Address is: ");
        io.print(email.getAltEmail());        
        io.readString("Please hit enter to continue");
    }
    
    public String getNewPassword(){
        io.print("Passwords must be 8-20 character's long.");
        return io.readString("Please input your new password.");
    }
    
    public String validatePassword(){
        return io.readString("Please input your current password.");
    }
    
    public void displayPassword(Email email){
        io.print("\nYour password is: ");
        io.print(email.getPassword());
    }
    
    
    public void displayEmailAddress(Email email){
        io.print("Your email address is: ");
        io.print(email.getEmailAddress());
    }
    
    public String inputAltEmailAddress(){
        return io.readString("Please enter a secondary email address");
    }
    
    public int inputNewCapacity(){
        return io.readInt("Enter the new number of emails your mailbox can hold.", 100, 1000);
    }
    
    public void displayNewCapacity(Email email){
        io.print("Your mailbox's new capacity is: ");
        io.print(Integer.toString(email.getMailboxCapacity()));
    }
    
    
    // SEARCH BY
    public String searchFirstName(){
        return io.readString("Enter your first name.");
    }
    public String searchLastName(){
        return io.readString("Enter your last name.");
    }
    public String searchEmail(){
        return io.readString("Enter your email address.");
    }
    
    
    
    //BANNERS
    public void unknownCommandBanner(){
        io.print("Unknown Command.");
    }
    public void exitBanner(){
        io.print("Good Bye");
    }
    public void createEmailBanner(){
        io.print("== Create Email ==");
    }
    public void emailCreatedSuccess(){
        io.print("== Email Created ==");
    }
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    public void displaySaveBanner(){
        io.print("== Saving Data ==");
    }
    public void displaySaveSuccessBanner(){
        io.print("Your data has been saved.");
    }
    public void displayExitBanner(){
        io.print("Good Bye");
    }
}
