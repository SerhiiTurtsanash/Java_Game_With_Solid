package domain.model;

import domain.model.habilidades.Habilidad;
import java.util.List;

public class Jugador {
    private final String nombre;
    private int vida;
    private final List<Habilidad> habilidades;
    private int puntuacion;

    public Jugador(String nombre, int vida, List<Habilidad> habilidades) {
        this.nombre = nombre;
        this.vida = Math.max(vida, 0);
        this.habilidades = habilidades;
        this.puntuacion = 0;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getPuntuacion() { return puntuacion; }
    public void setVida(int vida) {
        this.vida = Math.max(vida, 0);
    }
    public void sumarPuntos(int puntos) {
        this.puntuacion += puntos;
    }
    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
    public void resetearHabilidades() {
        habilidades.forEach(Habilidad::resetear);
    }

    public void mostrarHabilidadesDisponibles() {
        System.out.println("\nHabilidades disponibles:");
        int index = 1;
        for (Habilidad h : habilidades) {
            if (h.puedeUsar()) {
                System.out.printf("%d. %s - %s%n", index++, h.getNombre(), h.getDescripcion());
            }
        }
    }
}