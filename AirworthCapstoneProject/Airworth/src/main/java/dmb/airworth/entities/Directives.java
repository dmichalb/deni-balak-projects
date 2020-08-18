/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dmich
 */
@Entity
@Data
public class Directives implements Serializable {
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private int id;
    
    private String tailNumber;
    
    private String make;
    
    private String model;
    
    private String compliance;
    
    private String adNumber;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;
    
    private String subject;
   
    
   /* @ManyToMany(mappedBy = "directives")
    private List<Plane> planes;*/
    
    @NotNull
     private boolean notCompliedWith;   
     
     
     public boolean isNotCompliedWith(){
        return notCompliedWith;
     }
     public void setNotCompliedWith(boolean notCompliedWith){
         this.notCompliedWith = notCompliedWith;
     }
}
