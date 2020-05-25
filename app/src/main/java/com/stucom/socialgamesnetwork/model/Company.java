package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Company implements Serializable {

    @SerializedName("company")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("developer")
    private boolean developer;

    @SerializedName("porting")
    private boolean porting;

    @SerializedName("publisher")
    private boolean publisher;

    @SerializedName("supporting")
    private boolean supporting;

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

    public boolean isDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }

    public boolean isPorting() {
        return porting;
    }

    public void setPorting(boolean porting) {
        this.porting = porting;
    }

    public boolean isPublisher() {
        return publisher;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public boolean isSupporting() {
        return supporting;
    }

    public void setSupporting(boolean supporting) {
        this.supporting = supporting;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", developer=" + developer +
                ", porting=" + porting +
                ", publisher=" + publisher +
                ", supporting=" + supporting +
                '}';
    }
}
