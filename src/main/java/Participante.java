public class Participante {
    private String nome;
    private int idade;
    private int acertosGerais;
    private int acertosEspecificos;

    public Participante(String nome, int idade, int acertosGerais, int acertosEspecificos) {
        this.nome = nome;
        this.idade = idade;
        this.acertosGerais = acertosGerais;
        this.acertosEspecificos = acertosEspecificos;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", acertosGerais=" + acertosGerais +
                ", acertosEspecificos=" + acertosEspecificos +
                '}';
    }
}
