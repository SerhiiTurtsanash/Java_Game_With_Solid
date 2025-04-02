package domain.model.habilidades;

import domain.model.Jugador;
import domain.model.Pregunta;

public class HabilidadPista implements Habilidad {
    private boolean usada = false;

    @Override public String getNombre() { return "Pista"; }
    @Override public String getDescripcion() { return "Muestra una pista para la pregunta"; }

    @Override
    public boolean puedeUsar() { return !usada; }

    @Override
    public void usar(Jugador jugador, Pregunta pregunta) {
        System.out.println("\nPista: " + pregunta.getPista());
        usada = true;
    }

    @Override
    public void resetear() { usada = false; }
}