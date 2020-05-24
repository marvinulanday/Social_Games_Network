package com.stucom.socialgamesnetwork.callbacks;

import android.content.Context;

import java.io.Serializable;

public interface SgnCallback extends Serializable {
    void isGameFavourite(Context context, boolean isFavourite);
}
