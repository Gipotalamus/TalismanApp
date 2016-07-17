package com.example.talisman.entities;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by мир on 25.03.2016.
 */
@Entity
public class TalismanEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Column(length = 10000)
    @NotEmpty(message = "{validation.entityText}")
    private String text;

    @NotEmpty(message = "{validation.entityTitle}")
    private String title;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "event")
    private List<TalismanPhotoEntity> photos;

    private int views;

    public TalismanEventEntity() {
    }

    public TalismanEventEntity(String text, String title, Date date) {
        this.text = text;
        this.date = date;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<TalismanPhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<TalismanPhotoEntity> photos) {
        this.photos = photos;
    }
}
