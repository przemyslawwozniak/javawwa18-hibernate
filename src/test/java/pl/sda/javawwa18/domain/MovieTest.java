package pl.sda.javawwa18.domain;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pl.sda.javawwa18.util.SessionUtil;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class MovieTest {

    @Test
    public void cascade_operations() {
        //TESTING CASCADE PERSISTING
        Long movieId, copyId1, copyId2;
        Movie movie;
        Copy copy1, copy2;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));

            copy1 = new Copy();
            copy2 = new Copy();

            //Copy zarzadza relacja z Movie
            copy1.setMovie(movie);
            copy2.setMovie(movie);

            //kaskadowe zapisanie filmu przy zapisie kopii
            //for the save() operation to be cascaded, you need to enable CascadeType.SAVE_UPDATE
            //czyli: @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
            //session.save(movie);
            session.persist(copy1);
            session.persist(copy2);

            //po zapisie do bazy, ID zostaje nadane
            copyId1 = copy1.getCopyId();
            copyId2 = copy2.getCopyId();

            //jezeli zapisano kaskadowo, powinno zostac nadane ID dla filmu
            movieId = movie.getMovieId();

            tx.commit();
        }

        //odswiezenie obiektu JVM danymi z bazy
        //baza przechowuje FK z copy na movie
        //i relacja jest odwzorowana na obiekty JVM
        try(Session session = SessionUtil.getSession()) {
            //#get zwraca null jesli nie znaleziono rekordu
            movie = session.get(Movie.class, movieId);
            copy1 = session.get(Copy.class, copyId1);
            copy2 = session.get(Copy.class, copyId2);
        }

        assertNotNull(movie);
        //nie ustawilismy listy copies na movie explicite
        //zostalo to zarzadzone przez relacje Hibernate
        assertNotNull(movie.getCopies());
        //assertEquals(movie.getCopies().size(), 2);
        //assertEquals(movie.getCopies().get(0).getCopyId(), copyId1.longValue());
        //assertEquals(movie.getCopies().get(1).getCopyId(), copyId2.longValue());
        assertNotNull(copy1.getMovie());
        assertNotNull(copy2.getMovie());

        //TESTING ORPHAN REMOVAL
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            movie = session.load(Movie.class, movieId);
            assertEquals(movie.getCopies().size(), 2);
            session.delete(movie);

            tx.commit();
        }
        try(Session session = SessionUtil.getSession()) {
            Query<Copy> query = session.createQuery("from Copy c", Copy.class);
            List<Copy> copies = query.list();
            assertEquals(copies.size(), 0);
        }
    }

    @Test
    public void init_days_from_release_on_load() {
        Long movieId;
        Movie movie;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));

            session.save(movie);
            movieId = movie.getMovieId();
            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            movie = session.get(Movie.class, movieId);
            assertNotNull(movie);
            assertNotNull(movie.getDaysFromRelease());
            assertEquals(movie.getDaysFromRelease(),
                    ChronoUnit.DAYS.between(movie.getReleaseDate(), LocalDate.now()));
        }
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void invalid_score() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setAvgScore(-1.0);

            session.save(movie);
            tx.commit();
        }
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void description_too_short() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setAvgScore(7.5);
            movie.setDescription("too short description");

            session.save(movie);
            tx.commit();
        }
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void no_title() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setAvgScore(7.5);

            session.save(movie);
            tx.commit();
        }
    }

    @Test
    public void namedquery_finds_movies_by_company() {
        //add movie to db
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setAvgScore(7.5);
            //movie.setDescription("Thanks. That's pretty much what I figured. I'm familiar with both of the approaches you suggested in your other answer, but I'm stuck with a nightmarish legacy db schema that just isn't well suited to the Hibernate approach for my particular case. I've made it work so far, but have some new requirements I just can't seem to meet using straight Hibernate mapping. I'm probably going to give up and use iBATIS for this one case. Not real thrilled adding another technology to the stack as a band-aid, but that's life I guess. Thanks.");
            movie.setCompany("Warner Bros");

            session.save(movie);
            tx.commit();
        }
        //check named query
        try(Session session = SessionUtil.getSession()) {
            Query query = session.getNamedQuery("movie.findByCompany");
            query.setParameter("company", "Warner Bros");
            List<Movie> warnerBrosMovies = query.list();
            assertNotNull(warnerBrosMovies);
            assertEquals(warnerBrosMovies.size(), 1);
            assertEquals(warnerBrosMovies.get(0).getTitle(), "Smierc w Wenecji");

            Query query2 = session.getNamedQuery("movie.findByCompany");
            query2.setParameter("company", "Janusz Movies");
            List<Movie> januszMovies = query2.list();
            assertEquals(januszMovies.size(), 0);
        }
    }

    @Test
    public void namedquery_finds_movies_by_score_above() {
        //add movie to db
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setAvgScore(7.5);
            movie.setCompany("Warner Bros");

            session.save(movie);
            tx.commit();
        }
        //check named query
        try(Session session = SessionUtil.getSession()) {
            Query query = session.getNamedQuery("movie.findByScoreAbove");
            query.setParameter("score", 7.0);
            List<Movie> movies = query.list();
            assertNotNull(movies);
            assertEquals(movies.size(), 1);
            assertEquals(movies.get(0).getTitle(), "Smierc w Wenecji");

            Query query2 = session.getNamedQuery("movie.findByScoreAbove");
            query2.setParameter("score", 9.0);
            List<Movie> movies2 = query2.list();
            assertEquals(movies2.size(), 0);
        }
    }

    //TODO: java.lang.NullPointerException
    //at org.hibernate.hql.internal.ast.util.JoinProcessor.processDynamicFilterParameters(JoinProcessor.java:245)
    //cos nie tak z typem filtra?
    @Ignore
    @Test
    public void filtering() {
        //add movies to db
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Movie movie = new Movie();
            movie.setTitle("Smierc w Wenecji");
            movie.setGenre(MovieGenre.ACTION);
            movie.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie.setCompany("Warner Bros");
            movie.setRating(18);

            session.save(movie);

            Movie movie2 = new Movie();
            movie2.setTitle("Mis Uszatek");
            movie2.setGenre(MovieGenre.ACTION);
            movie2.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie2.setCompany("Walt Disney");
            movie2.setRating(7);

            session.save(movie2);

            Movie movie3 = new Movie();
            movie3.setTitle("Mis Uszatek: Teenage edition");
            movie3.setGenre(MovieGenre.ACTION);
            movie3.setReleaseDate(LocalDate.of(2001, 4, 1));
            movie3.setCompany("Walt Disney");
            movie3.setRating(12);

            session.save(movie3);

            tx.commit();
        }
        //filtering
        try(Session session = SessionUtil.getSession()) {
            Query<Movie> query = session.createQuery("from Movie", Movie.class);
            session.enableFilter("byCompany").setParameter("company", "Walt Disney");
            session.enableFilter("byAgeRating").setParameter("rating", 7);
            List<Movie> filteredMovies = query.list();
            assertNotNull(filteredMovies);
            assertEquals(filteredMovies.size(), 1);
            assertEquals(filteredMovies.get(0).getTitle(), "Mis Uszatek");
        }
    }

}
