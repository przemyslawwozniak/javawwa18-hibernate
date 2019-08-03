package pl.sda.javawwa18.domain;

import pl.sda.javawwa18.listener.MovieEntityListener;
import pl.sda.javawwa18.validation.DvdReleaseDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners({MovieEntityListener.class})
@Table(name = "movies")
@DvdReleaseDate
@NamedQueries({
        @NamedQuery(name = "movie.findByCompany", query = "from Movie m where m.company=:company")
})
public class Movie {

    @Id
    @GeneratedValue
    Long movieId;

    @NotNull
    @Column(nullable = false)
    String title;

    @Enumerated //by default works on ordinal
    @Column(nullable = false)
    MovieGenre genre;

    @Column(nullable = false)
    LocalDate releaseDate;  //potencjalny problem z mapowaniem

    @Min(100)
    @Column(length = 2000)
    String description;

    @Column(nullable = false, columnDefinition = "integer default 0")
    int rentedTimes;

    //dla typu long, rzutowanie; dla double mozna dac @DecimalMax(Min)
    @Min(0)
    @Max(10)
    double avgScore;

    @OneToMany(orphanRemoval = true, mappedBy = "movie")
    List<Copy> copies;

    @Transient
    int daysFromRelease;

    String company;

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

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

    public int getDaysFromRelease() {
        return daysFromRelease;
    }

    public void setDaysFromRelease(int daysFromRelease) {
        this.daysFromRelease = daysFromRelease;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
