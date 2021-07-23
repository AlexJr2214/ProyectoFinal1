package com.example.proyectofinal1.Modelos;

import java.sql.Time;
import java.util.Date;

public class Tarea {
    int id_tarea;
    int id_usuario;
    String nombre_tarea;
    String descripcion_tarea;
    String fecha_tarea;
    String hora_tarea;

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public String getDescripcion_tarea() {
        return descripcion_tarea;
    }

    public void setDescripcion_tarea(String descripcion_tarea) {
        this.descripcion_tarea = descripcion_tarea;
    }

    public String getFecha_tarea() {
        return fecha_tarea;
    }

    public void setFecha_tarea(String fecha_tarea) {
        this.fecha_tarea = fecha_tarea;
    }

    public String getHora_tarea() {
        return hora_tarea;
    }

    public void setHora_tarea(String hora_tarea) {
        this.hora_tarea = hora_tarea;
    }
}
