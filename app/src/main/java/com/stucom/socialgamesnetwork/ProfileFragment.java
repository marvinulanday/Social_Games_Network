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
import com.stucom.socialgamesnetwork.callbacks.CustomCallback;

public class ProfileFragment extends Fragment {
    private ImageView ivAvatar;
    private FloatingActionButton btnEdit;
    private CustomCallback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        ivAvatar = view.findViewById(R.id.ivAvatarProfile);
        ivAvatar.setImageResource(R.drawable.user);
        btnEdit = view.findViewById(R.id.btnEdit);
        Bundle bundle = getArguments();
        callback = (CustomCallback) bundle.getSerializable("callback");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.accessFragment(R.id.fragment_container, new ProfileEditFragment());
            }
        });
        return view;
    }
}
