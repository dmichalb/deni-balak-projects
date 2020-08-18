/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Taxes;
import java.util.Map;


/**
 *
 * @author dmich
 */
public interface TaxesDao {
    Taxes getTaxes(String state);
    
    void loadTaxData() throws FlooringPersistenceException;
    
    Map<String, Taxes> getTaxRates();
}
