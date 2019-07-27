package pl.sda.javawwa18.service;

import org.hibernate.Session;
import pl.sda.javawwa18.domain.Movie;
import pl.sda.javawwa18.domain.MovieGenre;

import java.time.LocalDate;

public interface MovieService {

    Movie findMovie(String title, Session session);

    Movie findOrCreateMovie(String title, MovieGenre genre, LocalDate releaseDate, Session session);

    Movie createMovie(String title, MovieGenre genre, LocalDate releaseDate, String description, Session session);

    Movie updateMovie(String title, int rentedTimes, double avgScore, Session session);

}
