package com.stucom.socialgamesnetwork.callbacks;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

public interface CustomCallback extends Serializable {
    void accessFragment(int containerViewId, Fragment fragment);
}
