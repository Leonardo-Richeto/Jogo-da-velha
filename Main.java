import java.util.Scanner;

public class Main {
    public static void main(String args[]){

        // Instanciando as variaveis de classe scanner e tabuleiro
        Scanner scan = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro();
        
        // Inicialização das variaveis necessárias no início
        boolean vencedor = false;
        boolean jogador = true;
        int jogadaLinha, jogadaColuna;
        
        System.out.println("\n\n* BEM-VINDOS AO JOGO DA VELHA *\n");
        
        // Inicio do loop do jogo
        while(!vencedor){
            // Essa variavel acompanha as jogadas para alterar automaticamente entre os jogadores
            char simboloJogador = jogador ? 'X' : 'O';

            // Aqui temos a impressão do tabuleiro e a orientação da escolha do campo
            System.out.println("Vez do jogador " + simboloJogador + ", escolha uma linha e coluna entre 1 e 3. \n");
            tabuleiro.imprimirTabuleiro(simboloJogador);

            // Esses dois próximos loops se mantém até o usuario digitar os parâmetros corretamente para a linha e coluna, previnindo entradas inválidas
            while(true){
                System.out.println("Linha:");
                if(scan.hasNextInt()){
                    jogadaLinha = scan.nextInt() -1;
                    if(jogadaLinha >= 0 && jogadaLinha <= 2){
                        break;
                    }else{
                        System.out.println("Jogada inválida. Faça novamente.");
                    }
                }else{
                        System.out.println("Jogada inválida, apenas números são permitidos!");
                        scan.next();
                }
            }

            while(true){
                System.out.println("Coluna:");
                if(scan.hasNextInt()){
                    jogadaColuna = scan.nextInt() -1;
                    System.out.println("\n\n");
                    if(jogadaColuna >= 0 && jogadaColuna <= 2){
                        break;
                    }else{
                        System.out.println("Jogada inválida. Faça novamente.");
                    }
                }else{
                    System.out.println("Jogada inválida, apenas números são permitidos!");
                    scan.next();
                }
            }
            
            // Essa variavel e sua condicional chamam o método da classe Tabuleiro para verificar se a jogada efetuada foi realmente válida, instruindo o usuário caso necessário
            boolean jogadaValida = tabuleiro.verificaJogada(jogadaLinha, jogadaColuna);

            if(!jogadaValida){
                char campoTabuleiro = tabuleiro.getCampoTabuleiro(jogadaLinha, jogadaColuna);
                System.out.println("\n\nPosição ocupada pelo jogador " + campoTabuleiro);
                System.out.println("Refaça sua jogada!");
            }

            if(jogadaValida){
                tabuleiro.efetuaJogada(jogadaLinha, jogadaColuna, simboloJogador);
                jogador = !jogador;
            }

            // Essas duas variaveis também chamam os métodos da classe Tabuleiro para verificar a cada jogada se há um empate ou uma vitória, em seguida verificando com condicionais
            boolean empate = tabuleiro.verificaEmpate(jogadaLinha, jogadaColuna);
            boolean vitoria = tabuleiro.verificaVencedor();

            if(vitoria){
                System.out.println("****** Vitória do jogador " + simboloJogador + " ******");
                tabuleiro.imprimirTabuleiro(simboloJogador);
                vencedor = true;   
            }
            
            if(empate && !vitoria){
                System.out.println("**** Empate! O jogo terminou sem vencedores. ****");
                tabuleiro.imprimirTabuleiro(simboloJogador);
                vencedor = true;
            }

            // Quando a partida termina, essa condicional verifica se o usuário deseja iniciar outra partida ou encerrar o jogo. Também verifica a resposta do usuário previnindo entradas inválidas
            if(vencedor){
                System.out.println("\nIniciar nova partida ? (s/n)");
                char resposta = scan.next().charAt(0);

                while(resposta != 's' && resposta != 'n'){
                    System.out.println("Digite 's' para sim e 'n' para não.");
                    resposta = scan.next().charAt(0);
                }

                if(resposta == 's'){
                    vencedor = false;
                    tabuleiro = new Tabuleiro();
                }else{
                    System.out.println("\n\n** JOGO ENCERRADO **");
                    System.out.println("Obrigado por jogar! :)");
                    break;
                }
            }
        }
        scan.close();
    }
}

    // Aqui eu optei por separar a classe tabuleiro pois o como era necessário repetir algumas partes de código, fica mais limpo apenas chamar o método em vez de repetir todo o código, e também ficando mais facil para visualização e manutenção
class Tabuleiro {
    char[][] tabuleiro = {
        {'-', '-', '-'},
        {'-', '-', '-'},
        {'-', '-', '-'}
    };

        // Aqui eu preferi fazer uma variavel com as possibilidades de vitória em vez de escrever cada possibilidade na verificação
    private final int[][][] possibilidadesDeVitoria = {
        //Linhas
        {{0, 0}, {0, 1}, {0, 2}},
        {{1, 0}, {1, 1}, {1, 2}},
        {{2, 0}, {2, 1}, {2, 2}},
        //Colunas
        {{0, 0}, {1, 0}, {2, 0}},
        {{0, 1}, {1, 1}, {2, 1}},
        {{0, 2}, {1, 2}, {2, 2}},
        //Diagonais
        {{0, 0}, {1, 1}, {2, 2}},
        {{0, 2}, {1, 1}, {2, 0}},
    };

    // Métodos necessários para acessar, alterar e verificar jogadas na tabela
    public char getCampoTabuleiro(int jogadaLinha, int jogadaColuna){
        return tabuleiro[jogadaLinha][jogadaColuna];
    }

    public void efetuaJogada(int jogadaLinha, int jogadaColuna, char simboloJogador){
        tabuleiro[jogadaLinha][jogadaColuna] = simboloJogador;
    }

    public void imprimirTabuleiro(char simboloJogador){
        System.out.println("    1    2    3");
        System.out.println("1   " + tabuleiro[0][0] + " |  " + tabuleiro[0][1] + " | " + tabuleiro[0][2]);
        System.out.println("  ----+----+----");
        System.out.println("2   " + tabuleiro[1][0] + " |  " + tabuleiro[1][1] + " | " + tabuleiro[1][2]);
        System.out.println("  ----+----+----");
        System.out.println("3   " + tabuleiro[2][0] + " |  " + tabuleiro[2][1] + " | " + tabuleiro[2][2]);
    }

    public boolean verificaEmpate(int jogadaLinha, int jogadaColuna){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(tabuleiro[i][j] == '-'){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verificaVencedor(){
        for (int[][] combinacao : possibilidadesDeVitoria) {
            char primeiro = tabuleiro[combinacao[0][0]][combinacao[0][1]];
            char segundo = tabuleiro[combinacao[1][0]][combinacao[1][1]];
            char terceiro = tabuleiro[combinacao[2][0]][combinacao[2][1]];
        
            if (primeiro != '-' && primeiro == segundo && primeiro == terceiro){
                return true;
            }
        }
        return false;
    }

    public boolean verificaJogada(int jogadaLinha, int jogadaColuna){
        if(tabuleiro[jogadaLinha][jogadaColuna] == '-'){
            return true;
        }
        return false;
    }
}
