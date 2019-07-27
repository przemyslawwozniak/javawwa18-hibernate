package pl.sda.javawwa18.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.sda.javawwa18.domain.Movie;
import pl.sda.javawwa18.domain.MovieGenre;

import java.time.LocalDate;

public class DefaultMovieService implements MovieService {

    @Override
    public Movie findMovie(String title, Session session) {
        Query<Movie> query = session.createQuery("from Movie m where m.title:=title", Movie.class);
        query.setParameter("title", title);
        return query.uniqueResult();
    }

    @Override
    public Movie findOrCreateMovie(String title, MovieGenre genre, LocalDate releaseDate, Session session) {
        Movie movie = findMovie(title, session);
        if(movie == null) {
            movie = new Movie();
            movie.setTitle(title);
            movie.setGenre(genre);
            movie.setReleaseDate(releaseDate);
            Transaction tx = session.beginTransaction();
            session.persist(movie);
            tx.commit();
        }
        return movie;
    }
}
