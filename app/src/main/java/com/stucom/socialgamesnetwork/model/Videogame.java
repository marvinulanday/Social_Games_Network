package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videogame {

    @SerializedName("id")
    private String idGame;

    @SerializedName("name")
    private String name;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("total_rating")
    private double rating;

    @SerializedName("cover")
    private String image;

    public Videogame(String idGame, String name) {
        this.idGame = idGame;
        this.name = name;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getName() {
        return name;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
