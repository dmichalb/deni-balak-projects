/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author dmich
 */
@Entity
@Data
public class Plane implements Serializable {
 
    @Id
    @Column(name = "tail_number")
    private String tailNumber;
    
    private int year;
    
    @NotBlank(message = "This field is required.")
    private String make;
    
    @NotBlank(message = "This field is required.")
    private String model;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastServicedDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextServiceDate;
    
    
    
    @ManyToMany
    @JoinTable(name= "directives_plane",
            joinColumns = {@JoinColumn(name = "tailNumber")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Directives> directives;

    
    
     
}
