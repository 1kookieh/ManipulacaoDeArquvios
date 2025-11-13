package atividade;

public class Contato {
    private final String nome;
    private final String telefone;

    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }

    @Override
    public String toString() {
        return String.format("Nome: %-30s Fone: %s", nome, telefone);
    }
}
