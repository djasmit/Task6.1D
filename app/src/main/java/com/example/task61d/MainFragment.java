package com.example.task61d;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.User;

public class MainFragment extends Fragment {

    private DatabaseHelper db;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText usernameEditText = view.findViewById(R.id.MainUsernameEditText);
        EditText passwordEditText = view.findViewById(R.id.MainPasswordEditText);
        Button loginButton = view.findViewById(R.id.MainLoginButton);
        Button signupButton = view.findViewById(R.id.MainSignupButton);
        db = new DatabaseHelper(getContext());

        //actions when login button is hit
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameString = usernameEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();

                //fail if wrong user name
                if (db.fetchUser(usernameString) == false) {
                    Toast.makeText(getContext(), "Incorrect username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //fail if wrong username + password
                if (db.fetchUser(usernameString, passwordString) == false) {
                    Toast.makeText(getContext(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //successfully logged in, so get user details and log in
                Toast.makeText(getContext(), "Successfully logged in!", Toast.LENGTH_SHORT).show();
                User user = db.getUser(usernameString);
                loginFragment(user.get_userId());
            }
        });

        //actions when signup button is hit
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupFragment();
            }
        });
    }

    //displays a selected fragment
    private void loginFragment(int userId) {
        //get the support fragment manager from main activity and initialize a transaction
        Fragment fragment = new HomePageFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", userId);
        Log.e("Sending from Start", String.valueOf(userId));
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }

    //displays a selected fragment
    private void signupFragment() {
        //get the support fragment manager from main activity and initialize a transaction
        Fragment fragment = new SignupFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }
}