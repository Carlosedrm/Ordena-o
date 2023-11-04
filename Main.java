import java.util.Random;
import java.util.Scanner;

public class Main {
    static int counterInteracao = 0;
    static int counterTroca = 0;

    public static void bubbleSort(int[] vetor) {
        int n = lens(vetor);
        int temp = 0;

        for (int i = 0; i < n - 1; i++) {
            counterInteracao++; // Incrementa a contagem de iterações do loop externo
            for (int j = 1; j < (n - i); j++) {
                counterInteracao++; // Incrementa a contagem de iterações
                if (vetor[j - 1] > vetor[j]) {
                    temp = vetor[j - 1];
                    vetor[j - 1] = vetor[j];
                    vetor[j] = temp;
                    counterTroca++; // Incrementa a contagem de trocas
                }
            }
        }
    }

    public static void insertSort(int[] vetor) {
        int tamanho = lens(vetor);

        for (int i = 1; i < tamanho; ++i) {
            counterInteracao++;
            int x = vetor[i];
            int j = i - 1;
            while (j >= 0 && vetor[j] > x) {
                vetor[j + 1] = vetor[j];
                j -= 1;
                counterTroca++;
            }
            vetor[j + 1] = x;
        }
    }

    public static int[] mergeSort(int[] vetor) {

        if(lens(vetor) <= 1){
            return vetor;
        }
        int[] ladoEsquerdo;
        int[] ladoDireito;

        ladoEsquerdo = splitLeft(vetor);
        ladoDireito = splitRight(vetor);

        ladoEsquerdo = mergeSort(ladoEsquerdo);
        counterInteracao++;
        ladoDireito = mergeSort(ladoDireito);
        counterInteracao++;

        return merge(ladoEsquerdo, ladoDireito);
    }

    public static int[] merge(int[] vetorEsquerdo, int[] vetorDireito) {
        int tamanhoEsquerdo = lens(vetorEsquerdo);
        int tamanhoDireito = lens(vetorDireito);
        int tamanhoTotal = tamanhoEsquerdo + tamanhoDireito;
        int[] resultado = new int[tamanhoTotal];

        int indiceEsquerdo = 0, indiceDireito = 0, indiceResultado = 0;

        while (indiceEsquerdo < tamanhoEsquerdo && indiceDireito < tamanhoDireito) {
            if (vetorEsquerdo[indiceEsquerdo] < vetorDireito[indiceDireito]) {
                resultado[indiceResultado] = vetorEsquerdo[indiceEsquerdo];
                indiceEsquerdo++;
            } else {
                resultado[indiceResultado] = vetorDireito[indiceDireito];
                indiceDireito++;
            }
            indiceResultado++;
            counterTroca++;
        }

        while (indiceEsquerdo < tamanhoEsquerdo) {
            resultado[indiceResultado] = vetorEsquerdo[indiceEsquerdo];
            indiceEsquerdo++;
            indiceResultado++;
        }

        while (indiceDireito < tamanhoDireito) {
            resultado[indiceResultado] = vetorDireito[indiceDireito];
            indiceDireito++;
            indiceResultado++;
        }

        return resultado;
    }


    public static int[] splitLeft(int[] vetor) {
        int metade = lens(vetor) / 2;
        int[] vetorEsquerdo = new int[metade];
        for(int i = 0; i < metade; i++){
            vetorEsquerdo[i] = vetor[i];
        }

        return vetorEsquerdo;
    }

    public static int[] splitRight(int[] vetor) {
        int metade = lens(vetor) / 2;
        int tamanhoDireito = lens(vetor) - metade;
        int[] vetorDireito = new int[tamanhoDireito];

        for (int i = metade; i < lens(vetor); i++) {
            vetorDireito[i - metade] = vetor[i];
        }

        return vetorDireito;
    }

    public static void shellSort(int[] vetor, int[] incrementos) {
        int incr, j, k, span, y;

        for (incr = 0; incr < lens(incrementos); incr++) { // Aqui muda o valor do incremento
            span = incrementos[incr];// Span pega o valor do elemento do array de incrementos
            for (j = span; j < lens(vetor); j++) {
                counterInteracao++; // Incrementa a contagem de iterações
                y = vetor[j];
                for (k = j - span; k >= 0 && y < vetor[k]; k -= span) { // Aqui é realizada a comparacao
                    vetor[k + span] = vetor[k];
                    counterTroca++; // Incrementa a contagem de trocas
                }
                // Insere o valor 'y' na posição correta dentro do intervalo atual (k + span)
                vetor[k + span] = y;
            }
        }
    }

    public static int[] incrementosKnuth(int tamanho) {
        int h = 1;
        int i = 0; // Inicializa um contador 'i' para o índice dos incrementos
        int[] incrementos = new int[tamanho / 2]; // Cria um array para armazenar os incrementos

        while (h < tamanho) { // Enquanto o 'h' for menor que o tamanho
            incrementos[i] = h; // Armazena o valor atual de 'h' no array de incrementos
            i++; // Incrementa o contador 'i' para o próximo índice
            h = 3 * h + 1; // Calcula o próximo valor de 'h' de acordo com a sequência de Knuth
        }

        // Array de incrementos, retira elementos indesejados, sem essa parte, incrementos teriam vários 0
        int[] resultado = new int[i]; // Cria um novo array 'resultado' com tamanho 'i'
        for (int j = 0; j < i; j++) { // Copia os valores não nulos do array 'incrementos' para 'resultado'
            resultado[j] = incrementos[j];
        }
        return resultado; // Retorna o array 'resultado' com os incrementos válidos.
    }

    public static int lens(int[] vetor){
        int count = 0;
        for(int n : vetor){
            count ++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] tamanhos = {50, 500, 1000, 5000, 10000};
        int numExecucoes = 5;

        System.out.println("Escolha qual método de ordenação você deseja utilizar:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Merge Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Shell Sort");

        Scanner scanner = new Scanner(System.in);
        int escolha = scanner.nextInt();

        for (int tamanho : tamanhos) {
            long[] temposExecucao = new long[numExecucoes]; // Array para armazenar tempos de execução
            // Zera os contadores para a próxima execução
            counterTroca = 0;
            counterInteracao = 0;

            int totalTrocas = 0; // Variável para o número total de trocas
            int totalIteracoes = 0; // Variável para o número total de iterações

            for (int seed = 1; seed <= 5; seed++) {
                Random rand = new Random(seed);
                // Zera os contadores para a próxima execução
                counterTroca = 0;
                counterInteracao = 0;

                System.out.println("Executando para tamanho " + tamanho + " e seed " + seed);

                // Cria o vetor com o tamanho determinado
                int[] random = new int[tamanho];
                for (int i = 0; i < tamanho; i++) {
                    random[i] = rand.nextInt(tamanho * 2);
                }


                // Mostramos o vetor
                for (int n : random) {
                    System.out.print(n + " ");
                }

                long startTime = System.nanoTime();

                switch (escolha) {
                    case 1 -> bubbleSort(random);
                    case 2 -> random = mergeSort(random);
                    case 3 -> insertSort(random);
                    case 4 -> {
                        int[] incrementos = incrementosKnuth(tamanho);
                        shellSort(random, incrementos);
                    }
                    default -> {
                        System.out.println("Escolha inválida.");
                        System.exit(1);
                    }
                }

                long endTime = System.nanoTime();
                long duration = endTime - startTime;


                System.out.println("\nVetor ordenado:");
                for (int n : random) {
                    System.out.print(n + " ");
                }
                

                System.out.println("\nTempo decorrido: " + duration + " nanossegundos");
                System.out.println("Número de trocas: " + counterTroca);
                System.out.println("Número de iterações: " + counterInteracao + "\n");
                totalTrocas += counterTroca; // Acumula as trocas
                totalIteracoes += counterInteracao; // Acumula as iterações

                temposExecucao[seed - 1] = duration;
            }

            double totalTempo = 0;

            for (long tempo : temposExecucao) {
                totalTempo += tempo;
            }

            double mediaTempo = totalTempo / numExecucoes;
            double mediaTrocas = (double) totalTrocas / numExecucoes;
            double mediaIteracoes = (double) totalIteracoes / numExecucoes;

            System.out.println("Média de tempo para tamanho " + tamanho + ": " + mediaTempo + " nanossegundos");
            System.out.println("Média de trocas para tamanho " + tamanho + ": " + mediaTrocas);
            System.out.println("Média de iterações para tamanho " + tamanho + ": " + mediaIteracoes);
            System.out.println("\n\n\n");
        }

    }
}