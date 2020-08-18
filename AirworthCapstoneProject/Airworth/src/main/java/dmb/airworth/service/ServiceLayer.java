/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.service;

import dmb.airworth.data.AdminRepository;
import dmb.airworth.data.DirectivesRepository;
import dmb.airworth.data.MaintainerRepository;
import dmb.airworth.data.PlaneRepository;
import dmb.airworth.entities.Admin;
import dmb.airworth.entities.Directives;
import dmb.airworth.entities.Maintainer;
import dmb.airworth.entities.Plane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author dmich
 */
@Service
public class ServiceLayer {
    
    @Autowired
    DirectivesRepository directivesRepo;
    
    @Autowired
    MaintainerRepository maintainerRepo;
    
    @Autowired
    PlaneRepository planeRepo;
    
    @Autowired
    AdminRepository adminRepo;
    
    //Admin Methods
    public List<Admin> findAllAdmins(){
        return adminRepo.findAll();
    }
    public String findAdminId(Admin admin){
        return admin.getUsername();
    }
    public Admin findAdminById(String username){
        return adminRepo.findById(username).orElse(null);
    }
    public Admin addAdmin(Admin admin){
        adminRepo.save(admin);
        return admin;
    }
    public Admin editAdmin(Admin admin){
        return adminRepo.save(admin);
    }
    public void deleteAdmin(String username){
        Admin admin = adminRepo.findById(username).orElse(null);    
        adminRepo.delete(admin);
    }
    private List<String> findAllAdminUsernames(){
        List<String> usernames = new ArrayList<>();
        List<Admin> admins = adminRepo.findAll();
        
        for(Admin admin : admins){
            String username = admin.getUsername();
            usernames.add(username);
        }
        return usernames;
    }
    public BindingResult validateAdminIdExists(String username, BindingResult result) {
        boolean exists = false;
        List<String> usernames = findAllAdminUsernames();
        
        for(String thisUsername : usernames){
            if(thisUsername.equals(username)) {
               exists = true;
            }
        }
        if(!exists){
            FieldError error = new FieldError("admin", "username",
                    "This username does not exist. Try again.");
            result.addError(error);
        }
        return result;
    }
 
    
    
    //Validate Add Admin
    public BindingResult validateAdmin(Admin admin, BindingResult result){
        List<String> usernames = findAllUsernames();
        
        if(admin.getFirstName() == null || admin.getFirstName().equals("")){
            FieldError error = new FieldError("admin", "firstName", "This field is required.");
            result.addError(error);
        } else {
            String firstName = admin.getFirstName();
            admin.setFirstName(firstName);
        }
        
        if(admin.getLastName() == null || admin.getLastName().equals("")){
            FieldError error = new FieldError("admin", "lastName", "This field is required.");
            result.addError(error);
        } else {
            String lastName = admin.getLastName();
            admin.setLastName(lastName);
        }
        
        if(admin.getUsername() == null || admin.getUsername().equals("")){
            FieldError error = new FieldError("admin", "username", "This field is required.");
            result.addError(error);
        } else {
            String username = admin.getUsername();
            admin.setUsername(username);
        }
        
        for(String thisUsername : usernames){
            if(thisUsername.equals(admin.getUsername())) {
                FieldError error = new FieldError("admin", "username", "This username is taken. Try another.");
                result.addError(error);
            } else {
                String username = admin.getUsername();
                admin.setUsername(username);
            }
        }
        return result;
    }
    

    
    //Maintainer Methods
    public String findMaintainerId(Maintainer maintainer){
        return maintainer.getUsername();
    }
    public List<Maintainer> findAllMaintainers(){
        return maintainerRepo.findAll();
    }
    public Maintainer findByUsername(String username){
        return maintainerRepo.findById(username).orElse(null);
    }
    public Maintainer addMaintainer(Maintainer maintainer){
        maintainerRepo.save(maintainer);
        return maintainer;
    }
    public Maintainer editMaintainer(Maintainer maintainer){
        return maintainerRepo.save(maintainer);
    }
    public void deleteMaintainer(String username){
        Maintainer maintainer = maintainerRepo.findById(username).orElse(null);    
        maintainerRepo.delete(maintainer);
    }
    private List<String> findAllUsernames(){
        List<String> usernames = new ArrayList<>();
        List<Maintainer> maintainers = maintainerRepo.findAll();
        
        for(Maintainer maintainer : maintainers){
            String username = maintainer.getUsername();
            usernames.add(username);
        }
        return usernames;
    }
    public BindingResult validateMaintainerIdExists(String username, BindingResult result) {
        boolean exists = false;
        List<String> usernames = findAllUsernames();
        for(String thisUsername : usernames){
            if(thisUsername.equals(username)) {
                exists = true;
            }
        }
        if(!exists){
            FieldError error = new FieldError("maintainer", "username", "ID does not exist. Try again.");
            result.addError(error);
        }
        return result;
    }
    
    
    //Validate Add Maintainer
    public BindingResult validateMaintainer(Maintainer maintainer, BindingResult result){
        
        if(maintainer.getFirstName() == null || maintainer.getFirstName().equals("")){
            FieldError error = new FieldError("maintainer", "firstName", "This field is required.");
            result.addError(error);
        } else {
            String firstName = maintainer.getFirstName();
            maintainer.setFirstName(firstName);
        }
        
        if(maintainer.getLastName() == null || maintainer.getLastName().equals("")){
            FieldError error = new FieldError("maintainer", "lastName", "This field is required.");
            result.addError(error);
        } else {
            String lastName = maintainer.getLastName();
            maintainer.setLastName(lastName);
        }
        
        if(maintainer.getUsername() == null || maintainer.getUsername().equals("")){
            FieldError error = new FieldError("maintainer", "username", "This field is required.");
            result.addError(error);
        } else {
            String username = maintainer.getUsername();
            maintainer.setUsername(username);
        }
        
        return result;
    }

    
    //Plane Methods
    public List<Plane> findAll(){
        return planeRepo.findAll();
    }
    public Plane findById(String tailNumber){
        Plane plane = planeRepo.findById(tailNumber).orElse(null);
        if(plane.getNextServiceDate() == null){
            settingNextService(plane);
        }
        return plane;
    }
    
    public Plane editPlane(Plane plane){
        return planeRepo.save(plane);
    }
    public void deletePlane(String tailNumber){
        planeRepo.deleteById(tailNumber);
    }
    public List<String> findAllTailNumbers(){
        List<String> tailNumbers = new ArrayList<>();
        List<Plane> allPlanes = planeRepo.findAll();
        
        for(Plane plane : allPlanes){
            String tailNumber = plane.getTailNumber();
            tailNumbers.add(tailNumber);
        }
        return tailNumbers;
    }
    public LocalDate settingNextService(Plane plane){
        LocalDate lastServicedDate = plane.getLastServicedDate();
        LocalDate nextServiceDate = lastServicedDate.plusWeeks(2).plusDays(4);
        plane.setNextServiceDate(nextServiceDate);
        
        return nextServiceDate;
    }
    public LocalDate settingLastServicedDate(Plane plane){
        LocalDate newLastServicedDate = LocalDate.now();
        plane.setLastServicedDate(newLastServicedDate);
        
        return newLastServicedDate;
    }
    
   
    //airworthy planes
    public List<Plane> airworthyPlanes(List<Plane> allPlanes, List<Plane> groundedPlanes){
        List<Plane> airworthyPlanes = new ArrayList<>();
        
        for(Plane plane : allPlanes){
            if(!groundedPlanes.contains(plane)){
                airworthyPlanes.add(plane);
            }
        }
        
        
        /*
        
        int notCompliedCounter = 0;
        List<Plane> serviceNotDue = new ArrayList<>();
        
        
        for(Plane plane : allPlanes){
            if(plane.getNextServiceDate() == null) {
               settingNextService(plane); 
            }
            
            
            LocalDate proposedNextService = plane.getLastServicedDate().plusWeeks(2).plusDays(4);
            if(plane.getNextServiceDate().isAfter(LocalDate.now()) && plane.getNextServiceDate().equals(proposedNextService)){
                serviceNotDue.add(plane);
            }
        }
        for (Plane plane : serviceNotDue){
            String tailNumber = plane.getTailNumber();
            List<Directives> planeDirectives = planeDirectives(tailNumber);
            plane.setDirectives(planeDirectives);
            
            
            List<Directives> directiveList = plane.getDirectives();
            for (Directives directive : directiveList) {
                if(directive.isNotCompliedWith() == true){
                    notCompliedCounter++;
                }
            } 
            if(notCompliedCounter == 0){
                airworthyPlanes.add(plane);
            }
        }
        return airworthyPlanes;*/
        return airworthyPlanes;
    }
    
    //grounded planes
    public List<Plane> groundedPlanes(List<Plane> allPlanes){
        List<Plane> groundedPlanes = new ArrayList<>();

        boolean addPlane = false;

        for(Plane plane : allPlanes){
            addPlane = false;
            
            LocalDate proposedNextService = plane.getLastServicedDate().plusWeeks(2).plusDays(4);
            if(plane.getNextServiceDate() == null) {
               settingNextService(plane); 
            }
            
            if(plane.getNextServiceDate().isBefore(proposedNextService) | plane.getNextServiceDate().isBefore(LocalDate.now())){
                addPlane = true;
            }
            
            String tailNumber = plane.getTailNumber();
            List<Directives> planeDirectives = planeDirectives(tailNumber);
            
            for (Directives directive : planeDirectives) {
                if(directive.isNotCompliedWith()){
                    addPlane = true;
                }
            }
            if(addPlane){
                groundedPlanes.add(plane);
            }
        }
        return groundedPlanes; 
    }
    
    
    //BELOW METHOD CHANGES ALL DIRECTIVES APPLYING TO PLANE OF SIMILAR MAKE/MODEL
    //NEED TO JUST APPLY TO THE PLANE (TAIL NUMBER)
    //ALSO CAUSES DUPLICATE ENTRY IN GROUNDED TABLE IF PLANE IS GROUNDED
    
    //Update Plane's Directives
    public void updateDirectives(Plane plane, HttpServletRequest request){
        String tailNumber = plane.getTailNumber();
        planeDirectives(tailNumber);
        List<Directives> planeDirectives = plane.getDirectives();
        
        for(Directives directive : planeDirectives){
            String value = request.getParameter("notCompliedWith_" + directive.getId()); 
            boolean checked = false;

            if (value == null) {
                directive.setNotCompliedWith(false);
                checked = false;
            } else {
                if (value.equals("on")){
                checked = true;
                } else {
                    checked = false;
                }
            }

            directive.setNotCompliedWith(checked);
            directivesRepo.save(directive); 
        }
        
    }
    
    
    
    //Validate tail number exists
    public BindingResult validateTailNumberExists(String tailNumber, BindingResult result) {
        boolean exists = false;
        List<String> tailNumbers = findAllTailNumbers();
        
        for(String thisTailNumber : tailNumbers){
            if(thisTailNumber.equals(tailNumber)){
                exists = true;
            }
        }
        if(!exists) {
            FieldError error = new FieldError("plane", "tailNumber", "This tail number "
                    + "does not exist. Try again.");
            result.addError(error);
        }
        return result;
    }
    

    
    public List<Directives> planeDirectives(String tailNumber){
        List<Directives> planeSpecificDirectives = new ArrayList<>();
        Plane plane = findById(tailNumber);
        
        
        if(tailNumber.equals("N49785")){
            planeSpecificDirectives = N49785();
            
        } else if(tailNumber.equals("N757QH")){
            planeSpecificDirectives = N757QH();
            
        } else if(tailNumber.equals("N4702P")){
            planeSpecificDirectives = N4702P();
            
        } else if(tailNumber.equals("N5241Q")){
            planeSpecificDirectives = N5241Q();
            
        } else if(tailNumber.equals("N4711L")){
            planeSpecificDirectives = N4711L();
            
        } else if(tailNumber.equals("N5442B")){
         planeSpecificDirectives = N5442B();
         
        } else if(tailNumber.equals("N25497")){
        planeSpecificDirectives = N25497();
        
        } else if(tailNumber.equals("N73381")){
        planeSpecificDirectives = N73381();
        
        } else if(tailNumber.equals("N5284Q")){
        planeSpecificDirectives = N5284Q();
        
        } else if(tailNumber.equals("N53603")){
        planeSpecificDirectives = N53603();
        
        } else if(tailNumber.equals("N78445")){
        planeSpecificDirectives = N78445();
        
        } else if(tailNumber.equals("N9899G")){
        planeSpecificDirectives = N9899G();
            
        } else {
            planeSpecificDirectives = null;
        }
        
        plane.setDirectives(planeSpecificDirectives);
        
        return planeSpecificDirectives;
    }
    
    
    private List<Directives> N49785(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N49785 = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N49785")){
                N49785.add(directive);
            }
        }
        return N49785;
    }
    private List<Directives> N757QH(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N757QH = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N757QH")){
                N757QH.add(directive);
            }
        }
        return N757QH;
    }
    private List<Directives> N4702P(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N4702P = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N4702P")){
                N4702P.add(directive);
            }
        }
        return N4702P;
    }
    private List<Directives> N5241Q(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N5241Q = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N5241Q")){
                N5241Q.add(directive);
            }
        }
        return N5241Q;
    }
    private List<Directives> N4711L(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N4711L = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N4711L")){
                N4711L.add(directive);
            }
        }
        return N4711L;
    }
    private List<Directives> N5442B(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N5442B = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N5442B")){
                N5442B.add(directive);
            }
        }
        return N5442B;
    }
    private List<Directives> N25497(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N25497 = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N25497")){
                N25497.add(directive);
            }
        }
        return N25497;
    }
    private List<Directives> N73381(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N73381 = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N73381")){
                N73381.add(directive);
            }
        }
        return N73381;
    }
    private List<Directives> N5284Q(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N5284Q = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N5284Q")){
                N5284Q.add(directive);
            }
        }
        return N5284Q;
    }
    private List<Directives> N53603(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N53603 = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N53603")){
                N53603.add(directive);
            }
        }
        return N53603;
    }
    private List<Directives> N78445(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N78445 = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N78445")){
                N78445.add(directive);
            }
        }
        return N78445;
    }
    private List<Directives> N9899G(){
        List<Directives> allDirectives = directivesRepo.findAll();
        List<Directives> N9899G = new ArrayList<>();
        
        for(Directives directive : allDirectives){
            if(directive.getTailNumber().equals("N9899G")){
                N9899G.add(directive);
            }
        }
        return N9899G;
    }

}
