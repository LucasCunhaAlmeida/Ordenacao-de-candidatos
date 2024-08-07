import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private static int passo;
    private static List<String> passos;

    public static List<String> getPassos() {
        return passos;
    }

    public static void merge_sort(Participante[] vetor, int inicio, int fim) {
        // Inicializa a lista de passos no início da ordenação
        if (inicio == 0 && fim == vetor.length - 1) {
            passos = new ArrayList<>();
            passo = 0;
        }

        // Caso base do algoritmo: se o intervalo contém mais de um elemento
        if (inicio < fim) {
            // Calcula o ponto médio do intervalo
            int meio = (inicio + fim) / 2;
            // Ordena recursivamente a metade esquerda
            merge_sort(vetor, inicio, meio);
            // Ordena recursivamente a metade direita
            merge_sort(vetor, meio + 1, fim);
            // Registra o estado do vetor antes da fusão
            registrarPasso(vetor);
            // Mescla as duas metades ordenadas
            merge(vetor, inicio, meio, fim);
            // Registra o estado do vetor após a fusão
            registrarPasso(vetor);
        }
    }


    public static void merge(Participante[] vetor, int inicio, int meio, int fim) {
        int i = 0, j = 0;
        int k = inicio;
        int tam_esq = meio - inicio + 1; // Tamanho da metade esquerda
        int tam_dir = fim - meio; // Tamanho da metade direita
        Participante[] vetor_esq = new Participante[tam_esq]; // Array para a metade esquerda
        Participante[] vetor_dir = new Participante[tam_dir]; // Array para a metade direita

        // Copia os elementos da metade esquerda para o array vetor_esq
        for (int l = 0; l < tam_esq; l++) {
            vetor_esq[l] = vetor[inicio + l];
        }

        // Copia os elementos da metade direita para o array vetor_dir
        for (int m = 0; m < tam_dir; m++) {
            vetor_dir[m] = vetor[meio + 1 + m];
        }

        // Mescla os elementos dos dois arrays de volta no array original
        while (i < tam_esq && j < tam_dir) {
            // Compara os elementos dos arrays e coloca o maior no array original
            if (vetor_esq[i].getMedia() >= vetor_dir[j].getMedia()) {
                vetor[k++] = vetor_esq[i++];
            } else {
                vetor[k++] = vetor_dir[j++];
            }
        }

        // Copia os elementos restantes da metade esquerda, se houver
        while (i < tam_esq) {
            vetor[k++] = vetor_esq[i++];
        }

        // Copia os elementos restantes da metade direita, se houver
        while (j < tam_dir) {
            vetor[k++] = vetor_dir[j++];
        }

    }

    // Para registrar os passos da ordenação.
    private static void registrarPasso(Participante[] vetor) {
        StringBuilder sb = new StringBuilder();
        sb.append("Passo ").append(++passo).append(": ");
        for (Participante p : vetor) {
            sb.append(p.toString()).append(" ");
        }
        passos.add(sb.toString().trim());
    }
}
