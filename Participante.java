import java.util.ArrayList;
import java.util.List;

public class Participante extends Usuario implements Buscavel<Participante> {
    private static List<Participante> participantes = new ArrayList<>();

    public Participante(String nome) {
        super(nome);
    }

    // Método para cadastrar um participante
    public static Participante cadastrarParticipante(String nome) {
        Participante participante = new Participante(nome);
        participantes.add(participante);
        return participante;
    }

    // Método para remover um participante
    public static boolean removerParticipante(String nome) {
        return participantes.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    // Implementação da interface Buscavel
    @Override
    public Participante buscar(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    // Sobrecarga para busca por critério
    public static List<Participante> buscarPorParteDoNome(String parteNome) {
        List<Participante> resultados = new ArrayList<>();
        for (Participante p : participantes) {
            if (p.getNome().toLowerCase().contains(parteNome.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    // Método para listar todos os participantes
    public static List<Participante> listarParticipantes() {
        return new ArrayList<>(participantes);
    }
}
