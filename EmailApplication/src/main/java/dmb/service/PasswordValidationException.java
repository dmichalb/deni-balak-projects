/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.service;

/**
 *
 * @author dmich
 */
public class PasswordValidationException extends Exception{
    public PasswordValidationException(String message) {
        super(message);
    }
    
    public PasswordValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
