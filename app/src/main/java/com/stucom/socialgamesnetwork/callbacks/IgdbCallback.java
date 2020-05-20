package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;

import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public interface IgdbCallback {
    void findGenres(Context context, List<Genre> genresAPI);

    void findGames(Context context, List<Videogame> videogamesAPI);


}
