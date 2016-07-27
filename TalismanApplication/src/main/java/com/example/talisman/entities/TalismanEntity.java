package com.example.talisman.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by gipotalamus on 23.07.16.
 */
@Entity
public class TalismanEntity {

    @Id
    private int id;

    private String story;

    private String phone;

    private String email;

    private String skype;

    private String address;

    public TalismanEntity() {
    }

    public TalismanEntity(String story, String phone, String email, String skype, String address) {
        this.story = story;
        this.phone = phone;
        this.email = email;
        this.skype = skype;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
