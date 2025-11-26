package com.example.hanotyapp.ui.auth;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hanotyapp.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lier le layout login.xml à cette activité
        setContentView(R.layout.login);
    }
}
