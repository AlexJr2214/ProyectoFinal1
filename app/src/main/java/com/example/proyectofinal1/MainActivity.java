package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnLogear:
                Toast.makeText(this, "LOGEAR", Toast.LENGTH_SHORT).show();
                break;
                
            case R.id.btnRegistrar:
                Toast.makeText(this, "REGISTRAR", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}