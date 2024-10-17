import java.io.*;

public class Participante {
    private String nomeParticipante;

    private Participante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }
    public static Participante criarParticipante(String nomeParticipante) {
        return new Participante(nomeParticipante);
    }
    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void salvarParticipante(String nomeArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            bw.write(nomeParticipante);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar o participante: " + e.getMessage());
        }
    }

    public static void cadastrarParticipante(String nomeArquivoEvento, String nomeEvento, String nomeParticipante) {
        try {
            Event.atualizarEvento(nomeArquivoEvento, nomeEvento, nomeParticipante, true);
            System.out.println("Participante " + nomeParticipante + " cadastrado no evento " + nomeEvento);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar participante: " + e.getMessage());
        }
    }

    public static void removerParticipante(String nomeArquivoEvento, String nomeEvento, String nomeParticipante) {
        try {
            Event.atualizarEvento(nomeArquivoEvento, nomeEvento, nomeParticipante, false);
            System.out.println("Participante " + nomeParticipante + " removido do evento " + nomeEvento);
        } catch (Exception e) {
            System.out.println("Erro ao remover participante: " + e.getMessage());
        }
    }
}
