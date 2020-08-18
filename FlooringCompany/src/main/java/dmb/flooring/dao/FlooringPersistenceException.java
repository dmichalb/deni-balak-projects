/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

/**
 *
 * @author dmich
 */
public class FlooringPersistenceException extends Exception{
    public FlooringPersistenceException(String msg) {
        super(msg);
    }
    
    public FlooringPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
