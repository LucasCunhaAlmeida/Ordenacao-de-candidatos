public class Participante {
    private String nome;
    private int idade;
    private int acertosGerais;
    private int acertosEspecificos;

    private double media;

    public Participante(String nome, int idade, int acertosGerais, int acertosEspecificos,double media) {
        this.nome = nome;
        this.idade = idade;
        this.acertosGerais = acertosGerais;
        this.acertosEspecificos = acertosEspecificos;
        this.media = media;
    }

    @Override
    protected Participante clone() {
        return new Participante(this.nome, this.idade, this.acertosGerais, this.acertosEspecificos, this.media);
    }
    @Override
    public String   toString() {
        return "" + nome + '-' + media;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAcertosGerais() {
        return acertosGerais;
    }

    public void setAcertosGerais(int acertosGerais) {
        this.acertosGerais = acertosGerais;
    }

    public int getAcertosEspecificos() {
        return acertosEspecificos;
    }

    public void setAcertosEspecificos(int acertosEspecificos) {
        this.acertosEspecificos = acertosEspecificos;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }
}
