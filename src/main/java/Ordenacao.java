public class Ordenacao {

    public static void desempatar(Participante[] participantes){

        int inicio_emp = 0, fim_emp = 0;
        for(int i = 0; i < participantes.length-1; i++){
            if((participantes[i] == participantes[i+1]) && (inicio_emp != -1)){
                inicio_emp = i;
            }else{
                if((participantes[i] != participantes[i+1]) && (inicio_emp != -1)){
                    fim_emp = i-1;
                }
            }
        }

        System.out.printf("Começo do empate %d e fim do empate %d\n", inicio_emp, fim_emp);

    }

    public void aplicarRegras(Participante[] participantes, int inicio_emp, int fim_emp){

        boolean empatou;
        for(int i = inicio_emp; i <= fim_emp; i++){

        }

    }
}
