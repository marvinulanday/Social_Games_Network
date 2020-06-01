package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Videogame implements Serializable {

    @SerializedName("id")
    private int idGame;

    @SerializedName("name")
    private String name;

    @SerializedName("summary")
    private String summary;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("platforms")
    private List<Platform> platforms;

    @SerializedName("game_modes")
    private List<GameMode> gameModes;

    @SerializedName("total_rating")
    private double rating;

    @SerializedName("cover")
    private Cover cover;

    @SerializedName("first_release_date")
    private int releaseDate;

    @SerializedName("involved_companies")
    private List<Company> companies;

    @SerializedName("parent_game")
    private Videogame parentVideogame;

    public Videogame(int idGame, String name, String summary, List<Genre> genres, List<Platform> platforms, List<GameMode> gameModes, double rating, Cover cover, int releaseDate, List<Company> companies, Videogame parentVideogame) {
        this.idGame = idGame;
        this.name = name;
        this.summary = summary;
        this.genres = genres;
        this.platforms = platforms;
        this.gameModes = gameModes;
        this.rating = rating;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.companies = companies;
        this.parentVideogame = parentVideogame;
    }

    public int getIdGame() {
        return idGame;
    }
    public void setIdGame(int idGame) {
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

    public String getReleaseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String x = formatter.format(new Date((long) this.releaseDate * 1000));
            return x;
        } catch (Exception ex) {
            return null;
        }
    }
    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }
    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<GameMode> getGameModes() {
        return gameModes;
    }
    public void setGameModes(List<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    public List<Company> getCompanies() {
        return companies;
    }
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getDevelopers() {
        List<Company> developers = new ArrayList<>();
        if (getCompanies().isEmpty()) {
            return developers;
        }
        for (Company company : companies) {
            if (company.isDeveloper()) {
                developers.add(company);
            }
        }
        return developers;
    }

    public List<Company> getPublishers() {
        List<Company> publishers = new ArrayList<>();
        if (publishers.isEmpty()) {
            return publishers;
        }
        for (Company company : companies) {
            if (company.isPublisher()) {
                publishers.add(company);
            }
        }
        return publishers;
    }


    public Videogame getParentVideogame() {
        return parentVideogame;
    }

    public void setParentVideogame(Videogame parentVideogame) {
        this.parentVideogame = parentVideogame;
    }

    @Override
    public String toString() {
        return "Videogame{" +
                "idGame='" + idGame + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", genres=" + genres +
                ", platforms=" + platforms +
                ", gameModes=" + gameModes +
                ", rating=" + rating +
                ", cover=" + cover +
                ", releaseDate=" + releaseDate +
                ", companies=" + companies +
                ", parentVideogame=" + parentVideogame +
                '}';
    }
}
