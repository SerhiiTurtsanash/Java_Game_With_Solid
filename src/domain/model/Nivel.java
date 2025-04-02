package domain.model;

import java.util.List;

public class Nivel {
    private final String nombre;
    private final List<Pregunta> preguntas;
    private final int danioPorSegundo;
    private final int puntosBase;

    public Nivel(String nombre, List<Pregunta> preguntas, int danioPorSegundo) {
        this(nombre, preguntas, danioPorSegundo, 100);
    }

    public Nivel(String nombre, List<Pregunta> preguntas, int danioPorSegundo, int puntosBase) {
        this.nombre = nombre;
        this.preguntas = preguntas;
        this.danioPorSegundo = danioPorSegundo;
        this.puntosBase = puntosBase;
    }
    public String getNombre() {
        return nombre;
    }
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
    public int getDanioPorSegundo() {
        return danioPorSegundo;
    }
    public int getPuntosBase() {
        return puntosBase;
    }
    public boolean estaCompletado() {
        return preguntas.isEmpty();
    }
    public Pregunta obtenerPreguntaActual() {
        return preguntas.isEmpty() ? null : preguntas.get(0);
    }
    public void removerPreguntaActual() {
        if (!preguntas.isEmpty()) {
            preguntas.remove(0);
        }
    }
}
