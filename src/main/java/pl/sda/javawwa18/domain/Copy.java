package pl.sda.javawwa18.domain;

import javax.persistence.*;

@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue
    long copyId;
    //Long movieId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean isRented;

    public long getCopyId() {
        return copyId;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}
