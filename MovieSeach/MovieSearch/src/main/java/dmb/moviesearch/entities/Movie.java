/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.moviesearch.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
//used Entity and Data for getters and setters

/**
 *
 * @author dmich
 */
@Entity
@Data
public class Movie implements Serializable{
    @Id
    private String title;
    
    private String director;
    
    private String mpaaRating;
    
    private String yearReleased;
    
    private String plot;
    
    private String rating;
    
    private boolean bookmarked;
    
    public boolean isBookmarked(){
        return bookmarked;
    }
    
    public void setBookmarked(boolean bookmarked){
        this.bookmarked = bookmarked;
    }
    
}
