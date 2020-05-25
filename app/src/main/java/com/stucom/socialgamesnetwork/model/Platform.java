package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Platform implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("platform_logo")
    private int idLogo;

    public Platform(String id, String name, int idLogo) {
        this.id = id;
        this.name = name;
        this.idLogo = idLogo;
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

    public int getIdLogo() {
        return idLogo;
    }
    public void setIdLogo(int idLogo) {
        this.idLogo = idLogo;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idLogo=" + idLogo +
                '}';
    }
}
