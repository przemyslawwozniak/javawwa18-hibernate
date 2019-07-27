package pl.sda.javawwa18.service;

import org.hibernate.Session;
import pl.sda.javawwa18.domain.Movie;
import pl.sda.javawwa18.domain.MovieGenre;

import java.time.LocalDate;

public interface MovieServiceRefactored {

    Movie findMovie(String title);

    Movie findOrCreateMovie(String title, MovieGenre genre, LocalDate releaseDate);

    Movie createMovie(String title, MovieGenre genre, LocalDate releaseDate, String description);

    Movie updateMovie(String title, int rentedTimes, double avgScore);

    Movie removeMovie(String title);

}
