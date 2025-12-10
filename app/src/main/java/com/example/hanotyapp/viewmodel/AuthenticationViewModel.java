package com.example.hanotyapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hanotyapp.data.local.entity.UserEntity;
import com.example.hanotyapp.data.repository.AuthenticationRepository;

public class AuthenticationViewModel extends AndroidViewModel {

    private AuthenticationRepository repository;
    private MutableLiveData<UserEntity> userData;
    private MutableLiveData<Boolean> loggedStatus;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepository(application);
        userData = repository.getUserMutableLiveData();
        loggedStatus = repository.getUserLoggedMutableLiveData();
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public MutableLiveData<UserEntity> getUserData() {
        return userData;
    }

    public void register(String email, String pass, String name, String phone) {
        repository.register(email, pass, name, phone);
    }

    public void signIn(String email, String pass) {
        repository.login(email, pass);
    }

    public void signOut() {
        repository.signOut();
    }
}
