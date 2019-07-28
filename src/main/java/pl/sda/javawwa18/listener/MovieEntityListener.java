package pl.sda.javawwa18.listener;

import pl.sda.javawwa18.domain.Movie;

import javax.persistence.PostLoad;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MovieEntityListener {
    @PostLoad
    public void calculateDaysFromRelease(Object o) {
        if(o instanceof Movie) {
            Movie movie = (Movie) o;
            movie.setDaysFromRelease((int)ChronoUnit.DAYS.between(movie.getReleaseDate(), LocalDate.now()));
        }
    }
}
