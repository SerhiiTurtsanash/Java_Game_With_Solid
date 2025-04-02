package infrastructure.console;

import domain.model.*;
import domain.model.habilidades.*;
import domain.service.JuegoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final JuegoService juegoService;
    private boolean juegoEnCurso = false;

    public ConsoleUI(JuegoService juegoService) {
        this.scanner = new Scanner(System.in);
        this.juegoService = juegoService;
    }

    public void iniciar() {
        mostrarBienvenida();
        String nombreJugador = obtenerNombreJugador();
        List<Habilidad> habilidades = seleccionarHabilidades();

        juegoService.inicializarJuego(nombreJugador, habilidades);
        comenzarJuego();
    }

    private void mostrarBienvenida() {
        System.out.println("BIENVENIDO A MI JUEGO");
        System.out.println("=============================================");
    }

    private String obtenerNombreJugador() {
        System.out.print("\nIngresa tu nombre: ");
        return scanner.nextLine();
    }

    private List<Habilidad> seleccionarHabilidades() {
        System.out.println("\nSelecciona 3 habilidades para tu aventura:");

        List<Habilidad> habilidadesDisponibles = new ArrayList<>();
        habilidadesDisponibles.add(new HabilidadPista());
        habilidadesDisponibles.add(new HabilidadEliminarOpcion());
        habilidadesDisponibles.add(new HabilidadRecuperarVida());
        habilidadesDisponibles.add(new HabilidadIntentoAdicional());

        habilidadesDisponibles.forEach(h ->
                System.out.printf("- %s: %s%n", h.getNombre(), h.getDescripcion()));

        List<Habilidad> seleccionadas = new ArrayList<>();
        while (seleccionadas.size() < 3) {
            System.out.printf("\nElige la habilidad %d (1-4): ", seleccionadas.size() + 1);
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion >= 1 && opcion <= 4) {
                Habilidad seleccionada = habilidadesDisponibles.get(opcion - 1);
                seleccionadas.add(seleccionada);
                System.out.printf("Añadida: %s%n", seleccionada.getNombre());
            } else {
                System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }

        return seleccionadas;
    }

    private void comenzarJuego() {
        juegoEnCurso = true;
        Jugador jugador = juegoService.getJugador();

        while (juegoEnCurso && jugador.getVida() > 0) {
            Nivel nivelActual = juegoService.getNivelActual();

            if (nivelActual == null) {
                System.out.println("\n¡Has completado todos los niveles!");
                break;
            }

            System.out.printf("\n=== NIVEL: %s ===\n", nivelActual.getNombre());
            System.out.printf("Vida: %d | Puntos: %d\n", jugador.getVida(), jugador.getPuntuacion());

            Pregunta preguntaActual = juegoService.obtenerPreguntaActual();
            if (preguntaActual == null) {
                if (juegoService.avanzarAlSiguienteNivel()) {
                    System.out.println("\n¡Nivel completado! Pasando al siguiente...");
                    continue;
                } else {
                    System.out.println("\n¡Has completado el juego!");
                    break;
                }
            }

            mostrarPregunta(preguntaActual);
            manejarRespuesta(preguntaActual);
        }

        if (jugador.getVida() <= 0) {
            System.out.println("\n¡Has perdido! Te quedaste sin vida.");
        }

        mostrarResumenFinal();
    }

    private void mostrarPregunta(Pregunta pregunta) {
        System.out.println("\n" + pregunta.getTexto());
        String[] opciones = pregunta.getOpciones();

        for (int i = 0; i < opciones.length; i++) {
            if (!opciones[i].startsWith("[X] ")) {
                System.out.printf("%d. %s%n", i + 1, opciones[i]);
            }
        }
    }

    private void manejarRespuesta(Pregunta pregunta) {
        Jugador jugador = juegoService.getJugador();
        boolean respuestaCorrecta = false;
        boolean usarHabilidad = true;

        while (!respuestaCorrecta) {
            if (usarHabilidad && tieneHabilidadesDisponibles(jugador)) {
                System.out.print("\n¿Quieres usar una habilidad? (s/n): ");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("s")) {
                    usarHabilidad(jugador, pregunta);
                }
                usarHabilidad = false;
            }

            System.out.print("\nIngresa el número de tu respuesta: ");
            long tiempoInicio = System.currentTimeMillis();
            int respuesta = obtenerRespuestaValida(pregunta.getOpciones().length);
            long tiempoFin = System.currentTimeMillis();

            int segundos = (int)((tiempoFin - tiempoInicio) / 1000);
            int danio = segundos * juegoService.getNivelActual().getDanioPorSegundo();
            jugador.setVida(jugador.getVida() - danio);

            System.out.printf("\nTardaste %d segundos. Pierdes %d de vida.%n", segundos, danio);
            System.out.printf("Vida actual: %d%n", jugador.getVida());

            if (pregunta.esCorrecta(respuesta)) {
                System.out.println("\n¡Respuesta correcta!");
                int puntosGanados = juegoService.getNivelActual().getPuntosBase() / segundos;
                jugador.sumarPuntos(puntosGanados);
                System.out.printf("Ganaste %d puntos! Total: %d%n", puntosGanados, jugador.getPuntuacion());
                respuestaCorrecta = true;
                juegoService.removerPreguntaActual();
            } else {
                System.out.println("\nRespuesta incorrecta. Pierdes 10 de vida adicional.");
                jugador.setVida(jugador.getVida() - 10);
                System.out.printf("Vida actual: %d%n", jugador.getVida());

                if (jugador.getVida() <= 0) {
                    break;
                }
            }
        }
    }

    private boolean tieneHabilidadesDisponibles(Jugador jugador) {
        return jugador.getHabilidades().stream().anyMatch(Habilidad::puedeUsar);
    }

    private void usarHabilidad(Jugador jugador, Pregunta pregunta) {
        jugador.mostrarHabilidadesDisponibles();
        System.out.print("Elige una habilidad (número): ");
        int opcion = obtenerRespuestaValida(jugador.getHabilidades().size()) - 1;

        Habilidad habilidad = jugador.getHabilidades().get(opcion);
        if (habilidad.puedeUsar()) {
            habilidad.usar(jugador, pregunta);
        } else {
            System.out.println("Esta habilidad ya ha sido usada.");
        }
    }

    private int obtenerRespuestaValida(int maxOpciones) {
        while (true) {
            try {
                int respuesta = scanner.nextInt();
                scanner.nextLine();

                if (respuesta >= 1 && respuesta <= maxOpciones) {
                    return respuesta;
                } else {
                    System.out.printf("Por favor ingresa un número entre 1 y %d: ", maxOpciones);
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Entrada inválida. Ingresa un número: ");
            }
        }
    }

    private void mostrarResumenFinal() {
        Jugador jugador = juegoService.getJugador();
        System.out.println("\n=== RESUMEN FINAL ===");
        System.out.printf("Jugador: %s%n", jugador.getNombre());
        System.out.printf("Puntuación final: %d%n", jugador.getPuntuacion());
        System.out.printf("Niveles completados: %d/%d%n",
                juegoService.getNivelIndex(),
                juegoService.getTotalNiveles());
    }
}