public class Ordenacao {

    public static void desempatar(Participante[] participantes) {
        int inicio_emp = -1, fim_emp = -1; // -1 é para dizer que ainda não encontrou empate

        for (int i = 0; i < participantes.length - 1; i++) {
            if (Double.compare(participantes[i].getMedia(), participantes[i + 1].getMedia()) == 0) {
                if (inicio_emp == -1) {
                    inicio_emp = i;
                }
                fim_emp = i + 1;
            } else {
                if (inicio_emp != -1 && fim_emp != -1) {
                    aplicarRegras(participantes, inicio_emp, fim_emp);
                    inicio_emp = -1;
                    fim_emp = -1;
                }
            }
        }

        // Caso haja um empate na última posição
        if (inicio_emp != -1 && fim_emp != -1) {
            aplicarRegras(participantes, inicio_emp, fim_emp);
        }
    }

    public static void aplicarRegras(Participante[] participantes, int inicio_emp, int fim_emp) {
        Participante[] empatados = new Participante[fim_emp - inicio_emp + 1];
        System.arraycopy(participantes, inicio_emp, empatados, 0, fim_emp - inicio_emp + 1);

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

        System.arraycopy(empatados, 0, participantes, inicio_emp, fim_emp - inicio_emp + 1);
    }

    // Método para verificar se houve empate
    public static boolean verificaEmpate(Participante[] participantes) {
        for (int i = 0; i < participantes.length - 1; i++) {
            if (Double.compare(participantes[i].getMedia(), participantes[i + 1].getMedia()) == 0) {
                return true; // Houve empate
            }
        }
        return false; // Não houve empate
    }
}
