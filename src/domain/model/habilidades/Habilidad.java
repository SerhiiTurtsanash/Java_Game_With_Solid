package domain.model.habilidades;

import domain.model.Jugador;
import domain.model.Pregunta;

public interface Habilidad {
    String getNombre();
    String getDescripcion();
    boolean puedeUsar();
    void usar(Jugador jugador, Pregunta pregunta);
    void resetear();
}