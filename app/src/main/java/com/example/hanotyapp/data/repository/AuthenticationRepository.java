package com.example.hanotyapp.data.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepository{

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private FirebaseAuth auth;
    GoogleSignInAccount googleSignInAccount;

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public AuthenticationRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public void register(String email, String pass){
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public  void login(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signOut(){
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account){
        if(account == null) return;

        // Récupère le token ID du compte Google
        String idToken = account.getIdToken();
        if(idToken == null) return;

        // Crée les credentials Firebase à partir du token Google
        com.google.firebase.auth.AuthCredential credential =
                com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null);

        // Authentifie avec Firebase
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
            } else {
                Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
