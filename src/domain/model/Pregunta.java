package domain.model;

public class Pregunta {
    private final String id;
    private final String texto;
    private final String[] opciones;
    private final int respuestaCorrecta;
    private final String pista;
    private final int dificultad;
    private final String categoria;

    public Pregunta(String id, String texto, String[] opciones,
                    int respuestaCorrecta, String pista,
                    int dificultad, String categoria) {
        this.id = id;
        this.texto = texto;
        this.opciones = opciones.clone();
        this.respuestaCorrecta = respuestaCorrecta;
        this.pista = pista;
        this.dificultad = dificultad;
        this.categoria = categoria;
    }
    public String getId() { return id; }
    public String getTexto() { return texto; }
    public String[] getOpciones() { return opciones.clone(); }
    public int getRespuestaCorrecta() { return respuestaCorrecta; }
    public String getPista() { return pista; }
    public int getDificultad() { return dificultad; }
    public String getCategoria() { return categoria; }
    public boolean esCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }
}