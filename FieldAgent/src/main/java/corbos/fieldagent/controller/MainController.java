/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.fieldagent.controller;

import corbos.fieldagent.entities.Agency;
import corbos.fieldagent.entities.Agent;
import corbos.fieldagent.entities.Assignment;
import corbos.fieldagent.entities.Country;
import corbos.fieldagent.entities.SecurityClearance;
import corbos.fieldagent.service.LookupService;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Validation;
import javax.validation.Validator;
import jdk.internal.agent.resources.agent;



/**
 *
 * @author dmich
 */
@Controller
public class MainController {
       
    @Autowired
    LookupService service;
    
    /*AGENT METHODS*/
    @GetMapping("/")
    public String home(Model model){
        List<Agent> agentList = service.findAllAgents();
        model.addAttribute("agents", agentList);
        return "home";
    }
    @GetMapping("/delete")
    public String deleteAgent(Model model, String identifier){
        Agent thisAgent = service.findAgentByIdentifier(identifier);
        List<Assignment> assignmentList = service.findByAgentIdentifier(identifier);
        int assignmentCounter = assignmentList.size();
        model.addAttribute("assignmentCount", assignmentCounter);
        model.addAttribute("agent", thisAgent);
        return "delete";
    }
    @PostMapping("deleteAgent")
    public String performDeleteAgent(HttpServletRequest request) {
        String identifier = request.getParameter("identifier");
        service.deleteAgentByIdentifier(identifier);
        return "redirect:/";
    }
    @GetMapping("/addAgent")
    public String addAgent(Agent agent, Model model){
        List<Agency> agencyList = service.findAllAgencies();
        List<SecurityClearance> securityList = service.findAllSecurityClearances();
        model.addAttribute("agencyList", agencyList);
        model.addAttribute("securityList", securityList);
        
        return "addAgent";
    } 
    @PostMapping("/addAgent")
    public String performAddAgent(@Valid Agent thisAgent, BindingResult result, 
            HttpServletRequest request, Model model){
        
        result = service.validateAgent(thisAgent, result, request);
        if(result.hasErrors()) {
            model.addAttribute("agencies", service.findAllAgencies());
            model.addAttribute("securities", service.findAllSecurityClearances());
            return "addAgent";
        }
        service.addAgent(thisAgent);
        return "redirect:/";
    }
    @GetMapping("/updateAgent{id}")
    public String updateAgent(String identifier, Model model){
        Agent thisAgent = service.findAgentByIdentifier(identifier);
        List<Agency> agencyList = service.findAllAgencies();
        List<SecurityClearance> securityList = service.findAllSecurityClearances();
        List<Assignment> agentAssignment = service.findByAgentIdentifier(identifier);
        model.addAttribute("agentAssignment", agentAssignment);
        model.addAttribute("agencyList", agencyList);
        model.addAttribute("securityList", securityList);
        model.addAttribute("agent", thisAgent);
        
        return "updateAgent";
    }
    @PostMapping("updateAnAgent")
    public String performUpdateAgent(Agent thisAgent){
        service.updateAgent(thisAgent);
    
        return "redirect:/";
    }

    
    
    /*ASSIGNMENT METHODS*/
    @GetMapping("/viewAssignments")
    public String viewAgentAssignments(String identifier, Model model){
        Agent agent = service.findAgentByIdentifier(identifier);
        List<Assignment> assignmentList = service.findByAgentIdentifier(identifier);
        
        model.addAttribute("agent", agent);
        model.addAttribute("assignments", assignmentList);
        return "assignment";
    }    
    @PostMapping("/deleteAnAssignment")
    public String deleteAnAssignment(HttpServletRequest request, Model model){
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        Assignment thisAssignment = service.findAssignmentById(assignmentId);
        model.addAttribute("assignment", thisAssignment);
        service.deleteAssignment(thisAssignment);
        return "redirect:/";
    }
    @GetMapping("/addAssignment")
    public String addAssignment(Assignment assignment, Model model){
        List<Country> countryList = service.findAllCountries();
        List<Agent> agentList = service.findAllAgents();
        model.addAttribute("countryList", countryList);
        model.addAttribute("agentList", agentList);
        return "addAssignment";
    }
    @PostMapping("/addAssignment")
    public String performAddAssignment(@Valid Assignment thisAssignment, 
            BindingResult result, HttpServletRequest request, Model model){
        
        result = service.validateAssignment(thisAssignment, result, request);
        if(result.hasErrors()){
            model.addAttribute("countryList", service.findAllCountries());
            model.addAttribute("agentList", service.findAllAgents());
            return "addAssignment";
        }
        
        
        service.addAssignment(thisAssignment, request);
        return "redirect:/";
    }
    @GetMapping("/updateAssignment")
    public String getUpdateAssignment(int assignmentId, Model model){
        Assignment thisAssignment = service.findAssignmentById(assignmentId);
        List<Country> countryList = service.findAllCountries();
        List<Agent> agentList = service.findAllAgents();
        model.addAttribute("agentList", agentList);
        model.addAttribute("assignment", thisAssignment);
        model.addAttribute("countryList", countryList);
        
        return "updateAssignment";
    }
    @PostMapping("updateAnAssignment")
    public String updateAnAssignment(Assignment thisAssignment) {
        service.updateAssignment(thisAssignment);
        return "redirect:/";
    }

}
