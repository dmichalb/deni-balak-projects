/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.flooring;

import dmb.flooring.controller.FlooringController;
import dmb.flooring.dao.FlooringPersistenceException;
import java.util.Properties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dmich
 */
public class App {
    public static void main(String[] args) throws FlooringPersistenceException {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringController controller = ctx.getBean("controller", FlooringController.class);
    
    controller.run();
    
    }
}

