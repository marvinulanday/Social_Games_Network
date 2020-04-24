package com.stucom.socialgamesnetwork.model;

import java.util.Date;

public class History {
    private String email;
    private String idGame;
    private Date date;

    public History(String email, String idGame, Date date)
    {
        this.email = email;
        this.idGame = idGame;
        this.date = date;
    }

    public String getEmail()
    {
        return email;
    }

    public String getIdGame()
    {
        return idGame;
    }

    public Date getDate()
    {
        return date;
    }

    public void setIdGame()
    {
        this.idGame = idGame;
    }

    public void setEmail()
    {
        this.email = email;
    }

    public void setDate()
    {
        this.date = date;
    }
}
