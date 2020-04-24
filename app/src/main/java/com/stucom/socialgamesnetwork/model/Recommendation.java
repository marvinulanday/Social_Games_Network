package com.stucom.socialgamesnetwork.model;

public class Recommendation {
    private String email;
    private String idGameRecommended;
    private String idGameBase;
    private String text;

    public Recommendation(String email, String idGameRecommended, String idGameBase, String text)
    {
        this.email = email;
        this.idGameRecommended = idGameRecommended;
        this.idGameBase = idGameBase;
        this.text = text;
    }

    public String getEmail()
    {
        return email;
    }

    public String getIdGameRecommended()
    {
        return idGameRecommended;
    }

    public String getIdGameBase()
    {
        return idGameBase;
    }

    public String getText()
    {
        return text;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIdGameRecommended(String idGameRecommended) {
        this.idGameRecommended = idGameRecommended;
    }

    public void setIdGameBase(String idGameBase)
    {
        this.idGameBase = idGameBase;
    }

    public void setText(String text) {
        this.text = text;
    }
}
