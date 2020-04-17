package com.stucom.socialgamesnetwork.model;

public class Score {
    private String email;
    private String idGame;
    private Boolean positive;

    public Score(String email, String idGame, Boolean positive)
    {
        this.email = email;
        this.idGame = idGame;
        this.positive = positive;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIdGame(String idGame)
    {
        this.idGame = idGame;
    }

    public void setPositive(Boolean positive)
    {
        this.positive = positive;
    }

    public String getEmail()
    {
        return email;
    }

    public String getIdGame()
    {
        return idGame;
    }

    public Boolean getPositive()
    {
        return positive;
    }
}
