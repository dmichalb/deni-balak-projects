/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author dmich
 */
public class UserIOImpl implements UserIO{
    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }
    
    @Override
    public double readDouble(String prompt) {
        print(prompt);
        return sc.nextDouble();
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double output;
        do {
            output = readDouble(prompt);
        } while (output < min || output> max);
        return output;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        return sc.nextFloat();
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float output;
        do{
            output = readFloat(prompt);
        } while (output < min || output > max);
        return output;
    }

    @Override
    public int readInt(String prompt){
        boolean error;
        int output = 0;
        do {
            error = false;
            print(prompt);
            if (sc.hasNextInt()){
                output = sc.nextInt();
            } else {
                sc.nextLine();
                print("FAILURE: Please enter an int!");
                error = true;
            }
        } while (error);
        sc.nextLine();
        return output;
    }
    
    @Override
    public int readInt(String prompt, int min, int max) {
        int output;
        do {
            output = readInt(prompt);
        } while (output < min || output > max);
        return output;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        return sc.nextLong();
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long output;
        do {
            output = readLong(prompt);
        } while (output < min || output > max);
        return output;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String temp = sc.nextLine();
        return temp;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        print(prompt);
        BigDecimal temp = sc.nextBigDecimal();
        sc.nextLine();
        return temp;
    }
    /*
    public BigDecimal readBigDecimal(String prompt, boolean allowEmpty) {
        boolean error;
        
        do {
        error = false;
        print(prompt);
        if (allowEmpty) {
            String userInput = sc.nextLine();
            if (userInput == "") {
                return new BigDecimal(-1000);
            } else {
                return ;
            }
        }
        
        
        BigDecimal temp = sc.nextBigDecimal();
        sc.nextLine();
        return temp;
        } while (error);
    }*/

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal output;
        do {
            output = readBigDecimal(prompt);
        } while (output.compareTo(min) == -1 || output.compareTo(max) == 1);
        return output;
    }

    @Override
    public String readString(String prompt, List<String> availableResponses) {
        boolean unavailable;
        int counter = 0;
        String userInput = "";
        
        do {
            unavailable = true;
            if (counter != 0) {
                print("Error");
            }
            print(prompt);
            if (sc.hasNextLine()) {
                userInput = sc.nextLine();
                if (availableResponses.contains(userInput)) {
                    unavailable = false;
                } 
            }
            counter++;
        } while (unavailable);
            return userInput;
    }
}
