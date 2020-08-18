package corbos.fieldagent.service;

import corbos.fieldagent.data.AgencyRepository;
import corbos.fieldagent.data.AgentRepository;
import corbos.fieldagent.data.AssignmentRepository;
import corbos.fieldagent.data.CountryRepository;
import corbos.fieldagent.data.SecurityClearanceRepository;
import corbos.fieldagent.entities.Agency;
import corbos.fieldagent.entities.Agent;
import corbos.fieldagent.entities.Assignment;
import corbos.fieldagent.entities.Country;
import corbos.fieldagent.entities.SecurityClearance;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class LookupService {
    
    @Autowired
    AgencyRepository agencies;
    
    @Autowired
    CountryRepository countries;
    
    @Autowired
    SecurityClearanceRepository clearances;
 
    
    @Autowired
    AgentRepository agents;
    
    @Autowired
    AssignmentRepository assignments;
    
    /*AGENCY METHODS*/
    public List<Agency> findAllAgencies() {
        return agencies.findAll();
    }
    public Agency findAgencyById(int agencyId) {
        return agencies.findById(agencyId)
                .orElse(null);
    }

    /*AGENT METHODS*/
    public List<Agent> findAllAgents(){
        return agents.findAll();
    }
    public Agent findAgentByIdentifier(String identifier){
        return agents.findById(identifier)
                .orElse(null);
    }
    public void deleteAgentByIdentifier(String identifier){
        List<Assignment> assignmentList = assignments.findByAgentIdentifier(identifier);
        assignments.deleteAll(assignmentList);
        agents.deleteById(identifier);
    }
    public Agent addAgent(Agent thisAgent){
        agents.save(thisAgent);
        return thisAgent;
    }
    public Agent updateAgent(Agent thisAgent){
        return agents.save(thisAgent);
    }
    
    /*ASSIGNMENT METHODS*/
    public List<Assignment> findByAgentIdentifier(String identifier){
        return assignments.findByAgentIdentifier(identifier);
    }
    public void deleteAssignmentsForAgent(String identifier){
        List<Assignment> agentAssignments = assignments.findByAgentIdentifier(identifier);
        assignments.deleteAll(agentAssignments);
    }
    public Assignment findAssignmentById(int assignmentId){
        return assignments.findById(assignmentId).orElse(null);
    }
    public void deleteAssignment(Assignment thisAssginment){
        assignments.delete(thisAssginment);
    }
    public Assignment addAssignment(Assignment thisAssignment, HttpServletRequest request){
        String identifier = request.getParameter("identifier");
        Agent thisAgent = agents.findById(identifier).orElse(null);
        
        thisAssignment.setAgent(thisAgent);
        assignments.save(thisAssignment);
        
        return thisAssignment;
    }
    public Assignment updateAssignment(Assignment thisAssignment){
        return assignments.save(thisAssignment);
    }
    
    
    /*COUNTRY METHODS*/
    public List<Country> findAllCountries() {
        return countries.findAll();
    }
    public Country findCountryByCode(String countryCode) {
        return countries.findById(countryCode)
                .orElse(null);
    }

    /*SECURITY METHODS*/
    public List<SecurityClearance> findAllSecurityClearances() {
        return clearances.findAll();
    }
    public SecurityClearance findSecurityClearanceById(int securityClearanceId) {
        return clearances.findById(securityClearanceId)
                .orElse(null);
    }
    
    
    //VAIDATE AGENT METHOD
    public BindingResult validateAgent(Agent agent, 
            BindingResult result, HttpServletRequest request) {
        
        String agencyId = request.getParameter("agencyId");
        String securityId = request.getParameter("securityClearanceId");
        
        //agency
        if (agencyId == null || agencyId.equals("")){
            FieldError error = new FieldError("agent", "agency", "Agency is required.");
            result.addError(error);
        } else {
            Agency agency = findAgencyById(Integer.parseInt(agencyId));
            agent.setAgency(agency);
        }
        
        //security 
        if(securityId == null || securityId.equals("")){
            FieldError error = new FieldError("agent", "securityClearance", "Security Clearance is required.");
            result.addError(error);
        } else {
            SecurityClearance security = findSecurityClearanceById(Integer.parseInt(securityId));
            agent.setSecurityClearance(security);
        }
        
        //birthDate
        if(agent.getBirthDate() == null 
                || agent.getBirthDate().isAfter(LocalDate.of(1900, 1, 1))
                || agent.getBirthDate().isBefore(LocalDate.now().minusYears(10))) {
            FieldError error = new FieldError("agent", "birthDate", "Birth Date must be between 01/01/1990 and ten years before today's date.");
            result.addError(error);
        } else {
            LocalDate birthDate = agent.getBirthDate();
            agent.setBirthDate(birthDate);
        }
        
        /*//height
        if(agent.getHeight() > 96 || agent.getHeight() < 36) {
            FieldError error = new FieldError("agent", "height", "Height must be between 36 and 96 inches");
            result.addError(error);
        } else {
            int height = agent.getHeight();
            agent.setHeight(height);
        }*/
        
        //activationDate
        if(agent.getActivationDate() == null) {
            FieldError error = new FieldError("agent", "activationDate", "Activation Date is required");
            result.addError(error);
        } else {
            LocalDate activationDate = agent.getActivationDate();
            agent.setActivationDate(activationDate);
        }
               
        return result;
    }
    
    
    //VALIDATE ASSIGNMENT METHOD
    public BindingResult validateAssignment(Assignment assignment, 
            BindingResult result, HttpServletRequest request) {
        
        String countryCode = request.getParameter("countryCode");   
        String identifier = request.getParameter("identifier");
        
        //country
        if(countryCode == null || countryCode.equals("")){
            FieldError error = new FieldError("assignment", "country", "This field is required.");
            result.addError(error);
        } else {
            Country country = findCountryByCode(countryCode);
            assignment.setCountry(country);
        }
        
        //agent
        if(identifier == null || identifier.equals("")) {
            FieldError error = new FieldError("assignment", "agent", "This field is required");
            result.addError(error);
        } else {
            Agent agent = findAgentByIdentifier(identifier);
            assignment.setAgent(agent);
        }
        
        //startDate
        if(assignment.getProjectedEndDate().isBefore(assignment.getStartDate())) {
            FieldError error = new FieldError("assignment", "startDate", "Start Date must be BEFORE Projected End Date");
            result.addError(error);
        } else {
            LocalDate startDate = assignment.getStartDate();
            assignment.setStartDate(startDate);
        }
        
        //projectedEndDate
        if(assignment.getProjectedEndDate().isBefore(assignment.getStartDate())){
            FieldError error = new FieldError("assignment", "proectedEndDate", "Projected End Date must be AFTER Start Date");
            result.addError(error);
        } else {
            LocalDate projectedEndDate = assignment.getProjectedEndDate();
            assignment.setProjectedEndDate(projectedEndDate);
        }
        
        //actualEndDate
        if(assignment.getActualEndDate().isBefore(assignment.getStartDate()) || assignment.getAgent().isIsActive() == true) {
            FieldError error = new FieldError("assignment", "actualEndDate", "Actual End Date must be AFTER Start Date and can"
                    + " only be valid if it is not Active.");
            result.addError(error);
        } else {
            LocalDate actualEndDate = assignment.getActualEndDate();
            assignment.setActualEndDate(actualEndDate);
        }
        return result;
    }
}
