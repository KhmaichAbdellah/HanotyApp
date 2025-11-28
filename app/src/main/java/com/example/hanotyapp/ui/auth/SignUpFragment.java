package com.example.hanotyapp.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanotyapp.R;
import com.example.hanotyapp.viewmodel.AuthenticationViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class SignUpFragment extends Fragment {

    private static final int RC_SIGN_IN = 100;
    private TextInputEditText emailEdit, passEdit, confirmPassEdit;
    private MaterialButton signUpBtn, googleSignInBtn;
    private TextView alreadyHaveAccount;
    private AuthenticationViewModel viewModel;
    private NavController navController;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthenticationViewModel.class);
        viewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEdit = view.findViewById(R.id.registerEmail);
        passEdit = view.findViewById(R.id.registerPassword);
        confirmPassEdit = view.findViewById(R.id.registerConfirmPassword);
        signUpBtn = view.findViewById(R.id.registerButton);
        googleSignInBtn = view.findViewById(R.id.googleSignInButton);
        alreadyHaveAccount = view.findViewById(R.id.alreadyHaveAccount);
        navController = Navigation.findNavController(view);

        // Configurer Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString().trim();
                String pass = passEdit.getText().toString().trim();
                String confirmPass = confirmPassEdit.getText().toString().trim();

                if(email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!pass.equals(confirmPass)) {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.length() < 6) {
                    Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                viewModel.register(email, pass);
            }
        });
        googleSignInBtn.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                if(account != null) {
                    viewModel.signInWithGoogle(account);
                }
            } catch (ApiException e) {
                Log.e("GoogleSignIn", "code=" + e.getStatusCode() + ", message=" + e.getMessage());
                Toast toast = Toast.makeText(getContext(), "Google sign-in failed", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundColor(Color.RED); // fond rouge
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE); // texte blanc
                text.setTextSize(18); // texte plus grand
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}