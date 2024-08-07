import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Principal {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Programa de verificação de concurso. Por favor passe o <caminho_do_arquivo de texto>");
            return;
        }

        extrairDadosArquivo(args[0]); // Caminho do arquivo passado como argumento
    }

    public static void extrairDadosArquivo(String caminho) {
        Participante[] participantes; // Vetor de Participantes (ainda não informado o tamanho)
        int quant_participantes; // Ao ler o arquivo vamos saber a quantidade de participantes
        double peso_geral; // Peso da nota geral
        double peso_especifico; // Peso da nota específica

        // Abrindo o arquivo para a leitura a partir do caminho passado
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            // Lê a primeira linha com N participantes, Peso Gerais e Peso Específicos
            String linha = br.readLine();
            if (linha != null) { // Se a linha tiver alguma coisa
                String[] primeiraLinha = linha.split(" "); // Particiona a linha em partes a partir do espaço " "
                quant_participantes = Integer.parseInt(primeiraLinha[0]); // Primeira parte pega o número de participantes
                peso_geral = Double.parseDouble(primeiraLinha[1]); // Segunda parte pega o peso geral
                peso_especifico = Double.parseDouble(primeiraLinha[2]); // Terceira parte pega o peso específico

                // Verificando se os pesos são validos
                if (peso_especifico < 1.0 || peso_especifico > 10.0) {
                    System.out.printf("O peso específico é %.0f e não está entre 1 e 10 <incorreto>\n", peso_especifico);
                    System.out.println("Corrija seu arquivo de texto e tente novamente!");
                    return;
                } else {
                    if (peso_geral < 1.0 || peso_geral > 10.0) {
                        System.out.printf("O peso geral é %.0f e não está entre 1 e 10 <incorreto>\n", peso_geral);
                        System.out.println("Corrija seu arquivo de texto e tente novamente!");
                        return;
                    }
                }

                // Inicializa o vetor de participantes com o tamanho que foi lido
                participantes = new Participante[quant_participantes];

                // Lê as linhas subsequentes com os dados dos participantes
                int indice = 0;
                while ((linha = br.readLine()) != null && indice < quant_participantes) {
                    // Ler linhas do arquivo enquanto tiver linhas e o número de participantes não tiver sido atingido.
                    String[] dados = linha.split(" "); // Particiona novamente a linha a partir de espaços " "
                    if (dados.length < 4) { // Se não tiver pelo menos 4 espaços então não está no formato (nome, idade, acertos G, acertos E)
                        System.err.println("Formato de linha incorreto: " + linha);
                        System.out.println("Corrija seu arquivo de texto e tente novamente!");
                        return;
                    }

                    try {
                        // idade, acertos_gerais, acertos_especificos: vai extrair e converter os três últimos valores da linha em inteiros.
                        int idade = Integer.parseInt(dados[dados.length - 3]); // Pegando a idade
                        int acertos_gerais = Integer.parseInt(dados[dados.length - 2]); // Pegando os acertos gerais
                        int acertos_especificos = Integer.parseInt(dados[dados.length - 1]); // Pegando os acertos específicos

                        // Lembrando: o nome é tudo o que está antes da idade
                        // Usamos o StringBuilder para não precisar fazer vários objetos do tipo String para cada parte do nome
                        StringBuilder contruir_nome = new StringBuilder();
                        for (int i = 0; i < dados.length - 3; i++) { // Pega o "dados" da posição 0 até o final do nome
                            if (i > 0) { // Não queremos um espaço antes do primeiro nome, mas depois queremos para dividir o sobrenome
                                contruir_nome.append(" ");
                            }
                            contruir_nome.append(dados[i]); // Pegando parte por parte do nome que antes foi quebrado pelo método "split"
                        }
                        String nome = contruir_nome.toString(); // Convertendo o nome completo para uma String comum

                        if ((acertos_gerais > 50) || (acertos_gerais < 0)) {
                            System.out.printf("%s tem acerto geral %d que é maior que 50 ou negativo. ", nome, acertos_gerais);
                            System.out.println("Corrija seu arquivo de texto e tente novamente!");
                            return;
                        } else {
                            if ((acertos_especificos > 50) || (acertos_especificos < 0)) {
                                System.out.printf("%s tem acerto específico %d que é maior que 50 ou negativo.  ", nome, acertos_especificos);
                                System.out.println("Corrija seu arquivo de texto e tente novamente!");
                                return;
                            }
                        }

                        // Calculando a média com os dados obtidos
                        double media = (((acertos_gerais * peso_geral) + (acertos_especificos * peso_especifico)) / (peso_especifico + peso_geral));

                        // Criando um objeto do tipo Participante a partir dos dados extraídos
                        Participante participante = new Participante(nome, idade, acertos_gerais, acertos_especificos, media);
                        participantes[indice] = participante; // Atribuindo ao vetor de Participante esse novo objeto criado
                        indice++;
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter os dados para o participante: " + linha);
                    }
                }

                // Verificando se o número de participantes lidos é menor que o esperado
                if (indice < quant_participantes) {
                    System.err.println("Número de participantes lidos é menor que o esperado. Lido: " + indice + ", Esperado: " + quant_participantes);
                }

                // Filtra se houver participantes nulos
                participantes = Arrays.stream(participantes).filter(p -> p != null).toArray(Participante[]::new);

                // Chama a leitura de tempos com os dados já tratados
                leituraTempos(participantes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Chama as ordenações para ver qual é o tempo e escreve nos dois arquivos de texto
    public static void leituraTempos(Participante[] participantes) {
        long inicio_tempo, fim_tempo; // Para guardar os tempos de ordenação

        // Fazer uma cópia profunda de participantes, esse participantes_aux vai servir para o QuickSort
        Participante[] participantes_aux = new Participante[participantes.length];
        for (int i = 0; i < participantes.length; i++) {
            participantes_aux[i] = participantes[i] != null ? participantes[i].clone() : null;
        }

        // Nome do arquivo de texto que já está criado para o quick
        String caminhoArquivo_quick = "SaidaQuickSort.txt";

        // Medindo tempo de execução para o QuickSort
        inicio_tempo = System.nanoTime();
        QuickSort.quickSort(participantes_aux, 0, participantes_aux.length - 1);
        fim_tempo = System.nanoTime();

        // Tempo de execução do quick
        double tempo_execucao_quick = (fim_tempo - inicio_tempo) / 1e9;

        // Aplicando desempate no vetor de que foi ordenado pelo quick sort
        Ordenacao.desempatar(participantes_aux);

        // Recupera os passos do QuickSort
        List<String> passos1 = QuickSort.getPassos();

        // Escrevendo os passos e o tempo de execução no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_quick))) {
            for (String passo : passos1) {
                writer.write(passo);
                writer.newLine();
                writer.newLine();
            }
            writer.write("Tempo de Execução: " + tempo_execucao_quick + " Segundos");
            writer.newLine();

            // Adiciona o resultado do desempate se houver
            boolean teveEmpate = Ordenacao.verificaEmpate(participantes_aux);

            if (teveEmpate) {
                writer.write("Resultado do Desempate:");
                writer.newLine();
                for (Participante p : participantes_aux) {
                    if (p != null) {
                        writer.write(p.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Repetindo o processo para MergeSort
        Participante[] participantes_aux2 = new Participante[participantes.length];
        for (int i = 0; i < participantes.length; i++) {
            participantes_aux2[i] = participantes[i] != null ? participantes[i].clone() : null;
        }

        // Nome do arquivo de texto que já está criado para o merge
        String caminhoArquivo_merge = "SaidaMergeSort.txt";

        // Medindo tempo de execução para o MergeSort
        inicio_tempo = System.nanoTime();
        MergeSort.merge_sort(participantes, 0, participantes.length - 1);
        fim_tempo = System.nanoTime();

        // Tempo de execução
        double tempo_execucao_merge = (fim_tempo - inicio_tempo) / 1e9;

        // Aplicando desempate
        Ordenacao.desempatar(participantes);

        // Recupera os passos do MergeSort
        List<String> passos2 = MergeSort.getPassos();

        // Escreve os passos e o tempo de execução no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_merge))) {
            for (String passo : passos2) {
                writer.write(passo);
                writer.newLine();
                writer.newLine();
            }
            writer.write("Tempo de Execução: " + tempo_execucao_merge + " Segundos");
            writer.newLine();

            // Adiciona o resultado do desempate se houver
            boolean teveEmpate = Ordenacao.verificaEmpate(participantes);

            if (teveEmpate) {
                writer.write("Resultado do Desempate:");
                writer.newLine();
                for (Participante p : participantes) {
                    if (p != null) {
                        writer.write(p.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            // Para saber qual algoritmo foi mais rápido e adicionar ao final do arquivo correto
            if (tempo_execucao_quick < tempo_execucao_merge) {
                // Abrindo a saida do quick e do merge para escrever qual foi o tempo mais rapido
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_quick, true))) {
                    writer.write("O algoritmo mais rápido foi esse (QuickSort) com " + tempo_execucao_quick + " segundos " +
                            "contra os " + tempo_execucao_merge + " segundos do MergeSort");
                    writer.newLine();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_merge, true))) {
                    writer.write("O algoritmo mais rápido foi o QuickSort com " + tempo_execucao_quick + " segundos " +
                            "contra os " + tempo_execucao_merge + " segundos do MergeSort(Desse aqui)");
                    writer.newLine();
                }

            } else {

                // Abrindo a saida do quick e do merge para escrever qual foi o tempo mais rapido
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_merge, true))) {
                    writer.write("O algoritmo mais rápido foi esse (MergeSort) com " + tempo_execucao_merge + " segundos " +
                            "contra os " + tempo_execucao_quick + " segundos do QuickSort");
                    writer.newLine();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo_quick, true))) {
                    writer.write("O algoritmo mais rápido foi o MergeSort com " + tempo_execucao_merge + " segundos " +
                        "contra os " + tempo_execucao_quick + " segundos do QuickSort(Desse aqui)");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
