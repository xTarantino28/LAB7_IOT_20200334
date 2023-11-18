package com.example.lab7_20200334_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab7_20200334_iot.adapters.ListaCitasAdapter;
import com.example.lab7_20200334_iot.databinding.ActivityMainBinding;
import com.example.lab7_20200334_iot.dtos.Cita;
import com.example.lab7_20200334_iot.dtos.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ActivityMainBinding binding;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        binding.logoutBoton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


        db.collection("usuarios")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("citas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Cita> listaCitas = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("msg-test", document.getId() + " => " + document.getData());
                                Cita cita = document.toObject(Cita.class);
                                listaCitas.add(cita);
                            }
                            if (listaCitas.isEmpty()) {
                                binding.textView.setText("No hay citas");
                            }
                            ListaCitasAdapter adapter = new ListaCitasAdapter();
                            adapter.setContext(MainActivity.this);
                            adapter.setListaCitas(listaCitas);

                            binding.recyclerViewCitas.setAdapter(adapter);
                            binding.recyclerViewCitas.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        } else {
                            Log.d("msg-test", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}