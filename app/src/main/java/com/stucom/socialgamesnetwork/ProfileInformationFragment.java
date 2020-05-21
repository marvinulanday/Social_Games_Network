package com.stucom.socialgamesnetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stucom.socialgamesnetwork.DAO.SgnDAO;
import com.stucom.socialgamesnetwork.model.User;
import com.stucom.socialgamesnetwork.ui.login.MyCallback;


public class ProfileInformationFragment extends Fragment {

    private MyCallback myCallback;

    private SgnDAO dao;

    private TableLayout tableLayoutInfo;
    private TableLayout tableLayoutEdit;

    private TextView tvEmail;
    private TextView tvName;
    private TextView tvSurname;
    private TextView tvEmail2;

    private EditText etName;
    private EditText etSurname;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etPasswordConfirm;

    private FloatingActionButton btnEdit;
    private FloatingActionButton btnSave;
    private FloatingActionButton btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_information, container, false);

        tableLayoutInfo = view.findViewById(R.id.tableLayoutInfo);
        tableLayoutEdit = view.findViewById(R.id.tableLayoutEdit);

        tvEmail = view.findViewById(R.id.tvEmail);
        tvName = view.findViewById(R.id.tvName);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvEmail2 = view.findViewById(R.id.tvEmail2);

        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm);

        btnEdit = view.findViewById(R.id.btnEdit);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEdit.setEnabled(false);
                btnEdit.hide();
                tableLayoutInfo.setEnabled(false);
                tableLayoutInfo.setVisibility(View.INVISIBLE);

                btnSave.setEnabled(true);
                btnSave.show();
                btnCancel.setEnabled(true);
                btnCancel.show();
                tableLayoutEdit.setEnabled(true);
                tableLayoutEdit.setVisibility(View.VISIBLE);
            }
        });

        dao = new SgnDAO();
        myCallback = new MyCallback() {
            @Override
            public void login(User user2) {
                setValues(user2);
            }
        };
        dao.selectUserByEmail(getContext(), myCallback);

        myCallback = new MyCallback() {
            @Override
            public void login(User user2) {
                setValues(user2);

                etName.setText(tvName.getText());
                etSurname.setText(tvSurname.getText());
                etOldPassword.setText("");
                etNewPassword.setText("");
                etPasswordConfirm.setText("");

                btnEdit.setEnabled(true);
                btnEdit.show();
                tableLayoutInfo.setEnabled(true);
                tableLayoutInfo.setVisibility(View.VISIBLE);

                btnSave.setEnabled(false);
                btnSave.hide();
                btnCancel.setEnabled(false);
                btnCancel.hide();
                tableLayoutEdit.setEnabled(false);
                tableLayoutEdit.setVisibility(View.INVISIBLE);
            }
        };

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(tvEmail.getText().toString(), etOldPassword.getText().toString(), etName.getText().toString(), etSurname.getText().toString());
                if (etNewPassword.getText().length() < 5) {
                    Toast.makeText(getContext(), R.string.errorPasswordShort, Toast.LENGTH_SHORT).show();
                } else {
                    dao.updateUser(getContext(), myCallback, user, etNewPassword.getText().toString(), etPasswordConfirm.getText().toString());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(tvName.getText());
                etSurname.setText(tvSurname.getText());
                etOldPassword.setText("");
                etNewPassword.setText("");
                etPasswordConfirm.setText("");

                btnEdit.setEnabled(true);
                btnEdit.show();
                tableLayoutInfo.setEnabled(true);
                tableLayoutInfo.setVisibility(View.VISIBLE);

                btnSave.setEnabled(false);
                btnSave.hide();
                btnCancel.setEnabled(false);
                btnCancel.hide();
                tableLayoutEdit.setEnabled(false);
                tableLayoutEdit.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    private void setValues(User user) {
        tvEmail.setText(user.getEmail());
        tvName.setText(user.getName());
        tvSurname.setText(user.getSurname());
        tvEmail2.setText(user.getEmail());

        etName.setText(user.getName());
        etSurname.setText(user.getSurname());
    }
}
