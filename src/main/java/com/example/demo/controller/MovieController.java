package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/Movies")
public class MovieController {
	@Autowired
	private MovieService movieService;

	private String formatTime = "yyyy-MM-dd'T'HH:mm:ss";

	// Get all Movies
	@GetMapping
	public ResponseEntity<Object> getAllMovies() {
		try {
			return new ResponseEntity<Object>(movieService.getAllMovies(), HttpStatus.OK);
		} catch (Exception error) {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
			map.put("status", statusResult.value());
			map.put("exceptionMessage", error.getMessage());
			map.put("message", "Error Get Data Movie");
			return new ResponseEntity<Object>(map, statusResult);
		}
	}

	// Get Movie by ID
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMovieById(@PathVariable Long id) {
		try {
			Optional<Movie> tempData = movieService.getMovieById(id);
			if (!tempData.isEmpty()) {
				return new ResponseEntity<Object>(movieService.getMovieById(id), HttpStatus.OK);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
				map.put("status", statusResult.value());
				map.put("message", "Movie not Found");
				return new ResponseEntity<Object>(map, statusResult);
			}
		} catch (Exception error) {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
			map.put("status", statusResult.value());
			map.put("exceptionMessage", error.getMessage());
			map.put("message", "Failed Get Data Movie By ID");
			return new ResponseEntity<Object>(map, statusResult);
		}
	}

	// Create a new Movie
	@PostMapping
	public ResponseEntity<Object> createMovie(@RequestBody Movie movie) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = null;
			if (validationInput(movie)) {
				statusResult = HttpStatus.OK;
				map.put("message ", "Movie has been successfully added");
				movieService.createMovie(movie);
			} else {
				statusResult = HttpStatus.BAD_GATEWAY;
				map.put("message ", "Data does not match the format like format time");
			}
			map.put("status", statusResult.value());
			return new ResponseEntity<Object>(map, statusResult);
		} catch (Exception error) {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
			map.put("status", statusResult.value());
			map.put("exceptionMessage", error.getMessage());
			map.put("message", "Error Create Movie");
			return new ResponseEntity<Object>(map, statusResult);
		}
	}

	// Update Movie by ID
	@PatchMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = null;
			Optional<Movie> tempData = movieService.getMovieById(id);
			if (!tempData.isEmpty()) {
				if (validationInput(movieDetails)) {
					statusResult = HttpStatus.OK;
					map.put("message ", "Movie has been successfully edited");
					movieService.updateMovie(id, movieDetails);
				} else {
					statusResult = HttpStatus.BAD_GATEWAY;
					map.put("message ", "Data does not match the format like format time");
				}
			} else {
				statusResult = HttpStatus.BAD_GATEWAY;
				map.put("message", "Movie not Found");
			}
			map.put("status", statusResult.value());
			return new ResponseEntity<Object>(map, statusResult);
		} catch (Exception error) {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
			map.put("status", statusResult.value());
			map.put("exceptionMessage", error.getMessage());
			map.put("message", "Error Create Movie");
			return new ResponseEntity<Object>(map, statusResult);
		}
	}

	// Delete Movie by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable Long id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = null;
			Optional<Movie> tempData = movieService.getMovieById(id);
			if (!tempData.isEmpty()) {
				statusResult = HttpStatus.OK;
				map.put("message", "The movie has been successfully deleted");
				movieService.deleteMovie(id);
			} else {
				statusResult = HttpStatus.BAD_GATEWAY;
				map.put("message", "Movie Not Found");
			}
			map.put("status", statusResult.value());

			return new ResponseEntity<Object>(map, statusResult);
		} catch (Exception error) {
			Map<String, Object> map = new HashMap<String, Object>();
			HttpStatus statusResult = HttpStatus.BAD_GATEWAY;
			map.put("status", statusResult.value());
			map.put("exceptionMessage", error.getMessage());
			map.put("message", "Failed Delete Movie");
			return new ResponseEntity<Object>(map, statusResult);
		}
	}

//	Validation for Created_At and Updated_At
	protected boolean validationInput(Movie movie) {
		return this.validationFormatTime(movie.getCreated_at().toString())
				&& validationFormatTime(movie.getUpdated_at().toString());
	}

	protected boolean validationFormatTime(String timeInput) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(formatTime);
		ldt = LocalDateTime.parse(timeInput, fomatter);
		String result = ldt.format(fomatter);
		return result.equals(timeInput);
	}
}
