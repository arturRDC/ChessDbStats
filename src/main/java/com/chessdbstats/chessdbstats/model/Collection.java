package com.chessdbstats.chessdbstats.model;

import jakarta.persistence.*;


@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String pgnPath;

    public Collection(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.pgnPath = "games/collectionPgn";
    }
    public  Collection() {}


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userOwner) {
        this.user = userOwner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPgnPath() {
        return pgnPath;
    }

    public void setPgnPath(String pgnPath) {
        this.pgnPath = pgnPath;
    }
}
