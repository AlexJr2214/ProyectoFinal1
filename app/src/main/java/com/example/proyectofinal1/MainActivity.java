package com.example.proyectofinal1;

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
import com.example.proyectofinal1.LoginAndRegisterActivities.RegistrarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String nombre, pass;
    EditText usuario, passowrd;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InicializarControles();
    }

    private void InicializarControles() {
        usuario = findViewById(R.id.etNombreUsuarioLogin);
        passowrd = findViewById(R.id.etPasswordLogin);
    }

    private void ObtenerCampos(){
        nombre = usuario.getText().toString();
        pass = passowrd.getText().toString();
    }

    private boolean ComprobarCampos(){
        ObtenerCampos();
        if(nombre.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "No dejar campos vacios!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    private void LogearUsuario() {
        String ip = getString(R.string.ip);
        String URL_LOGIN = "http://"+ip+"/ApisPHP/ApiD6Semestral/login_usuario.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int x=0;
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(MainActivity.this, message.toUpperCase(), Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            String usuario = "";
                            int id = 0;

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                id = object.getInt("id");
                                usuario = object.getString("nombre_usuario");

                                Toast.makeText(MainActivity.this, "id: " + id + " usuario: " + usuario, Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(getApplicationContext(), TareasActivity.class);
                            //intent.putExtra("nombre_usuario", usuario);
                            //intent.putExtra("id", id);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int x=0;
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nombre_usuario", nombre);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnLogear:
                if(ComprobarCampos()){
                    LogearUsuario();
                }
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