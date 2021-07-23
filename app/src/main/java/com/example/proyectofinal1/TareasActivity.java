package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TareasActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnCrearTarea:
                intent = new Intent(this, CrearTareaActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnCerrarSesion:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}