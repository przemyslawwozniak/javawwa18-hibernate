package pl.sda.javawwa18.validation;

import pl.sda.javawwa18.domain.Movie;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DefaultDvdReleaseDateValidator implements ConstraintValidator<DvdReleaseDate, Movie> {

    final static LocalDate firstDvdPremiere = LocalDate.of(1996, 12, 20);

    @Override
    public boolean isValid(Movie movie, ConstraintValidatorContext constraintValidatorContext) {
        return movie.getReleaseDate().isAfter(firstDvdPremiere);
    }

    @Override
    public void initialize(DvdReleaseDate constraintAnnotation) {

    }
}
