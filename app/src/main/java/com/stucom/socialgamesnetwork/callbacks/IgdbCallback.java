package com.stucom.socialgamesnetwork.callbacks;

import com.stucom.socialgamesnetwork.model.Genre;

import java.util.List;

public interface IgdbCallback {
    void findGenres(List<Genre> genres);
}
