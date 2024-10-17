import java.io.*;
import java.util.*;

public class Event {
    private String nomeEvento;
    private int capacidade;
    private int participantesInscritos;
    private Participante[] participantes;
    private int local;

    public Event(String nomeEvento, int capacidade, int local) {
        this.nomeEvento = nomeEvento;
        this.capacidade = capacidade;
        this.participantesInscritos = 0;
        this.participantes = new Participante[capacidade];
        this.local = local;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public int getParticipantesInscritos() {
        return participantesInscritos;
    }
    public int getLocal() {
        return local;
    }

    public boolean adicionarParticipante(Participante p) {
        if (participantesInscritos < capacidade) {
            participantes[participantesInscritos] = p;
            participantesInscritos++;
            return true;
        }
        return false;
    }

    public boolean removerParticipante(String nome) {
        for (int i = 0; i < participantesInscritos; i++) {
            if (participantes[i].getNomeParticipante().equals(nome)) {
                for (int j = i; j < participantesInscritos - 1; j++) {
                    participantes[j] = participantes[j + 1];
                }
                participantes[participantesInscritos - 1] = null;
                participantesInscritos--;
                return true;
            }
        }
        return false;
    }

    public String salvarEvento() {
        return nomeEvento + "," + capacidade + "," + participantesInscritos + "," + local;
    }

    public String mostrarEstatisticas() {
        StringBuilder estatisticas = new StringBuilder();
        estatisticas.append(nomeEvento)
                    .append(" (")
                    .append(participantesInscritos)
                    .append(" / ")
                    .append(capacidade)
                    .append(")\n");
        estatisticas.append("Participantes:\n");
        for (int i = 0; i < participantesInscritos; i++) {
            estatisticas.append(participantes[i].getNomeParticipante()).append("\n");
        }
        return estatisticas.toString();
    }

    public static void atualizarEvento(String nomeArquivo, String nomeEvento, String nomeParticipante, boolean adicionar) {
        String[] linhas = new String[100];
        int contadorLinhas = 0;
        boolean participanteEncontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) { //leitura
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas[contadorLinhas++] = linha;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < contadorLinhas; i++) {
            String[] partes = linhas[i].split(",");
            if (partes[0].trim().equals(nomeEvento)) {
                if (adicionar) {
                    boolean existeParticipante = false;
                    for (int j = 1; j < partes.length; j++) {
                        if (partes[j].trim().equals(nomeParticipante)) {
                            existeParticipante = true;
                            break;
                        }
                    }
                    if (!existeParticipante) {
                        linhas[i] += "," + nomeParticipante;
                    }
                } 
                else {
                    StringBuilder novaLinha = new StringBuilder(partes[0]);
                    for (int j = 1; j < partes.length; j++) {
                        if (!partes[j].trim().equals(nomeParticipante)) {
                            novaLinha.append(",").append(partes[j].trim());
                        } else {
                            participanteEncontrado = true;
                        }
                    }
                    linhas[i] = novaLinha.toString();
                }
            }
        }

        if (!participanteEncontrado && !adicionar) {
            System.out.println("Participante não encontrado no evento: " + nomeEvento);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < contadorLinhas; i++) {
                if (linhas[i] != null) {
                    bw.write(linhas[i]);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}