package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videogame {

    @SerializedName("id")
    private String idGame;

    @SerializedName("name")
    private String name;

    @SerializedName("summary")
    private String summary;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("total_rating")
    private double rating;

    @SerializedName("cover")
    private Cover cover;

    public Videogame(String idGame, String name, String summary, List<Genre> genres, double rating, Cover cover) {
        this.idGame = idGame;
        this.name = name;
        this.summary = summary;
        this.genres = genres;
        this.rating = rating;
        this.cover = cover;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Videogame{" +
                "idGame='" + idGame + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", genres=" + genres +
                ", rating=" + rating +
                ", cover=" + cover +
                '}';
    }
}
