package pl.sda.javawwa18.service;

import pl.sda.javawwa18.domain.Movie;
import pl.sda.javawwa18.domain.MovieGenre;

import java.time.LocalDate;

public class DefaultMovieServiceRefactored implements MovieServiceRefactored {

    @Override
    public Movie findMovie(String title) {
        return null;
    }

    @Override
    public Movie findOrCreateMovie(String title, MovieGenre genre, LocalDate releaseDate) {
        return null;
    }

    @Override
    public Movie createMovie(String title, MovieGenre genre, LocalDate releaseDate, String description) {
        return null;
    }

    @Override
    public Movie updateMovie(String title, int rentedTimes, double avgScore) {
        return null;
    }

    @Override
    public Movie removeMovie(String title) {
        return null;
    }
}
