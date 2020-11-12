package com.example.servitec.ui.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servitec.R;
import com.example.servitec.clases.Modelos.POJORespuesta;
import com.example.servitec.API.RetroClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Up_Image_Fragment extends Fragment {

    private static final int IMG_REQUEST = 21;
    private Bitmap bitmap;
    private ImageView imageView;
    private Button btnSelectImage, btnUploadImage;
    private ProgressBar progressBar;

    public Up_Image_Fragment() {
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
        return inflater.inflate(R.layout.fragment_up__image_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = requireActivity().findViewById(R.id.imageView);
        btnSelectImage = requireActivity().findViewById(R.id.btnSelectImage);
        btnUploadImage = requireActivity().findViewById(R.id.btnUploadImage);
        progressBar = requireActivity().findViewById(R.id.pb_media_up);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageView.getDrawable() == null)
                {
                    showToast("Debe Escoger una Imagen");
                }
                else {
                    upLoadImage();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),path);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upLoadImage()
    {
        btnUploadImage.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage =  Base64.encodeToString(imageInByte,Base64.DEFAULT);


        Call<POJORespuesta> call = RetroClient.getInstance().getApi().uploadImage(encodedImage);

        call.enqueue(new Callback<POJORespuesta>() {
            @Override
            public void onResponse(Call<POJORespuesta> call, Response<POJORespuesta> response) {

                try
                {
                    if (response.isSuccessful() && response.code() == 200 && response.body().estado() == Boolean.TRUE)
                    {
                        showToast(response.body().respuesta());
                        progressBar.setVisibility(View.INVISIBLE);
                        btnUploadImage.setEnabled(true);
                    }
                    else
                    {
                        showToast(response.body().respuesta());
                        progressBar.setVisibility(View.INVISIBLE);
                        btnUploadImage.setEnabled(true);
                    }
                }
                catch (Exception e)
                {
                    showToast(e.toString());
                    progressBar.setVisibility(View.INVISIBLE);
                    btnUploadImage.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<POJORespuesta> call, Throwable t)
            {
                showToast(t.toString());
                progressBar.setVisibility(View.INVISIBLE);
                btnUploadImage.setEnabled(true);
            }
        });

    }

    private void showToast(String mensaje)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.layout_toast));

        TextView txt_mensaje = layout.findViewById(R.id.txt_mensaje);

        txt_mensaje.setText(mensaje);

        Toast toast = new Toast(requireActivity());

        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);

        toast.show();
    }
}