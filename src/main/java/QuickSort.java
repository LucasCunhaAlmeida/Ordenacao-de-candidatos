import java.util.ArrayList;
import java.util.List;

public class QuickSort {

    private static List<String> passos = new ArrayList<>(); // Para armazenar os passos de ordenação
    private static int contadorPassos = 0; // Para contar o número de passos

    // Método principal do algoritmo QuickSort.

    public static void quickSort(Participante[] vetor, int inicio, int fim) {

        if (inicio < fim) {
            registrarPasso(vetor); // Registra o estado atual do array antes da partição
            int posPivo = particionar(vetor, inicio, fim); // Particiona o array e obtém a posição do pivo
            quickSort(vetor, inicio, posPivo - 1); // Ordena a parte esquerda do pivo
            quickSort(vetor, posPivo + 1, fim); // Ordena a parte direita do pivo
        }

    }

    // Método que particiona o array ao redor do pivo.
    public static int particionar(Participante[] vetor, int inicio, int fim) {
        double pivo = vetor[fim].getMedia(); // Seleciona o pivo (último elemento do intervalo)
        int i = inicio - 1; // Índice do menor elemento

        for (int j = inicio; j < fim; j++) {
            // Compara o elemento atual com o pivo
            if (vetor[j].getMedia() >= pivo) {
                i++; // Incrementa o índice do menor elemento
                trocar(vetor, i, j); // Troca os elementos
                registrarPasso(vetor); // Registrar após a troca
            }
        }
        trocar(vetor, i + 1, fim); // Move o pivo para a posição correta
        registrarPasso(vetor); // Registrar após a troca com o pivo
        return i + 1; // Retorna a nova posição do pivo
    }

    // Troca dois elementos no array.

    public static void trocar(Participante[] vetor, int indiceA, int indiceB) {
        Participante aux = vetor[indiceA];
        vetor[indiceA] = vetor[indiceB];
        vetor[indiceB] = aux;
    }

    // Registra o estado atual do array em um passo de ordenação.
    public static void registrarPasso(Participante[] vetor) {
        StringBuilder sb = new StringBuilder();
        for (Participante p : vetor) {
            sb.append(p).append(" "); // Concatena a representação de cada participante
        }
        passos.add("Passo " + (++contadorPassos) + ": " + sb.toString().trim()); // Adiciona o passo à lista
    }

    public static List<String> getPassos() {
        return new ArrayList<>(passos); // Retorna uma cópia dos passos
    }
}