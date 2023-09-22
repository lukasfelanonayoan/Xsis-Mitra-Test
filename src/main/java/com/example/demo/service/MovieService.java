package com.example.demo.service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
    private MovieRepository movieRepo;

    // Create a new Movie
    public Movie createMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    // Get all Movies
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    // Get Movie by ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepo.findById(id);
    }

    // Update Movie
    public Movie updateMovie(Long id, Movie movieDetails) {
        Optional<Movie> movie = movieRepo.findById(id);
        if (movie.isPresent()) {
            Movie existingMovie = movie.get();
            existingMovie.setTitle(movieDetails.getTitle());
            existingMovie.setDescription(movieDetails.getDescription());
            existingMovie.setRating(movieDetails.getRating());
            existingMovie.setImage(movieDetails.getImage());
            return movieRepo.save(existingMovie);
        }
        return null;
    }

    // Delete Movie
    public void deleteMovie(Long id) {
    	movieRepo.deleteById(id);
    }

}
