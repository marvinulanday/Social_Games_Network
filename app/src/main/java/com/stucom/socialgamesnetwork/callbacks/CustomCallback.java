package com.stucom.socialgamesnetwork.callbacks;

import java.io.Serializable;

public interface CustomCallback extends Serializable {
    void accessFragment(int containerViewId, Fragment fragment);
}
