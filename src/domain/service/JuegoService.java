package domain.service;

import domain.model.*;
import domain.model.habilidades.Habilidad;
import domain.repository.PreguntaRepository;
import java.util.ArrayList;
import java.util.List;

public class JuegoService {
    private final PreguntaRepository preguntaRepository;
    private Jugador jugador;
    private List<Nivel> niveles;
    private Nivel nivelActual;
    private int nivelIndex;

    public JuegoService(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    public void inicializarJuego(String nombreJugador, List<Habilidad> habilidades) {
        this.jugador = new Jugador(nombreJugador, 100, habilidades);
        this.niveles = construirNiveles();
        this.nivelIndex = 0;
        if (!niveles.isEmpty()) {
            this.nivelActual = niveles.get(0);
        }
    }

    private List<Nivel> construirNiveles() {
        List<Nivel> niveles = new ArrayList<>();

        // Nivel 1
        List<Pregunta> preguntasNivel1 = preguntaRepository.obtenerPorCategoria("Programación");
        if (!preguntasNivel1.isEmpty()) {
            niveles.add(new Nivel("Fundamentos de Programación", preguntasNivel1, 2));
        }

        // Nivel 2
        List<Pregunta> preguntasNivel2 = preguntaRepository.obtenerPorCategoria("POO");
        if (!preguntasNivel2.isEmpty()) {
            niveles.add(new Nivel("Programación Orientada a Objetos", preguntasNivel2, 3));
        }

        // Nivel final
        List<Pregunta> preguntasFinal = preguntaRepository.obtenerPorDificultad(3);
        if (!preguntasFinal.isEmpty()) {
            niveles.add(new Nivel("Nivel Final", preguntasFinal, 5, 200));
        }

        return niveles;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public Pregunta obtenerPreguntaActual() {
        if (nivelActual == null || nivelActual.getPreguntas().isEmpty()) {
            return null;
        }
        return nivelActual.getPreguntas().get(0);
    }

    public void removerPreguntaActual() {
        if (nivelActual != null) {
            nivelActual.removerPreguntaActual();
        }
    }

    public boolean avanzarAlSiguienteNivel() {
        nivelIndex++;
        if (nivelIndex < niveles.size()) {
            nivelActual = niveles.get(nivelIndex);
            jugador.resetearHabilidades();
            return true;
        }
        nivelActual = null;
        return false;
    }

    public int getNivelIndex() {
        return nivelIndex;
    }

    public int getTotalNiveles() {
        return niveles.size();
    }
}