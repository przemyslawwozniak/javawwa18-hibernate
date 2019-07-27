package pl.sda.javawwa18.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pl.sda.javawwa18.domain.Movie;
import pl.sda.javawwa18.domain.MovieGenre;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class DefaultMovieServiceTest {

    SessionFactory sessionFactory;
    MovieService movieService = new DefaultMovieService();

    @BeforeSuite
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    //test nr 1
    //findOrCreateMovie
    //otworzyc nowa sesje i odczytac po Id ten film
    //sprawdzacie czy zwrocil poprawny obiekt Movie

    //test nr 2
    //utworzony w test nr 1 film jest juz w bazie
    //findMovie
    //sprawdzacie czy zwrocil poprawny obiekt Movie

    @Test
    public void create_movies_via_movieservice() {
        //test nr 1
        try(Session session = sessionFactory.openSession()) {
            movieService.findOrCreateMovie("Smierc w Wenecji",
                    MovieGenre.ACTION, LocalDate.of(2001, 4, 1),
                    session);
        }
        //test nr 2
        try(Session session = sessionFactory.openSession()) {
            Movie movie = movieService.findMovie("Smierc w Wenecji", session);

            assertNotNull(movie.getMovieId());
            assertEquals(movie.getTitle(), "Smierc w Wenecji");
            assertEquals(movie.getGenre(), MovieGenre.ACTION);
            //test time
            assertEquals(movie.getReleaseDate(), LocalDate.of(2001, 4, 1));
            assertNotNull(movie.getRentedTimes());
            //test default
            assertEquals(movie.getRentedTimes(), 0);
        }
    }

    @Test(dependsOnMethods = "create_movies_via_movieservice")
    public void find_created_movie() {
        try(Session session = sessionFactory.openSession()) {
            Movie movie = movieService.findMovie("Smierc w Wenecji", session);

            assertNotNull(movie.getMovieId());
            assertEquals(movie.getTitle(), "Smierc w Wenecji");
            assertEquals(movie.getGenre(), MovieGenre.ACTION);
            //test time
            assertEquals(movie.getReleaseDate(), LocalDate.of(2001, 4, 1));
            assertNotNull(movie.getRentedTimes());
            //test default
            assertEquals(movie.getRentedTimes(), 0);
        }
    }

    @Test(dependsOnMethods = "create_movies_via_movieservice")
    public void update_movie_stats() {
        //wczytac Smierc w Wenecji
        //sprawdzic, ze ma rentedTimes = 0
        //zrobic modyfikacje metoda serwisu obu parametrow
        //wczytac w nowej sesji SwW i sprawdzic wartosci pol
    }

}
