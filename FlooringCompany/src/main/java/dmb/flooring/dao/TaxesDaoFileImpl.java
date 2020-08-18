/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.Taxes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author dmich
 */
public class TaxesDaoFileImpl implements TaxesDao{

    private Map<String, Taxes> taxes = new HashMap<>();
        
    private static final String TAX_FILE = "tax.txt";
    private static final String DELIMITER = "::";
   
    
    private Taxes unmarshallTaxes(String taxesAsText){
        
        String[] taxTokens = taxesAsText.split(DELIMITER);
        String state = taxTokens[0];
        Taxes taxFromFile = new Taxes(state);
        
        BigDecimal currentRate = new BigDecimal(taxTokens[1]);
        taxFromFile.setTaxRate(currentRate);

        return taxFromFile;
    }
    
    private void loadTaxes() throws FlooringPersistenceException {
        Scanner sc;
        
        try {
            sc = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load tax data into memory.", e);
        }
            String currentLine;
            Taxes currentTaxRate;
            
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentTaxRate = unmarshallTaxes(currentLine);
                
                taxes.put(currentTaxRate.getState(), currentTaxRate);
        }
            
            sc.close();
    }

    @Override
    public Taxes getTaxes(String state) {
        return taxes.get(state);
    }

    @Override
    public void loadTaxData() throws FlooringPersistenceException {
        loadTaxes();
    }
    
    public Map<String, Taxes> getTaxRates() {
        return taxes;
    }
}
