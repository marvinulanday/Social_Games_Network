package com.stucom.socialgamesnetwork.model;

public class Favourite {
    private String email;
    private String idGame;

    public Favourite(String email, String idGame)
    {
        this.email = email;
        this.idGame = idGame;
    }

    public String getEmail()
    {
        return email;
    }

    public String getIdGame()
    {
        return idGame;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIdGame(String idGame)
    {
        this.idGame = idGame;
    }
}
