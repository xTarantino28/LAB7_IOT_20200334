package com.example.lab7_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.lab7_20200334_iot.databinding.ActivityStorageBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageActivity extends AppCompatActivity {
    ActivityStorageBinding binding;
    FirebaseStorage storage;
    StorageReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        /*binding.btnUploadFile.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });*/

        /*binding.btnDownloadSave.setOnClickListener(view -> {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                descargarYguardar();
            } else {
                String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
                requestReadPermissionLauncher.launch(permission);
            }
        });*/

        //listarArchivos();
        //descargarYMostrarPeroNoGuardar();
        //descargarYMostrarPeroNoGuardarForma2();
    }

    public void descargarYMostrarPeroNoGuardar(){
        StorageReference islandRef = reference.child("images/1000000010");

        final long three_MEGABYTE = 1024 * 1024 * 3;
        /*islandRef.getBytes(three_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                InputStream is = new ByteArrayInputStream(bytes);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                binding.imageView2.setImageBitmap(bmp);
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/
    }

    public void descargarYMostrarPeroNoGuardarForma2(){
        StorageReference islandRef = reference.child("images/1000000011");

        /*Glide.with(StorageActivity.this).load(islandRef).into(binding.imageView2);*/
    }

    public void listarArchivos(){
        StorageReference listRef = storage.getReference().child("images");

        listRef.listAll()
                .addOnSuccessListener(listResult -> {
                    String[] items = new String[listResult.getItems().size()];
                    int i = 0;

                    for (StorageReference item : listResult.getItems()) {
                        // All the items under listRef.
                        Log.d("msg-test","item.getName(): " + item.getName());
                        Log.d("msg-test","item.getPath(): " + item.getPath());
                        items[i++] = item.getName();
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(StorageActivity.this, android.R.layout.simple_spinner_dropdown_item,items);
                   // binding.spinner.setAdapter(adapter);

                })
                .addOnFailureListener(e -> e.printStackTrace());
    }

    public void descargarYguardar() {

       // String selectedItem = (String) binding.spinner.getSelectedItem();

       // File directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File localFile = new File(directorio, selectedItem);

        //StorageReference docRef = storage.getReference().child("images").child(selectedItem);

        /*docRef.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.d("msg-test", "archivo descargado");
                    Toast.makeText(StorageActivity.this,"Archivo descargado",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.d("msg-test", "error", e.getCause());
                })
                .addOnProgressListener(snapshot -> {
                    long bytesTransferred = snapshot.getBytesTransferred();
                    long totalByteCount = snapshot.getTotalByteCount();

                    binding.textViewDownload.setText(Math.round((bytesTransferred * 1.0f / totalByteCount) * 100) + "%");
                    //
                });*/
    }

   /* ActivityResultLauncher<String> requestReadPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    descargarYguardar();
                }
            }
    );*/

    /*ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    Log.d("msg-test", "Selected URI: " + uri);
                    Log.d("msg-test", "uri.getLastPathSegment(): " + uri.getLastPathSegment());

                    StorageReference imagesRef = reference.child("images/" + uri.getLastPathSegment() + ".jpg"); //.child("images").child(uri.getLastPathSegment());

                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setCustomMetadata("tipo", "gato")
                            .setCustomMetadata("dueño", "profe 2")
                            .build();

                    UploadTask uploadTask = imagesRef.putFile(uri);

                    uploadTask.addOnFailureListener(exception -> {
                        exception.printStackTrace();
                        //
                    }).addOnSuccessListener(taskSnapshot -> {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(StorageActivity.this, "Archivo subido correctamente", Toast.LENGTH_SHORT).show();
                    }).addOnProgressListener(snapshot -> {
                        long bytesTransferred = snapshot.getBytesTransferred();
                        long totalByteCount = snapshot.getTotalByteCount();
                        double porcentajeSubida = Math.round((bytesTransferred * 1.0f / totalByteCount) * 100);

                        binding.textViewPorc.setText(porcentajeSubida + "%");
                        //
                    });
                } else {
                    Log.d("msg-test", "No media selected");
                }
            });*/
}


