package com.example.proyectofinal1.LoginAndRegisterActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal1.MainActivity;
import com.example.proyectofinal1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    
    String nombre, pass, comprobar;
    EditText usuario, passowrd, comprobarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        
        this.InicializarControles();
    }

    private void InicializarControles() { 
        usuario = findViewById(R.id.etNombreUsuario);
        passowrd = findViewById(R.id.etPassword);
        comprobarPassword = findViewById(R.id.etComprobarPassword);
    }
    
    private void ObtenerCampos(){
        nombre = usuario.getText().toString();
        pass = passowrd.getText().toString();
        comprobar = comprobarPassword.getText().toString();
    }
    
    private boolean ComprobarCampos(){
        ObtenerCampos();
        if(nombre.isEmpty() || pass.isEmpty() || comprobar.isEmpty()){
            Toast.makeText(this, "Campos vacios! Por favor llenelos.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(pass.equals(comprobar)){
                nombre = nombre.toLowerCase();
                Toast.makeText(this, "nombre: " + nombre, Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(this, "Pass no coinciden", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnRegistrar:
                if (ComprobarCampos()){
                    RegistrarUsuario();
                }
                break;

            case R.id.btnCancelarRegistro:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void RegistrarUsuario() {
        String ip = getString(R.string.ip);
        String URL_REGISTRO = "http://"+ip+"/ApisPHP/ApiD6Semestral/register_usuario.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int x=0;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if(success.equals("1")){
                        Toast.makeText(RegistrarActivity.this, message.toUpperCase(), Toast.LENGTH_SHORT).show();
                        RedirigirUsuarioRegistrado();
                    }else {
                        Toast.makeText(RegistrarActivity.this, message.toUpperCase(), Toast.LENGTH_SHORT).show();
                        VaciarCampos();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre_usuario", nombre);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void RedirigirUsuarioRegistrado(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void VaciarCampos(){
        ObtenerCampos();
        usuario.setText("");
        passowrd.setText("");
        comprobarPassword.setText("");
    }
}