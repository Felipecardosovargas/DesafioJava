package DesafioModulo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DesafioModulo {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final List<Score> historicoPontuacoes = new ArrayList<>();
    private static final List<Score> recordes = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            try {
                exibirMenu();
                int escolha = obterEscolhaDoUsuario();

                switch (escolha) {
                    case 1:
                        iniciarNovoJogo();
                        break;
                    case 2:
                        exibirRegras();
                        break;
                    case 3:
                        exibirHistoricoPontuacoes();
                        break;
                    case 4:
                        exibirRecordes();
                        break;
                    case 5:
                        System.out.println("Obrigado por jogar!");
                        System.exit(0);
                    default:
                        System.out.println("Escolha inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                System.out.println("Por favor, tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Iniciar Novo Jogo");
        System.out.println("2. Ver Regras");
        System.out.println("3. Ver Histórico de Pontuações");
        System.out.println("4. Ver Recordes");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int obterEscolhaDoUsuario() {
        try {
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, insira um número válido.");
                scanner.next(); // Limpa a entrada inválida
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha
            return escolha;
        } catch (Exception e) {
            scanner.nextLine(); // Limpa o buffer
            System.out.println("Erro na leitura da entrada: " + e.getMessage());
            return -1; // Valor inválido para indicar erro
        }
    }

    private static void iniciarNovoJogo() {
        try {
            System.out.println("\nEscolha o modo de jogo:");
            System.out.println("1. Modo Normal");
            System.out.println("2. Modo Sequência");
            System.out.print("Escolha: ");

            int modo = obterEscolhaDoUsuario();
            if (modo == 1) {
                iniciarJogoNormal();
            } else if (modo == 2) {
                iniciarJogoSequencia();
            } else {
                System.out.println("Modo inválido. Voltando ao menu principal.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
        }
    }

    private static void iniciarJogoNormal() {
        try {
            System.out.println("\nEscolha o nível de dificuldade:");
            System.out.println("1. Fácil (1-50, 10 tentativas)");
            System.out.println("2. Médio (1-100, 7 tentativas)");
            System.out.println("3. Difícil (1-200, 5 tentativas)");
            System.out.print("Escolha: ");

            int nivel = obterEscolhaDoUsuario();
            if (nivel < 1 || nivel > 3) {
                System.out.println("Nível inválido. Voltando ao menu principal.");
                return;
            }

            NivelDificuldade dificuldade = NivelDificuldade.values()[nivel - 1];
            int numeroSecreto = random.nextInt(dificuldade.getMaximo()) + 1;
            int tentativasRestantes = dificuldade.getTentativas();
            int pontuacao = dificuldade.getPontuacaoBase();

            System.out.println("\nJogo iniciado! Tente adivinhar o número.");

            while (tentativasRestantes > 0) {
                System.out.print("Tentativas restantes: " + tentativasRestantes + ". Insira seu palpite: ");
                int palpite = obterEscolhaDoUsuario();

                if (palpite == numeroSecreto) {
                    pontuacao += (tentativasRestantes - 1) * 50;
                    System.out.println("Parabéns! Você acertou o número " + numeroSecreto + "!");
                    System.out.println("Sua pontuação: " + pontuacao);
                    adicionarAoHistorico(new Score(dificuldade, pontuacao));
                    atualizarRecorde(new Score(dificuldade, pontuacao));
                    return;
                } else {
                    pontuacao -= 10;
                    tentativasRestantes--;

                    if (tentativasRestantes > 0) {
                        System.out.println("Palpite incorreto. " + obterDica(numeroSecreto, palpite));
                        System.out.println("Deseja uma dica? (Custo: 10 pontos)");
                        System.out.print("1. Sim / 2. Não: ");
                        int escolhaDica = obterEscolhaDoUsuario();

                        if (escolhaDica == 1) {
                            pontuacao -= 10;
                            System.out.println(obterDica(numeroSecreto, palpite));
                        }
                    }
                }
            }

            System.out.println("Suas tentativas acabaram. O número secreto era " + numeroSecreto + ".");
            System.out.println("Sua pontuação: " + pontuacao);
            adicionarAoHistorico(new Score(dificuldade, pontuacao));
            atualizarRecorde(new Score(dificuldade, pontuacao));
        } catch (Exception e) {
            System.out.println("Erro durante o jogo normal: " + e.getMessage());
        }
    }

    private static void iniciarJogoSequencia() {
        try {
            System.out.println("\nModo Sequência: Adivinhe uma sequência de 3 números.");
            System.out.println("Escolha o nível de dificuldade:");
            System.out.println("1. Fácil (1-50, 10 tentativas)");
            System.out.println("2. Médio (1-100, 7 tentativas)");
            System.out.println("3. Difícil (1-200, 5 tentativas)");
            System.out.print("Escolha: ");

            int nivel = obterEscolhaDoUsuario();
            if (nivel < 1 || nivel > 3) {
                System.out.println("Nível inválido. Voltando ao menu principal.");
                return;
            }

            NivelDificuldade dificuldade = NivelDificuldade.values()[nivel - 1];
            List<Integer> sequenciaSecreta = gerarSequencia(dificuldade.getMaximo());
            int tentativasRestantes = dificuldade.getTentativas();
            int pontuacao = dificuldade.getPontuacaoBase();

            System.out.println("\nJogo iniciado! Tente adivinhar a sequência.");

            for (int i = 0; i < 3; i++) {
                int numeroSecreto = sequenciaSecreta.get(i);

                while (tentativasRestantes > 0) {
                    System.out.print("Tentativas restantes: " + tentativasRestantes + ". Insira seu palpite para o número " + (i + 1) + ": ");
                    int palpite = obterEscolhaDoUsuario();

                    if (palpite == numeroSecreto) {
                        System.out.println("Parabéns! Você acertou o número " + numeroSecreto + "!");
                        break;
                    } else {
                        pontuacao -= 10;
                        tentativasRestantes--;

                        if (tentativasRestantes > 0) {
                            System.out.println("Palpite incorreto. " + obterDica(numeroSecreto, palpite));
                            System.out.println("Deseja uma dica? (Custo: 10 pontos)");
                            System.out.print("1. Sim / 2. Não: ");
                            int escolhaDica = obterEscolhaDoUsuario();

                            if (escolhaDica == 1) {
                                pontuacao -= 10;
                                System.out.println(obterDica(numeroSecreto, palpite));
                            }
                        } else {
                            System.out.println("Suas tentativas acabaram para este número. O número era " + numeroSecreto + ".");
                            break;
                        }
                    }
                }

                if (tentativasRestantes <= 0) {
                    break;
                }
            }

            if (tentativasRestantes > 0) {
                pontuacao += tentativasRestantes * 50;
                System.out.println("Parabéns! Você adivinhou toda a sequência!");
            } else {
                System.out.println("Suas tentativas acabaram. A sequência era " + sequenciaSecreta + ".");
            }

            System.out.println("Sua pontuação: " + pontuacao);
            adicionarAoHistorico(new Score(dificuldade, pontuacao));
            atualizarRecorde(new Score(dificuldade, pontuacao));
        } catch (Exception e) {
            System.out.println("Erro durante o jogo de sequência: " + e.getMessage());
        }
    }

    // Os métodos restantes permanecem iguais, pois não lidam diretamente com entrada de usuário
    private static List<Integer> gerarSequencia(int maximo) {
        List<Integer> sequencia = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sequencia.add(random.nextInt(maximo) + 1);
        }
        return sequencia;
    }

    private static String obterDica(int numeroSecreto, int palpite) {
        if (palpite < numeroSecreto) {
            return "O número secreto é maior.";
        } else {
            return "O número secreto é menor.";
        }
    }

    private static void exibirRegras() {
        System.out.println("\nRegras do Jogo:");
        System.out.println("1. Escolha um modo de jogo (Normal ou Sequência).");
        System.out.println("2. Escolha um nível de dificuldade.");
        System.out.println("3. Tente adivinhar o número secreto dentro do limite de tentativas.");
        System.out.println("4. A pontuação é calculada com base no nível de dificuldade e número de tentativas usadas.");
        System.out.println("5. Você pode usar dicas durante o jogo, mas isso custará pontos.");
        System.out.println("6. Você pode ver o histórico de pontuações anteriores e os recordes para cada nível.");
    }

    private static void exibirHistoricoPontuacoes() {
        System.out.println("\nHistórico de Pontuações:");
        for (int i = 0; i < Math.min(10, historicoPontuacoes.size()); i++) {
            Score score = historicoPontuacoes.get(i);
            System.out.println((i + 1) + ". Nível: " + score.getNivel() + ", Pontuação: " + score.getPontuacao());
        }
    }

    private static void exibirRecordes() {
        System.out.println("\nRecordes:");
        for (NivelDificuldade nivel : NivelDificuldade.values()) {
            Score recorde = encontrarRecorde(nivel);
            if (recorde != null) {
                System.out.println(nivel + ": " + recorde.getPontuacao());
            } else {
                System.out.println(nivel + ": Sem recorde ainda.");
            }
        }
    }

    private static void adicionarAoHistorico(Score score) {
        historicoPontuacoes.add(0, score);
        if (historicoPontuacoes.size() > 10) {
            historicoPontuacoes.remove(historicoPontuacoes.size() - 1);
        }
    }

    private static void atualizarRecorde(Score score) {
        Score recordeAtual = encontrarRecorde(score.getNivel());
        if (recordeAtual == null || score.getPontuacao() > recordeAtual.getPontuacao()) {
            removerRecorde(score.getNivel());
            recordes.add(score);
        }
    }

    private static Score encontrarRecorde(NivelDificuldade nivel) {
        for (Score score : recordes) {
            if (score.getNivel() == nivel) {
                return score;
            }
        }
        return null;
    }

    private static void removerRecorde(NivelDificuldade nivel) {
        recordes.removeIf(score -> score.getNivel() == nivel);
    }

    private enum NivelDificuldade {
        FACIL(50, 10, 100),
        MEDIO(100, 7, 200),
        DIFICIL(200, 5, 300);

        private final int maximo;
        private final int tentativas;
        private final int pontuacaoBase;

        NivelDificuldade(int maximo, int tentativas, int pontuacaoBase) {
            this.maximo = maximo;
            this.tentativas = tentativas;
            this.pontuacaoBase = pontuacaoBase;
        }

        public int getMaximo() {
            return maximo;
        }

        public int getTentativas() {
            return tentativas;
        }

        public int getPontuacaoBase() {
            return pontuacaoBase;
        }
    }

    private static class Score {
        private final NivelDificuldade nivel;
        private final int pontuacao;

        public Score(NivelDificuldade nivel, int pontuacao) {
            this.nivel = nivel;
            this.pontuacao = pontuacao;
        }

        public NivelDificuldade getNivel() {
            return nivel;
        }

        public int getPontuacao() {
            return pontuacao;
        }
    }
}