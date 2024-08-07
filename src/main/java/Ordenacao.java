public class Ordenacao {

    //Método que resolve empates entre participantes.
    public static void desempatar(Participante[] participantes) {
        int inicio_emp = -1, fim_emp = -1; // -1 indica que ainda não encontramos um empate

        // Percorre o array de participantes para identificar e tratar empates
        for (int i = 0; i < participantes.length - 1; i++) {
            // Verifica se o participante atual tem a mesma média que o próximo
            if (Double.compare(participantes[i].getMedia(), participantes[i + 1].getMedia()) == 0) {
                // Marca o início do empate, se ainda não estiver marcado
                if (inicio_emp == -1) {
                    inicio_emp = i;
                }
                // Atualiza o fim do intervalo de empate
                fim_emp = i + 1;
            } else {
                // Se o empate foi identificado e o intervalo está definido
                if (inicio_emp != -1 && fim_emp != -1) {
                    // Aplica as regras de desempate
                    aplicarRegras(participantes, inicio_emp, fim_emp);
                    // Reseta os índices de início e fim do intervalo de empate
                    inicio_emp = -1;
                    fim_emp = -1;
                }
            }
        }

        // Caso haja um empate que vai até o final do array
        if (inicio_emp != -1 && fim_emp != -1) {
            aplicarRegras(participantes, inicio_emp, fim_emp);
        }
    }

    /**
     * Aplica as regras de desempate nos participantes que estão em empate.
     * inicio_emp Índice de início do intervalo de empate.
     * fim_emp Índice de fim do intervalo de empate.
     */
    public static void aplicarRegras(Participante[] participantes, int inicio_emp, int fim_emp) {
        // Cria um subarray contendo apenas os participantes empatados
        Participante[] empatados = new Participante[fim_emp - inicio_emp + 1];
        System.arraycopy(participantes, inicio_emp, empatados, 0, fim_emp - inicio_emp + 1);

        // Ordena o subarray de participantes empatados com base nas regras definidas
        java.util.Arrays.sort(empatados, (p1, p2) -> {
            // Regra 1: Comparar por Nota em Conhecimentos Específicos
            if (p1.getAcertosEspecificos() != p2.getAcertosEspecificos()) {
                return Integer.compare(p2.getAcertosEspecificos(), p1.getAcertosEspecificos());
            }
            // Regra 2: Comparar por Nota em Conhecimentos Gerais
            else if (p1.getAcertosGerais() != p2.getAcertosGerais()) {
                return Integer.compare(p2.getAcertosGerais(), p1.getAcertosGerais());
            }
            // Regra 3: Comparar por Idade (menor idade é melhor)
            else if (p1.getIdade() != p2.getIdade()) {
                return Integer.compare(p1.getIdade(), p2.getIdade());
            }
            // Regra 4: Manter a ordem original do arquivo (não faz nada, pois o sort é estável)
            else {
                return 0;
            }
        });

        // Substitui o intervalo original de participantes com o subarray ordenado
        System.arraycopy(empatados, 0, participantes, inicio_emp, fim_emp - inicio_emp + 1);
    }

    /**
     * Verifica se há empate entre os participantes.
     * true se houver empate, false caso contrário.
     */
    public static boolean verificaEmpate(Participante[] participantes) {
        // Percorre o array de participantes para identificar empates
        for (int i = 0; i < participantes.length - 1; i++) {
            // Verifica se o participante atual tem a mesma média que o próximo
            if (Double.compare(participantes[i].getMedia(), participantes[i + 1].getMedia()) == 0) {
                return true; // Empate encontrado
            }
        }
        return false; // Nenhum empate encontrado
    }
}
