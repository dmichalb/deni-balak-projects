package corbos.fieldagent.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class Agent implements Serializable {

    @Id
    private String identifier;
    
    @NotBlank(message = "First name is required.")
    private String firstName;
    
    private String middleName;
    
    
    @NotBlank(message = "Last name is required.")
    private String lastName;
    
    private String pictureUrl;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @Range(min=36, max=96)
    private int height;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate activationDate;
    
    
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "security_clearance_id")
    private SecurityClearance securityClearance;

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
