package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GameMode implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public GameMode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GameMode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
