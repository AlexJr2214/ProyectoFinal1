package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal1.Modelos.Tarea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareasActivity extends AppCompatActivity {

    ListView listView;

    List<Tarea> tareaList;
    List<String> listaInformacion;

    Intent intent;


    int id;
    String nombre_usuario;


    ArrayAdapter<Tarea> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        this.InicializarControles();
        this.RecibirDatos();

    }

    private void RecibirDatos(){
        intent = getIntent();
        nombre_usuario = intent.getStringExtra("nombre_usuario");
        id = intent.getIntExtra("id", 0);

        this.HacerConsulta(id);

    }
    private void HacerConsulta(int id) {
        String ip = getString(R.string.ip);
        String URL_TAREAS = "http://"+ip+"/ApisPHP/ApiD6Semestral/mostrar_tareas.php?id_usuario="+id;

        tareaList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_TAREAS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int x=0;
                        Toast.makeText(TareasActivity.this, "Tamaño" + response.length(), Toast.LENGTH_SHORT).show();
                        if(response.length() == 2){
                            try {

                                String success = response.getString("success");
                                String message = response.getString("message");

                                if(success.equals("0")){
                                    Toast.makeText(TareasActivity.this, message.toUpperCase(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if(response.length() == 1){
                            Toast.makeText(TareasActivity.this, "Tienes tareas pendientes", Toast.LENGTH_SHORT).show();

                            try {
                                JSONArray jsonArray = response.getJSONArray("tareas");

                                for(int index = 0; index < jsonArray.length(); index++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(index);

                                    Tarea tarea = new Tarea();

                                    String nombre_tarea = jsonObject.getString("nombre_tarea");
                                    String descripcion_tarea = jsonObject.getString("descripcion_tarea");
                                    String fecha_tarea = jsonObject.getString("fecha_tarea");
                                    String hora_tarea = jsonObject.getString("hora_tarea");

                                    tarea.setNombre_tarea(nombre_tarea);
                                    tarea.setDescripcion_tarea(descripcion_tarea);
                                    tarea.setFecha_tarea(fecha_tarea);
                                    tarea.setHora_tarea(hora_tarea);

                                    tareaList.add(tarea);
                                }
                                listaInformacion = new ArrayList<>();
                                for(int i=0; i<tareaList.size(); i++){
                                    listaInformacion.add(tareaList.get(i).getNombre_tarea() + " - " + tareaList.get(i).getDescripcion_tarea() + " - " + tareaList.get(i).getFecha_tarea() + " - " + tareaList.get(i).getHora_tarea());
                                }

                                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaInformacion);
                                listView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void InicializarControles() {
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(TareasActivity.this, "posicion: " + tareaList.get(position).getDescripcion_tarea(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnCrearTarea:
                intent = new Intent(this, CrearTareaActivity.class);
                intent.putExtra("nombre_usuario", nombre_usuario);
                intent.putExtra("id", id);
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