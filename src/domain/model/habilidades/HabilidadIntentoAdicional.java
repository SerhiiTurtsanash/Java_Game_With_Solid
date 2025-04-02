package domain.model.habilidades;

import domain.model.Jugador;
import domain.model.Pregunta;

public class HabilidadIntentoAdicional implements Habilidad {
    private boolean usada = false;

    @Override
    public String getNombre() {
        return "Intento extra";
    }

    @Override
    public String getDescripcion() {
        return "Te permite responder nuevamente si fallas";
    }

    @Override
    public boolean puedeUsar() {
        return !usada;
    }

    @Override
    public void usar(Jugador jugador, Pregunta pregunta) {
        System.out.println("\n¡Tienes un intento adicional para esta pregunta!");
        usada = true;
    }

    @Override
    public void resetear() {
        usada = false;
    }
}