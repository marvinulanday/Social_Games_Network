package com.stucom.socialgamesnetwork;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.stucom.socialgamesnetwork.DAO.SharedPrefsManagement;
import com.stucom.socialgamesnetwork.callbacks.CallbackUpdateUser;
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;
import com.stucom.socialgamesnetwork.model.Data;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditFragment extends Fragment {
    private ImageView ivAvatar;
    private MyCallback myCallback;
    private CallbackUpdateUser callbackUpdateUser;
    private User readUser;
    private SgnDAO dao;
    private EditText etName;
    private EditText etSurname;
    private TextView tvEmail;
    private TextView tvUsername;
    private CustomCallback callbackScreen;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etPasswordConfirm;
    private CustomCallback callback;
    private FloatingActionButton btnEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, null);
        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        tvEmail = view.findViewById(R.id.tvEmailData);
        tvUsername = view.findViewById(R.id.tvUserData);
        btnEdit = view.findViewById(R.id.btnEdit);
        ivAvatar.setImageResource(R.drawable.user);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm);
        final Bundle bundle = getArguments();
        callback = (CustomCallback) bundle.getSerializable("callback");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readUser.setPassword(etOldPassword.getText().toString());
                if (etNewPassword.getText().length()<5)
                {
                    AlertDialog show = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage(R.string.errorPasswordShort)
                            .setNeutralButton("OK", null)
                            .show();
                }
                else
                {
                    SgnDAO dao = new SgnDAO();

                    dao.updateUser(callback, bundle, getContext(),readUser,etNewPassword.getText().toString(),etPasswordConfirm.getText().toString());
                }
            }
        });

        dao = new SgnDAO();
        myCallback = new MyCallback() {
            @Override
            public void login(User user) {
                readUser = user;
                etName.setText(user.getName());
                etSurname.setText(user.getSurname());
                tvEmail.setText(user.getEmail());
                tvUsername.setText(user.getUsername());
            }
        };
        dao.selectUserByEmail(getContext(), myCallback);
        return view;
    }



}
