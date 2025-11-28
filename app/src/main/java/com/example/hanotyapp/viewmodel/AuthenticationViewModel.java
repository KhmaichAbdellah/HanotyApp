package com.example.hanotyapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hanotyapp.data.repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationViewModel extends AndroidViewModel {

    private AuthenticationRepository repository;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> loggedStatus;
    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository(application);
        userData = repository.getFirebaseUserMutableLiveData();
        loggedStatus = repository.getUserLoggedMutableLiveData();
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }


    public void register(String email, String pass){
        repository.register(email, pass);
    }
    public void signIn(String email, String pass){
        repository.login(email, pass);
    }
    public void signOut(){
        repository.signOut();
    }
}
