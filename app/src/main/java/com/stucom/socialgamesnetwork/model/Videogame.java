package com.stucom.socialgamesnetwork.model;

import java.util.List;

public class Videogame {

    private String idGame;
    private String name;
    private List<Genre> genres;
    private int rating;
    private String image;

    public Videogame(String idGame, String name) {
        this.idGame = idGame;
        this.name = name;
    }

    public String getIdGame() {
        return idGame;
    }

    public String getName() {
        return name;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
