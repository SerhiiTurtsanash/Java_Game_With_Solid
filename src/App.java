import domain.repository.PreguntaRepository;
import domain.repository.PreguntaRepositoryImpl;
import domain.service.JuegoService;
import infrastructure.console.ConsoleUI;

public class App {
    public static void main(String[] args) {
        PreguntaRepository preguntaRepository = new PreguntaRepositoryImpl();

        System.out.println("Total preguntas cargadas: " + preguntaRepository.obtenerTodas().size());
        System.out.println("Preguntas de Programación: " + preguntaRepository.obtenerPorCategoria("Programación").size());
        System.out.println("Preguntas de POO: " + preguntaRepository.obtenerPorCategoria("POO").size());
        System.out.println("Preguntas difíciles: " + preguntaRepository.obtenerPorDificultad(3).size());

        JuegoService juegoService = new JuegoService(preguntaRepository);
        ConsoleUI consoleUI = new ConsoleUI(juegoService);
        consoleUI.iniciar();
    }
}