package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;

import com.stucom.socialgamesnetwork.model.Videogame;

import java.util.List;

public interface SgnCallback {
    void isGameFavourite(Context context, boolean isFavourite);

    void setListGames(Context context, List<Videogame> videogameList);
}
