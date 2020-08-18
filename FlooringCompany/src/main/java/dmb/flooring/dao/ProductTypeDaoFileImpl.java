/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.ProductType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmich
 */
public class ProductTypeDaoFileImpl implements ProductTypeDao{
    
    private static final String PRODUCT_FILE = "prod.txt";
    private static final String DELIMITER = "::";
    
    private Map<String, ProductType> products = new HashMap<>();
    
    private ProductType unmarshallProduct(String productAsText){
        
        String[] prodTokens = productAsText.split(DELIMITER);
        String productType = prodTokens[0];
        ProductType prodFromFile = new ProductType(productType);
        
        BigDecimal currentCost = new BigDecimal(prodTokens[1]);
        prodFromFile.setCostSqFt(currentCost);
        
        BigDecimal currentLaborCost = new BigDecimal(prodTokens[2]);
        
        prodFromFile.setLaborCostSqFt(currentLaborCost);
       
        return prodFromFile;
    }
    
    private void loadProduct() throws FlooringPersistenceException {
        Scanner sc;
        
        try {
            sc = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load product data into memory.", e);
        }
            String currentLine;
            ProductType currentProduct;
            
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentProduct = unmarshallProduct(currentLine);
                
                products.put(currentProduct.getProductType(), currentProduct);
        }
            sc.close();
    }

    @Override
    public ProductType getProduct(String productType) {
        return products.get(productType);
    }

    @Override
    public void loadProductData() throws FlooringPersistenceException{
        loadProduct();
    }
    
    public Map<String, ProductType> getProductTypeInfo() {
        return products;
    }
}
