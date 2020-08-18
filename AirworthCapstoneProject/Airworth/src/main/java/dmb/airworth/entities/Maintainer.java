/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;


/**
 *
 * @author dmich
 */
@Entity
@Data
public class Maintainer implements Serializable {
    
    @Id
    private String username;
    
    private String firstName;
    
    private String lastName;
    
}
