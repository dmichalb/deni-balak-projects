/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.moviesearch.data;

import dmb.moviesearch.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
//used Java Persistence API for basic CRUD functionalities

/**
 *
 * @author dmich
 */
public interface MovieRepository extends 
        JpaRepository<Movie, String>{
    
}
