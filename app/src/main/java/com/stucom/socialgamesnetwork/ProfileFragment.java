package com.stucom.socialgamesnetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {
    private ImageView ivAvatar;
    private FloatingActionButton btnEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        ivAvatar.setImageResource(R.drawable.user);
        btnEdit = view.findViewById(R.id.btnEdit);

        return view;
    }




}
