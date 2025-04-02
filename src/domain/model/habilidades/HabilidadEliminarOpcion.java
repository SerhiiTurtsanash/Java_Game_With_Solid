package domain.model.habilidades;

import domain.model.Jugador;
import domain.model.Pregunta;

public class HabilidadEliminarOpcion implements Habilidad {
    private boolean usada = false;

    @Override public String getNombre() { return "Eliminar opción"; }
    @Override public String getDescripcion() { return "Elimina una respuesta incorrecta"; }

    @Override
    public boolean puedeUsar() { return !usada; }

    @Override
    public void usar(Jugador jugador, Pregunta pregunta) {
        String[] opciones = pregunta.getOpciones();
        int incorrecta = -1;

        for (int i = 0; i < opciones.length; i++) {
            if (i != pregunta.getRespuestaCorrecta() - 1 && !opciones[i].startsWith("[X] ")) {
                incorrecta = i;
                break;
            }
        }

        if (incorrecta != -1) {
            opciones[incorrecta] = "[X] " + opciones[incorrecta];
            System.out.println("\nSe eliminó la opción: " + opciones[incorrecta].substring(4));
        } else {
            System.out.println("\nNo hay opciones para eliminar");
        }
        usada = true;
    }

    @Override
    public void resetear() { usada = false; }
}