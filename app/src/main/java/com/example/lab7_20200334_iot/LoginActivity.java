package com.example.lab7_20200334_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lab7_20200334_iot.databinding.ActivityLoginBinding;
import com.example.lab7_20200334_iot.dtos.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    FirebaseStorage storage;
    StorageReference reference;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //storage = FirebaseStorage.getInstance();
        //reference = storage.getReference();
        db = FirebaseFirestore.getInstance();


        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) { //user logged-in
            db.collection("usuarios")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(taskDB -> {
                        if (taskDB.isSuccessful()) {
                            DocumentSnapshot document = taskDB.getResult();
                            if (document.exists()) {
                                Log.d("msg-test", "DocumentSnapshot data: " + document.getData());
                                Usuario usuario = document.toObject(Usuario.class);
                                if (usuario != null) {

                                    if (usuario.getRol().equalsIgnoreCase("gestor")) {
                                        //ACCEDE A LOGUEARSE A GESTOR (MAIN ACTIVITY)
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("usuario", usuario);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        //ACCEDE A LOGUEARSE A CLIENTE (CLIENTE ACTIVITY)
                                        Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
                                        intent.putExtra("usuario", usuario);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            } else {
                                Log.d("nsg-test", "No such document");
                            }
                        } else {
                            Log.d("msg-test", "get failed with ", taskDB.getException());
                        }
                    });
            Log.d("msg-test", "Firebase uid: " + currentUser.getUid());
        }


        binding.loginBtn.setOnClickListener(view -> {

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("msg-test", "signInWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    db.collection("usuarios")
                                            .document(user.getUid())
                                            .get()
                                            .addOnCompleteListener(taskDB -> {
                                                if (taskDB.isSuccessful()) {
                                                    DocumentSnapshot document = taskDB.getResult();
                                                    if (document.exists()) {
                                                        Log.d("msg-test", "DocumentSnapshot data: " + document.getData());
                                                        Usuario usuario = document.toObject(Usuario.class);
                                                        if (usuario != null) {

                                                            if (usuario.getRol().equalsIgnoreCase("gestor")) {
                                                                //ACCEDE A LOGUEARSE A GESTOR (MAIN ACTIVITY)
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                intent.putExtra("usuario", usuario);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                //ACCEDE A LOGUEARSE A CLIENTE (CLIENTE ACTIVITY)
                                                                Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
                                                                intent.putExtra("usuario", usuario);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }

                                                    } else {
                                                        Log.d("nsg-test", "No such document");
                                                    }
                                                } else {
                                                    Log.d("msg-test", "get failed with ", taskDB.getException());
                                                }
                                            });
                                } else {
                                    Log.w("msg-test", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Vuelva a ingresar credenciales", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            } else {
                Toast.makeText(LoginActivity.this, "Campos no vacios", Toast.LENGTH_SHORT).show();
            }
        });




    }
}