package pl.sda.javawwa18.domain;

import javax.persistence.*;

@Entity
public class Email {
    @Id
    //this is how you can change annotation config - simple, isn't it?
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String subject;
    @OneToOne//(mappedBy = "email") //IMPORTANT!
    Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}