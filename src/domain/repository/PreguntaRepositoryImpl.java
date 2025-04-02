package domain.repository;

import domain.model.Pregunta;
import java.util.ArrayList;
import java.util.List;

public class PreguntaRepositoryImpl implements PreguntaRepository {
    private final List<Pregunta> preguntas;

    public PreguntaRepositoryImpl() {
        this.preguntas = cargarPreguntasBase();
    }

    private List<Pregunta> cargarPreguntasBase() {
        List<Pregunta> preguntas = new ArrayList<>();

        // Nivel 1
        preguntas.add(new Pregunta("P1",
                "¿Cuál es la sintaxis correcta de un condicional en Java?",
                new String[]{"if(condicion) {}", "if condicion {}", "if: (condicion)", "if {condicion}"},
                1, "Recuerda que los paréntesis en los condicionales son obligatorios.", 2, "Programación"));

        preguntas.add(new Pregunta("P2",
                "¿Qué palabra clave se utiliza para declarar una variable?",
                new String[]{"var", "let", "int", "declare"},
                3, "Es una de las palabras reservadas para tipos primitivos en Java.", 1, "Programación"));

        // Nivel 2
        preguntas.add(new Pregunta("P3",
                "¿Qué significa POO?",
                new String[]{"Programación Orientada a Objetos", "Primera Orden Operacional",
                        "Planificación Objetiva de Operaciones", "Programación Obvia y Ordinaria"},
                1, "Es un paradigma muy popular en la programación moderna.", 2, "POO"));

        preguntas.add(new Pregunta("P4",
                "¿Qué palabra clave se utiliza para crear una clase?",
                new String[]{"class", "struct", "object", "create"},
                1, "Esta palabra clave define estructuras principales en POO.", 2, "POO"));

        // Nivel Final
        preguntas.add(new Pregunta("P5",
                "¿Qué es la sobrecarga de métodos?",
                new String[]{"Dos métodos con el mismo nombre pero diferentes parámetros",
                        "Un método que llama a otro",
                        "Un método que se ejecuta dos veces",
                        "Un método con parámetros idénticos"},
                1, "Es un concepto importante en POO.", 3, "Avanzado"));

        preguntas.add(new Pregunta("P6",
                "¿Qué palabra clave se usa para heredar de una clase?",
                new String[]{"extend", "inherit", "super", "class"},
                1, "Permite a una clase adquirir las propiedades de otra.", 3, "Avanzado"));

        return preguntas;
    }

    @Override
    public List<Pregunta> obtenerTodas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public List<Pregunta> obtenerPorCategoria(String categoria) {
        List<Pregunta> resultado = new ArrayList<>();
        for (Pregunta p : preguntas) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public List<Pregunta> obtenerPorDificultad(int dificultad) {
        List<Pregunta> resultado = new ArrayList<>();
        for (Pregunta p : preguntas) {
            if (p.getDificultad() == dificultad) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public Pregunta obtenerPorId(String id) {
        for (Pregunta p : preguntas) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}