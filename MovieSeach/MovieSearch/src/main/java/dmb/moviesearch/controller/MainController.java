/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.moviesearch.controller;

import dmb.moviesearch.entities.Movie;
import dmb.moviesearch.service.ServiceLayer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author dmich
 */
@Controller
public class MainController {
    @Autowired
    ServiceLayer service;
    
    //Get Mapping Home Page
    @GetMapping("/")
    public String home(Movie movie, Model model){

        model.addAttribute("movie", movie);
        return "home";
    }
    
    //Get Mapping Movie Info
    @GetMapping("/movieInfo")
    public String movieInfo(Movie movie, Model model){
        
        String title = service.findMovieTitle(movie);
        
        model.addAttribute("movie", movie);
        model.addAttribute("title", title);
        
        return "movieInfo";
    }
    
    //Post Mapping Edit Movie
    @PostMapping("/movieInfo")
    public String performEditMovie(String title, HttpServletRequest request){
        Movie movie = service.findByTitle(title);
        service.bookmarkMovie(movie, request);
        service.editMovie(movie);
        
        return "redirect:/moviesList";
    }
    
    //Get Mapping Favorites Page
    @GetMapping("/moviesList")
    public String moviesList(Movie movie, Model model){
        
        List<Movie> allMovies = service.findAllMovies();
        
        String title = service.findMovieTitle(movie);
               
        model.addAttribute("movie", movie);
        model.addAttribute("title", title);
        model.addAttribute("allMovies", allMovies);
        
        return "moviesList";
    }
    
    @PostMapping("/deleteMovie")
    public String deleteMovie(HttpServletRequest request){
        
        String title = request.getParameter("title");
        service.deleteMovie(title);
        
        return "redirect:/moviesList";
    }
  
    //Post Mapping Add Movie
    @PostMapping("/")
    public String performAddMovie(Movie movie, BindingResult result, HttpServletRequest request){
                
        service.addMovie(movie);
        
        return "redirect:/moviesList";
    }
    
}
