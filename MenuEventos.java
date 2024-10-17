import java.io.*;
import java.util.Scanner;

public class MenuEventos {
    private static final String NOME_ARQUIVO_EVENTOS = "eventos.txt";
    private static Local[] locais = new Local[5];
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
            System.out.println("[7] Mostrar analises");
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
                case 7:
                    mostrarAnalises(scanner);
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
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String nomeEvento = dados[0];
                int participantesInscritos = Integer.parseInt(dados[1]);
                int capacidadeEvento = Integer.parseInt(dados[2]);
                System.out.println(nomeEvento + " (" + participantesInscritos + "/" + capacidadeEvento + ")");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos");
        }
    }

    private static void mostrarEstatisticasEventoEspecifico(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equalsIgnoreCase(nomeEvento)) {
                    String nomeLocal = locais[Integer.parseInt(dados[3])].getNome();
                    int participantesInscritos = Integer.parseInt(dados[1]);
                    int capacidadeEvento = Integer.parseInt(dados[2]);
                    System.out.println("Local: " + nomeLocal);
                    System.out.println("Participantes inscritos: " + participantesInscritos + "/" + capacidadeEvento);
                    System.out.println("Participantes:");
                    for (int i = 4; i < dados.length; i++) {
                        System.out.println("- " + dados[i]);
                    }
                    return;
                }
            }
            System.out.println("Evento não encontrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
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
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO_EVENTOS, true))) {
            bw.write(nomeEvento + ",0," + capacidadeEvento + "," + indiceLocal);
            bw.newLine();
            System.out.println("Evento criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao gravar o evento no arquivo.");
        }
    }

    private static void excluirEvento(Scanner scanner) {
        System.out.println("Digite o nome do evento a ser excluído:");
        String nomeEvento = scanner.nextLine();

        File arquivoOriginal = new File(NOME_ARQUIVO_EVENTOS);
        File arquivoTemp = new File("eventos_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal)); BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTemp))) {
            String linha;
            boolean eventoExcluido = false;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (!dados[0].equalsIgnoreCase(nomeEvento)) {
                    bw.write(linha);
                    bw.newLine();
                } else {
                    locais[Integer.parseInt(dados[3])].setDisponivel(true);
                    eventoExcluido = true;
                }
            }

            if (eventoExcluido) {
                arquivoOriginal.delete();
                arquivoTemp.renameTo(arquivoOriginal);
                System.out.println("Evento excluído com sucesso.");
            } else {
                arquivoTemp.delete();
                System.out.println("Evento não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao excluir o evento.");
        }
    }

    private static void cadastrarParticipante(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        System.out.println("Digite o nome do participante:");
        String nomeParticipante = scanner.nextLine();

        File arquivoOriginal = new File(NOME_ARQUIVO_EVENTOS);
        File arquivoTemp = new File("eventos_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTemp))) {
            String linha;
            boolean participanteAdicionado = false;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equalsIgnoreCase(nomeEvento)) {
                    int participantesInscritos = Integer.parseInt(dados[1]);
                    int capacidadeEvento = Integer.parseInt(dados[2]);

                    if (participantesInscritos < capacidadeEvento) {
                        bw.write(dados[0] + "," + (participantesInscritos + 1) + "," + capacidadeEvento + "," + dados[3]);
                        for (int i = 4; i < dados.length; i++) {
                            bw.write("," + dados[i]);
                        }
                        bw.write("," + nomeParticipante);
                        bw.newLine();
                        participanteAdicionado = true;
                    } else {
                        System.out.println("Evento está com a capacidade máxima.");
                        bw.write(linha);
                        bw.newLine();
                    }
                } else {
                    bw.write(linha);
                    bw.newLine();
                }
            }

            if (participanteAdicionado) {
                arquivoOriginal.delete();
                arquivoTemp.renameTo(arquivoOriginal);
                System.out.println("Participante adicionado com sucesso.");
            } else {
                arquivoTemp.delete();
                System.out.println("Evento não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao adicionar participante.");
        }
    }

    private static void removerParticipante(Scanner scanner) {
        System.out.println("Digite o nome do evento:");
        String nomeEvento = scanner.nextLine();

        System.out.println("Digite o nome do participante a ser removido:");
        String nomeParticipante = scanner.nextLine();

        File arquivoOriginal = new File(NOME_ARQUIVO_EVENTOS);
        File arquivoTemp = new File("eventos_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTemp))) {
            String linha;
            boolean participanteRemovido = false;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equalsIgnoreCase(nomeEvento)) {
                    bw.write(dados[0] + "," + (Integer.parseInt(dados[1]) - 1) + "," + dados[2] + "," + dados[3]);
                    for (int i = 4; i < dados.length; i++) {
                        if (!dados[i].equalsIgnoreCase(nomeParticipante)) {
                            bw.write("," + dados[i]);
                        } else {
                            participanteRemovido = true;
                        }
                    }
                    bw.newLine();
                } else {
                    bw.write(linha);
                    bw.newLine();
                }
            }

            if (participanteRemovido) {
                arquivoOriginal.delete();
                arquivoTemp.renameTo(arquivoOriginal);
                System.out.println("Participante removido com sucesso.");
            } else {
                arquivoTemp.delete();
                System.out.println("Evento ou participante não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover participante.");
        }
    }


    private static void mostrarAnalises(Scanner scanner) {
        System.out.println("Análises dos Eventos:");
        System.out.println("[1] Evento com a maior taxa de ocupação");
        System.out.println("[2] Evento com a menor taxa de ocupação");
        System.out.println("[3] Eventos com a maior capacidade disponível");
        System.out.println("[4] Total de participantes cadastrados em todos os eventos");
        System.out.println("[5] Quantidade de eventos realizados em cada local");
        System.out.println("[0] Voltar ao menu principal");
    
        int opcao = scanner.nextInt();
        scanner.nextLine();
    
        switch (opcao) {
            case 1:
                eventoMaiorTaxaOcupacao();
                break;
            case 2:
                eventoMenorTaxaOcupacao();
                break;
            case 3:
                eventosMaiorCapacidadeDisponivel();
                break;
            case 4:
                totalParticipantesCadastrados();
                break;
            case 5:
                quantidadeEventosPorLocal();
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    
    private static void eventoMaiorTaxaOcupacao() {
        String maiorEvento = null;
        double maiorTaxa = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nomeEvento = partes[0].trim();
                int participantesInscritos = Integer.parseInt(partes[1].trim());
                int capacidade = Integer.parseInt(partes[2].trim());
    
                double taxaOcupacao = (double) participantesInscritos / capacidade;
                if (taxaOcupacao > maiorTaxa) {
                    maiorTaxa = taxaOcupacao;
                    maiorEvento = nomeEvento;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
    
        if (maiorEvento != null) {
            System.out.println("Evento com a maior taxa de ocupação: " + maiorEvento + " com taxa de " + maiorTaxa);
        } else {
            System.out.println("Nenhum evento encontrado.");
        }
    }  

    private static void eventoMenorTaxaOcupacao() {
        String menorEvento = null;
        double menorTaxa = Double.MAX_VALUE;
    
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nomeEvento = partes[0].trim();
                int participantesInscritos = Integer.parseInt(partes[1].trim());
                int capacidade = Integer.parseInt(partes[2].trim());
    
                if (capacidade > 0) {
                    double taxaOcupacao = (double) participantesInscritos / capacidade;
                    if (taxaOcupacao < menorTaxa) {
                        menorTaxa = taxaOcupacao;
                        menorEvento = nomeEvento;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
    
        if (menorEvento != null) {
            System.out.println("Evento com a menor taxa de ocupação: " + menorEvento + " com taxa de " + menorTaxa);
        } else {
            System.out.println("Nenhum evento encontrado.");
        }
    }

    private static void eventosMaiorCapacidadeDisponivel() {
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nomeEvento = partes[0].trim();
                int capacidade = Integer.parseInt(partes[2].trim());
                int participantesInscritos = Integer.parseInt(partes[1].trim());
    
                int capacidadeDisponivel = capacidade - participantesInscritos;
                if (capacidadeDisponivel > 0) {
                    System.out.println("Evento: " + nomeEvento + " - Capacidade disponível: " + capacidadeDisponivel);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
    }

    private static void totalParticipantesCadastrados() {
        int totalParticipantes = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                int participantesInscritos = Integer.parseInt(partes[1].trim());
                totalParticipantes += participantesInscritos;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
        System.out.println("Total de participantes cadastrados em todos os eventos: " + totalParticipantes);
    }

    private static void quantidadeEventosPorLocal() {
        int[] contadorEventos = new int[locais.length];
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO_EVENTOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                int indiceLocal = Integer.parseInt(partes[3].trim());
    
                if (indiceLocal >= 0 && indiceLocal < locais.length) {
                    contadorEventos[indiceLocal]++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de eventos.");
        }
        for (int i = 0; i < locais.length; i++) {
            System.out.println("Local: " + locais[i].getNome() + " - Eventos realizados: " + contadorEventos[i]);
        }
    }

}