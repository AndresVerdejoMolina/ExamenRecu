package com.example.examenrecu;

public class Comentario {

    String valoracion;
    String comentarios;

    public Comentario(String valoracion, String comentarios) {
        this.valoracion = valoracion;
        this.comentarios = comentarios;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
