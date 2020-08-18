/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.ui;

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
    public double readDouble(String prompt){
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
    public double readDouble(String prompt, double min, double max) {
        double output;
        do {
            output = readDouble(prompt);
        } while (output < min || output> max);
        return output;
    }

    @Override
    public float readFloat(String prompt){
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
    public long readLong(String prompt){
        boolean error;
        long output = 0;
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
        return sc.nextLine();   
    }
}
