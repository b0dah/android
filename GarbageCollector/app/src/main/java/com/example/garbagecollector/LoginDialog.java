package com.example.garbagecollector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginDialog extends AppCompatDialogFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText socketEditText;

    private ImageView logoImageView;

    private LoginDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_alert_layout, null);

        builder.setView(view)
                //.setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                    }
                })
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        String socket = socketEditText.getText().toString();

                        listener.applyFilledFields(username, password, socket);
                    }
                });


        // TODO Disable OK button whilst not all the fields filled


        // fetch from file
        String loginDetails[] = FileHolder.fetchLoginDataFromFile(getContext());

        // Assign vars to EditTexts
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
        socketEditText = view.findViewById(R.id.socket);
        logoImageView = view.findViewById(R.id.logo);


        // set fetched text
        usernameEditText.setText(loginDetails[0]);
        passwordEditText.setText(loginDetails[1]);
        socketEditText.setText(loginDetails[2]);
        logoImageView.setImageResource(R.drawable.logo);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (LoginDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoginDialogListener");
        }
    }





    public interface LoginDialogListener {
        void applyFilledFields(String username, String password, String socket);
    }


}
