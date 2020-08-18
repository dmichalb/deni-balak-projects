/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.controller;

import dmb.airworth.entities.Admin;
import dmb.airworth.entities.Directives;
import dmb.airworth.entities.Maintainer;
import dmb.airworth.entities.Plane;
import dmb.airworth.service.ServiceLayer;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author dmich
 */
@Controller
public class MainController {
    
    @Autowired
    ServiceLayer service;
    
    //Get Home Page
    @GetMapping("/")
    public String home(Maintainer maintainer, Admin admin, Model model){
        List<Admin> admins = service.findAllAdmins();
        String adminUsername = service.findAdminId(admin);
       
        List<Maintainer> maintainers = service.findAllMaintainers();
        String username = service.findMaintainerId(maintainer);
        
        model.addAttribute("admins", admins);
        model.addAttribute("adminUsername", adminUsername);
        
        model.addAttribute("maintainers", maintainers);
        model.addAttribute("username", username);
        return "home";
    }
    

    
    //Add Maintainer Page
    @GetMapping("/addMaintainer")
    public String addMaintainer(Maintainer maintainer){
        
        return "addMaintainer";
    }
    
    //Add Maintainer
    @PostMapping("/addMaintainer")
    public String performAddMaintainer(@Valid Maintainer thisMaintainer,
            BindingResult result) {
        
        result = service.validateMaintainer(thisMaintainer, result);
        if(result.hasErrors()){
            return "addMaintainer";
        }
        service.addMaintainer(thisMaintainer);
        return "redirect:/";
    }
    
    //Add Admin Page
    @GetMapping("/addAdmin")
    public String addAdmin(Admin admin){
        return "addAdmin";
    }
    
    //Add Admin
    @PostMapping("/addAdmin")
    public String performAddAdmin(@Valid Admin thisAdmin, BindingResult result){
        result = service.validateAdmin(thisAdmin, result);
        
        if(result.hasErrors()){
            return "addAdmin";
        }
        
        service.addAdmin(thisAdmin);
        return "redirect:/admin?hidden=&username=" + thisAdmin.getUsername();
    }


    
    //Get Maintainer Page
    @GetMapping("/maintainer")
    public String maintainerPage(@Valid Maintainer maintainer, Model model, Plane plane, BindingResult result){
        String username = maintainer.getUsername();
        result = service.validateMaintainerIdExists(username, result);
        if(result.hasErrors()){

            return "redirect:/";
        }
                
        List<Plane> allPlanes = service.findAll();
        
        List<Plane> groundedPlanes = service.groundedPlanes(allPlanes);
        
        List<Plane> airworthyPlanes = service.airworthyPlanes(allPlanes, groundedPlanes);
        
        
        
        model.addAttribute("plane", plane);
        model.addAttribute("allPlanes", allPlanes);
        model.addAttribute("airworthyPlanes", airworthyPlanes);
        model.addAttribute("groundedPlanes", groundedPlanes);
        
        return "maintainer";
    }
    
    
    //Get Admin Page
    @GetMapping("/admin")
    public String adminPage(@Valid Admin admin, Model model, BindingResult result){
        String adminUsername = admin.getUsername();
        result = service.validateAdminIdExists(adminUsername, result);
        if(result.hasErrors()){
            
            return "redirect:/";
        }
        
        
        List<Admin> admins = service.findAllAdmins();
        
        List<Maintainer> maintainers = service.findAllMaintainers();
        
        List<Plane> allPlanes = service.findAll();
        
        List<Plane> groundedPlanes = service.groundedPlanes(allPlanes);
        
        List<Plane> airworthyPlanes = service.airworthyPlanes(allPlanes, groundedPlanes);
        
        
        
        
        model.addAttribute("admin", admin);
        model.addAttribute("admins", admins);
        model.addAttribute("adminUsername", adminUsername);
        model.addAttribute("maintainers", maintainers);
        model.addAttribute("allPlanes", allPlanes);
        model.addAttribute("airworthyPlanes", airworthyPlanes);
        model.addAttribute("groundedPlanes", groundedPlanes);
        
        return "admin";
    }
    //Edit Maintainer Page
    @GetMapping("/editMaintainer")
    public String editMaintainer(String username, String adminUsername, Model model){
        Maintainer maintainer = service.findByUsername(username);
        Admin admin = service.findAdminById(adminUsername);
        
        model.addAttribute("admin", admin);
        model.addAttribute("maintainer", maintainer);
        model.addAttribute("adminUsername", adminUsername);
        return "editMaintainer";
    }
    
    //Edit Maintainer
    @PostMapping("/editMaintainer")
    public String performEditMaintainer(@Valid Maintainer maintainer, BindingResult result, HttpServletRequest request){
        result = service.validateMaintainer(maintainer, result);
        
        if(result.hasErrors()){
            return "editMaintainer";
        }
        
        service.editMaintainer(maintainer);
        return "redirect:/admin?hidden=&username=" + request.getParameter("adminUsername");
    }
    
    
    
    //Edit Admin Page
    @GetMapping("/editAdmin")
    public String editAdmin(String username, Model model){
        Admin admin = service.findAdminById(username);
        
        model.addAttribute("admin", admin);
        return "editAdmin";
    }
    
    //Edit Admin
    @PostMapping("/editAdmin")
    public String performEditAdmin(@Valid Admin admin, BindingResult result){
        result = service.validateAdmin(admin, result);
        
        if(result.hasErrors()){
            return "editAdmin";
        }
        
        Admin thisAdmin = service.editAdmin(admin);
        return "redirect:/admin?hidden=&username=" + thisAdmin.getUsername();
    }
    
    //Delete Maintainer
    @PostMapping("/deleteMaintainer")
    public String deleteMaintainer(HttpServletRequest request){
        String username = request.getParameter("username");
        service.deleteMaintainer(username);
        return "redirect:/admin";
    }
    
    //Delete Admin
    @PostMapping("/deleteAdmin")
    public String deleteAdmin(HttpServletRequest request){
        String username = request.getParameter("username");
        service.deleteAdmin(username);
        return "redirect:/";
    }
    
    
    
    //Edit Plane Get 
    @GetMapping("/editPlane")
    public String editPlane(String tailNumber, String maintainerUsername, Model model){
       
        Plane plane = service.findById(tailNumber);
        List<Directives> planeDirectives = service.planeDirectives(tailNumber);
        Maintainer maintainer = service.findByUsername(maintainerUsername);
        
        model.addAttribute("plane", plane);
        model.addAttribute("maintainer", maintainer);
        model.addAttribute("maintainerUsername", maintainerUsername);
        model.addAttribute("planeDirectives", planeDirectives);
       
        return "editPlane";
    }
    
    
    //Edit Plane (Maintainer) 
    @PostMapping("/editPlane")
    public String performEditPlane(String tailNumber, HttpServletRequest request){
        Plane plane = service.findById(tailNumber);
        service.updateDirectives(plane, request);
        
        service.settingLastServicedDate(plane);
        service.settingNextService(plane);
        
        service.editPlane(plane);
     
        return "redirect:/maintainer?hidden=&username=" + request.getParameter("maintainerUsername");
    }
    
    //Edit Plane Admin Get
    @GetMapping("/adminEditPlane")
    public String adminEditPlane(String tailNumber, String adminUsername, Model model){
        
        Plane plane = service.findById(tailNumber);
        Admin admin = service.findAdminById(adminUsername);
        
        
        model.addAttribute("plane", plane);
        model.addAttribute("admin", admin);
        model.addAttribute("adminUsername", adminUsername);
       
        
        return "adminEditPlane";
    }
    
    //Edit Plane (Admin)
    @PostMapping("/adminEditPlane")
    public String performAdminEditPlane(Plane plane, HttpServletRequest request){
        service.settingLastServicedDate(plane);
        service.editPlane(plane);
        
     
        return "redirect:/admin?hidden=&username=" + request.getParameter("adminUsername");
    }
}
 