package com.example.talisman.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by gipotalamus on 14.07.16.
 */
@Entity
public class TalismanPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String photo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "event")
    private TalismanEventEntity event;

    public TalismanPhotoEntity() {
    }

    public TalismanPhotoEntity(String title, String photo, Date date, TalismanEventEntity event) {
        this.title = title;
        this.photo = photo;
        this.date = date;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
