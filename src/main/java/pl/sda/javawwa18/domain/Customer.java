package pl.sda.javawwa18.domain;

import javax.persistence.*;
import java.util.List;

@Entity //default: Entity.name = Customer, Table.name = Customer
@Table(name = "customers")  //Table.name = customers
public class Customer {

    @Id //PK
    @GeneratedValue
    Long customerId;

    @Column(nullable = false)
    String fullName;

    //+48 777 777 777
    @Column(nullable = false, length = 15)
    String phone;

    @Column(length = 510)
    String address;

    @OneToMany(mappedBy="customer")
    List<Rent> rents;

    public Long getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }
}
