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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private ImageView ivAvatar;
    private FloatingActionButton btnEdit;
    private CustomCallback callback;
    MyCallback myCallback;
    User readUser;
    SgnDAO dao;
    TextView tvName;
    TextView tvSurname;
    TextView tvEmail;
    TextView tvUsername;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        ivAvatar.setImageResource(R.drawable.user);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvName = view.findViewById(R.id.tvNameData);

        tvSurname = view.findViewById(R.id.tvSurnameData);
        tvEmail = view.findViewById(R.id.tvEmailData);
        tvUsername = view.findViewById(R.id.tvUserData);
        Bundle bundle = getArguments();
        callback = (CustomCallback) bundle.getSerializable("callback");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.accessFragment(R.id.fragment_container, new ProfileEditFragment());
            }
        });

        dao = new SgnDAO();
        myCallback = new MyCallback() {
            @Override
            public void login(User user) {
                readUser = user;
                tvName.setText(user.getName());
                tvSurname.setText(user.getSurname());
                tvEmail.setText(user.getEmail());
                tvUsername.setText(user.getUsername());
            }
        };
        dao.selectUserByEmail(getContext(), myCallback);
        return view;
    }
}
