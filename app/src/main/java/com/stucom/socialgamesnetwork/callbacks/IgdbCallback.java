package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;

import com.stucom.socialgamesnetwork.model.Genre;

import java.util.List;

public interface IgdbCallback {
    void findGenres(Context context, List<Genre> genresAPI);


}
