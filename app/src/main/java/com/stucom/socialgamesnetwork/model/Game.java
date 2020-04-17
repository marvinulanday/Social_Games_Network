package com.stucom.socialgamesnetwork.model;

public class Game {
    private String idGame;
    private String name;

    public Game(String idGame, String name)
    {
        this.idGame = idGame;
        this.name = name;
    }

    public String getIdGame()
    {
        return idGame;
    }

    public String getName()
    {
        return name;
    }

    public void setIdGame(String idGame)
    {
        this.idGame = idGame;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
