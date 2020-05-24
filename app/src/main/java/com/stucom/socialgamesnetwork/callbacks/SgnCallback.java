package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;

import com.stucom.socialgamesnetwork.model.Videogame;

import java.io.Serializable;
import java.util.List;

public interface SgnCallback extends Serializable {
    void isGameFavourite(Context context, boolean isFavourite);

    void setListGames(Context context, List<Videogame> videogameList);
}
