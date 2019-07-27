package pl.sda.javawwa18.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue
    Long movieId;

    @Column(nullable = false)
    String title;

    @Enumerated //by default works on ordinal
    @Column(nullable = false)
    MovieGenre genre;

    @Column(nullable = false)
    LocalDate releaseDate;  //potencjalny problem z mapowaniem

    @Column(length = 2000)
    String description;

    @Column(nullable = false, columnDefinition = "integer default 0")
    int rentedTimes;

    double avgScore;

    public Long getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRentedTimes() {
        return rentedTimes;
    }

    public void setRentedTimes(int rentedTimes) {
        this.rentedTimes = rentedTimes;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }
}