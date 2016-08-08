package com.example.talisman.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by мир on 17.04.2016.
 */
@Entity
public class TalismanUser {

    @Id
    private String name;

    private String password;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date visit;

    private boolean online;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_ANONYMOUS;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getVisit() {
        return visit;
    }

    public void setVisit(Date visit) {
        this.visit = visit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(String r) {

        switch (r) {
            case "ROLE_ADMIN": this.role = Role.ROLE_ADMIN; break;
            case "ROLE_USER": this.role = Role.ROLE_USER; break;
            default: this.role = Role.ROLE_ANONYMOUS;
        }

    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
