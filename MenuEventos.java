import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEventos {
    private static final int NUMERO_LOCALS = 5;
    private static Local[] locais = new Local[NUMERO_LOCALS];
    private static List<Event> eventos = new ArrayList<>();

    public static void main(String[] args) {
        inicializarLocais();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("[1] Mostrar estatísticas gerais de todos os eventos");
            System.out.println("[2] Mostrar estatísticas de um evento específico");
            System.out.println("[3] Criar evento em um local");
            System.out.println("[4] Excluir um evento");
            System.out.println("[5] Cadastrar participante em um evento");
            System.out.println("[6] Remover participante de um evento");
            System.out.println("[0] Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    mostrarEstatisticasGerais();
                    break;
                case 2:
                    mostrarEstatisticasEventoEspecifico(scanner);
                    break;
                case 3:
                    criarEvento(scanner);
                    break;
                case 4:
                    excluirEvento(scanner);
                    break;
                case 5:
                    cadastrarParticipante(scanner);
                    break;
                case 6:
                    removerParticipante(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }
    


    private static void inicializarLocais() {
        locais[0] = new Local("Auditório Principal", 100);
        locais[1] = new Local("Sala de Conferências", 50);
        locais[2] = new Local("Sala de Reuniões", 20);
        locais[3] = new Local("Hall de Entrada", 30);
        locais[4] = new Local("Área Externa", 200);
    }

    private static void mostrarEstatisticasGerais() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Event evento : eventos) {
            System.out.println(evento.mostrarEstatisticas());
        }
    }

    private static void mostrarEstatisticasEventoEspecifico(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        Event evento = buscarEvento(nomeEvento);
        if (evento != null) {
            System.out.println(evento.mostrarEstatisticas());
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    private static Event buscarEvento(String nome) {
        for (Event evento : eventos) {
            if (evento.getNomeEvento().equalsIgnoreCase(nome)) {
                return evento;
            }
        }
        return null;
    }

    private static void criarEvento(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        System.out.println("Selecione o local para o evento:");
        for (int i = 0; i < locais.length; i++) {
            if (locais[i].isDisponivel()) {
                System.out.println("[" + i + "] " + locais[i].getNome() + " (Capacidade: " + locais[i].getCapacidade() + ")");
            }
        }

        int indiceLocal = scanner.nextInt();
        scanner.nextLine();

        if (indiceLocal < 0 || indiceLocal >= locais.length || !locais[indiceLocal].isDisponivel()) {
            System.out.println("Local inválido ou indisponível.");
            return;
        }

        System.out.println("Digite a capacidade do evento:");
        int capacidadeEvento = scanner.nextInt();
        scanner.nextLine();

        if (capacidadeEvento > locais[indiceLocal].getCapacidade()) {
            System.out.println("Capacidade do evento excede a capacidade do local.");
            return;
        }

        locais[indiceLocal].setDisponivel(false);
        Event novoEvento = new Event(nomeEvento, capacidadeEvento, indiceLocal);
        locais[indiceLocal].adicionarEvento(novoEvento);
        eventos.add(novoEvento);

        System.out.println("Evento criado com sucesso.");
    }

    private static void excluirEvento(Scanner scanner) {
        System.out.println("Digite o nome do evento a ser excluído:");
        String nomeEvento = scanner.nextLine();

        Event evento = buscarEvento(nomeEvento);
        if (evento != null) {
            locais[evento.getLocal()].setDisponivel(true);
            eventos.remove(evento);
            System.out.println("Evento excluído com sucesso.");
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    private static void cadastrarParticipante(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        Event evento = buscarEvento(nomeEvento);
        if (evento != null) {
            System.out.println("Digite o nome do participante:");
            String nomeParticipante = scanner.nextLine();

            Participante participante = Participante.cadastrarParticipante(nomeParticipante);
            if (evento.adicionarParticipante(participante)) {
                System.out.println("Participante cadastrado com sucesso.");
            } else {
                System.out.println("Evento está com a capacidade máxima.");
            }
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    private static void removerParticipante(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        Event evento = buscarEvento(nomeEvento);
        if (evento != null) {
            System.out.println("Digite o nome do participante a ser removido:");
            String nomeParticipante = scanner.nextLine();

            if (evento.removerParticipante(nomeParticipante)) {
                System.out.println("Participante removido com sucesso.");
            } else {
                System.out.println("Participante não encontrado no evento.");
            }
        } else {
            System.out.println("Evento não encontrado.");
        }
    }
}
