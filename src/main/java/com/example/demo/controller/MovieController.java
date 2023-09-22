package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/Movies")
public class MovieController {
	@Autowired
    private MovieService movieService;
	
	// Create a new user
    @PostMapping
    public Movie createUser(@RequestBody Movie user) {
        return movieService.createMovie(user);
    }

    // Get all users
    @GetMapping
    public List<Movie> getAllUsers() {
        return movieService.getAllMovies();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Optional<Movie> getUserById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    // Update user by ID
    @PutMapping("/{id}")
    public Movie updateUser(@PathVariable Long id, @RequestBody Movie movieDetails) {
        return movieService.updateMovie(id, movieDetails);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
    	movieService.deleteMovie(id);
    }
}
