package com.stucom.socialgamesnetwork.model;

public class Opinion {
    private String email;
    private String idGame;
    private String text;

    public Opinion(String email, String idGame, String text)
    {
        this.email = email;
        this.idGame = idGame;
        this.text = text;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIdGame(String idGame)
    {
        this.idGame = idGame;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getIdGame()
    {
        return idGame;
    }

    public String getEmail()
    {
        return email;
    }

    public String getText()
    {
        return text;
    }
}
