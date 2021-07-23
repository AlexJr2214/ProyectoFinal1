package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal1.Clases.Workmanagernoti;
import com.example.proyectofinal1.Modelos.Tarea;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrearTareaActivity extends AppCompatActivity {
    Intent intent;
    int id;
    String nombre_usuario;

    List<Tarea> tareaList = new ArrayList<>();


    TextView tvVerHora, tvVerFecha;
    EditText etNombreTarea, etDescripcionTarea;

    int t1Hour, t1Minute;
    String hora, fecha, nombre, descripcion; //Variables que guardan los campos
    String capturaFecha; //Variable que guardara la fecha que seleccionemos en nuestro CalendarDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        this.RecibirDatos();
        this.InicializarControles();
    }

    public void InicializarControles(){
        tvVerFecha = findViewById(R.id.tvMostrarFecha);
        tvVerHora = findViewById(R.id.tvMostrarHora);
        etNombreTarea = findViewById(R.id.etNombreTarea);
        etDescripcionTarea = findViewById(R.id.etDescripcionTarea);
    }

    private void RecibirDatos(){
        intent = getIntent();
        nombre_usuario = intent.getStringExtra("nombre_usuario");
        id = intent.getIntExtra("id", 0);
        Toast.makeText(this, "Soy: " + nombre_usuario, Toast.LENGTH_SHORT).show();
    }

    public void onclik(View view) {
        switch (view.getId()){
            case R.id.btnSetearFecha:
                ObtenerFecha();
                break;

            case R.id.btnSetearHora:
                ObtenerHora();
                break;

            case R.id.btnCancelar:
                Intent intent = new Intent(this, TareasActivity.class);
                intent.putExtra("nombre_usuario", nombre_usuario);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
                break;

            case R.id.btnGuardarTarea:
                if(VerificarCampos()){
                    RegistrarTarea();
                }
                break;
        }
    }

    private void LimpiarCampos() {
        tvVerHora.setText("");
        tvVerFecha.setText("");
        etNombreTarea.setText("");
        etDescripcionTarea.setText("");
    }

    private void RegistrarTarea() {
        String ip = getString(R.string.ip);
        String URL_REGISTRO_TAREA = "http://"+ip+"/ApisPHP/ApiD6Semestral/registrar_tarea.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRO_TAREA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int x=0;
                LimpiarCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x=0;
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", String.valueOf(id));
                params.put("nombre_tarea", nombre);
                params.put("descripcion_tarea", descripcion);
                params.put("fecha_tarea", fecha);
                params.put("hora_tarea", hora);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void ObtenerHora(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CrearTareaActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hour = hourOfDay;
                        t1Minute = minute;

                        String time = t1Hour + ":" + t1Minute;

                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            tvVerHora.setText(f24Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1Hour, t1Minute);
        timePickerDialog.show();
    }

    private void ObtenerFecha(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CrearTareaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String sDate = dayOfMonth + "/" + month + "/" + year;

                tvVerFecha.setText(sDate);
                capturaFecha = sDate; //Guardo en una variable de tipo String la fecha.
            }
        }, year, month, day
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    private void ObtenerCampos(){
        hora = tvVerHora.getText().toString();
        fecha = tvVerFecha.getText().toString();
        nombre = etNombreTarea.getText().toString();
        descripcion = etDescripcionTarea.getText().toString();
    }

    private boolean VerificarCampos() {
        ObtenerCampos();
        if(hora.isEmpty()  || fecha.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()){
            Toast.makeText(this, "LLene todos los campos, por favor!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }
}