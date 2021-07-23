package com.example.proyectofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrearTareaActivity extends AppCompatActivity {

    TextView tvVerHora, tvVerFecha;
    EditText etNombreTarea, etDescripcionTarea;
    Button crearTarea;
    int t1Hour, t1Minute;
    String hora, fecha, nombre, descripcion;
    String capturaFecha; //Variable que guardara la fecha que seleccionemos en nuestro CalendarDialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        this.InicializarControles();
    }

    public void InicializarControles(){
        tvVerFecha = findViewById(R.id.tvMostrarFecha);
        tvVerHora = findViewById(R.id.tvMostrarHora);
        etNombreTarea = findViewById(R.id.etNombreTarea);
        etDescripcionTarea = findViewById(R.id.etDescripcionTarea);
        crearTarea = findViewById(R.id.btnCrearTarea);

        //crearTarea.setOnClickListener(this::onclik);

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
                startActivity(intent);
                finish();
                break;

            case R.id.btnGuardarTarea:
                if(VerificarCampos()){
                    Toast.makeText(this, "Tarea Guardada", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
                            tvVerHora.setText(f12Hours.format(date));
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