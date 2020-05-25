package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable {

    @SerializedName("id")
    private String idCover;

    @SerializedName("image_id")
    private String imageId;

    public Cover(String idCover, String imageId) {
        this.idCover = idCover;
        this.imageId = imageId;
    }

    public String getIdCover() {
        return idCover;
    }

    public void setIdCover(String idCover) {
        this.idCover = idCover;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "idCover='" + idCover + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
