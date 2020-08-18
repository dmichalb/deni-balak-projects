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
public class DuplicateException extends Exception{
    public DuplicateException(String message) {
        super(message);
    }
    
    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
