/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.emailapplication;

import dmb.controller.Controller;
import dmb.dao.Dao;
import dmb.dao.DaoFileImpl;
import dmb.dao.PersistenceException;
import dmb.service.DataValidationException;
import dmb.service.DuplicateException;
import dmb.service.ServiceLayer;
import dmb.service.ServiceLayerImpl;
import dmb.ui.UserIO;
import dmb.ui.UserIOImpl;
import dmb.ui.View;

/**
 *
 * @author dmich
 */
public class App {
    public static void main(String[] args) throws PersistenceException, DuplicateException, DataValidationException {
        UserIO io = new UserIOImpl();
        View view = new View(io);
        Dao dao = new DaoFileImpl();
        ServiceLayer service = new ServiceLayerImpl(dao);
        Controller controller = new Controller(service, dao, view);
        controller.run();
    }
    
}
