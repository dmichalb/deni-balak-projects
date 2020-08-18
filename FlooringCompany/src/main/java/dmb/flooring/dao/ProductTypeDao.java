/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.dao;

import dmb.flooring.dto.ProductType;
import java.util.Map;

/**
 *
 * @author dmich
 */
public interface ProductTypeDao {
    
    ProductType getProduct(String productType);
    
    void loadProductData() throws FlooringPersistenceException;
    
    Map<String, ProductType> getProductTypeInfo();
}
