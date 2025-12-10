package com.example.hanotyapp.data.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.hanotyapp.data.local.AppDatabase;
import com.example.hanotyapp.data.local.dao.UserDao;
import com.example.hanotyapp.data.local.entity.UserEntity;

public class AuthenticationRepository {

    private Application application;
    private MutableLiveData<UserEntity> userMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private UserDao userDao;
    private AppDatabase db;

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public MutableLiveData<UserEntity> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public AuthenticationRepository(Application application) {
        this.application = application;
        userMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();

        db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public void register(String email, String pass, String name, String phone) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            UserEntity existingUser = userDao.findByEmail(email);
            if (existingUser == null) {
                UserEntity newUser = new UserEntity(email, pass, name, phone);
                userDao.insert(newUser);
                userMutableLiveData.postValue(newUser);
            } else {
                // User already exists
                userMutableLiveData.postValue(null);
            }
        });
    }

    public void login(String email, String pass) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            UserEntity user = userDao.login(email, pass);
            if (user != null) {
                userMutableLiveData.postValue(user);
            } else {
                userMutableLiveData.postValue(null);
            }
        });
    }

    public void signOut() {
        userMutableLiveData.postValue(null);
        userLoggedMutableLiveData.postValue(true);
    }
}
