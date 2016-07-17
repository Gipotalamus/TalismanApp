package com.example.talisman.entities;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by gipotalamus on 28.06.16.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty
    private String text;

    @ManyToOne
    @JoinColumn(name = "User")
    private TalismanUser user;

    @ManyToOne
    @JoinColumn(name = "Event")
    private TalismanEventEntity event;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    public Comment() {
    }

    public Comment(String text, TalismanUser user, Date date) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TalismanUser getUser() {
        return user;
    }

    public void setUser(TalismanUser user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TalismanEventEntity getEvent() {
        return event;
    }

    public void setEvent(TalismanEventEntity event) {
        this.event = event;
    }
}
