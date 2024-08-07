import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private static int passo;
    private static List<String> passos;

    public static List<String> getPassos() {
        return passos;
    }

    public static void merge_sort(Participante[] vetor, int inicio, int fim) {
        if (inicio == 0 && fim == vetor.length - 1) {
            passos = new ArrayList<>(); // Inicializa a lista de passos no início da ordenação
            passo = 0;
        }

        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            merge_sort(vetor, inicio, meio);
            merge_sort(vetor, meio + 1, fim);
            registrarPasso(vetor);
            merge(vetor, inicio, meio, fim);
            registrarPasso(vetor); // Registrar o estado do vetor após cada fusão
        }
    }

    public static void merge(Participante[] vetor, int inicio, int meio, int fim) {
        int i = 0, j = 0;
        int k = inicio;
        int tam_esq = meio - inicio + 1;
        int tam_dir = fim - meio;
        Participante[] vetor_esq = new Participante[tam_esq];
        Participante[] vetor_dir = new Participante[tam_dir];

        for (int l = 0; l < tam_esq; l++) {
            vetor_esq[l] = vetor[inicio + l];
        }

        for (int m = 0; m < tam_dir; m++) {
            vetor_dir[m] = vetor[meio + 1 + m];
        }

        while (i < tam_esq && j < tam_dir) {
            if (vetor_esq[i].getMedia() >= vetor_dir[j].getMedia()) {
                vetor[k++] = vetor_esq[i++];
            } else {
                vetor[k++] = vetor_dir[j++];
            }
        }

        while (i < tam_esq) {
            vetor[k++] = vetor_esq[i++];
        }

        while (j < tam_dir) {
            vetor[k++] = vetor_dir[j++];
        }

    }

    private static void registrarPasso(Participante[] vetor) {
        StringBuilder sb = new StringBuilder();
        sb.append("Passo ").append(++passo).append(": ");
        for (Participante p : vetor) {
            sb.append(p.toString()).append(" ");
        }
        passos.add(sb.toString().trim());
    }
}
