package domain.repository;

import domain.model.Pregunta;
import java.util.List;

public interface PreguntaRepository {
    List<Pregunta> obtenerTodas();
    List<Pregunta> obtenerPorCategoria(String categoria);
    List<Pregunta> obtenerPorDificultad(int dificultad);
    Pregunta obtenerPorId(String id);
}