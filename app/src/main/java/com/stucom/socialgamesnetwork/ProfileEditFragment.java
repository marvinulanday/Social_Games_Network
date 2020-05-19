package com.stucom.socialgamesnetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditFragment extends Fragment {
    private ImageView ivAvatar;
    MyCallback myCallback;
    User readUser;
    SgnDAO dao;
    TextView tname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, null);
        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        tname = view.findViewById(R.id.tName);
        ivAvatar.setImageResource(R.drawable.user);

        return view;
    }
}
