package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stucom.socialgamesnetwork.model.Genre;
import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public interface IgdbCallback {
    void findGenres(Context context, List<Genre> genresAPI);

    void findGames(Context context, List<Videogame> videogamesAPI, boolean add);

    void getGame(Context context, Videogame videogame);

    void getFavouriteGame(Context context, Videogame videogame, TextView txtViewTitle, ImageView imgView, ProgressBar pbRating, TextView tvRating, TextView tvGenres);

}
