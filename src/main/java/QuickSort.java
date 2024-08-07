import java.util.ArrayList;
import java.util.List;

public class QuickSort {

    private static List<String> passos = new ArrayList<>(); // Para armazenar os passos de ordenação
    private static int contadorPassos = 0; // Para contar o número de passos

    public static void quickSort(Participante[] vetor, int inicio, int fim) {

        if (inicio < fim) {
            registrarPasso(vetor);
            int posPivo = particionar(vetor, inicio, fim);
            quickSort(vetor, inicio, posPivo - 1);
            quickSort(vetor, posPivo + 1, fim);
        }

    }

    public static int particionar(Participante[] vetor, int inicio, int fim) {
        double pivo = vetor[fim].getMedia();
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (vetor[j].getMedia() >= pivo) {
                i++;
                trocar(vetor, i, j);
                registrarPasso(vetor); // Registrar após a troca
            }
        }
        trocar(vetor, i + 1, fim);
        registrarPasso(vetor); // Registrar após a troca com o pivo
        return i + 1;
    }

    public static void trocar(Participante[] vetor, int indiceA, int indiceB) {
        Participante aux = vetor[indiceA];
        vetor[indiceA] = vetor[indiceB];
        vetor[indiceB] = aux;
    }

    public static void registrarPasso(Participante[] vetor) {
        StringBuilder sb = new StringBuilder();
        for (Participante p : vetor) {
            sb.append(p).append(" ");
        }
        passos.add("Passo " + (++contadorPassos) + ": " + sb.toString().trim());
    }

    public static List<String> getPassos() {
        return new ArrayList<>(passos); // Retorna uma cópia dos passos
    }

    public static void limparPassos() {
        passos.clear(); // Limpa os passos após a execução
        contadorPassos = 0; // Resetar o contador de passos
    }
}