package domain.model.habilidades;

import domain.model.Jugador;
import domain.model.Pregunta;

public class HabilidadRecuperarVida implements Habilidad {
    private boolean usada = false;
    private static final int VIDA_RECUPERADA = 15;

    @Override
    public String getNombre() {
        return "Recuperar vida";
    }

    @Override
    public String getDescripcion() {
        return "Recupera " + VIDA_RECUPERADA + " puntos de vida";
    }

    @Override
    public boolean puedeUsar() {
        return !usada;
    }

    @Override
    public void usar(Jugador jugador, Pregunta pregunta) {
        int vidaActual = jugador.getVida();
        jugador.setVida(vidaActual + VIDA_RECUPERADA);
        System.out.printf("\n¡Has recuperado %d puntos de vida! (Vida actual: %d)%n",
                VIDA_RECUPERADA, jugador.getVida());
        usada = true;
    }

    @Override
    public void resetear() {
        usada = false;
    }
}