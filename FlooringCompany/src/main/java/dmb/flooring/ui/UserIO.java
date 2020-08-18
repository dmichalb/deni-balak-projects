/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.ui;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author dmich
 */
public interface UserIO {
    void print(String msg);
    
    double readDouble(String prompt);
    double readDouble (String prompt, double min, double max);
    
    float readFloat(String prompt);
    float readFloat(String prompt, float min, float max);
    
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    
    BigDecimal readBigDecimal(String prompt);
    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);
    //public BigDecimal readBigDecimal(String prompt, boolean allowEmpty);
    
    String readString(String prompt);
    String readString(String prompt, List<String> availableResponses);
}
