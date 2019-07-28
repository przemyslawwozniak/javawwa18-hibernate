package pl.sda.javawwa18.domain;

import javax.persistence.*;

@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue
    long copyId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean isRented;

    @ManyToOne
    Movie movie;

    @OneToOne(mappedBy = "copy")
    Rent rent;

    public long getCopyId() {
        return copyId;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
