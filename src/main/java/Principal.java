import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Principal {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Programa de verificação de concurso. Por favor " +
                    "passe o <caminho_do_arquivo de texto>");
            return;
        }

        String caminho = args[0]; // Caminho do arquivo passado como argumento
        Participante[] participantes; // Vetor de Participantes(ainda não informado o tamanho)
        int quant_participantes; // Ao ler o arquivo vamos saber a quantidade de participantes
        double peso_geral; // Peso da nota geral
        double peso_especifico; // Peso da nota especifica

        // Abrindo o arquivo para a leitura a partir do caminho passado
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            // Lê a primeira linha com N participantes, Peso Gerais e Peso Específicos
            String linha = br.readLine();
            if (linha != null) { // Se a linha tiver alguma coisa
                String[] primeiraLinha = linha.split(" "); // Particiona a linha em partes a partir do espaço " "
                quant_participantes = Integer.parseInt(primeiraLinha[0]);// Primeira parte pega o número de participantes
                peso_geral = Double.parseDouble(primeiraLinha[1]);// Segunda parte pega o peso geral
                peso_especifico = Double.parseDouble(primeiraLinha[2]);// Terceira parte pega o peso específico

                if(peso_especifico < 1.0 || peso_especifico > 10.0){
                    System.out.printf("O peso específico é %.0f e não está entre 1 e 10 <incorreto>\n", peso_especifico);
                    System.out.println("Corrija seu arquivo de texto e tente novamente!");
                    return;
                }else{
                    if((peso_geral > peso_especifico) || (peso_geral < 1.0)){
                        System.out.printf("O peso geral é %.0f e é maior do que o específico ou é menor que 0 <incorreto>\n", peso_geral);
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
                    String[] dados = linha.split(" ");// Particiona novamente a linha a partir de espaços " "
                    if (dados.length < 4) { // Se não tiver pelo menos 4 espaços então não está no formato(nome, idade, acertos G, acertos E)
                        System.err.println("Formato de linha incorreto: " + linha);
                        System.out.println("Corrija seu arquivo de texto e tente novamente!");
                        return;
                    }

                    try {
                        // idade, acertos_gerais, acertos_especificos: vai extrair e converter os três últimos valores da linha em inteiros.
                        int idade = Integer.parseInt(dados[dados.length - 3]);// Pegando a idade
                        int acertos_gerais = Integer.parseInt(dados[dados.length - 2]);// Pegando os acertos gerais
                        int acertos_especificos = Integer.parseInt(dados[dados.length - 1]);// Pegando os acertos especificos

                        
                        // Lembrando: o nome é tudo o que está antes da idade
                        // Usamos o StringBuilder para não precisar fazer varios objetos do tipo String para cada parte do nome
                        StringBuilder contruir_nome = new StringBuilder();
                        for (int i = 0; i < dados.length - 3; i++) {// Pega o "dados" da posição 0 até o final do nome
                            if (i > 0) {// Não queremos um espaço antes do primeiro nome, mas depois queremos para dividir o sobrenome
                                contruir_nome.append(" ");
                            }
                            contruir_nome.append(dados[i]);// Pegando parte por parte do nome que antes foi quebrado pelo método "split"
                        }
                        String nome = contruir_nome.toString();// Convertendo o nome completo para uma String comum
                        
                        if((acertos_gerais > 50) || (acertos_gerais < 0)){
                            System.out.printf("%s tem acerto geral %d que é maior que 50 ou negativo. ", nome, acertos_gerais);
                            System.out.println("Corrija seu arquivo de texto e tente novamente!");
                            return;
                        }else{
                            if((acertos_especificos > 50) || (acertos_especificos < 0)){
                                System.out.printf("%s tem acerto específico %d que é maior que 50 ou negativo.  ", nome, acertos_especificos);
                                System.out.println("Corrija seu arquivo de texto e tente novamente!");
                                return;
                            }
                        }

                        double media = (((acertos_gerais*peso_geral) + (acertos_especificos*peso_especifico)) / (peso_especifico+peso_geral));
                        // Criando um objeto do tipo Participante a partir dos dados extraidos
                        Participante participante = new Participante(nome, idade, acertos_gerais, acertos_especificos,media);
                        participantes[indice] = participante;// Atribuindo ao vetor de Participante esse novo objeto criado
                        indice++;
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter os dados para o participante: " + linha);
                    }
                }

                //MergeSort.merge_sort();
                QuickSort.quickSort(participantes,0,participantes.length-1);
                // Exibe os dados para verificar
                System.out.println("Quantidade de Participantes: " + quant_participantes);
                System.out.println("Peso dos Conhecimentos Gerais: " + peso_geral);
                System.out.println("Peso dos Conhecimentos Específicos: " + peso_especifico);

                for (Participante p : participantes) {// Aqui está usando o toString da classe Participante
                    if (p != null) {
                        System.out.println(p);
                    }
                }
                Ordenacao.desempatar(participantes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
