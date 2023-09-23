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
		Movie tempMovie = new Movie(movie.getTitle(), movie.getDescription(), movie.getImage(), movie.getRating(), movie.getCreated_at(),movie.getUpdated_at());
		return movieRepo.save(tempMovie);
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
	public void updateMovie(Long id, Movie movieDetails) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			Movie existingMovie = movie.get();
			existingMovie.setTitle(movieDetails.getTitle());
			existingMovie.setDescription(movieDetails.getDescription());
			existingMovie.setRating(movieDetails.getRating());
			existingMovie.setImage(movieDetails.getImage());
			existingMovie.setUpdated_at(movieDetails.getUpdated_at());
			movieRepo.save(existingMovie);
		}
	}

	// Delete Movie
	public void deleteMovie(Long id) {
		movieRepo.deleteById(id);
	}

}
