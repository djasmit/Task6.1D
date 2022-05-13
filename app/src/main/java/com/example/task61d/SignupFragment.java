package com.example.task61d;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.User;

import java.io.ByteArrayOutputStream;

public class SignupFragment extends Fragment {

    private DatabaseHelper db;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText usernameEditText = view.findViewById(R.id.SUFUsernameEditText);
        EditText passwordEditText = view.findViewById(R.id.SUFPasswordEditText);
        EditText confPassEditText = view.findViewById(R.id.SUFConfPassEditText);
        EditText fullNameEditText = view.findViewById(R.id.SUFFullNameEditText);
        EditText phoneNoEditText = view.findViewById(R.id.SUFPhoneNoEditText);

        Button signupButton = view.findViewById(R.id.SUFSignupButton);
        Button avatarSelect = view.findViewById(R.id.SUFUploadButton);

        //start image selection process when image button is clicked
        avatarSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        //verify and finish signup whhen signup button is clicked
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameString = usernameEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();
                String confPassString = confPassEditText.getText().toString();
                String fullNameString = fullNameEditText.getText().toString();
                String phoneNoString = phoneNoEditText.getText().toString();

                //make sure username and password aren't empty
                if (usernameString.trim().equals("") || usernameString == null) {
                    Toast.makeText(getContext(), "Username can't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordString.trim().equals("") || passwordString == null) {
                    Toast.makeText(getContext(), "Password can't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //fail if username already taken
                if (db.fetchUser(usernameString) == true) {
                    Toast.makeText(getContext(), "Username already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //fail if passwords don't match
                if (confPassString.equals(passwordString) == false) {
                    Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create new user and insert into database
                User newUser = new User(usernameString, passwordString, fullNameString, phoneNoString);
                long result = db.insertUser(newUser);

                //fail if user wasn't entered successfully
                if (result <= 0) {
                    Toast.makeText(getContext(), "Failed to Register User!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //user entered successfully, so set toast and leave this fragment
                Toast.makeText(getContext(), "Registered advert successfully!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            //get the image location from the device
            Uri uploadedImage = data.getData();

            //display bitmap image
            ImageView imageView = getView().findViewById(R.id.SUFAvatarImageView);
            imageView.setImageURI(uploadedImage);
        }
    }
}