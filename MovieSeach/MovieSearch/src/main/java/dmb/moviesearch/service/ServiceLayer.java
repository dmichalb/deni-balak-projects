/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.moviesearch.service;

import dmb.moviesearch.data.MovieRepository;
import dmb.moviesearch.entities.Movie;
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
    MovieRepository movieRepo;
    
    public List<Movie> findAllMovies(){
        return movieRepo.findAll();
    }
    
    public Movie findByTitle(String title){
        return movieRepo.findById(title).orElse(null);
    }
    
    public Movie addMovie(Movie movie){
        movieRepo.save(movie);
        return movie;
    }
    
    public Movie editMovie(Movie movie){
        return movieRepo.save(movie);
    }
    
    public void deleteMovie(String title){
        Movie movie = movieRepo.findById(title).orElse(null);
        movieRepo.delete(movie);
    }
    
    public String findMovieTitle(Movie movie){
        String title = movie.getTitle();
        return title;
    }
    
    public boolean isMovieBookmarked(Movie movie){
        boolean isBookmarked = movie.isBookmarked();
        return isBookmarked;
    }
    
    public String findMovieRating(Movie movie){
        String rating = movie.getRating();
        return rating;
    }
     
    //checks if the boolean isBookmarked is checked
    public void bookmarkMovie(Movie movie, HttpServletRequest request){
        
        String value = request.getParameter("bookmarked_" + movie.getTitle());
        boolean checked = false;
        
        if(value == null){
            movie.setBookmarked(false);
                checked = false;
        } else {
            if(value.equals("on")){
            checked = true;
            } else {
            checked = false;
            }
        }
        movie.setBookmarked(checked);
        movieRepo.save(movie);
    }
    
    
    
    
}
