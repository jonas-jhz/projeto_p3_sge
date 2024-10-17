public class Local {
    private String nome;
    private int capacidade;
    private boolean disponivel;
    private Event[] eventos; 

    public Local(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = true; 
        this.eventos = new Event[10]; 
    }

    public String getNome() {
        return nome;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String salvarLocal() {
        return nome + "," + capacidade + "," + (disponivel ? "disponível" : "indisponível");
    }

    public String mostrarLocal() {
        return nome + " (Capacidade: " + capacidade + ", Disponível: " + (disponivel ? "Sim" : "Não") + ")";
    }

    public boolean adicionarEvento(Event evento) {
        for (int i = 0; i < eventos.length; i++) {
            if (eventos[i] == null) {
                eventos[i] = evento; 
                return true;
            }
        }
        return false; 
    }

    public boolean verificarOcupacao() {
        for (int i = 0; i < eventos.length; i++) {
            if (eventos[i] != null) {
                return true;
            }
        }
        return false;
    }
}
