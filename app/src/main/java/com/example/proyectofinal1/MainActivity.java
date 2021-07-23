package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.proyectofinal1.LoginAndRegisterActivities.RegistrarActivity;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnLogear:
                Toast.makeText(this, "LOGEAR", Toast.LENGTH_SHORT).show();
                finish();
                break;
                
            case R.id.btnRegistrar:
                intent = new Intent(this, RegistrarActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnContinuar:
                intent = new Intent(this, TareasActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}