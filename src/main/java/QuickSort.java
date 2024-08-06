public class QuickSort {

    public static void quickSort(Participante[] vetor, int inicio, int fim) {
        
        /*System.out.println();
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i]+", ");
        }*/

        if (inicio < fim) {
            int posPivo = particionar(vetor, inicio, fim);
            quickSort(vetor, inicio, posPivo - 1);
            quickSort(vetor, posPivo + 1, fim);
        }

    }

    public static int particionar(Participante[] vetor, int inicio, int fim){

        double pivo = vetor[fim].getMedia();
        int i = inicio-1;

        for (int j=inicio;j < fim;j++) {
            if (vetor[j].getMedia() >= pivo) {
                i++;
                trocar(vetor, i, j);
            }
        }
        trocar(vetor, i+1, fim);

        return i+1;
    }

    public static void trocar(Participante[] vetor, int indiceA, int indiceB) {
        Participante aux = vetor[indiceA];
        vetor[indiceA] = vetor[indiceB];
        vetor[indiceB] = aux;
    }
}
